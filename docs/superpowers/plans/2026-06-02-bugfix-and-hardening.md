# HIS-Mixed 项目 Bug 修复与加固实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复项目中已知的关键 Bug，添加生产必备的优雅关闭和配置加载能力，补充核心业务层单元测试，启用限流中间件。

**Architecture:** 按优先级分 5 个任务组：(1) 修复关键 Bug (2) 优雅关闭 (3) Gateway 配置加载 (4) 业务层测试 (5) 限流启用。每个任务组独立可验证，按顺序执行。硬件资源有限，按需启停 Docker 服务。

**Tech Stack:** Go 1.22+, Gin, gRPC, GORM, Viper, PostgreSQL, Redis, testify

---

## Task 1: 修复 RequestID 碰撞 Bug

**问题：** `generateID()` 使用 `time.Now().Format("20060102150405")` 生成 ID，同一秒内的请求会产生相同 ID。

**Files:**
- Modify: `backend/go/pkg/middleware/middleware.go:72-74`
- Modify: `backend/go/pkg/middleware/middleware_test.go` (添加并发唯一性测试)

- [ ] **Step 1: 添加 `crypto/rand` 导入并修改 `generateID()` 函数**

将 `backend/go/pkg/middleware/middleware.go` 中的 `generateID()` 替换为：

```go
func generateID() string {
	b := make([]byte, 8)
	_, _ = rand.Read(b)
	return "req-" + hex.EncodeToString(b)
}
```

同时在文件头部 import 中添加：

```go
import (
	"crypto/rand"
	"encoding/hex"
	"log"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)
```

- [ ] **Step 2: 运行现有测试确认不破坏已有功能**

```bash
cd backend/go && go test ./pkg/middleware/ -v -run "TestRequestID|TestAuth|TestCORS|TestRecovery"
```

Expected: 所有 4 个测试 PASS

- [ ] **Step 3: 添加并发唯一性测试**

在 `backend/go/pkg/middleware/middleware_test.go` 末尾添加：

```go
func TestRequestIDUniqueness(t *testing.T) {
	r := setupRouter(middleware.RequestID())
	r.GET("/api/test", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{})
	})

	ids := make(map[string]bool)
	for i := 0; i < 100; i++ {
		w := httptest.NewRecorder()
		req, _ := http.NewRequest(http.MethodGet, "/api/test", nil)
		r.ServeHTTP(w, req)
		id := w.Header().Get("X-Request-Id")
		assert.NotEmpty(t, id)
		assert.False(t, ids[id], "重复的 RequestID: %s", id)
		ids[id] = true
	}
}
```

- [ ] **Step 4: 运行全部中间件测试**

```bash
cd backend/go && go test ./pkg/middleware/ -v
```

Expected: 全部 PASS（含新增的 TestRequestIDUniqueness）

---

## Task 2: 修复 Statistics GetTrendData 类型断言 panic

**问题：** `handler/statistics.go:73` 中 `d["value"].(float64)` 对 COUNT 查询返回的 `int64` 值会 panic。

**Files:**
- Modify: `backend/go/internal/statistics/handler/statistics.go:69-76`
- Modify: `backend/go/internal/statistics/repository/statistics.go:186-247` (统一返回类型)

- [ ] **Step 1: 修改 repository 层，统一 value 为 float64**

将 `backend/go/internal/statistics/repository/statistics.go` 中 `GetTrendData` 方法的三个 case 分支都改为返回 `float64` 类型的 value。

对于 "门诊量" case（第 191-209 行），将：
```go
results = append(results, map[string]interface{}{
    "date":  d.Date,
    "value": d.Count,
})
```
替换为：
```go
results = append(results, map[string]interface{}{
    "date":  d.Date,
    "value": float64(d.Count),
})
```

对于 "处方量" case（第 228-243 行），做同样的替换：
```go
results = append(results, map[string]interface{}{
    "date":  d.Date,
    "value": float64(d.Count),
})
```

- [ ] **Step 2: 验证编译通过**

```bash
cd backend/go && go build ./internal/statistics/...
```

Expected: 无错误

---

## Task 3: 修复 Java AuthService refreshToken Bug

**问题：** `refreshToken()` 方法调用 `login()` 时传入空密码，`passwordEncoder.matches("", hash)` 返回 false，导致刷新必失败。

**Files:**
- Modify: `backend/java/his-auth/src/main/java/com/hismixed/auth/service/AuthService.java:108-133`

- [ ] **Step 1: 重写 refreshToken 方法，绕过密码验证**

将 `AuthService.java` 的 `refreshToken` 方法（第 108-133 行）替换为：

