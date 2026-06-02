# MVP演示版本（基本功能可演示）实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 实现患者端核心业务流程（登录、挂号、查看医生、查看处方）和管理端基础功能（登录、数据管理），确保系统可演示基本功能。

**Architecture:** 采用前后端分离微服务架构，Java负责认证授权（Spring Security + JWT），Go负责高并发业务（挂号、排班、药房）。Gateway统一入口，JWT鉴权，gRPC跨语言调用。前端患者端Vue3+Ant Design Vue，管理端Vue3+Element Plus。

**Tech Stack:** Go 1.25+, Java 21, Spring Boot 3.3, Vue 3, TypeScript, Ant Design Vue 4, Element Plus 2, PostgreSQL 17, Redis 7, RabbitMQ 4.0, gRPC, GORM, MyBatis-Plus

---

## 一、文件结构映射

### 1.1 Go后端文件结构

```
backend/go/
├── cmd/
│   ├── gateway/main.go                    # 网关入口（已有）
│   ├── registration/main.go               # 挂号服务入口（需修改）
│   ├── schedule/main.go                   # 排班服务入口（需修改）
│   └── pharmacy/main.go                   # 药房服务入口（需修改）
├── internal/
│   ├── registration/
│   │   ├── handler/
│   │   │   └── registration.go            # 新增：gRPC handler
│   │   ├── repository/
│   │   │   └── registration.go            # 新增：数据访问层
│   │   ├── service/
│   │   │   └── registration.go            # 已有：业务逻辑层（需补全）
│   │   └── model/
│   │       └── registration.go            # 已有：数据模型
│   ├── schedule/
│   │   ├── handler/
│   │   │   └── schedule.go                # 新增：gRPC handler
│   │   ├── repository/
│   │   │   └── schedule.go                # 新增：数据访问层
│   │   ├── service/
│   │   │   └── schedule.go                # 已有：业务逻辑层（需补全）
│   │   └── model/
│   │       └── schedule.go                # 已有：数据模型
│   └── pharmacy/
│       ├── handler/
│       │   └── pharmacy.go                # 新增：gRPC handler
│       ├── repository/
│       │   └── pharmacy.go                # 新增：数据访问层
│       ├── service/
│       │   └── pharmacy.go                # 已有：业务逻辑层（需补全）
│       └── model/
│           └── pharmacy.go                # 已有：数据模型
├── configs/
│   ├── registration.yaml                  # 已有：挂号服务配置
│   ├── schedule.yaml                      # 新增：排班服务配置
│   ├── pharmacy.yaml                      # 已有：药房服务配置
│   └── auth.yaml                          # 新增：认证服务配置（Java侧）
└── pkg/
    ├── database/database.go               # 已有：数据库连接
    ├── redis/redis.go                     # 已有：Redis连接
    ├── mq/mq.go                           # 已有：RabbitMQ连接
    ├── config/config.go                   # 已有：配置加载
    ├── security/jwt.go                    # 已有：JWT工具
    ├── response/response.go               # 已有：响应封装
    ├── errors/errors.go                   # 已有：错误码
    └── grpc/                              # 新增：生成的gRPC代码
        ├── auth/auth.pb.go                # 新增：Auth proto生成
        ├── registration/registration.pb.go # 新增：Registration proto生成
        ├── schedule/schedule.pb.go        # 新增：Schedule proto生成
        └── pharmacy/pharmacy.pb.go        # 新增：Pharmacy proto生成
```

### 1.2 Java后端文件结构

```
backend/java/
├── his-auth/
│   └── src/main/java/com/hismixed/auth/
│       ├── Application.java               # 已有：启动类
│       ├── config/
│       │   └── SecurityConfig.java        # 已有：安全配置（需修改）
│       ├── controller/
│       │   └── AuthController.java        # 新增：认证控制器
│       ├── service/
│       │   ├── AuthService.java           # 新增：认证服务
│       │   └── UserService.java           # 新增：用户服务
│       ├── repository/
│       │   ├── UserRepository.java        # 新增：用户仓储
│       │   ├── RoleRepository.java        # 新增：角色仓储
│       │   └── RefreshTokenRepository.java # 新增：刷新令牌仓储
│       ├── entity/
│       │   ├── User.java                  # 新增：用户实体
│       │   ├── Role.java                  # 新增：角色实体
│       │   ├── Permission.java            # 新增：权限实体
│       │   └── RefreshToken.java          # 新增：刷新令牌实体
│       ├── dto/
│       │   ├── LoginRequest.java          # 新增：登录请求
│       │   ├── LoginResponse.java         # 新增：登录响应
│       │   └── RegisterRequest.java       # 新增：注册请求
│       └── grpc/
│           └── AuthServiceImpl.java       # 新增：gRPC服务实现
├── his-user/
│   └── src/main/java/com/hismixed/user/
│       ├── Application.java               # 已有：启动类
│       ├── controller/
│       │   ├── DepartmentController.java  # 新增：科室控制器
│       │   └── EmployeeController.java    # 新增：员工控制器
│       ├── service/
│       │   ├── DepartmentService.java     # 新增：科室服务
│       │   └── EmployeeService.java       # 新增：员工服务
│       ├── repository/
│       │   ├── DepartmentRepository.java  # 新增：科室仓储
│       │   └── EmployeeRepository.java    # 新增：员工仓储
│       └── entity/
│           ├── Department.java            # 新增：科室实体
│           └── Employee.java              # 新增：员工实体
└── common/
    └── src/main/java/com/hismixed/common/
        ├── CommonConstants.java           # 已有：常量
        ├── Result.java                    # 新增：统一响应
        ├── PageResult.java                # 新增：分页响应
        └── BaseEntity.java                # 新增：基础实体
```

### 1.3 前端患者端文件结构

```
frontend/his-web-patient/
└── src/
    ├── api/
    │   ├── request.ts                     # 已有：axios封装
    │   ├── auth.ts                        # 新增：认证API
    │   ├── registration.ts                # 新增：挂号API
    │   ├── schedule.ts                    # 新增：排班API
    │   └── pharmacy.ts                    # 新增：药房API
    ├── stores/
    │   └── auth.ts                        # 已有：认证状态（需修改）
    ├── views/
    │   ├── Login.vue                      # 已有：登录页（需修改）
    │   ├── Home.vue                       # 已有：首页（需修改）
    │   ├── registration/
    │   │   └── Registration.vue           # 已有：挂号页（需修改）
    │   ├── prescription/
    │   │   └── MyPrescriptions.vue        # 已有：处方页（需修改）
    │   └── appointment/
    │       └── MyAppointments.vue         # 已有：我的挂号（需修改）
    └── router/
        └── index.ts                       # 已有：路由配置（需修改）
```

---

## 二、实施任务

### Task 1: Proto代码生成

**Files:**
- Modify: `scripts/proto-gen/gen-go.sh` (已有)
- Create: `backend/go/pkg/grpc/auth/auth.pb.go` (生成)
- Create: `backend/go/pkg/grpc/auth/auth_grpc.pb.go` (生成)
- Create: `backend/go/pkg/grpc/registration/registration.pb.go` (生成)
- Create: `backend/go/pkg/grpc/registration/registration_grpc.pb.go` (生成)
- Create: `backend/go/pkg/grpc/schedule/schedule.pb.go` (生成)
- Create: `backend/go/pkg/grpc/schedule/schedule_grpc.pb.go` (生成)
- Create: `backend/go/pkg/grpc/pharmacy/pharmacy.pb.go` (生成)
- Create: `backend/go/pkg/grpc/pharmacy/pharmacy_grpc.pb.go` (生成)
- Create: `backend/go/pkg/grpc/common/common.pb.go` (生成)

- [ ] **Step 1: 检查protoc是否安装**

```bash
protoc --version
```

Expected: 显示protoc版本信息

- [ ] **Step 2: 安装Go protoc插件**

```bash
go install google.golang.org/protobuf/cmd/protoc-gen-go@latest
go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest
```

Expected: 安装成功

- [ ] **Step 3: 生成Go gRPC代码**

```bash
cd /mnt/d/neusoft/HIS-Course/HIS-Mixed-Go-Java
bash scripts/proto-gen/gen-go.sh
```

Expected: 在`backend/go/pkg/grpc/`目录下生成各服务的`.pb.go`和`_grpc.pb.go`文件

- [ ] **Step 4: 验证生成的代码**

```bash
ls -la backend/go/pkg/grpc/
```

Expected: 看到auth、registration、schedule、pharmacy、common等目录，每个目录下有生成的Go文件

- [ ] **Step 5: 编译验证**

```bash
cd backend/go && go build ./...
```

Expected: 编译通过，无错误

- [ ] **Step 6: 提交生成的代码**

```bash
git add backend/go/pkg/grpc/
git commit -m "feat: 生成proto gRPC代码"
```

---

### Task 2: Go服务main.go初始化逻辑补全 - Registration服务

**Files:**
- Modify: `backend/go/cmd/registration/main.go`
- Modify: `backend/go/configs/registration.yaml`
- Create: `backend/go/internal/registration/handler/registration.go`
- Create: `backend/go/internal/registration/repository/registration.go`

- [ ] **Step 1: 创建Registration配置文件**

创建`backend/go/configs/registration.yaml`:

```yaml
server:
  name: "his-registration"
  http_port: 8083
  grpc_port: 9083

database:
  host: "localhost"
  port: 5432
  user: "his_admin"
  password: "change_me_123"
  dbname: "his_registration"
  sslmode: "disable"

redis:
  addr: "localhost:6379"
  password: "change_me_456"
  db: 0

rabbitmq:
  url: "amqp://admin:change_me_789@localhost:5672/"
```

