package main

import (
	"fmt"
	"log"
	"net"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"google.golang.org/grpc"
)

func main() {
	fmt.Println("=== HIS 药房管理服务启动 ===")
	go startHTTP(8087)
	startGRPC(9087)
}

func startHTTP(port int) {
	r := gin.New()
	r.Use(middleware.Recovery(), middleware.RequestID(), middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)
	log.Printf("药房服务 HTTP 启动在端口 %d", port)
	if err := r.Run(fmt.Sprintf(":%d", port)); err != nil {
		log.Printf("HTTP 启动失败: %v", err)
	}
}

func startGRPC(port int) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("监听失败: %v", err)
	}
	s := grpc.NewServer()
	log.Printf("药房服务 gRPC 启动在端口 %d", port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("gRPC 失败: %v", err)
	}
}