```java
public LoginResponse refreshToken(String refreshToken) {
    RefreshToken token = refreshTokenRepository.selectOne(
        new LambdaQueryWrapper<RefreshToken>()
            .eq(RefreshToken::getToken, refreshToken)
            .eq(RefreshToken::getRevoked, false)
    );

    if (token == null || token.getExpiresAt().isBefore(LocalDateTime.now())) {
        throw new RuntimeException("刷新令牌无效或已过期");
    }

    User user = userRepository.selectById(token.getUserId());
    if (user == null) {
        throw new RuntimeException("用户不存在");
    }

    if (user.getStatus() != 1) {
        throw new RuntimeException("账号已禁用");
    }

    // 撤销旧令牌
    token.setRevoked(true);
    refreshTokenRepository.updateById(token);

    // 查询角色
    List<Role> roles = roleRepository.selectRolesByUserId(user.getId());
    List<String> roleCodes = roles.stream().map(Role::getCode).toList();

    // 生成新 AccessToken
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    String accessToken = Jwts.builder()
        .setSubject(user.getUsername())
        .claim("userId", user.getId())
        .claim("realName", user.getRealName())
        .claim("roles", roleCodes)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    // 生成新 RefreshToken
    String newRefreshToken = UUID.randomUUID().toString().replace("-", "");
    RefreshToken newToken = new RefreshToken();
    newToken.setUserId(user.getId());
    newToken.setToken(newRefreshToken);
    newToken.setExpiresAt(LocalDateTime.now().plusDays(7));
    newToken.setRevoked(false);
    refreshTokenRepository.insert(newToken);

    // 构建响应
    LoginResponse response = new LoginResponse();
    response.setAccessToken(accessToken);
    response.setRefreshToken(newRefreshToken);
    response.setTokenType("Bearer");
    response.setExpiresIn(jwtExpiration);
    response.setUserId(user.getId());
    response.setUsername(user.getUsername());
    response.setRealName(user.getRealName());
    response.setRoles(roleCodes);
    response.setPermissions(new ArrayList<>());

    return response;
}
```

- [ ] **Step 2: 验证 Java 编译通过**

```bash
cd backend/java && mvn compile -pl his-auth -q
```

Expected: BUILD SUCCESS

---

## Task 4: 为所有 Go 服务添加优雅关闭

**问题：** 所有 9 个 Go 服务没有 signal 处理，进程被 kill 时请求中断、数据库事务未提交。

**Files:**
- Modify: `backend/go/cmd/registration/main.go`
- Modify: `backend/go/cmd/pharmacy/main.go`
- Modify: `backend/go/cmd/schedule/main.go`
- Modify: `backend/go/cmd/examination/main.go`
- Modify: `backend/go/cmd/outpatient/main.go`
- Modify: `backend/go/cmd/followup/main.go`
- Modify: `backend/go/cmd/notification/main.go`
- Modify: `backend/go/cmd/statistics/main.go`
- Modify: `backend/go/cmd/gateway/main.go`

以 registration 为模板，其他 8 个服务参照相同模式修改。

- [ ] **Step 1: 修改 registration/main.go 添加优雅关闭**

将 `backend/go/cmd/registration/main.go` 替换为：

```go
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

	// 连接Redis
	_, err = redis.Connect(cfg.Redis.Addr, cfg.Redis.Password, cfg.Redis.DB)
	if err != nil {
		log.Fatalf("连接Redis失败: %v", err)
	}

	// 初始化业务组件
	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)
	h := handler.NewRegistrationHandler(svc)

	// 启动HTTP服务
	httpSrv := startHTTPServer(cfg.Server.HTTPPort)

	// 启动gRPC服务
	grpcSrv, grpcLis := startGRPCServer(cfg.Server.GRPCPort, h)

	// 优雅关闭
	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
	log.Println("收到关闭信号，开始优雅关闭...")

	// 关闭HTTP
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	if err := httpSrv.Shutdown(ctx); err != nil {
		log.Printf("HTTP 服务关闭失败: %v", err)
	}

	// 关闭gRPC
	grpcSrv.GracefulStop()
	grpcLis.Close()

	log.Println("挂号预约服务已关闭")
}

func startHTTPServer(port int) *http.Server {
	r := gin.New()
	r.Use(middleware.Recovery())
	r.Use(middleware.RequestID())
	r.Use(middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)

	srv := &http.Server{
		Addr:    fmt.Sprintf(":%d", port),
		Handler: r,
	}

	go func() {
		log.Printf("挂号服务 HTTP 启动在端口 %d", port)
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Printf("HTTP 服务启动失败: %v", err)
		}
	}()

	return srv
}

func startGRPCServer(port int, h *handler.RegistrationHandler) (*grpc.Server, net.Listener) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("gRPC 监听失败: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterRegistrationServiceServer(s, h)

	go func() {
		log.Printf("挂号服务 gRPC 启动在端口 %d", port)
		if err := s.Serve(lis); err != nil {
			log.Printf("gRPC 服务启动失败: %v", err)
		}
	}()

	return s, lis
}
```

- [ ] **Step 2: 修改 pharmacy/main.go**

将 `backend/go/cmd/pharmacy/main.go` 替换为相同模式的优雅关闭版本：