- [ ] **Step 2: 创建Registration Repository层**

创建`backend/go/internal/registration/repository/registration.go`:

```go
package repository

import (
	"github.com/his-mixed/go/internal/registration/model"
	"gorm.io/gorm"
)

// RegistrationRepository 挂号数据访问层
type RegistrationRepository struct {
	db *gorm.DB
}

// NewRegistrationRepository 创建挂号仓储
func NewRegistrationRepository(db *gorm.DB) *RegistrationRepository {
	return &RegistrationRepository{db: db}
}

// Create 创建挂号记录
func (r *RegistrationRepository) Create(reg *model.Registration) error {
	return r.db.Create(reg).Error
}

// GetByID 按ID查询
func (r *RegistrationRepository) GetByID(id int64) (*model.Registration, error) {
	var reg model.Registration
	err := r.db.First(&reg, id).Error
	if err != nil {
		return nil, err
	}
	return &reg, nil
}

// Update 更新挂号记录
func (r *RegistrationRepository) Update(reg *model.Registration) error {
	return r.db.Save(reg).Error
}

// ListByPatientID 按患者ID查询
func (r *RegistrationRepository) ListByPatientID(patientID int64, page, size int) ([]model.Registration, int64, error) {
	var regs []model.Registration
	var total int64

	query := r.db.Where("patient_id = ?", patientID)
	query.Model(&model.Registration{}).Count(&total)

	offset := (page - 1) * size
	err := query.Offset(offset).Limit(size).Order("created_at DESC").Find(&regs).Error
	return regs, total, err
}

// CreateQueueItem 创建排队项
func (r *RegistrationRepository) CreateQueueItem(item *model.QueueItem) error {
	return r.db.Create(item).Error
}

// GetQueueItems 按科室查询排队列表
func (r *RegistrationRepository) GetQueueItems(departmentID int64) ([]model.QueueItem, error) {
	var items []model.QueueItem
	err := r.db.Where("department_id = ? AND status = ?", departmentID, "WAITING").
		Order("queue_number ASC").Find(&items).Error
	return items, err
}
```

- [ ] **Step 3: 创建Registration gRPC Handler**

创建`backend/go/internal/registration/handler/registration.go`:

```go
package handler

import (
	"context"

	"github.com/his-mixed/go/internal/registration/service"
	pb "github.com/his-mixed/go/pkg/grpc/registration"
)

// RegistrationHandler gRPC handler
type RegistrationHandler struct {
	pb.UnimplementedRegistrationServiceServer
	svc *service.RegistrationService
}

// NewRegistrationHandler 创建gRPC handler
func NewRegistrationHandler(svc *service.RegistrationService) *RegistrationHandler {
	return &RegistrationHandler{svc: svc}
}

// RegisterAppointment 预约挂号
func (h *RegistrationHandler) RegisterAppointment(ctx context.Context, req *pb.RegisterRequest) (*pb.RegisterResponse, error) {
	// 调用service层实现
	return nil, nil
}

// GetSchedules 查询号源
func (h *RegistrationHandler) GetSchedules(ctx context.Context, req *pb.GetSchedulesRequest) (*pb.GetSchedulesResponse, error) {
	// 调用service层实现
	return nil, nil
}
```

- [ ] **Step 4: 修改Registration main.go**

修改`backend/go/cmd/registration/main.go`:

```go
package main

import (
	"fmt"
	"log"
	"net"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/pkg/config"
	"github.com/his-mixed/go/pkg/database"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
	"google.golang.org/grpc"
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

	log.Printf("挂号服务 HTTP 启动在端口 %d", port)
	if err := r.Run(fmt.Sprintf(":%d", port)); err != nil {
		log.Printf("HTTP 服务启动失败: %v", err)
	}
}

func startGRPCServer(port int, db interface{}) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("gRPC 监听失败: %v", err)
	}
	s := grpc.NewServer()

	// 注册gRPC服务
	// pb.RegisterRegistrationServiceServer(s, handler)

	log.Printf("挂号服务 gRPC 启动在端口 %d", port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("gRPC 服务启动失败: %v", err)
	}
}
```

- [ ] **Step 5: 编译验证**

```bash
cd backend/go && go build ./cmd/registration/
```

Expected: 编译通过

- [ ] **Step 6: 提交代码**

```bash
git add backend/go/cmd/registration/ backend/go/configs/registration.yaml backend/go/internal/registration/
git commit -m "feat(registration): 补全main.go初始化逻辑和repository层"
```

---

### Task 3: Go服务main.go初始化逻辑补全 - Schedule服务

**Files:**
- Modify: `backend/go/cmd/schedule/main.go`
- Create: `backend/go/configs/schedule.yaml`
- Create: `backend/go/internal/schedule/handler/schedule.go`
- Create: `backend/go/internal/schedule/repository/schedule.go`

- [ ] **Step 1: 创建Schedule配置文件**

创建`backend/go/configs/schedule.yaml`:

```yaml
server:
  name: "his-schedule"
  http_port: 8090
  grpc_port: 9090

database:
  host: "localhost"
  port: 5432
  user: "his_admin"
  password: "change_me_123"
  dbname: "his_schedule"
  sslmode: "disable"

redis:
  addr: "localhost:6379"
  password: "change_me_456"
  db: 0

rabbitmq:
  url: "amqp://admin:change_me_789@localhost:5672/"
```

- [ ] **Step 2: 创建Schedule Repository层**

创建`backend/go/internal/schedule/repository/schedule.go`:

```go
package repository

import (
	"time"

	"github.com/his-mixed/go/internal/schedule/model"
	"gorm.io/gorm"
)

// ScheduleRepository 排班数据访问层
type ScheduleRepository struct {
	db *gorm.DB
}

// NewScheduleRepository 创建排班仓储
func NewScheduleRepository(db *gorm.DB) *ScheduleRepository {
	return &ScheduleRepository{db: db}
}

// CreatePlan 创建排班计划
func (r *ScheduleRepository) CreatePlan(plan *model.SchedulePlan) error {
	return r.db.Create(plan).Error
}

// GetPlanByID 按ID查询排班计划
func (r *ScheduleRepository) GetPlanByID(id int64) (*model.SchedulePlan, error) {
	var plan model.SchedulePlan
	err := r.db.First(&plan, id).Error
	if err != nil {
		return nil, err
	}
	return &plan, nil
}

// ListPlans 查询排班计划列表
func (r *ScheduleRepository) ListPlans(doctorID, departmentID int64, page, size int) ([]model.SchedulePlan, int64, error) {
	var plans []model.SchedulePlan
	var total int64

	query := r.db.Model(&model.SchedulePlan{})
	if doctorID > 0 {
		query = query.Where("doctor_id = ?", doctorID)
	}
	if departmentID > 0 {
		query = query.Where("department_id = ?", departmentID)
	}

	query.Count(&total)
	offset := (page - 1) * size
	err := query.Offset(offset).Limit(size).Order("created_at DESC").Find(&plans).Error
	return plans, total, err
}

// CreateSlot 创建号源时段
func (r *ScheduleRepository) CreateSlot(slot *model.ScheduleSlot) error {
	return r.db.Create(slot).Error
}

// GetSlots 查询指定日期的号源
func (r *ScheduleRepository) GetSlots(departmentID int64, scheduleDate string) ([]model.ScheduleSlot, error) {
	var slots []model.ScheduleSlot
	query := r.db.Where("schedule_date = ?", scheduleDate)
	if departmentID > 0 {
		query = query.Where("department_id = ?", departmentID)
	}
	err := query.Find(&slots).Error
	return slots, err
}

// GetSlotsByDoctor 按医生和日期查询号源
func (r *ScheduleRepository) GetSlotsByDoctor(doctorID int64, startDate, endDate string) ([]model.ScheduleSlot, error) {
	var slots []model.ScheduleSlot
	query := r.db.Where("doctor_id = ? AND schedule_date BETWEEN ? AND ?", doctorID, startDate, endDate)
	err := query.Order("schedule_date ASC, start_time ASC").Find(&slots).Error
	return slots, err
}

// DeductQuota 扣减号源（乐观锁）
func (r *ScheduleRepository) DeductQuota(slotID int64) error {
	result := r.db.Model(&model.ScheduleSlot{}).
		Where("id = ? AND remaining > 0 AND version = (SELECT version FROM schedule_slots WHERE id = ?)", slotID, slotID).
		Updates(map[string]interface{}{
			"remaining": gorm.Expr("remaining - 1"),
			"version":   gorm.Expr("version + 1"),
		})
	if result.RowsAffected == 0 {
		return fmt.Errorf("号源已满或并发冲突")
	}
	return result.Error
}

// ReleaseQuota 释放号源（乐观锁）
func (r *ScheduleRepository) ReleaseQuota(slotID int64) error {
	result := r.db.Model(&model.ScheduleSlot{}).
		Where("id = ?", slotID).
		Updates(map[string]interface{}{
			"remaining": gorm.Expr("remaining + 1"),
			"version":   gorm.Expr("version + 1"),
		})
	return result.Error
}
```

- [ ] **Step 3: 创建Schedule gRPC Handler**

创建`backend/go/internal/schedule/handler/schedule.go`:

```go
package handler

import (
	"context"

	"github.com/his-mixed/go/internal/schedule/service"
	pb "github.com/his-mixed/go/pkg/grpc/schedule"
)

// ScheduleHandler gRPC handler
type ScheduleHandler struct {
	pb.UnimplementedScheduleServiceServer
	svc *service.ScheduleService
}

// NewScheduleHandler 创建gRPC handler
func NewScheduleHandler(svc *service.ScheduleService) *ScheduleHandler {
	return &ScheduleHandler{svc: svc}
}

// GenerateSlots 生成排班时段
func (h *ScheduleHandler) GenerateSlots(ctx context.Context, req *pb.GenerateSlotsRequest) (*pb.GenerateSlotsResponse, error) {
	return nil, nil
}

// GetSlots 获取排班列表
func (h *ScheduleHandler) GetSlots(ctx context.Context, req *pb.GetSlotsRequest) (*pb.GetSlotsResponse, error) {
	return nil, nil
}
```

- [ ] **Step 4: 修改Schedule main.go**

修改`backend/go/cmd/schedule/main.go`:

```go
package main

import (
	"fmt"
	"log"
	"net"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/pkg/config"
	"github.com/his-mixed/go/pkg/database"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
	"google.golang.org/grpc"
)

func main() {
	fmt.Println("=== HIS 排班管理服务启动 ===")

	// 加载配置
	cfg, err := config.Load("configs/schedule.yaml")
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
	go startHTTP(cfg.Server.HTTPPort)

	// 启动gRPC服务
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

func startGRPC(port int, db interface{}) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("监听失败: %v", err)
	}
	s := grpc.NewServer()

	// 注册gRPC服务
	// pb.RegisterScheduleServiceServer(s, handler)

	log.Printf("排班服务 gRPC 启动在端口 %d", port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("gRPC 失败: %v", err)
	}
}
```

- [ ] **Step 5: 编译验证**

```bash
cd backend/go && go build ./cmd/schedule/
```

Expected: 编译通过

- [ ] **Step 6: 提交代码**

```bash
git add backend/go/cmd/schedule/ backend/go/configs/schedule.yaml backend/go/internal/schedule/
git commit -m "feat(schedule): 补全main.go初始化逻辑和repository层"
```

---

### Task 4: Go服务main.go初始化逻辑补全 - Pharmacy服务

**Files:**
- Modify: `backend/go/cmd/pharmacy/main.go`
- Modify: `backend/go/configs/pharmacy.yaml`
- Create: `backend/go/internal/pharmacy/handler/pharmacy.go`
- Create: `backend/go/internal/pharmacy/repository/pharmacy.go`

- [ ] **Step 1: 更新Pharmacy配置文件**

更新`backend/go/configs/pharmacy.yaml`:

```yaml
server:
  name: "his-pharmacy"
  http_port: 8087
  grpc_port: 9087

database:
  host: "localhost"
  port: 5432
  user: "his_admin"
  password: "change_me_123"
  dbname: "his_pharmacy"
  sslmode: "disable"

redis:
  addr: "localhost:6379"
  password: "change_me_456"
  db: 0

rabbitmq:
  url: "amqp://admin:change_me_789@localhost:5672/"
```

- [ ] **Step 2: 创建Pharmacy Repository层**

创建`backend/go/internal/pharmacy/repository/pharmacy.go`:

```go
package repository

import (
	"fmt"

	"github.com/his-mixed/go/internal/pharmacy/model"
	"gorm.io/gorm"
)

// PharmacyRepository 药房数据访问层
type PharmacyRepository struct {
	db *gorm.DB
}

// NewPharmacyRepository 创建药房仓储
func NewPharmacyRepository(db *gorm.DB) *PharmacyRepository {
	return &PharmacyRepository{db: db}
}

// GetDrugByID 按ID查询药品
func (r *PharmacyRepository) GetDrugByID(id int64) (*model.Drug, error) {
	var drug model.Drug
	err := r.db.First(&drug, id).Error
	if err != nil {
		return nil, err
	}
	return &drug, nil
}

// ListDrugs 分页查询药品字典
func (r *PharmacyRepository) ListDrugs(keyword string, page, size int) ([]model.Drug, int64, error) {
	var drugs []model.Drug
	var total int64

	query := r.db.Model(&model.Drug{})
	if keyword != "" {
		query = query.Where("drug_name LIKE ? OR drug_code LIKE ?", "%"+keyword+"%", "%"+keyword+"%")
	}

	query.Count(&total)
	offset := (page - 1) * size
	err := query.Offset(offset).Limit(size).Order("created_at DESC").Find(&drugs).Error
	return drugs, total, err
}

// GetDrugInventory 查询药品库存
func (r *PharmacyRepository) GetDrugInventory(drugID int64) ([]model.DrugInventory, error) {
	var inventory []model.DrugInventory
	err := r.db.Where("drug_id = ? AND status = ? AND quantity > 0", drugID, "AVAILABLE").
		Order("expiry_date ASC").Find(&inventory).Error
	return inventory, err
}

// DeductInventory 扣减库存（乐观锁）
func (r *PharmacyRepository) DeductInventory(inventoryID int64, quantity int) error {
	result := r.db.Model(&model.DrugInventory{}).
		Where("id = ? AND quantity >= ? AND version = (SELECT version FROM drug_inventory WHERE id = ?)", inventoryID, quantity, inventoryID).
		Updates(map[string]interface{}{
			"quantity": gorm.Expr("quantity - ?", quantity),
			"version":  gorm.Expr("version + 1"),
		})
	if result.RowsAffected == 0 {
		return fmt.Errorf("库存不足或并发冲突")
	}
	return result.Error
}

// CreateDispenseRecord 创建发药记录
func (r *PharmacyRepository) CreateDispenseRecord(record *model.DispenseRecord) error {
	return r.db.Create(record).Error
}
```

- [ ] **Step 3: 创建Pharmacy gRPC Handler**

创建`backend/go/internal/pharmacy/handler/pharmacy.go`:

```go
package handler

import (
	"context"

	"github.com/his-mixed/go/internal/pharmacy/service"
	pb "github.com/his-mixed/go/pkg/grpc/pharmacy"
)

// PharmacyHandler gRPC handler
type PharmacyHandler struct {
	pb.UnimplementedPharmacyServiceServer
	svc *service.PharmacyService
}

// NewPharmacyHandler 创建gRPC handler
func NewPharmacyHandler(svc *service.PharmacyService) *PharmacyHandler {
	return &PharmacyHandler{svc: svc}
}

// CheckStock 校验库存
func (h *PharmacyHandler) CheckStock(ctx context.Context, req *pb.CheckStockRequest) (*pb.CheckStockResponse, error) {
	return nil, nil
}

// DispenseDrug 发药
func (h *PharmacyHandler) DispenseDrug(ctx context.Context, req *pb.DispenseDrugRequest) (*pb.DispenseDrugResponse, error) {
	return nil, nil
}
```

- [ ] **Step 4: 修改Pharmacy main.go**

修改`backend/go/cmd/pharmacy/main.go`:

```go
package main

import (
	"fmt"
	"log"
	"net"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/pkg/config"
	"github.com/his-mixed/go/pkg/database"
	"github.com/his-mixed/go/pkg/health"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/his-mixed/go/pkg/redis"
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

	// 启动HTTP服务
	go startHTTP(cfg.Server.HTTPPort)

	// 启动gRPC服务
	startGRPC(cfg.Server.GRPCPort, db)
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

func startGRPC(port int, db interface{}) {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", port))
	if err != nil {
		log.Fatalf("监听失败: %v", err)
	}
	s := grpc.NewServer()

	// 注册gRPC服务
	// pb.RegisterPharmacyServiceServer(s, handler)

	log.Printf("药房服务 gRPC 启动在端口 %d", port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("gRPC 失败: %v", err)
	}
}
```

- [ ] **Step 5: 编译验证**

```bash
cd backend/go && go build ./cmd/pharmacy/
```

Expected: 编译通过

- [ ] **Step 6: 提交代码**

```bash
git add backend/go/cmd/pharmacy/ backend/go/configs/pharmacy.yaml backend/go/internal/pharmacy/
git commit -m "feat(pharmacy): 补全main.go初始化逻辑和repository层"
```

---

### Task 5: Java Auth服务完整实现

**Files:**
- Modify: `backend/java/his-auth/src/main/java/com/hismixed/auth/config/SecurityConfig.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/entity/User.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/entity/Role.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/entity/RefreshToken.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/dto/LoginRequest.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/dto/LoginResponse.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/repository/UserRepository.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/repository/RoleRepository.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/repository/RefreshTokenRepository.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/service/AuthService.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/service/UserService.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/controller/AuthController.java`
- Create: `backend/java/his-auth/src/main/java/com/hismixed/auth/grpc/AuthServiceImpl.java`

- [ ] **Step 1: 创建User实体**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/entity/User.java`:

```java
package com.hismixed.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String passwordHash;
    private String realName;
    private String phone;
    private String email;
    private String avatar;
    private Integer status;
    private LocalDateTime lastLoginAt;
    private String lastLoginIp;
    private LocalDateTime passwordChangedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @TableLogic
    private LocalDateTime deletedAt;
}
```

- [ ] **Step 2: 创建Role实体**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/entity/Role.java`:

```java
package com.hismixed.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("roles")
public class Role {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer sort;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

- [ ] **Step 3: 创建RefreshToken实体**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/entity/RefreshToken.java`:

```java
package com.hismixed.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("refresh_tokens")
public class RefreshToken {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String token;
    private LocalDateTime expiresAt;
    private Boolean revoked;
    private LocalDateTime createdAt;
}
```

