package main

import (
	"fmt"
	"log"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/internal/gateway/router"
	"github.com/his-mixed/go/pkg/middleware"
)

func main() {
	fmt.Println("=== HIS Gateway 启动 ===")

	r := gin.Default()
	r.Use(middleware.CORS())
	r.Use(middleware.RequestID())
	r.Use(middleware.Recovery())
	r.Use(middleware.Logger())
	r.Use(middleware.Auth())

	router.SetupRouter(r)

	log.Println("Gateway 启动在端口 8080")
	if err := r.Run(":8080"); err != nil {
		log.Fatalf("Gateway 启动失败: %v", err)
	}
}
