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
	fmt.Println("=== HIS 挂号预约服务启动 ===")

	go startHTTPServer()
	startGRPCServer()
}

func startHTTPServer() {
	r := gin.New()
	r.Use(middleware.Recovery())
	r.Use(middleware.RequestID())
	r.Use(middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)

	log.Println("挂号服务 HTTP 启动在端口 8083")
	if err := r.Run(":8083"); err != nil {
		log.Printf("HTTP 服务启动失败: %v", err)
	}
}

func startGRPCServer() {
	lis, err := net.Listen("tcp", ":9083")
	if err != nil {
		log.Fatalf("gRPC 监听失败: %v", err)
	}
	s := grpc.NewServer()
	log.Println("挂号服务 gRPC 启动在端口 9083")
	if err := s.Serve(lis); err != nil {
		log.Fatalf("gRPC 服务启动失败: %v", err)
	}
}