```go
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
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
	pb "github.com/his-mixed/go/pkg/grpc/pharmacy"
	"google.golang.org/grpc"
)

func main() {
	fmt.Println("=== HIS 药房管理服务启动 ===")

	cfg, err := config.Load("configs/pharmacy.yaml")
	if err != nil {
		log.Fatalf("加载配置失败: %v", err)
	}

	db, err := database.Connect(
		cfg.Database.Host, cfg.Database.Port,
		cfg.Database.User, cfg.Database.Password,
		cfg.Database.DBName, cfg.Database.SSLMode,
	)
	if err != nil {
		log.Fatalf("连接数据库失败: %v", err)
	}
	defer database.Close()

	_, err = redis.Connect(cfg.Redis.Addr, cfg.Redis.Password, cfg.Redis.DB)
	if err != nil {
		log.Fatalf("连接Redis失败: %v", err)
	}

	repo := repository.NewPharmacyRepository(db)
	svc := service.NewPharmacyService(repo)
	h := handler.NewPharmacyHandler(svc)

	httpSrv := startHTTP(cfg.Server.HTTPPort)
	grpcSrv, grpcLis := startGRPC(cfg.Server.GRPCPort, h)

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

func startHTTP(port int) *http.Server {
	r := gin.New()
	r.Use(middleware.Recovery(), middleware.RequestID(), middleware.Logger())
	r.GET("/api/health", health.Handler)
	r.GET("/api/ping", health.PingHandler)
	srv := &http.Server{Addr: fmt.Sprintf(":%d", port), Handler: r}
	go func() {
		log.Printf("药房服务 HTTP 启动在端口 %d", port)
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Printf("HTTP 启动失败: %v", err)
		}
	}()
	return srv
}

func startGRPC(port int, h *handler.PharmacyHandler) (*grpc.Server, net.Listener) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("监听失败: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterPharmacyServiceServer(s, h)
	go func() {
		log.Printf("药房服务 gRPC 启动在端口 %d", port)
		if err := s.Serve(lis); err != nil {
			log.Printf("gRPC 失败: %v", err)
		}
	}()
	return s, lis
}
```

- [ ] **Step 3: 修改 schedule/main.go**

参照相同模式修改 `backend/go/cmd/schedule/main.go`。需要先读取当前文件确认其 handler/service/repository 的具体名称。

```bash
cat backend/go/cmd/schedule/main.go
```

然后按照 registration 的模式重写：保留原有初始化逻辑，添加 signal 处理、HTTP Server 返回 *http.Server、gRPC Server 返回 (*grpc.Server, net.Listener)。

- [ ] **Step 4: 修改 examination/main.go**

同上模式。

- [ ] **Step 5: 修改 outpatient/main.go**

同上模式。

- [ ] **Step 6: 修改 followup/main.go**

同上模式。

- [ ] **Step 7: 修改 notification/main.go**

同上模式。

- [ ] **Step 8: 修改 statistics/main.go**

同上模式。

- [ ] **Step 9: 修改 gateway/main.go 添加优雅关闭**

将 `backend/go/cmd/gateway/main.go` 替换为：

```go
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

	srv := &http.Server{
		Addr:    ":8080",
		Handler: r,
	}

	go func() {
		log.Println("Gateway 启动在端口 8080")
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
```

- [ ] **Step 10: 验证所有服务编译通过**

```bash
cd backend/go && go build ./cmd/... 2>&1
```

Expected: 无错误输出

---

## Task 5: Gateway 配置加载

**问题：** `gateway.yaml` 存在但从未被读取，17 个下游服务地址全部硬编码在 `proxy.go` 中。

**Files:**
- Modify: `backend/go/configs/gateway.yaml` (重新设计配置结构)
- Modify: `backend/go/internal/gateway/config/config.go` (匹配新配置结构)
- Modify: `backend/go/internal/gateway/handler/proxy.go` (从配置读取地址)
- Modify: `backend/go/internal/gateway/router/router.go` (接收 config 参数)
- Modify: `backend/go/cmd/gateway/main.go` (加载配置并传递)

- [ ] **Step 1: 重写 gateway.yaml 配置文件**

将 `backend/go/configs/gateway.yaml` 替换为：

```yaml
server:
  name: "his-gateway"
  http_port: 8080

routes:
  auth: "http://localhost:8081"
  user: "http://localhost:8082"
  registration: "http://localhost:8083"
  clinic: "http://localhost:8084"
  prescription: "http://localhost:8085"
  billing: "http://localhost:8086"
  pharmacy: "http://localhost:8087"
  examination: "http://localhost:8088"
  inpatient: "http://localhost:8089"
  schedule: "http://localhost:8090"
  outpatient: "http://localhost:8091"
  followup: "http://localhost:8092"
  health-record: "http://localhost:8093"
  notification: "http://localhost:8094"
  statistics: "http://localhost:8095"
  system: "http://localhost:8096"
  emr: "http://localhost:8097"
```

- [ ] **Step 2: 重写 gateway config.go**

将 `backend/go/internal/gateway/config/config.go` 替换为：

