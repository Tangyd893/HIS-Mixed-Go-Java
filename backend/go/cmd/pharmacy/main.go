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
	"github.com/his-mixed/go/internal/pharmacy/handler"
	"github.com/his-mixed/go/internal/pharmacy/repository"
	"github.com/his-mixed/go/internal/pharmacy/service"
	"github.com/his-mixed/go/pkg/config"
	"github.com/his-mixed/go/pkg/database"
	pb "github.com/his-mixed/go/pkg/grpc/pharmacy"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
	"github.com/his-mixed/go/pkg/response"
	"google.golang.org/grpc"
)

func main() {
	fmt.Println("=== HIS 药房管理服务启动 ===")

	// 加载配置
	cfg, err := config.Load("configs/pharmacy.yaml")
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

	// 初始化业务组件
	repo := repository.NewPharmacyRepository(db)
	svc := service.NewPharmacyService(repo)
	h := handler.NewPharmacyHandler(svc)

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
	log.Println("药房管理服务已关闭")
}

func startHTTPServer(port int, svc *service.PharmacyService) *http.Server {
	r := gin.New()
	r.Use(middleware.Recovery(), middleware.RequestID(), middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)

	// HTTP 业务端点
	r.GET("/api/pharmacy/drugs", func(c *gin.Context) {
		keyword := c.Query("keyword")
		page := 1
		size := 10
		fmt.Sscanf(c.DefaultQuery("page", "1"), "%d", &page)
		fmt.Sscanf(c.DefaultQuery("pageSize", "10"), "%d", &size)
		drugs, total, err := svc.ListDrugs(keyword, page, size)
		if err != nil {
			response.Error(c, 500, 50001)
			return
		}
		response.Success(c, gin.H{"list": drugs, "total": total})
	})

	r.GET("/api/pharmacy/drugs/:id", func(c *gin.Context) {
		var id int64
		fmt.Sscanf(c.Param("id"), "%d", &id)
		drug, err := svc.GetDrugByID(id)
		if err != nil {
			response.Error(c, 404, 40401)
			return
		}
		response.Success(c, drug)
	})

	r.GET("/api/pharmacy/inventory/:drugId", func(c *gin.Context) {
		var drugID int64
		fmt.Sscanf(c.Param("drugId"), "%d", &drugID)
		inventory, err := svc.GetDrugInventory(drugID)
		if err != nil {
			response.Error(c, 500, 50002)
			return
		}
		response.Success(c, inventory)
	})

	srv := &http.Server{Addr: fmt.Sprintf(":%d", port), Handler: r}
	go func() {
		log.Printf("药房服务 HTTP 启动在端口 %d", port)
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Printf("HTTP 服务启动失败: %v", err)
		}
	}()
	return srv
}

func startGRPCServer(port int, h *handler.PharmacyHandler) (*grpc.Server, net.Listener) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("gRPC 监听失败: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterPharmacyServiceServer(s, h)

	go func() {
		log.Printf("药房服务 gRPC 启动在端口 %d", port)
		if err := s.Serve(lis); err != nil {
			log.Printf("gRPC 服务启动失败: %v", err)
		}
	}()
	return s, lis
}
