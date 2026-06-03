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
	"github.com/his-mixed/go/internal/followup/handler"
	"github.com/his-mixed/go/internal/followup/repository"
	"github.com/his-mixed/go/internal/followup/service"
	"github.com/his-mixed/go/pkg/config"
	"github.com/his-mixed/go/pkg/database"
	pb "github.com/his-mixed/go/pkg/grpc/followup"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
	"google.golang.org/grpc"
)

func main() {
	fmt.Println("=== HIS 随访管理服务启动 ===")

	// 加载配置
	cfg, err := config.Load("configs/followup.yaml")
	if err != nil {
		log.Fatalf("加载配置失败: %v", err)
	}

	// 连接数据库
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

	// 连接Redis
	_, err = redis.Connect(cfg.Redis.Addr, cfg.Redis.Password, cfg.Redis.DB)
	if err != nil {
		log.Fatalf("连接Redis失败: %v", err)
	}

	// 初始化业务组件
	repo := repository.NewFollowupRepository(db)
	svc := service.NewFollowupService(repo)
	h := handler.NewFollowupHandler(svc)

	// 启动HTTP服务
	httpSrv := startHTTPServer(cfg.Server.HTTPPort)

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
	log.Println("随访管理服务已关闭")
}

func startHTTPServer(port int) *http.Server {
	r := gin.New()
	r.Use(middleware.Recovery())
	r.Use(middleware.RequestID())
	r.Use(middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)

	// 初始化业务组件
	cfg, _ := config.Load("configs/followup.yaml")
	db, _ := database.Connect(cfg.Database.Host, cfg.Database.Port, cfg.Database.User, cfg.Database.Password, cfg.Database.DBName, cfg.Database.SSLMode)
	repo := repository.NewFollowupRepository(db)
	svc := service.NewFollowupService(repo)

	// HTTP 业务端点
	r.GET("/api/followup/plans", func(c *gin.Context) {
		patientID := c.Query("patientId")
		page := c.DefaultQuery("page", "1")
		pageSize := c.DefaultQuery("pageSize", "10")
		var pid int64
		var p, ps int
		fmt.Sscanf(patientID, "%d", &pid)
		fmt.Sscanf(page, "%d", &p)
		fmt.Sscanf(pageSize, "%d", &ps)
		plans, total, err := svc.ListFollowupPlansByPatientID(pid, p, ps)
		if err != nil {
			c.JSON(500, gin.H{"code": 500, "message": "查询失败"})
			return
		}
		c.JSON(200, gin.H{"list": plans, "total": total})
	})

	r.GET("/api/followup/plans/:id", func(c *gin.Context) {
		var id int64
		fmt.Sscanf(c.Param("id"), "%d", &id)
		plan, err := svc.GetFollowupPlanByID(id)
		if err != nil {
			c.JSON(404, gin.H{"code": 404, "message": "计划不存在"})
			return
		}
		c.JSON(200, plan)
	})

	r.GET("/api/followup/plans/patient/:patientId", func(c *gin.Context) {
		var patientID int64
		fmt.Sscanf(c.Param("patientId"), "%d", &patientID)
		plans, _, err := svc.ListFollowupPlansByPatientID(patientID, 1, 100)
		if err != nil {
			c.JSON(500, gin.H{"code": 500, "message": "查询失败"})
			return
		}
		c.JSON(200, plans)
	})

	r.GET("/api/followup/records/plan/:planId", func(c *gin.Context) {
		var planID int64
		fmt.Sscanf(c.Param("planId"), "%d", &planID)
		records, err := svc.GetFollowupRecords(planID, 0)
		if err != nil {
			c.JSON(500, gin.H{"code": 500, "message": "查询失败"})
			return
		}
		c.JSON(200, records)
	})

	srv := &http.Server{Addr: fmt.Sprintf(":%d", port), Handler: r}
	go func() {
		log.Printf("随访服务 HTTP 启动在端口 %d", port)
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Printf("HTTP 服务启动失败: %v", err)
		}
	}()
	return srv
}

func startGRPCServer(port int, h *handler.FollowupHandler) (*grpc.Server, net.Listener) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("gRPC 监听失败: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterFollowupServiceServer(s, h)

	go func() {
		log.Printf("随访服务 gRPC 启动在端口 %d", port)
		if err := s.Serve(lis); err != nil {
			log.Printf("gRPC 服务启动失败: %v", err)
		}
	}()
	return s, lis
}