```go
package config

import (
	"github.com/spf13/viper"
)

// GatewayConfig 网关配置
type GatewayConfig struct {
	Server struct {
		Name     string `mapstructure:"name"`
		HTTPPort int    `mapstructure:"http_port"`
	} `mapstructure:"server"`
	Routes map[string]string `mapstructure:"routes"`
}

// Load 加载网关配置
func Load(configFile string) (*GatewayConfig, error) {
	v := viper.New()
	v.SetConfigFile(configFile)
	v.SetConfigType("yaml")

	// 设置默认值
	v.SetDefault("server.http_port", 8080)
	v.SetDefault("routes", map[string]string{
		"auth":          "http://localhost:8081",
		"user":          "http://localhost:8082",
		"registration":  "http://localhost:8083",
		"clinic":        "http://localhost:8084",
		"prescription":  "http://localhost:8085",
		"billing":       "http://localhost:8086",
		"pharmacy":      "http://localhost:8087",
		"examination":   "http://localhost:8088",
		"inpatient":     "http://localhost:8089",
		"schedule":      "http://localhost:8090",
		"outpatient":    "http://localhost:8091",
		"followup":      "http://localhost:8092",
		"health-record": "http://localhost:8093",
		"notification":  "http://localhost:8094",
		"statistics":    "http://localhost:8095",
		"system":        "http://localhost:8096",
		"emr":           "http://localhost:8097",
	})

	if err := v.ReadInConfig(); err != nil {
		return nil, err
	}

	v.AutomaticEnv()

	var cfg GatewayConfig
	if err := v.Unmarshal(&cfg); err != nil {
		return nil, err
	}

	return &cfg, nil
}
```

- [ ] **Step 3: 修改 proxy.go 从配置读取地址**

将 `backend/go/internal/gateway/handler/proxy.go` 替换为：

```go
package handler

import (
	"fmt"
	"net/http"
	"net/http/httputil"
	"net/url"

	"github.com/gin-gonic/gin"
)

// InitProxies 根据配置初始化所有代理
func InitProxies(routes map[string]string) map[string]gin.HandlerFunc {
	proxies := make(map[string]gin.HandlerFunc)
	for name, target := range routes {
		proxies[name] = createProxy(target)
	}
	return proxies
}

func createProxy(target string) gin.HandlerFunc {
	targetURL, _ := url.Parse(target)

	return func(c *gin.Context) {
		proxy := httputil.NewSingleHostReverseProxy(targetURL)
		proxy.Director = func(req *http.Request) {
			req.URL.Scheme = targetURL.Scheme
			req.URL.Host = targetURL.Host
			req.URL.Path = c.Request.URL.Path
			req.Host = targetURL.Host

			if userID, exists := c.Get("userId"); exists {
				req.Header.Set("X-User-Id", fmt.Sprintf("%v", userID))
			}
			if username, exists := c.Get("username"); exists {
				req.Header.Set("X-Username", fmt.Sprintf("%v", username))
			}
			if realName, exists := c.Get("realName"); exists {
				req.Header.Set("X-Real-Name", fmt.Sprintf("%v", realName))
			}
			if roles, exists := c.Get("roles"); exists {
				req.Header.Set("X-Roles", fmt.Sprintf("%v", roles))
			}
		}
		proxy.ErrorHandler = func(w http.ResponseWriter, r *http.Request, err error) {
			c.JSON(http.StatusBadGateway, gin.H{
				"code":    503,
				"message": fmt.Sprintf("服务不可用: %v", err),
			})
		}
		proxy.ServeHTTP(c.Writer, c.Request)
	}
}
```

- [ ] **Step 4: 修改 router.go 接收 proxies 参数**

将 `backend/go/internal/gateway/router/router.go` 替换为：