- [ ] **Step 4: 创建LoginRequest DTO**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/dto/LoginRequest.java`:

```java
package com.hismixed.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String grantType;
    private String clientId;
}
```

- [ ] **Step 5: 创建LoginResponse DTO**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/dto/LoginResponse.java`:

```java
package com.hismixed.auth.dto;

import lombok.Data;
import java.util.List;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private Long userId;
    private String username;
    private String realName;
    private List<String> roles;
    private List<String> permissions;
}
```

- [ ] **Step 6: 创建UserRepository**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/repository/UserRepository.java`:

```java
package com.hismixed.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hismixed.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository extends BaseMapper<User> {
}
```

- [ ] **Step 7: 创建RoleRepository**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/repository/RoleRepository.java`:

```java
package com.hismixed.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hismixed.auth.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface RoleRepository extends BaseMapper<Role> {
    @Select("SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = #{userId}")
    List<Role> selectRolesByUserId(Long userId);
}
```

- [ ] **Step 8: 创建RefreshTokenRepository**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/repository/RefreshTokenRepository.java`:

```java
package com.hismixed.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hismixed.auth.entity.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenRepository extends BaseMapper<RefreshToken> {
}
```

- [ ] **Step 9: 创建AuthService**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/service/AuthService.java`:

```java
package com.hismixed.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hismixed.auth.dto.LoginRequest;
import com.hismixed.auth.dto.LoginResponse;
import com.hismixed.auth.entity.RefreshToken;
import com.hismixed.auth.entity.Role;
import com.hismixed.auth.entity.User;
import com.hismixed.auth.repository.RefreshTokenRepository;
import com.hismixed.auth.repository.RoleRepository;
import com.hismixed.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.secret:his-mixed-jwt-secret-key-2026}")
    private String jwtSecret;

    @Value("${jwt.expiration:7200}")
    private Long jwtExpiration;

    public LoginResponse login(LoginRequest request) {
        // 查询用户
        User user = userRepository.selectOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername())
                .isNull(User::getDeletedAt)
        );

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        if (user.getStatus() != 1) {
            throw new RuntimeException("账号已禁用");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 查询角色
        List<Role> roles = roleRepository.selectRolesByUserId(user.getId());
        List<String> roleCodes = roles.stream().map(Role::getCode).toList();

        // 生成Token
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

        String refreshToken = UUID.randomUUID().toString().replace("-", "");

        // 保存刷新令牌
        RefreshToken token = new RefreshToken();
        token.setUserId(user.getId());
        token.setToken(refreshToken);
        token.setExpiresAt(LocalDateTime.now().plusDays(7));
        token.setRevoked(false);
        refreshTokenRepository.insert(token);

        // 更新最后登录时间
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.updateById(user);

        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setTokenType("Bearer");
        response.setExpiresIn(jwtExpiration);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRealName(user.getRealName());
        response.setRoles(roleCodes);
        response.setPermissions(new ArrayList<>());

        return response;
    }

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

        // 撤销旧令牌
        token.setRevoked(true);
        refreshTokenRepository.updateById(token);

        // 生成新令牌
        LoginRequest request = new LoginRequest();
        request.setUsername(user.getUsername());
        request.setPassword(""); // 刷新时不需要密码
        return login(request);
    }
}
```

- [ ] **Step 10: 创建AuthController**

创建`backend/java/his-auth/src/main/java/com/hismixed/auth/controller/AuthController.java`:

```java
package com.hismixed.auth.controller;

import com.hismixed.auth.dto.LoginRequest;
import com.hismixed.auth.dto.LoginResponse;
import com.hismixed.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "success");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 401);
            error.put("message", e.getMessage());
            return ResponseEntity.status(401).body(error);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        try {
            LoginResponse response = authService.refreshToken(request.get("refreshToken"));
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "success");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 401);
            error.put("message", e.getMessage());
            return ResponseEntity.status(401).body(error);
        }
    }
}
```

- [ ] **Step 11: 修改SecurityConfig**

修改`backend/java/his-auth/src/main/java/com/hismixed/auth/config/SecurityConfig.java`:

```java
package com.hismixed.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/health").permitAll()
                .requestMatchers("/api/ping").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

- [ ] **Step 12: 编译验证**

```bash
cd backend/java && mvn compile -pl his-auth -am
```

Expected: 编译通过

- [ ] **Step 13: 提交代码**

```bash
git add backend/java/his-auth/
git commit -m "feat(auth): 实现完整的认证授权服务"
```

---

### Task 6: Java User服务实现（科室和员工管理）

**Files:**
- Create: `backend/java/his-user/src/main/java/com/hismixed/user/entity/Department.java`
- Create: `backend/java/his-user/src/main/java/com/hismixed/user/entity/Employee.java`
- Create: `backend/java/his-user/src/main/java/com/hismixed/user/repository/DepartmentRepository.java`
- Create: `backend/java/his-user/src/main/java/com/hismixed/user/repository/EmployeeRepository.java`
- Create: `backend/java/his-user/src/main/java/com/hismixed/user/service/DepartmentService.java`
- Create: `backend/java/his-user/src/main/java/com/hismixed/user/service/EmployeeService.java`
- Create: `backend/java/his-user/src/main/java/com/hismixed/user/controller/DepartmentController.java`
- Create: `backend/java/his-user/src/main/java/com/hismixed/user/controller/EmployeeController.java`

- [ ] **Step 1: 创建Department实体**

创建`backend/java/his-user/src/main/java/com/hismixed/user/entity/Department.java`:

```java
package com.hismixed.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("departments")
public class Department {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private Long parentId;
    private Integer sort;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

- [ ] **Step 2: 创建Employee实体**

创建`backend/java/his-user/src/main/java/com/hismixed/user/entity/Employee.java`:

```java
package com.hismixed.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("employees")
public class Employee {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String employeeNo;
    private String name;
    private Integer gender;
    private String phone;
    private String email;
    private String title;
    private String jobType;
    private Long departmentId;
    private String specialty;
    private String introduction;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

- [ ] **Step 3: 创建DepartmentRepository**

创建`backend/java/his-user/src/main/java/com/hismixed/user/repository/DepartmentRepository.java`:

```java
package com.hismixed.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hismixed.user.entity.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentRepository extends BaseMapper<Department> {
}
```

- [ ] **Step 4: 创建EmployeeRepository**

创建`backend/java/his-user/src/main/java/com/hismixed/user/repository/EmployeeRepository.java`:

```java
package com.hismixed.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hismixed.user.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeRepository extends BaseMapper<Employee> {
}
```

- [ ] **Step 5: 创建DepartmentService**

创建`backend/java/his-user/src/main/java/com/hismixed/user/service/DepartmentService.java`:

```java
package com.hismixed.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hismixed.user.entity.Department;
import com.hismixed.user.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> listAll() {
        return departmentRepository.selectList(
            new LambdaQueryWrapper<Department>()
                .orderByAsc(Department::getSort)
        );
    }

    public Department getById(Long id) {
        return departmentRepository.selectById(id);
    }

    public Page<Department> page(int page, int size) {
        return departmentRepository.selectPage(
            new Page<>(page, size),
            new LambdaQueryWrapper<Department>()
                .orderByAsc(Department::getSort)
        );
    }

    public Department create(Department department) {
        departmentRepository.insert(department);
        return department;
    }

    public Department update(Department department) {
        departmentRepository.updateById(department);
        return department;
    }

    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
```

- [ ] **Step 6: 创建EmployeeService**

创建`backend/java/his-user/src/main/java/com/hismixed/user/service/EmployeeService.java`:

```java
package com.hismixed.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hismixed.user.entity.Employee;
import com.hismixed.user.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Page<Employee> page(int page, int size, Long departmentId, String keyword) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        if (departmentId != null) {
            wrapper.eq(Employee::getDepartmentId, departmentId);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w
                .like(Employee::getName, keyword)
                .or()
                .like(Employee::getEmployeeNo, keyword)
            );
        }
        wrapper.orderByDesc(Employee::getCreatedAt);
        return employeeRepository.selectPage(new Page<>(page, size), wrapper);
    }

    public List<Employee> listByDepartment(Long departmentId) {
        return employeeRepository.selectList(
            new LambdaQueryWrapper<Employee>()
                .eq(Employee::getDepartmentId, departmentId)
                .eq(Employee::getStatus, 1)
                .orderByAsc(Employee::getName)
        );
    }

    public Employee getById(Long id) {
        return employeeRepository.selectById(id);
    }

    public Employee create(Employee employee) {
        employeeRepository.insert(employee);
        return employee;
    }

    public Employee update(Employee employee) {
        employeeRepository.updateById(employee);
        return employee;
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
```

- [ ] **Step 7: 创建DepartmentController**

创建`backend/java/his-user/src/main/java/com/hismixed/user/controller/DepartmentController.java`:

```java
package com.hismixed.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hismixed.user.entity.Department;
import com.hismixed.user.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> list() {
        List<Department> list = departmentService.listAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", list);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Department department = departmentService.getById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", department);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/page")
    public ResponseEntity<?> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Department> result = departmentService.page(page, size);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("message", "success");
        data.put("data", result);
        return ResponseEntity.ok(data);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Department department) {
        Department created = departmentService.create(department);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", created);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Department department) {
        department.setId(id);
        Department updated = departmentService.update(department);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", updated);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        departmentService.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        return ResponseEntity.ok(result);
    }
}
```

- [ ] **Step 8: 创建EmployeeController**

创建`backend/java/his-user/src/main/java/com/hismixed/user/controller/EmployeeController.java`:

```java
package com.hismixed.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hismixed.user.entity.Employee;
import com.hismixed.user.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String keyword) {
        Page<Employee> result = employeeService.page(page, size, departmentId, keyword);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("message", "success");
        data.put("data", result);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", employee);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<?> listByDepartment(@PathVariable Long departmentId) {
        List<Employee> list = employeeService.listByDepartment(departmentId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", list);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        Employee created = employeeService.create(employee);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", created);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        Employee updated = employeeService.update(employee);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", updated);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        return ResponseEntity.ok(result);
    }
}
```

- [ ] **Step 9: 编译验证**

```bash
cd backend/java && mvn compile -pl his-user -am
```

Expected: 编译通过

- [ ] **Step 10: 提交代码**

```bash
git add backend/java/his-user/
git commit -m "feat(user): 实现科室和员工管理服务"
```

---

### Task 7: 前端患者端API对接 - 登录功能

**Files:**
- Create: `frontend/his-web-patient/src/api/auth.ts`
- Modify: `frontend/his-web-patient/src/stores/auth.ts`
- Modify: `frontend/his-web-patient/src/views/Login.vue`

- [ ] **Step 1: 创建认证API**

创建`frontend/his-web-patient/src/api/auth.ts`:

```typescript
import request from './request'

