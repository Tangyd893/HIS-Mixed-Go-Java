package main

import (
	"fmt"
	"log"
	"net"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/internal/registration/handler"
	"github.com/his-mixed/go/internal/registration/repository"
	"github.com/his-mixed/go/internal/registration/service"
	"github.com/his-mixed/go/pkg/config"
	"github.com/his-mixed/go/pkg/database"
	pb "github.com/his-mixed/go/pkg/grpc/registration"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
	"google.golang.org/grpc"
	"gorm.io/gorm"
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

	// 启动HTTP服务
	go startHTTPServer(cfg.Server.HTTPPort)

	// 启动gRPC服务
	startGRPCServer(cfg.Server.GRPCPort, db)
}

func startHTTPServer(port int) {
	r := gin.New()
	r.Use(middleware.Recovery())
	r.Use(middleware.RequestID())
	r.Use(middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)

	log.Printf("挂号服务 HTTP 启动在端口 %d", port)
	if err := r.Run(fmt.Sprintf(":%d", port)); err != nil {
		log.Printf("HTTP 服务启动失败: %v", err)
	}
}

func startGRPCServer(port int, db *gorm.DB) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("gRPC 监听失败: %v", err)
	}
	s := grpc.NewServer()

	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)
	h := handler.NewRegistrationHandler(svc)

	pb.RegisterRegistrationServiceServer(s, h)

	log.Printf("挂号服务 gRPC 启动在端口 %d", port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("gRPC 服务启动失败: %v", err)
	}
}