```go
package router

import (
	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/pkg/health"
)

// SetupRouter 为每个下游服务注册 API 路由分组
func SetupRouter(r *gin.Engine, proxies map[string]gin.HandlerFunc) {
	r.GET("/api/health", health.Handler)
	r.GET("/api/ready", health.ReadyHandler)
	r.GET("/api/ping", health.PingHandler)

	proxy := func(name string) gin.HandlerFunc {
		if h, ok := proxies[name]; ok {
			return h
		}
		return func(c *gin.Context) {
			c.JSON(503, gin.H{"code": 503, "message": "服务未配置: " + name})
		}
	}

	auth := r.Group("/api/auth")
	{
		auth.POST("/login", proxy("auth"))
		auth.POST("/refresh", proxy("auth"))
		auth.GET("/captcha", proxy("auth"))
	}

	user := r.Group("/api/user")
	{
		user.GET("/patients", proxy("user"))
		user.GET("/patients/:id", proxy("user"))
		user.POST("/patients", proxy("user"))
		user.PUT("/patients/:id", proxy("user"))
		user.GET("/employees", proxy("user"))
		user.GET("/departments", proxy("user"))
	}

	reg := r.Group("/api/registration")
	{
		reg.GET("/schedules", proxy("registration"))
		reg.POST("/appointments", proxy("registration"))
		reg.GET("/appointments", proxy("registration"))
		reg.GET("/queue", proxy("registration"))
	}

	clinic := r.Group("/api/clinic")
	{
		clinic.POST("/encounters", proxy("clinic"))
		clinic.GET("/encounters", proxy("clinic"))
		clinic.POST("/diagnoses", proxy("clinic"))
	}

	pres := r.Group("/api/prescription")
	{
		pres.POST("/prescriptions", proxy("prescription"))
		pres.GET("/prescriptions", proxy("prescription"))
	}

	bill := r.Group("/api/billing")
	{
		bill.POST("/calculate", proxy("billing"))
		bill.POST("/payments", proxy("billing"))
	}

	pharm := r.Group("/api/pharmacy")
	{
		pharm.GET("/drugs", proxy("pharmacy"))
		pharm.POST("/dispense", proxy("pharmacy"))
		pharm.GET("/dispense-queue", proxy("pharmacy"))
	}

	exam := r.Group("/api/examination")
	{
		exam.GET("/requests", proxy("examination"))
		exam.POST("/reports", proxy("examination"))
		exam.GET("/reports/:id", proxy("examination"))
	}

	inp := r.Group("/api/inpatient")
	{
		inp.POST("/admissions", proxy("inpatient"))
		inp.GET("/beds", proxy("inpatient"))
		inp.POST("/orders", proxy("inpatient"))
		inp.POST("/discharges", proxy("inpatient"))
	}

	sched := r.Group("/api/schedule")
	{
		sched.GET("/plans", proxy("schedule"))
		sched.POST("/slots/generate", proxy("schedule"))
		sched.GET("/slots", proxy("schedule"))
	}

	out := r.Group("/api/outpatient")
	{
		out.POST("/consultations", proxy("outpatient"))
		out.GET("/consultations/:id", proxy("outpatient"))
	}

	fu := r.Group("/api/followup")
	{
		fu.GET("/plans", proxy("followup"))
		fu.POST("/records", proxy("followup"))
	}

	hr := r.Group("/api/health-record")
	{
		hr.GET("/patients/:id/overview", proxy("health-record"))
	}

	notify := r.Group("/api/notification")
	{
		notify.POST("/send", proxy("notification"))
		notify.GET("/templates", proxy("notification"))
	}

	stat := r.Group("/api/statistics")
	{
		stat.GET("/dashboard", proxy("statistics"))
		stat.GET("/registration-trend", proxy("statistics"))
	}

	sys := r.Group("/api/system")
	{
		sys.GET("/dict/types", proxy("system"))
		sys.GET("/configs", proxy("system"))
		sys.GET("/audit-logs", proxy("system"))
	}

	emr := r.Group("/api/emr")
	{
		emr.POST("/records", proxy("emr"))
		emr.GET("/records/:id", proxy("emr"))
	}
}
```

- [ ] **Step 5: 更新 gateway/main.go 加载配置**

更新 `backend/go/cmd/gateway/main.go`：

```go
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

	// 加载配置
	cfg, err := gwconfig.Load("configs/gateway.yaml")
	if err != nil {
		log.Printf("加载配置失败，使用默认配置: %v", err)
		cfg = &gwconfig.GatewayConfig{}
		cfg.Server.HTTPPort = 8080
		cfg.Routes = map[string]string{
			"auth":          "http://localhost:8081",
			"user":          "http://localhost:8082",
			"registration":  "http://localhost:8083",
			"clinic":        "http://localhost:8084",
			"prescription":  "http://localhost:8085",
			"billing":       "http://localhost:8086",
			"pharmacy":      "http://localhost:8087",
			"examination":   "http://localhost:8088",
			"inpatient":     "http://localhost:8089",
			"schedule":      "http://localhost:8090",
			"outpatient":    "http://localhost:8091",
			"followup":      "http://localhost:8092",
			"health-record": "http://localhost:8093",
			"notification":  "http://localhost:8094",
			"statistics":    "http://localhost:8095",
			"system":        "http://localhost:8096",
			"emr":           "http://localhost:8097",
		}
	}

	// 初始化代理
	proxies := handler.InitProxies(cfg.Routes)

	r := gin.Default()
	r.Use(middleware.CORS())
	r.Use(middleware.RequestID())
	r.Use(middleware.Recovery())
	r.Use(middleware.Logger())
	r.Use(middleware.Auth())

	router.SetupRouter(r, proxies)

	srv := &http.Server{
		Addr:    fmt.Sprintf(":%d", cfg.Server.HTTPPort),
		Handler: r,
	}

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
```

- [ ] **Step 6: 清理不再需要的 DefaultConfig 函数**

删除 `backend/go/internal/gateway/config/config.go` 中不再使用的 `DefaultConfig()` 函数（如果 Step 2 中已替换整个文件则跳过此步）。

- [ ] **Step 7: 验证编译通过**