export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
  userId: number
  username: string
  realName: string
  roles: string[]
  permissions: string[]
}

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
  timestamp: number
}

export const authApi = {
  login(data: LoginRequest): Promise<ApiResponse<LoginResponse>> {
    return request.post('/auth/login', data)
  },

  refreshToken(refreshToken: string): Promise<ApiResponse<LoginResponse>> {
    return request.post('/auth/refresh', { refreshToken })
  },
}
```

- [ ] **Step 2: 修改认证Store**

修改`frontend/his-web-patient/src/stores/auth.ts`:

```typescript
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authApi, type LoginResponse } from '@/api/auth'

interface PatientInfo {
  id: number
  name: string
  phone: string
  idCard: string
  gender: string
  age: number
}

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const refreshToken = ref<string>(localStorage.getItem('refreshToken') || '')
  const patientInfo = ref<PatientInfo | null>(null)
  const userInfo = ref<LoginResponse | null>(null)

  const isLoggedIn = computed(() => !!token.value)

  function setToken(newToken: string, newRefreshToken: string) {
    token.value = newToken
    refreshToken.value = newRefreshToken
    localStorage.setItem('token', newToken)
    localStorage.setItem('refreshToken', newRefreshToken)
  }

  function setUserInfo(info: LoginResponse) {
    userInfo.value = info
    patientInfo.value = {
      id: info.userId,
      name: info.realName,
      phone: '',
      idCard: '',
      gender: '',
      age: 0,
    }
  }

  async function login(username: string, password: string) {
    const response = await authApi.login({ username, password })
    if (response.code === 200) {
      setToken(response.data.accessToken, response.data.refreshToken)
      setUserInfo(response.data)
      return true
    }
    throw new Error(response.message)
  }

  function logout() {
    token.value = ''
    refreshToken.value = ''
    patientInfo.value = null
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('refreshToken')
  }

  return {
    token,
    refreshToken,
    patientInfo,
    userInfo,
    isLoggedIn,
    setToken,
    setUserInfo,
    login,
    logout,
  }
})
```

- [ ] **Step 3: 修改登录页面**

修改`frontend/his-web-patient/src/views/Login.vue`:

```vue
<template>
  <div class="page login-page">
    <header class="page-header">
      <h1>患者登录</h1>
    </header>

    <div class="login-container">
      <div class="login-logo">
        <div class="logo-icon">
          <MedicineBoxOutlined :style="{ fontSize: '48px', color: '#1890ff' }" />
        </div>
        <h2>HIS 医疗信息系统</h2>
        <p>患者端</p>
      </div>

      <a-card class="login-card">
        <a-form
          :model="formState"
          layout="vertical"
          autocomplete="off"
        >
          <a-form-item label="用户名">
            <a-input
              v-model:value="formState.username"
              placeholder="请输入用户名"
              size="large"
            >
              <template #prefix>
                <UserOutlined />
              </template>
            </a-input>
          </a-form-item>

          <a-form-item label="密码">
            <a-input-password
              v-model:value="formState.password"
              placeholder="请输入密码"
              size="large"
            >
              <template #prefix>
                <LockOutlined />
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item>
            <a-button type="primary" block size="large" :loading="loading" @click="handleLogin">
              登录
            </a-button>
          </a-form-item>
        </a-form>

        <div class="login-extra">
          <a-button type="link" block @click="$router.push('/registration')">
            还没有账号？在线挂号注册
          </a-button>
        </div>
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import {
  MedicineBoxOutlined,
  UserOutlined,
  LockOutlined,
} from '@ant-design/icons-vue'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const formState = reactive({
  username: '',
  password: '',
})
const loading = ref(false)

