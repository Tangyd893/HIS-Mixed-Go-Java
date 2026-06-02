package main

import (
	"fmt"
	"log"
	"net"

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
	"gorm.io/gorm"
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

	log.Printf("随访服务 HTTP 启动在端口 %d", port)
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

	repo := repository.NewFollowupRepository(db)
	svc := service.NewFollowupService(repo)
	h := handler.NewFollowupHandler(svc)

	pb.RegisterFollowupServiceServer(s, h)

	log.Printf("随访服务 gRPC 启动在端口 %d", port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("gRPC 服务启动失败: %v", err)
	}
}