```bash
cd backend/go && go build ./cmd/gateway/...
```

Expected: 无错误

---

## Task 6: 启用 Gateway RateLimiter

**问题：** `RateLimiter` 中间件已实现但未在 Gateway 中启用。

**Files:**
- Modify: `backend/go/cmd/gateway/main.go` (添加 RateLimiter 中间件)

- [ ] **Step 1: 在 gateway/main.go 中启用限流**

在 `backend/go/cmd/gateway/main.go` 的中间件注册部分，在 Auth 之前添加 RateLimiter：

```go
	// 限流：每IP每秒100个请求
	limiter := middleware.NewRateLimiter()
	r.Use(limiter.Allow("global", 100, 1))
	r.Use(middleware.Auth())
```

完整中间件链变为：
```go
	r.Use(middleware.CORS())
	r.Use(middleware.RequestID())
	r.Use(middleware.Recovery())
	r.Use(middleware.Logger())
	r.Use(limiter.Allow("global", 100, 1))
	r.Use(middleware.Auth())
```

- [ ] **Step 2: 验证编译通过**

```bash
cd backend/go && go build ./cmd/gateway/...
```

Expected: 无错误

---

## Task 7: Go 业务层单元测试 — Registration 服务

**Files:**
- Create: `backend/go/internal/registration/service/registration_test.go`

- [ ] **Step 1: 创建 Registration 服务单元测试**

创建 `backend/go/internal/registration/service/registration_test.go`：

```go
package service_test

import (
	"testing"
	"time"

	"github.com/his-mixed/go/internal/registration/model"
	"github.com/his-mixed/go/internal/registration/repository"
	"github.com/his-mixed/go/internal/registration/service"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)

	err = db.AutoMigrate(&model.Registration{}, &model.QueueItem{})
	require.NoError(t, err)

	return db
}

func TestCreateRegistration(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)

	reg := &model.Registration{
		PatientID:    1,
		ScheduleID:   100,
		DoctorID:     10,
		Status:       "PENDING",
		Symptom:      "头痛",
		RegisterDate: time.Now(),
		CreatedAt:    time.Now(),
		UpdatedAt:    time.Now(),
	}

	err := svc.CreateRegistration(reg)
	assert.NoError(t, err)
	assert.NotZero(t, reg.ID)
}

func TestCancelRegistration(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)

	reg := &model.Registration{
		PatientID:    1,
		ScheduleID:   100,
		Status:       "PENDING",
		RegisterDate: time.Now(),
		CreatedAt:    time.Now(),
		UpdatedAt:    time.Now(),
	}
	require.NoError(t, svc.CreateRegistration(reg))

	err := svc.CancelRegistration(reg.ID)
	assert.NoError(t, err)

	saved, err := svc.GetRegistrationByID(reg.ID)
	require.NoError(t, err)
	assert.Equal(t, "CANCELLED", saved.Status)
}

func TestCompleteRegistration(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)

	reg := &model.Registration{
		PatientID:    1,
		ScheduleID:   100,
		Status:       "PENDING",
		RegisterDate: time.Now(),
		CreatedAt:    time.Now(),
		UpdatedAt:    time.Now(),
	}
	require.NoError(t, svc.CreateRegistration(reg))

	err := svc.CompleteRegistration(reg.ID)
	assert.NoError(t, err)

	saved, err := svc.GetRegistrationByID(reg.ID)
	require.NoError(t, err)
	assert.Equal(t, "COMPLETED", saved.Status)
}

func TestGetRegistrationByID(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)

	reg := &model.Registration{
		PatientID:    42,
		ScheduleID:   100,
		Status:       "PENDING",
		RegisterDate: time.Now(),
		CreatedAt:    time.Now(),
		UpdatedAt:    time.Now(),
	}
	require.NoError(t, svc.CreateRegistration(reg))

	found, err := svc.GetRegistrationByID(reg.ID)
	require.NoError(t, err)
	assert.Equal(t, int64(42), found.PatientID)
	assert.Equal(t, "PENDING", found.Status)
}

func TestListRegistrations(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)

	for i := 0; i < 5; i++ {
		reg := &model.Registration{
			PatientID:    1,
			ScheduleID:   int64(100 + i),
			Status:       "PENDING",
			RegisterDate: time.Now(),
			CreatedAt:    time.Now(),
			UpdatedAt:    time.Now(),
		}
		require.NoError(t, svc.CreateRegistration(reg))
	}

	regs, total, err := svc.ListRegistrations(1, "", 1, 10)
	assert.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, regs, 5)
}

func TestCreateQueueItem(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)

	item := &model.QueueItem{
		RegistrationID: 1,
		DepartmentID:   1,
		DoctorID:       10,
		QueueNumber:    1,
		Status:         "WAITING",
		CreatedAt:      time.Now(),
	}

	err := svc.CreateQueueItem(item)
	assert.NoError(t, err)
	assert.NotZero(t, item.ID)
}

func TestGetQueueItems(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewRegistrationRepository(db)
	svc := service.NewRegistrationService(repo)

	for i := 1; i <= 3; i++ {
		item := &model.QueueItem{
			RegistrationID: int64(i),
			DepartmentID:   1,
			DoctorID:       10,
			QueueNumber:    i,
			Status:         "WAITING",
			CreatedAt:      time.Now(),
		}
		require.NoError(t, svc.CreateQueueItem(item))
	}

	items, err := svc.GetQueueItems(1)
	assert.NoError(t, err)
	assert.Len(t, items, 3)
	assert.Equal(t, 1, items[0].QueueNumber)
}
```

