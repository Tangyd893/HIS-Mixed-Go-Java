package main

import (
	"context"
	"fmt"
	"log"
	"net"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/internal/schedule/handler"
	"github.com/his-mixed/go/internal/schedule/model"
	"github.com/his-mixed/go/internal/schedule/repository"
	"github.com/his-mixed/go/internal/schedule/service"
	"github.com/his-mixed/go/pkg/config"
	"github.com/his-mixed/go/pkg/database"
	pb "github.com/his-mixed/go/pkg/grpc/schedule"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
	"github.com/his-mixed/go/pkg/response"
	"google.golang.org/grpc"
)

func main() {
	fmt.Println("=== HIS 排班管理服务启动 ===")

	cfg, err := config.Load("configs/schedule.yaml")
	if err != nil {
		log.Fatalf("加载配置失败: %v", err)
	}

	db, err := database.Connect(
		cfg.Database.Host,
		cfg.Database.Port,
		cfg.Database.User,
		cfg.Database.Password,
		cfg.Database.DBName,
		cfg.Database.SSLMode,
	)
	if err != nil {
		log.Fatalf("连接数据库失败: %v", err)
	}
	defer database.Close()

	_, err = redis.Connect(cfg.Redis.Addr, cfg.Redis.Password, cfg.Redis.DB)
	if err != nil {
		log.Fatalf("连接Redis失败: %v", err)
	}

	// 初始化业务组件
	repo := repository.NewScheduleRepository(db)
	svc := service.NewScheduleService(repo)
	h := handler.NewScheduleHandler(svc)

	// 启动HTTP服务
	httpSrv := startHTTPServer(cfg.Server.HTTPPort, svc)

	// 启动gRPC服务
	grpcSrv, grpcLis := startGRPCServer(cfg.Server.GRPCPort, h)

	// 优雅关闭
	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
	log.Println("收到关闭信号，开始优雅关闭...")

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	if err := httpSrv.Shutdown(ctx); err != nil {
		log.Printf("HTTP 服务关闭失败: %v", err)
	}
	grpcSrv.GracefulStop()
	grpcLis.Close()
	log.Println("排班管理服务已关闭")
}

func startHTTPServer(port int, svc *service.ScheduleService) *http.Server {
	r := gin.New()
	r.Use(middleware.Recovery(), middleware.RequestID(), middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)

	// HTTP 业务端点
	r.GET("/api/schedule/plans", func(c *gin.Context) {
		var doctorID, departmentID int64
		page := 1
		size := 10
		fmt.Sscanf(c.DefaultQuery("doctorId", "0"), "%d", &doctorID)
		fmt.Sscanf(c.DefaultQuery("departmentId", "0"), "%d", &departmentID)
		fmt.Sscanf(c.DefaultQuery("page", "1"), "%d", &page)
		fmt.Sscanf(c.DefaultQuery("pageSize", "10"), "%d", &size)
		plans, total, err := svc.ListSchedulePlans(doctorID, departmentID, page, size)
		if err != nil {
			response.Error(c, 500, 50001)
			return
		}
		response.Success(c, gin.H{"list": plans, "total": total})
	})

	r.GET("/api/schedule/slots", func(c *gin.Context) {
		departmentID := c.Query("departmentId")
		date := c.DefaultQuery("date", "")
		var did int64
		fmt.Sscanf(departmentID, "%d", &did)
		slots, err := svc.GetScheduleSlots(did, date)
		if err != nil {
			response.Error(c, 500, 50002)
			return
		}
		response.Success(c, slots)
	})

	r.POST("/api/schedule/plans", func(c *gin.Context) {
		var plan model.SchedulePlan
		if err := c.ShouldBindJSON(&plan); err != nil {
			response.Error(c, 400, 40001)
			return
		}
		if err := svc.CreateSchedulePlan(&plan); err != nil {
			response.Error(c, 500, 50003)
			return
		}
		response.Success(c, plan)
	})

	srv := &http.Server{Addr: fmt.Sprintf(":%d", port), Handler: r}
	go func() {
		log.Printf("排班服务 HTTP 启动在端口 %d", port)
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Printf("HTTP 服务启动失败: %v", err)
		}
	}()
	return srv
}

func startGRPCServer(port int, h *handler.ScheduleHandler) (*grpc.Server, net.Listener) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("gRPC 监听失败: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterScheduleServiceServer(s, h)

	go func() {
		log.Printf("排班服务 gRPC 启动在端口 %d", port)
		if err := s.Serve(lis); err != nil {
			log.Printf("gRPC 服务启动失败: %v", err)
		}
	}()
	return s, lis
}
