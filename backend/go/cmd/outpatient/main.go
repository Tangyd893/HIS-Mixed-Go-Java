package main

import (
	"fmt"
	"log"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
)

func main() {
	fmt.Println("=== HIS 院外患者服务启动 ===")
	go startHTTP(8091)
	select {}
}

func startHTTP(port int) {
	r := gin.New()
	r.Use(middleware.Recovery(), middleware.RequestID(), middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)
	log.Printf("院外服务 HTTP 启动在端口 %d\n", port)
	if err := r.Run(fmt.Sprintf(":%d", port)); err != nil {
		log.Printf("HTTP 启动失败: %v", err)
	}
}
