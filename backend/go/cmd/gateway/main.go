package main

import (
	"context"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"

	"github.com/gin-gonic/gin"
	gwconfig "github.com/his-mixed/go/internal/gateway/config"
	"github.com/his-mixed/go/internal/gateway/handler"
	"github.com/his-mixed/go/internal/gateway/router"
	"github.com/his-mixed/go/pkg/middleware"
)

func main() {
	fmt.Println("=== HIS Gateway 启动 ===")

	cfg, err := gwconfig.Load("configs/gateway.yaml")
	if err != nil {
		log.Printf("加载配置失败，使用默认配置: %v", err)
		cfg = &gwconfig.GatewayConfig{}
		cfg.Server.HTTPPort = 8080
		cfg.Routes = map[string]string{
			"auth": "http://localhost:8081", "user": "http://localhost:8082",
			"registration": "http://localhost:8083", "clinic": "http://localhost:8084",
			"prescription": "http://localhost:8085", "billing": "http://localhost:8086",
			"pharmacy": "http://localhost:8087", "examination": "http://localhost:8088",
			"inpatient": "http://localhost:8089", "schedule": "http://localhost:8090",
			"outpatient": "http://localhost:8091", "followup": "http://localhost:8092",
			"health-record": "http://localhost:8093", "notification": "http://localhost:8094",
			"statistics": "http://localhost:8095", "system": "http://localhost:8096",
			"emr": "http://localhost:8097",
		}
	}

	proxies := handler.InitProxies(cfg.Routes)

	r := gin.Default()
	limiter := middleware.NewRateLimiter()

	r.Use(middleware.CORS())
	r.Use(middleware.RequestID())
	r.Use(middleware.Recovery())
	r.Use(middleware.Logger())
	r.Use(limiter.Allow("global", 100, 1))
	r.Use(middleware.Auth())

	router.SetupRouter(r, proxies)

	srv := &http.Server{Addr: fmt.Sprintf(":%d", cfg.Server.HTTPPort), Handler: r}
	go func() {
		log.Printf("Gateway 启动在端口 %d", cfg.Server.HTTPPort)
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatalf("Gateway 启动失败: %v", err)
		}
	}()

	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
	log.Println("收到关闭信号，开始优雅关闭...")
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	if err := srv.Shutdown(ctx); err != nil {
		log.Printf("Gateway 关闭失败: %v", err)
	}
	log.Println("Gateway 已关闭")
}
