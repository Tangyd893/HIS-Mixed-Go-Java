package main

import (
	"fmt"
	"log"
	"net"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/internal/schedule/handler"
	"github.com/his-mixed/go/internal/schedule/repository"
	"github.com/his-mixed/go/internal/schedule/service"
	"github.com/his-mixed/go/pkg/config"
	"github.com/his-mixed/go/pkg/database"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
	pb "github.com/his-mixed/go/pkg/grpc/schedule"
	"google.golang.org/grpc"
	"gorm.io/gorm"
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

	go startHTTP(cfg.Server.HTTPPort)

	startGRPC(cfg.Server.GRPCPort, db)
}

func startHTTP(port int) {
	r := gin.New()
	r.Use(middleware.Recovery(), middleware.RequestID(), middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)
	log.Printf("排班服务 HTTP 启动在端口 %d", port)
	if err := r.Run(fmt.Sprintf(":%d", port)); err != nil {
		log.Printf("HTTP 启动失败: %v", err)
	}
}

func startGRPC(port int, db *gorm.DB) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("监听失败: %v", err)
	}
	s := grpc.NewServer()

	repo := repository.NewScheduleRepository(db)
	svc := service.NewScheduleService(repo)
	h := handler.NewScheduleHandler(svc)

	pb.RegisterScheduleServiceServer(s, h)

	log.Printf("排班服务 gRPC 启动在端口 %d", port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("gRPC 失败: %v", err)
	}
}