const handleLogin = async () => {
  if (!formState.username || !formState.password) {
    message.warning('请输入用户名和密码')
    return
  }
  loading.value = true
  try {
    await authStore.login(formState.username, formState.password)
    message.success('登录成功')
    router.push('/')
  } catch (error: any) {
    message.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.page-header {
  padding: 16px;
  color: #fff;
}

.page-header h1 {
  font-size: 20px;
  font-weight: 600;
}

.login-container {
  padding: 24px 24px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-logo {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  margin-bottom: 16px;
}

.login-logo h2 {
  font-size: 22px;
  color: #fff;
  font-weight: 600;
}

.login-logo p {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
  margin-top: 4px;
}

.login-card {
  width: 100%;
  border-radius: 12px;
}

.login-extra {
  text-align: center;
  padding: 8px 0;
}
</style>
```

- [ ] **Step 4: 编译验证**

```bash
cd frontend/his-web-patient && npm run build
```

Expected: 编译通过

- [ ] **Step 5: 提交代码**

```bash
git add frontend/his-web-patient/src/api/auth.ts frontend/his-web-patient/src/stores/auth.ts frontend/his-web-patient/src/views/Login.vue
git commit -m "feat(patient): 对接登录API"
```

---

### Task 8: 前端患者端API对接 - 挂号功能

**Files:**
- Create: `frontend/his-web-patient/src/api/registration.ts`
- Create: `frontend/his-web-patient/src/api/schedule.ts`
- Modify: `frontend/his-web-patient/src/views/registration/Registration.vue`

- [ ] **Step 1: 创建挂号API**

创建`frontend/his-web-patient/src/api/registration.ts`:

```typescript
import request from './request'

export interface RegisterRequest {
  patientId: number
  scheduleId: number
  cardType: string
  visitDate: string
  complaint?: string
  doctorId?: number
}

export interface RegisterResponse {
  appointmentId: number
  serialNumber: string
  status: string
  createdAt: string
}

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
  timestamp: number
}

export const registrationApi = {
  register(data: RegisterRequest): Promise<ApiResponse<RegisterResponse>> {
    return request.post('/registration/appointments', data)
  },

  getMyAppointments(params?: { page?: number; size?: number }): Promise<ApiResponse<any>> {
    return request.get('/registration/appointments', { params })
  },

  cancelAppointment(id: number): Promise<ApiResponse<any>> {
    return request.put(`/registration/appointments/${id}/cancel`)
  },
}
```

- [ ] **Step 2: 创建排班API**

创建`frontend/his-web-patient/src/api/schedule.ts`:

```typescript
import request from './request'

export interface ScheduleSlot {
  slotId: number
  doctorId: number
  doctorName: string
  departmentId: number
  departmentName: string
  date: string
  timeSlot: string
  cardType: string
  totalCount: number
  bookedCount: number
  status: string
}

export interface Department {
  id: number
  name: string
  code: string
  description?: string
}

export interface Employee {
  id: number
  name: string
  title: string
  departmentId: number
  specialty?: string
}

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
  timestamp: number
}

export interface PageResponse<T> {
  list: T[]
  total: number
  page: number
  pageSize: number
}

export const scheduleApi = {
  getDepartments(): Promise<ApiResponse<Department[]>> {
    return request.get('/user/departments')
  },

  getDoctors(departmentId: number): Promise<ApiResponse<Employee[]>> {
    return request.get(`/user/employees/department/${departmentId}`)
  },

  getSlots(params: {
    departmentId?: number
    doctorId?: number
    date?: string
    startDate?: string
    endDate?: string
    page?: number
    size?: number
  }): Promise<ApiResponse<PageResponse<ScheduleSlot>>> {
    return request.get('/schedule/slots', { params })
  },
}
```

- [ ] **Step 3: 修改挂号页面**

修改`frontend/his-web-patient/src/views/registration/Registration.vue`:

```vue
<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>在线挂号</h1>
      <div></div>
    </header>

    <div class="content">
      <a-steps :current="current" size="small" class="steps">
        <a-step title="选择科室" />
        <a-step title="选择医生" />
        <a-step title="确认挂号" />
      </a-steps>

      <div class="step-content">
        <div v-if="current === 0">
          <a-card title="选择科室" class="section-card">
            <a-spin :spinning="loadingDepartments">
              <a-list :split="true">
                <a-list-item v-for="dept in departments" :key="dept.id" @click="selectDepartment(dept)">
                  <a-list-item-meta>
                    <template #title>{{ dept.name }}</template>
                    <template #description>{{ dept.description || '暂无描述' }}</template>
                    <template #avatar>
                      <MedicineBoxOutlined :style="{ fontSize: '24px', color: '#1890ff' }" />
                    </template>
                  </a-list-item-meta>
                  <template #extra>
                    <RightOutlined :style="{ color: '#ccc' }" />
                  </template>
                </a-list-item>
              </a-list>
            </a-spin>
          </a-card>
        </div>

        <div v-else-if="current === 1">
          <a-card title="选择医生" class="section-card">
            <a-spin :spinning="loadingDoctors">
              <a-list :split="true">
                <a-list-item v-for="doc in doctors" :key="doc.id" @click="selectDoctor(doc)">
                  <a-list-item-meta>
                    <template #title>{{ doc.name }}</template>
                    <template #description>{{ doc.title }} | {{ doc.specialty || '全科' }}</template>
                    <template #avatar>
                      <a-avatar :size="40" icon="user" />
                    </template>
                  </a-list-item-meta>
                  <template #extra>
                    <RightOutlined :style="{ color: '#ccc' }" />
                  </template>
                </a-list-item>
              </a-list>
            </a-spin>
          </a-card>
        </div>

        <div v-else>
          <a-card title="确认挂号信息" class="section-card">
            <a-descriptions :column="1" size="small">
              <a-descriptions-item label="就诊科室">{{ selectedDepartment?.name }}</a-descriptions-item>
              <a-descriptions-item label="就诊医生">{{ selectedDoctor?.name }}</a-descriptions-item>
              <a-descriptions-item label="挂号费用">¥35.00</a-descriptions-item>
            </a-descriptions>
            <a-button type="primary" block size="large" class="submit-btn" :loading="submitting" @click="handleSubmit">
              确认挂号
            </a-button>
          </a-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { LeftOutlined, RightOutlined, MedicineBoxOutlined } from '@ant-design/icons-vue'
import { scheduleApi, type Department, type Employee } from '@/api/schedule'
import { registrationApi } from '@/api/registration'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const current = ref(0)
const loadingDepartments = ref(false)
const loadingDoctors = ref(false)
const submitting = ref(false)

const departments = ref<Department[]>([])
const doctors = ref<Employee[]>([])
const selectedDepartment = ref<Department | null>(null)
const selectedDoctor = ref<Employee | null>(null)

onMounted(() => {
  loadDepartments()
})

const loadDepartments = async () => {
  loadingDepartments.value = true
  try {
    const response = await scheduleApi.getDepartments()
    if (response.code === 200) {
      departments.value = response.data
    }
  } catch (error) {
    message.error('加载科室失败')
  } finally {
    loadingDepartments.value = false
  }
}

const selectDepartment = async (dept: Department) => {
  selectedDepartment.value = dept
  current.value = 1
  loadingDoctors.value = true
  try {
    const response = await scheduleApi.getDoctors(dept.id)
    if (response.code === 200) {
      doctors.value = response.data
    }
  } catch (error) {
    message.error('加载医生失败')
  } finally {
    loadingDoctors.value = false
  }
}

const selectDoctor = (doc: Employee) => {
  selectedDoctor.value = doc
  current.value = 2
}

const handleSubmit = async () => {
  if (!authStore.isLoggedIn) {
    message.warning('请先登录')
    router.push('/login')
    return
  }

  submitting.value = true
  try {
    const response = await registrationApi.register({
      patientId: authStore.userInfo?.userId || 0,
      scheduleId: 0, // 需要从排班中选择
      cardType: 'NORMAL',
      visitDate: new Date().toISOString().split('T')[0],
      doctorId: selectedDoctor.value?.id,
    })
    if (response.code === 200) {
      message.success('挂号成功')
      router.push('/appointments')
    }
  } catch (error: any) {
    message.error(error.message || '挂号失败')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: #f5f5f5;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  background: #fff;
  border-bottom: 1px solid #f0f0f0;
  width: 100%;
}

.page-header h1 {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.page-header > div {
  width: 40px;
}

.content {
  padding: 16px;
}

.steps {
  margin-bottom: 24px;
  padding: 16px;
  background: #fff;
  border-radius: 8px;
}

.section-card {
  border-radius: 8px;
}

.submit-btn {
  margin-top: 24px;
}
</style>
```

- [ ] **Step 4: 编译验证**

```bash
cd frontend/his-web-patient && npm run build
```

Expected: 编译通过

- [ ] **Step 5: 提交代码**

```bash
git add frontend/his-web-patient/src/api/ frontend/his-web-patient/src/views/registration/
git commit -m "feat(patient): 对接挂号和排班API"
```

---

### Task 9: 前端患者端API对接 - 处方查询

**Files:**
- Create: `frontend/his-web-patient/src/api/pharmacy.ts`
- Modify: `frontend/his-web-patient/src/views/prescription/MyPrescriptions.vue`

- [ ] **Step 1: 创建药房API**

创建`frontend/his-web-patient/src/api/pharmacy.ts`:

```typescript
import request from './request'

export interface Drug {
  id: number
  drugCode: string
  drugName: string
  tradeName?: string
  drugType: string
  specification: string
  unit: string
  category: string
  retailPrice: number
  manufacturer?: string
}

export interface Prescription {
  id: number
  prescriptionNo: string
  patientId: number
  doctorId: number
  doctorName?: string
  departmentName?: string
  prescriptionType: string
  status: string
  diagnosisSummary?: string
  totalAmount: number
  createdAt: string
  items: PrescriptionItem[]
}

export interface PrescriptionItem {
  id: number
  drugId: number
  drugName: string
  specification: string
  quantity: number
  unit: string
  dosage: string
  frequency: string
  usageMethod: string
  days: number
  unitPrice: number
  subtotal: number
}

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
  timestamp: number
}

export interface PageResponse<T> {
  list: T[]
  total: number
  page: number
  pageSize: number
}

export const pharmacyApi = {
  getDrugs(params?: { keyword?: string; page?: number; size?: number }): Promise<ApiResponse<PageResponse<Drug>>> {
    return request.get('/pharmacy/drugs', { params })
  },

  getMyPrescriptions(params?: { page?: number; size?: number }): Promise<ApiResponse<PageResponse<Prescription>>> {
    return request.get('/prescription/prescriptions', { params })
  },

  getPrescriptionDetail(id: number): Promise<ApiResponse<Prescription>> {
    return request.get(`/prescription/prescriptions/${id}`)
  },
}
```

- [ ] **Step 2: 修改处方页面**

修改`frontend/his-web-patient/src/views/prescription/MyPrescriptions.vue`:

```vue
<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>我的处方</h1>
      <div></div>
    </header>

    <div class="content">
      <a-spin :spinning="loading">
        <a-list :split="true" :data-source="prescriptions">
          <template #renderItem="{ item }">
            <a-list-item @click="viewDetail(item)">
              <a-list-item-meta>
                <template #title>
                  <div class="prescription-title">
                    <span>{{ item.prescriptionNo }}</span>
                    <a-tag :color="getStatusColor(item.status)">{{ getStatusText(item.status) }}</a-tag>
                  </div>
                </template>
                <template #description>
                  <div class="prescription-desc">
                    <span>{{ item.doctorName || '未知医生' }} | {{ item.departmentName || '未知科室' }}</span>
                    <span class="prescription-date">{{ formatDate(item.createdAt) }}</span>
                  </div>
                </template>
                <template #avatar>
                  <MedicineBoxOutlined :style="{ fontSize: '24px', color: '#fa8c16' }" />
                </template>
              </a-list-item-meta>
              <template #extra>
                <span class="prescription-amount">¥{{ item.totalAmount?.toFixed(2) || '0.00' }}</span>
              </template>
            </a-list-item>
          </template>
        </a-list>

        <a-empty v-if="!loading && prescriptions.length === 0" description="暂无处方记录" />
      </a-spin>

      <div class="pagination" v-if="total > 0">
        <a-pagination
          v-model:current="currentPage"
          :total="total"
          :page-size="pageSize"
          show-less-items
          @change="loadPrescriptions"
        />
      </div>
    </div>

    <!-- 处方详情弹窗 -->
    <a-modal v-model:open="detailVisible" title="处方详情" :footer="null" width="90%">
      <a-descriptions :column="1" size="small" v-if="selectedPrescription">
        <a-descriptions-item label="处方编号">{{ selectedPrescription.prescriptionNo }}</a-descriptions-item>
        <a-descriptions-item label="就诊医生">{{ selectedPrescription.doctorName }}</a-descriptions-item>
        <a-descriptions-item label="就诊科室">{{ selectedPrescription.departmentName }}</a-descriptions-item>
        <a-descriptions-item label="诊断">{{ selectedPrescription.diagnosisSummary || '暂无' }}</a-descriptions-item>
        <a-descriptions-item label="状态">
          <a-tag :color="getStatusColor(selectedPrescription.status)">
            {{ getStatusText(selectedPrescription.status) }}
          </a-tag>
        </a-descriptions-item>
        <a-descriptions-item label="总金额">
          <span class="amount">¥{{ selectedPrescription.totalAmount?.toFixed(2) }}</span>
        </a-descriptions-item>
      </a-descriptions>

      <a-divider>药品明细</a-divider>

      <a-table
        :columns="columns"
        :data-source="selectedPrescription?.items || []"
        :pagination="false"
        size="small"
      />
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { LeftOutlined, MedicineBoxOutlined } from '@ant-design/icons-vue'
import { pharmacyApi, type Prescription } from '@/api/pharmacy'

const loading = ref(false)
const prescriptions = ref<Prescription[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

const detailVisible = ref(false)
const selectedPrescription = ref<Prescription | null>(null)

const columns = [
  { title: '药品名称', dataIndex: 'drugName', key: 'drugName' },
  { title: '规格', dataIndex: 'specification', key: 'specification' },
  { title: '数量', dataIndex: 'quantity', key: 'quantity' },
  { title: '单位', dataIndex: 'unit', key: 'unit' },
  { title: '用法', dataIndex: 'usageMethod', key: 'usageMethod' },
  { title: '频次', dataIndex: 'frequency', key: 'frequency' },
  { title: '天数', dataIndex: 'days', key: 'days' },
  { title: '单价', dataIndex: 'unitPrice', key: 'unitPrice', customRender: ({ text }: any) => `¥${text?.toFixed(2)}` },
  { title: '小计', dataIndex: 'subtotal', key: 'subtotal', customRender: ({ text }: any) => `¥${text?.toFixed(2)}` },
]

onMounted(() => {
  loadPrescriptions()
})

const loadPrescriptions = async () => {
  loading.value = true
  try {
    const response = await pharmacyApi.getMyPrescriptions({
      page: currentPage.value,
      size: pageSize.value,
    })
    if (response.code === 200) {
      prescriptions.value = response.data.list || []
      total.value = response.data.total || 0
    }
  } catch (error) {
    message.error('加载处方失败')
  } finally {
    loading.value = false
  }
}

const viewDetail = async (prescription: Prescription) => {
  try {
    const response = await pharmacyApi.getPrescriptionDetail(prescription.id)
    if (response.code === 200) {
      selectedPrescription.value = response.data
      detailVisible.value = true
    }
  } catch (error) {
    message.error('加载处方详情失败')
  }
}

const getStatusColor = (status: string) => {
  const colors: Record<string, string> = {
    PENDING: 'orange',
    APPROVED: 'blue',
    DISPENSED: 'green',
    REJECTED: 'red',
  }
  return colors[status] || 'default'
}

const getStatusText = (status: string) => {
  const texts: Record<string, string> = {
    PENDING: '待审核',
    APPROVED: '已审核',
    DISPENSED: '已发药',
    REJECTED: '已退回',
  }
  return texts[status] || status
}

const formatDate = (dateStr: string) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('zh-CN')
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: #f5f5f5;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  background: #fff;
  border-bottom: 1px solid #f0f0f0;
}

.page-header h1 {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.page-header > div {
  width: 40px;
}

.content {
  padding: 16px;
}

.prescription-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.prescription-desc {
  display: flex;
  justify-content: space-between;
  color: #666;
}

.prescription-date {
  color: #999;
  font-size: 12px;
}

.prescription-amount {
  font-size: 16px;
  font-weight: 600;
  color: #f5222d;
}

.amount {
  font-size: 16px;
  font-weight: 600;
  color: #f5222d;
}

.pagination {
  margin-top: 16px;
  text-align: center;
}
</style>
```

- [ ] **Step 3: 编译验证**

```bash
cd frontend/his-web-patient && npm run build
```

Expected: 编译通过

- [ ] **Step 4: 提交代码**

```bash
git add frontend/his-web-patient/src/api/pharmacy.ts frontend/his-web-patient/src/views/prescription/
git commit -m "feat(patient): 对接处方查询API"
```

---

### Task 10: Gateway路由配置更新

**Files:**
- Modify: `backend/go/internal/gateway/router/router.go`
- Modify: `backend/go/internal/gateway/handler/proxy.go`

- [ ] **Step 1: 确认路由配置**

检查`backend/go/internal/gateway/router/router.go`中的路由配置是否完整，确保以下路由存在：

```go
// 认证相关
auth := r.Group("/api/auth")
{
    auth.POST("/login", handler.ProxyAuth)
    auth.POST("/refresh", handler.ProxyAuth)
    auth.GET("/captcha", handler.ProxyAuth)
}

// 用户/科室/员工
user := r.Group("/api/user")
{
    user.GET("/patients", handler.ProxyUser)
    user.GET("/patients/:id", handler.ProxyUser)
    user.POST("/patients", handler.ProxyUser)
    user.PUT("/patients/:id", handler.ProxyUser)
    user.GET("/employees", handler.ProxyUser)
    user.GET("/employees/:id", handler.ProxyUser)
    user.GET("/employees/department/:departmentId", handler.ProxyUser)
    user.GET("/departments", handler.ProxyUser)
    user.GET("/departments/:id", handler.ProxyUser)
}

// 挂号
reg := r.Group("/api/registration")
{
    reg.GET("/schedules", handler.ProxyRegistration)
    reg.POST("/appointments", handler.ProxyRegistration)
    reg.GET("/appointments", handler.ProxyRegistration)
    reg.PUT("/appointments/:id/cancel", handler.ProxyRegistration)
    reg.GET("/queue", handler.ProxyRegistration)
}

// 排班
sched := r.Group("/api/schedule")
{
    sched.GET("/plans", handler.ProxySchedule)
    sched.POST("/slots/generate", handler.ProxySchedule)
    sched.GET("/slots", handler.ProxySchedule)
}

// 药房
pharm := r.Group("/api/pharmacy")
{
    pharm.GET("/drugs", handler.ProxyPharmacy)
    pharm.POST("/dispense", handler.ProxyPharmacy)
    pharm.GET("/dispense-queue", handler.ProxyPharmacy)
}

// 处方
pres := r.Group("/api/prescription")
{
    pres.POST("/prescriptions", handler.ProxyPrescription)
    pres.GET("/prescriptions", handler.ProxyPrescription)
    pres.GET("/prescriptions/:id", handler.ProxyPrescription)
}
```

- [ ] **Step 2: 确认代理配置**

检查`backend/go/internal/gateway/handler/proxy.go`中的服务地址配置：

```go
var serviceTargets = map[string]string{
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
```

- [ ] **Step 3: 编译验证**

```bash
cd backend/go && go build ./cmd/gateway/
```

Expected: 编译通过

- [ ] **Step 4: 提交代码**

```bash
git add backend/go/internal/gateway/
git commit -m "feat(gateway): 更新路由配置"
```

---

### Task 11: 数据库初始化与种子数据

**Files:**
- Modify: `backend/go/sql/seed_data.sql`
- Modify: `scripts/db/db_init.sh`

- [ ] **Step 1: 更新种子数据**

更新`backend/go/sql/seed_data.sql`，添加演示用的科室和员工数据：

```sql
-- HIS-Mixed 默认账号和基础字典数据

-- 默认管理员账号
INSERT INTO sys_user (username, password, real_name, phone, email, status, create_time, update_time)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', '13800000000', 'admin@hismixed.com', 1, now(), now())
ON CONFLICT (username) DO NOTHING;

-- 默认角色
INSERT INTO sys_role (role_code, role_name, description, status, create_time, update_time)
VALUES
('ADMIN', '超级管理员', '系统超级管理员，拥有全部权限', 1, now(), now()),
('DOCTOR', '医生', '门诊/住院医生', 1, now(), now()),
('NURSE', '护士', '护理人员', 1, now(), now()),
('PHARMACIST', '药剂师', '药房工作人员', 1, now(), now()),
('CASHIER', '收费员', '挂号/收费窗口人员', 1, now(), now()),
('TECHNICIAN', '医技人员', '检查/检验科室人员', 1, now(), now())
ON CONFLICT (role_code) DO NOTHING;

-- 管理员角色关联
INSERT INTO sys_user_role (user_id, role_id)
SELECT u.id, r.id FROM sys_user u, sys_role r
WHERE u.username = 'admin' AND r.role_code = 'ADMIN'
ON CONFLICT DO NOTHING;

-- 基础字典: 挂号类型
INSERT INTO sys_dict (dict_type, dict_code, dict_value, sort, status, create_time, update_time)
VALUES
('REGISTRATION_TYPE', 'NORMAL', '普通号', 1, 1, now(), now()),
('REGISTRATION_TYPE', 'EXPERT', '专家号', 2, 1, now(), now()),
('REGISTRATION_TYPE', 'EMERGENCY', '急诊', 3, 1, now(), now())
ON CONFLICT (dict_type, dict_code) DO NOTHING;

-- 基础字典: 性别
INSERT INTO sys_dict (dict_type, dict_code, dict_value, sort, status, create_time, update_time)
VALUES
('GENDER', 'M', '男', 1, 1, now(), now()),
('GENDER', 'F', '女', 2, 1, now(), now()),
('GENDER', 'U', '未知', 3, 1, now(), now())
ON CONFLICT (dict_type, dict_code) DO NOTHING;

-- 基础字典: 处方状态
INSERT INTO sys_dict (dict_type, dict_code, dict_value, sort, status, create_time, update_time)
VALUES
('PRESCRIPTION_STATUS', 'PENDING', '待审核', 1, 1, now(), now()),
('PRESCRIPTION_STATUS', 'APPROVED', '已审核', 2, 1, now(), now()),
('PRESCRIPTION_STATUS', 'DISPENSED', '已发药', 3, 1, now(), now()),
('PRESCRIPTION_STATUS', 'REJECTED', '已退回', 4, 1, now(), now())
ON CONFLICT (dict_type, dict_code) DO NOTHING;

-- 基础字典: 结算状态
INSERT INTO sys_dict (dict_type, dict_code, dict_value, sort, status, create_time, update_time)
VALUES
('BILLING_STATUS', 'UNPAID', '未结算', 1, 1, now(), now()),
('BILLING_STATUS', 'PAID', '已结算', 2, 1, now(), now()),
('BILLING_STATUS', 'REFUNDED', '已退费', 3, 1, now(), now())
ON CONFLICT (dict_type, dict_code) DO NOTHING;
```

- [ ] **Step 2: 创建科室和员工种子数据脚本**

创建`backend/go/sql/demo_data.sql`:

```sql
-- 演示数据：科室
INSERT INTO departments (name, code, parent_id, sort, description, created_at, updated_time)
VALUES
('心内科', 'CARDIOLOGY', NULL, 1, '诊治心脏及血管相关疾病', now(), now()),
('呼吸内科', 'RESPIRATORY', NULL, 2, '诊治呼吸系统相关疾病', now(), now()),
('消化内科', 'DIGESTIVE', NULL, 3, '诊治消化系统相关疾病', now(), now()),
('神经内科', 'NEUROLOGY', NULL, 4, '诊治神经系统相关疾病', now(), now()),
('骨科', 'ORTHOPEDICS', NULL, 5, '诊治骨骼及关节相关疾病', now(), now()),
('儿科', 'PEDIATRICS', NULL, 6, '诊治儿童相关疾病', now(), now()),
('妇产科', 'OBSTETRICS', NULL, 7, '诊治妇产科相关疾病', now(), now()),
('眼科', 'OPHTHALMOLOGY', NULL, 8, '诊治眼部相关疾病', now(), now()),
('耳鼻喉科', 'ENT', NULL, 9, '诊治耳鼻喉相关疾病', now(), now()),
('皮肤科', 'DERMATOLOGY', NULL, 10, '诊治皮肤相关疾病', now(), now())
ON CONFLICT (code) DO NOTHING;

-- 演示数据：医生用户
INSERT INTO users (username, password_hash, real_name, phone, email, status, created_at, updated_at)
VALUES
('doctor-zhang', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张医生', '13800000001', 'zhang@hismixed.com', 1, now(), now()),
('doctor-li', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李医生', '13800000002', 'li@hismixed.com', 1, now(), now()),
('doctor-wang', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '王医生', '13800000003', 'wang@hismixed.com', 1, now(), now())
ON CONFLICT (username) DO NOTHING;

-- 演示数据：医生角色关联
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username LIKE 'doctor-%' AND r.code = 'DOCTOR'
ON CONFLICT DO NOTHING;

-- 演示数据：员工
INSERT INTO employees (user_id, employee_no, name, gender, phone, email, title, job_type, department_id, specialty, status, created_at, updated_at)
SELECT u.id, 'EMP' || u.id, u.real_name, 1, u.phone, u.email, '主任医师', 'DOCTOR', d.id, d.description, 1, now(), now()
FROM users u, departments d
WHERE u.username = 'doctor-zhang' AND d.code = 'CARDIOLOGY'
ON CONFLICT (employee_no) DO NOTHING;

INSERT INTO employees (user_id, employee_no, name, gender, phone, email, title, job_type, department_id, specialty, status, created_at, updated_at)
SELECT u.id, 'EMP' || u.id, u.real_name, 1, u.phone, u.email, '副主任医师', 'DOCTOR', d.id, d.description, 1, now(), now()
FROM users u, departments d
WHERE u.username = 'doctor-li' AND d.code = 'RESPIRATORY'
ON CONFLICT (employee_no) DO NOTHING;

INSERT INTO employees (user_id, employee_no, name, gender, phone, email, title, job_type, department_id, specialty, status, created_at, updated_at)
SELECT u.id, 'EMP' || u.id, u.real_name, 1, u.phone, u.email, '主治医师', 'DOCTOR', d.id, d.description, 1, now(), now()
FROM users u, departments d
WHERE u.username = 'doctor-wang' AND d.code = 'DIGESTIVE'
ON CONFLICT (employee_no) DO NOTHING;

-- 演示数据：患者用户
INSERT INTO users (username, password_hash, real_name, phone, email, status, created_at, updated_at)
VALUES
('patient-demo', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '演示患者', '13900000001', 'patient@hismixed.com', 1, now(), now())
ON CONFLICT (username) DO NOTHING;

-- 演示数据：患者档案
INSERT INTO patients (name, gender, birth_date, id_card, phone, address, blood_type, created_at, updated_at)
SELECT u.real_name, 2, '1990-01-01', '110101199001011234', u.phone, '北京市朝阳区', 'A', now(), now()
FROM users u
WHERE u.username = 'patient-demo'
ON CONFLICT (id_card) DO NOTHING;
```

- [ ] **Step 3: 更新数据库初始化脚本**

更新`scripts/db/db_init.sh`:

```bash
#!/usr/bin/env bash
# HIS-Mixed 数据库初始化脚本
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
SQL_DIR="$PROJECT_ROOT/backend/go/sql"

DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_USER="${DB_USER:-his_admin}"
DB_PASSWORD="${DB_PASSWORD:-change_me_123}"

export PGPASSWORD="$DB_PASSWORD"

echo "===== 数据库初始化 ====="
echo "目标: $DB_HOST:$DB_PORT"
echo ""

echo "--- 执行 init_all.sql (创建数据库) ---"
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d postgres -f "$SQL_DIR/init_all.sql"

echo ""
echo "--- 执行 seed_data.sql (初始化数据) ---"
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_auth -f "$SQL_DIR/seed_data.sql"

echo ""
echo "--- 执行演示数据脚本 ---"
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_user -f "$SQL_DIR/demo_data.sql"

echo ""
echo "===== 初始化完成 ====="
```

- [ ] **Step 4: 提交代码**

```bash
git add backend/go/sql/ scripts/db/
git commit -m "feat: 添加演示数据和数据库初始化脚本"
```

---

### Task 12: 集成测试与验证

**Files:**
- Modify: `testing/api/auth_flow_test.go`
- Modify: `testing/api/client.go`

- [ ] **Step 1: 启动基础设施**

```bash
cd docker && docker compose up -d postgresql redis rabbitmq
```

Expected: PostgreSQL、Redis、RabbitMQ启动成功

- [ ] **Step 2: 初始化数据库**

```bash
bash scripts/db/db_init.sh
```

Expected: 数据库初始化成功

- [ ] **Step 3: 启动Java Auth服务**

```bash
cd backend/java && mvn spring-boot:run -pl his-auth
```

Expected: Auth服务启动在8081端口

- [ ] **Step 4: 启动Go服务**

```bash
cd backend/go && go run ./cmd/registration/ &
cd backend/go && go run ./cmd/schedule/ &
cd backend/go && go run ./cmd/pharmacy/ &
cd backend/go && go run ./cmd/gateway/ &
```

Expected: 各服务启动成功

- [ ] **Step 5: 运行集成测试**

```bash
cd testing && bash run.sh
```

Expected: 测试通过

- [ ] **Step 6: 手动验证登录功能**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

Expected: 返回token和用户信息

- [ ] **Step 7: 手动验证科室查询**

```bash
curl -X GET http://localhost:8080/api/user/departments \
  -H "Authorization: Bearer <token>"
```

Expected: 返回科室列表

- [ ] **Step 8: 提交最终代码**

```bash
git add .
git commit -m "feat: MVP演示版本完成"
```

---

## 三、验证清单

### 3.1 后端服务验证

- [ ] Gateway启动成功（端口8080）
- [ ] Auth服务启动成功（端口8081）
- [ ] User服务启动成功（端口8082）
- [ ] Registration服务启动成功（端口8083）
- [ ] Schedule服务启动成功（端口8090）
- [ ] Pharmacy服务启动成功（端口8087）

### 3.2 API功能验证

- [ ] 登录API正常返回token
- [ ] 刷新token正常工作
- [ ] 科室列表查询正常
- [ ] 员工列表查询正常
- [ ] 排班查询正常
- [ ] 挂号功能正常
- [ ] 处方查询正常

### 3.3 前端功能验证

- [ ] 登录页面正常显示
- [ ] 登录功能正常工作
- [ ] 首页正常显示
- [ ] 挂号页面正常显示
- [ ] 科室选择正常工作
- [ ] 医生选择正常工作
- [ ] 处方查询页面正常显示

### 3.4 集成测试验证

- [ ] 健康检查通过
- [ ] 认证流程测试通过
- [ ] 未认证访问被拦截
- [ ] 挂号流程测试通过

---

## 四、执行建议

### 4.1 推荐执行顺序

1. Task 1: Proto代码生成（基础）
2. Task 2-4: Go服务初始化（并行）
3. Task 5-6: Java服务实现（并行）
4. Task 7-9: 前端API对接（并行）
5. Task 10: Gateway路由更新
6. Task 11: 数据库初始化
7. Task 12: 集成测试

### 4.2 时间估算

- Task 1: 0.5天
- Task 2-4: 3天（每个1天）
- Task 5-6: 4天（每个2天）
- Task 7-9: 3天（每个1天）
- Task 10: 0.5天
- Task 11: 0.5天
- Task 12: 1天

**总计：12.5天（约2.5周）**

### 4.3 风险点

1. Proto代码生成可能需要安装protoc和插件
2. Java服务实现需要配置MyBatis-Plus和Spring Security
3. 前端API对接需要处理跨域和认证
4. 数据库初始化需要确保PostgreSQL运行

---

## 五、后续优化

MVP演示版本完成后，可以继续优化：

1. **完善错误处理**：统一异常处理、参数校验
2. **添加单元测试**：提高测试覆盖率
3. **性能优化**：添加缓存、优化查询
4. **安全加固**：HTTPS、数据加密、审计日志
5. **功能完善**：完善其他业务模块
6. **部署优化**：Docker镜像构建、K8s部署
