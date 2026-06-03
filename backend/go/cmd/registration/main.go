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
	"github.com/his-mixed/go/internal/registration/handler"
	"github.com/his-mixed/go/internal/registration/model"
	"github.com/his-mixed/go/internal/registration/repository"
	"github.com/his-mixed/go/internal/registration/service"
	"github.com/his-mixed/go/pkg/config"
	"github.com/his-mixed/go/pkg/database"
	pb "github.com/his-mixed/go/pkg/grpc/registration"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
	"github.com/his-mixed/go/pkg/response"
	"google.golang.org/grpc"
)

func main() {
	fmt.Println("=== HIS 挂号预约服务启动 ===")

	// 加载配置
	cfg, err := config.Load("configs/registration.yaml")
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

	// 连接Redis（返回值赋给全局 redis.Client）
	_, err = redis.Connect(cfg.Redis.Addr, cfg.Redis.Password, cfg.Redis.DB)
	if err != nil {
		log.Fatalf("连接Redis失败: %v", err)
	}

	// 初始化业务组件
	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)
	h := handler.NewRegistrationHandler(svc)

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
	log.Println("挂号预约服务已关闭")
}

func startHTTPServer(port int, svc *service.RegistrationService) *http.Server {
	r := gin.New()
	r.Use(middleware.Recovery())
	r.Use(middleware.RequestID())
	r.Use(middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)

	// HTTP 业务端点
	r.POST("/api/registration/appointments", func(c *gin.Context) {
		var req struct {
			PatientID  int64  `json:"patientId"`
			ScheduleID int64  `json:"scheduleId"`
			DoctorID   int64  `json:"doctorId"`
			VisitDate  string `json:"visitDate"`
			CardType   string `json:"cardType"`
			Complaint  string `json:"complaint"`
		}
		if err := c.ShouldBindJSON(&req); err != nil {
			response.Error(c, 400, 40001)
			return
		}
		visitDate, _ := time.Parse("2006-01-02", req.VisitDate)
		reg := &model.Registration{
			PatientID:        req.PatientID,
			ScheduleID:       req.ScheduleID,
			DoctorID:         req.DoctorID,
			RegistrationType: req.CardType,
			Status:           "PENDING",
			Symptom:          req.Complaint,
			RegisterDate:     visitDate,
			CreatedAt:        time.Now(),
			UpdatedAt:        time.Now(),
		}
		if err := svc.CreateRegistration(reg); err != nil {
			response.Error(c, 500, 50001)
			return
		}
		response.Success(c, gin.H{
			"appointmentId": reg.ID,
			"serialNumber":  fmt.Sprintf("REG%06d", reg.ID),
			"status":        reg.Status,
		})
	})

	r.GET("/api/registration/appointments", func(c *gin.Context) {
		patientID := c.Query("patientId")
		page := c.DefaultQuery("page", "1")
		pageSize := c.DefaultQuery("pageSize", "10")
		var pid int64
		var p, ps int
		fmt.Sscanf(patientID, "%d", &pid)
		fmt.Sscanf(page, "%d", &p)
		fmt.Sscanf(pageSize, "%d", &ps)
		regs, total, err := svc.ListRegistrations(pid, "", p, ps)
		if err != nil {
			response.Error(c, 500, 50002)
			return
		}
		response.Success(c, gin.H{"list": regs, "total": total})
	})

	srv := &http.Server{Addr: fmt.Sprintf(":%d", port), Handler: r}
	go func() {
		log.Printf("挂号服务 HTTP 启动在端口 %d", port)
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Printf("HTTP 服务启动失败: %v", err)
		}
	}()
	return srv
}

func startGRPCServer(port int, h *handler.RegistrationHandler) (*grpc.Server, net.Listener) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("gRPC 监听失败: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterRegistrationServiceServer(s, h)

	go func() {
		log.Printf("挂号服务 gRPC 启动在端口 %d", port)
		if err := s.Serve(lis); err != nil {
			log.Printf("gRPC 服务启动失败: %v", err)
		}
	}()
	return s, lis
}