- [ ] **Step 2: 运行测试**

```bash
cd backend/go && go test ./internal/registration/... -v
```

Expected: 所有 7 个测试 PASS

---

## Task 8: Go 业务层单元测试 — Statistics 服务

**Files:**
- Create: `backend/go/internal/statistics/service/statistics_test.go`

- [ ] **Step 1: 创建 Statistics 服务单元测试**

创建 `backend/go/internal/statistics/service/statistics_test.go`：

```go
package service_test

import (
	"testing"
	"time"

	"github.com/his-mixed/go/internal/statistics/model"
	"github.com/his-mixed/go/internal/statistics/repository"
	"github.com/his-mixed/go/internal/statistics/service"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)

	err = db.AutoMigrate(&model.StatSnapshot{})
	require.NoError(t, err)

	// 创建统计所需的表结构
	db.Exec(`CREATE TABLE IF NOT EXISTS registrations (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		patient_id INTEGER,
		status TEXT,
		department_id INTEGER,
		created_at DATETIME DEFAULT CURRENT_TIMESTAMP
	)`)
	db.Exec(`CREATE TABLE IF NOT EXISTS encounters (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		patient_id INTEGER,
		department_id INTEGER,
		created_at DATETIME DEFAULT CURRENT_TIMESTAMP
	)`)
	db.Exec(`CREATE TABLE IF NOT EXISTS prescriptions (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		total_amount REAL DEFAULT 0,
		created_at DATETIME DEFAULT CURRENT_TIMESTAMP
	)`)
	db.Exec(`CREATE TABLE IF NOT EXISTS payments (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		amount REAL DEFAULT 0,
		created_at DATETIME DEFAULT CURRENT_TIMESTAMP
	)`)
	db.Exec(`CREATE TABLE IF NOT EXISTS drugs (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		name TEXT
	)`)

	return db
}

func TestCreateAndGetSnapshot(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewStatisticsRepository(db)
	svc := service.NewStatisticsService(repo)

	snapshot := &model.StatSnapshot{
		StatType: "daily",
		StatDate: time.Now().Format("2006-01-02"),
		Metric:   "registrations",
		Value:    100.0,
	}

	err := svc.CreateSnapshot(snapshot)
	assert.NoError(t, err)

	found, err := svc.GetSnapshot("daily", time.Now().Format("2006-01-02"))
	require.NoError(t, err)
	assert.Equal(t, "registrations", found.Metric)
}

func TestGetRegistrationStats(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewStatisticsRepository(db)
	svc := service.NewStatisticsService(repo)

	// 插入测试数据
	db.Exec("INSERT INTO registrations (patient_id, status, created_at) VALUES (1, 'COMPLETED', ?)", time.Now())
	db.Exec("INSERT INTO registrations (patient_id, status, created_at) VALUES (2, 'CANCELLED', ?)", time.Now())
	db.Exec("INSERT INTO registrations (patient_id, status, created_at) VALUES (3, 'PENDING', ?)", time.Now())

	stats, err := svc.GetRegistrationStats("", "")
	require.NoError(t, err)
	assert.Equal(t, int64(3), stats["total"])
	assert.Equal(t, int64(1), stats["completed"])
	assert.Equal(t, int64(1), stats["cancelled"])
}

func TestGetDashboardStats(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewStatisticsRepository(db)
	svc := service.NewStatisticsService(repo)

	// 插入测试数据
	db.Exec("INSERT INTO registrations (patient_id, status, created_at) VALUES (1, 'PENDING', ?)", time.Now())
	db.Exec("INSERT INTO encounters (patient_id, created_at) VALUES (1, ?)", time.Now())
	db.Exec("INSERT INTO prescriptions (total_amount, created_at) VALUES (50.0, ?)", time.Now())
	db.Exec("INSERT INTO payments (amount, created_at) VALUES (100.0, ?)", time.Now())

	stats, err := svc.GetDashboardStats("今天", 0)
	require.NoError(t, err)
	assert.Equal(t, int64(1), stats["total_registrations"])
	assert.Equal(t, int64(1), stats["total_outpatients"])
	assert.Equal(t, int64(1), stats["total_prescriptions"])
}

func TestGetTrendData(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewStatisticsRepository(db)
	svc := service.NewStatisticsService(repo)

	// 插入测试数据
	db.Exec("INSERT INTO encounters (patient_id, created_at) VALUES (1, ?)", time.Now())

	data, err := svc.GetTrendData("门诊量", time.Now().Format("2006-01-02"), time.Now().Format("2006-01-02"), "日", 0)
	require.NoError(t, err)
	assert.NotEmpty(t, data)

	// 验证返回的 value 是 float64 类型（不会 panic）
	for _, d := range data {
		_, ok := d["value"].(float64)
		assert.True(t, ok, "value 应该是 float64 类型")
	}
}
```

