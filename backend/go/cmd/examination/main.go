package main

import (
	"fmt"
	"log"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
)

func main() {
	fmt.Printf("=== HIS %s 服务启动 ===\n", "检查检验")
	go startHTTP(8088)
	select {}
}

func startHTTP(port int) {
	r := gin.New()
	r.Use(middleware.Recovery(), middleware.RequestID(), middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)
	log.Printf("检查服务 HTTP 启动在端口 %d\n", port)
	if err := r.Run(fmt.Sprintf(":%d", port)); err != nil {
		log.Printf("HTTP 启动失败: %v", err)
	}
}