- [ ] **Step 2: 运行测试**

```bash
cd backend/go && go test ./internal/statistics/... -v
```

Expected: 所有 4 个测试 PASS

---

## Task 9: Go 业务层单元测试 — Pharmacy 服务

**Files:**
- Create: `backend/go/internal/pharmacy/service/pharmacy_test.go`

- [ ] **Step 1: 创建 Pharmacy 服务单元测试**

创建 `backend/go/internal/pharmacy/service/pharmacy_test.go`：

```go
package service_test

import (
	"testing"
	"time"

	"github.com/his-mixed/go/internal/pharmacy/model"
	"github.com/his-mixed/go/internal/pharmacy/repository"
	"github.com/his-mixed/go/internal/pharmacy/service"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)

	err = db.AutoMigrate(&model.Drug{}, &model.DispenseRecord{})
	require.NoError(t, err)

	return db
}

func TestGetDrugByID(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewPharmacyRepository(db)
	svc := service.NewPharmacyService(repo)

	drug := &model.Drug{
		Name:      "阿莫西林",
		Code:      "AMX001",
		Category:  "抗生素",
		Unit:      "盒",
		Price:     15.50,
		Stock:     100,
		Status:    1,
		CreatedAt: time.Now(),
		UpdatedAt: time.Now(),
	}
	require.NoError(t, db.Create(drug).Error)

	found, err := svc.GetDrugByID(drug.ID)
	require.NoError(t, err)
	assert.Equal(t, "阿莫西林", found.Name)
	assert.Equal(t, 100, found.Stock)
}

func TestListDrugs(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewPharmacyRepository(db)
	svc := service.NewPharmacyService(repo)

	drugs := []model.Drug{
		{Name: "阿莫西林", Code: "AMX001", Category: "抗生素", Unit: "盒", Price: 15.50, Stock: 100, Status: 1, CreatedAt: time.Now(), UpdatedAt: time.Now()},
		{Name: "布洛芬", Code: "BLF001", Category: "解热镇痛", Unit: "盒", Price: 12.00, Stock: 200, Status: 1, CreatedAt: time.Now(), UpdatedAt: time.Now()},
	}
	for i := range drugs {
		require.NoError(t, db.Create(&drugs[i]).Error)
	}

	result, total, err := svc.ListDrugs("", 1, 10)
	assert.NoError(t, err)
	assert.Equal(t, int64(2), total)
	assert.Len(t, result, 2)
}

func TestCheckStock(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewPharmacyRepository(db)
	svc := service.NewPharmacyService(repo)

	drug := &model.Drug{
		Name: "阿莫西林", Code: "AMX001", Category: "抗生素",
		Unit: "盒", Price: 15.50, Stock: 10, Status: 1,
		CreatedAt: time.Now(), UpdatedAt: time.Now(),
	}
	require.NoError(t, db.Create(drug).Error)

	// 库存充足
	ok, err := svc.CheckStock(drug.ID, 5)
	assert.NoError(t, err)
	assert.True(t, ok)

	// 库存不足
	ok, err = svc.CheckStock(drug.ID, 15)
	assert.NoError(t, err)
	assert.False(t, ok)
}
```

- [ ] **Step 2: 运行测试**

```bash
cd backend/go && go test ./internal/pharmacy/... -v
```

Expected: 所有 3 个测试 PASS

---

## Task 10: 验证全部改动

- [ ] **Step 1: 运行 Go 全量测试**

```bash
cd backend/go && go test ./... -v 2>&1 | tail -50
```

Expected: 所有测试 PASS，无 FAIL

- [ ] **Step 2: 验证所有 Go 服务可编译**

```bash
cd backend/go && go build ./cmd/... 2>&1
```

Expected: 无错误

- [ ] **Step 3: 验证 Java auth 模块编译**

```bash
cd backend/java && mvn compile -pl his-auth -q 2>&1
```

Expected: BUILD SUCCESS

- [ ] **Step 4: 启动 Docker 基础设施并运行集成测试**

```bash
cd docker && docker compose up -d postgresql redis
sleep 5
cd ../backend/go && go build -o /tmp/gateway ./cmd/gateway/
/tmp/gateway &
sleep 2
cd ../../testing && HIS_INTEGRATION_TEST=true go test ./api/ -v -run "TestHealthCheck|TestPing"
kill %1 2>/dev/null
```

Expected: 集成测试 PASS

- [ ] **Step 5: 清理 Docker（节省资源）**

```bash
cd docker && docker compose down
```
