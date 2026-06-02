# HIS-Mixed

> 基于 Java + Go 混合架构的全链路医院信息系统（Hospital Information System，HIS）。采用前后端分离微服务架构，覆盖院内诊疗流程与院外患者服务，所有服务统一通过 Docker 容器化部署。

本项目是对原 [Hospital-Information-System](https://github.com/Tangyd893/Hospital-Information-System)（Spring Cloud Alibaba + Vue.js）的 **Java + Go 混合架构重构版本**，同时参考 [HIS-Go](https://github.com/Tangyd893/HIS-Go)（纯 Go 版 HIS）的架构设计。

## 项目能力

- 18 个微服务模块，覆盖挂号→就诊→处方→收费→发药→住院全流程
- Java + Go 各司其职：Go 负责高并发 I/O，Java 负责复杂事务与业务逻辑
- gRPC 跨语言同步调用 + RabbitMQ 异步解耦，proto 作为唯一 API 契约
- Gateway 统一入口，JWT 鉴权中间件已接入
- PostgreSQL 17 分库设计（Database per Service），17 个独立数据库
- GORM（Go）+ MyBatis-Plus（Java）双 ORM 持久化、乐观锁、逻辑删除
- RabbitMQ 消息可靠性设计（Publisher Confirm、手动 ACK、死信队列、本地消息表）
- Redis 缓存策略（号源缓存、分布式锁、排队 Sorted Set、滑动窗口限流）
- CDSS 临床决策支持（药物过敏/相互作用/剂量校验）
- SOAP 结构化病历（模板引擎、质控流程、FHIR R4 对接）
- Nacos 统一服务注册发现与配置中心（Java + Go 双端接入）
- Docker Compose 一键部署（PostgreSQL、Redis、RabbitMQ、Nacos、MinIO、Nginx）

## 当前完成度

> 本项目处于**基础搭建阶段**。18 个服务骨架已生成、Go/Java 双端编译通过、gRPC 接口已定义、数据库迁移脚本齐全、21 个单元测试通过、Gateway 反向代理已实现、Docker + K8s 部署方案齐全。

| 维度 | 状态 | 说明 |
| ---- | ---- | ---- |
| 后端骨架 | 已完成 | 18 个服务入口、18 个 proto 定义，Go handler/service/repository/model 分层完整 |
| Go 构建 | 已通过 | `go build ./cmd/...` 通过，`go vet` 通过，`gofmt -w .` 零输出 |
| Java 构建 | 已通过 | `mvn compile` 通过，9 个 Spring Boot 服务骨架可编译 |
| gRPC | 已定义 | 18 个 `.proto` 已编写，待生成 `.pb.go` 和 Java stub 代码 |
| Gateway | 已实现 | JWT 鉴权 + CORS + 限流 + 17 个下游服务反向代理路由 |
| 数据库迁移 | 已完成 | 18 个迁移脚本覆盖全部 17 个数据库，含完整建表 SQL |
| Docker | 配置就绪 | Dockerfile 和 docker-compose 已就绪，Compose 配置解析已通过 |
| 测试 | 已建立 | 21 个单元测试覆盖 JWT/错误码/通用工具/响应/中间件，全部通过 |
| K8s 部署 | 已就绪 | `docker/k8s/base/` 含 12 个 YAML 清单，覆盖全部基础设施和 18 微服务 |
| 前端 | 待搭建 | 目录已预留，管理端 + 患者端待启动 |

## 技术栈

### 后端

| 层次 | Go 侧 | Java 侧 |
| ---- | ----- | ------- |
| 语言 | Go 1.25+ | Java 21 LTS |
| HTTP 框架 | Gin 1.10+ | Spring Boot 3.3+ |
| RPC | gRPC 1.70+ | gRPC 1.68+ (grpc-java) |
| ORM | GORM 2.x | MyBatis-Plus 3.5+ |
| 安全 | golang-jwt + Gin 中间件 | Spring Security + JWT (RS256) |
| 微服务 | - | Spring Cloud Alibaba 2023.x |
| 分布式事务 | - | Seata 2.x (AT 模式) |
| 依赖注入 | Wire 0.6+ | Spring DI |
| 定时任务 | robfig/cron 3.x | Spring @Scheduled |
| 日志 | Zap | Logback |
| 配置 | Viper + Nacos | Nacos Config |

### 前端

- Vue 3.5, TypeScript, Ant Design Vue 4, Element Plus 2, ECharts 5, Vite 6, Pinia 2

### 基础设施

- PostgreSQL 17, Redis 7, RabbitMQ 4.0, Nacos 2.4.3, MinIO, Docker + Docker Compose/K8s

## 默认端口

| 组件 | 地址 | 语言 | 说明 |
| ---- | ---- | ---- | ---- |
| API Gateway | `http://localhost:8080` | Go | 统一入口，路由 `/api/*` |
| his-auth | `http://localhost:8081` | Java | 认证授权 (gRPC:9081) |
| his-user | `http://localhost:8082` | Java | 用户/患者/科室管理 (gRPC:9082) |
| his-registration | `http://localhost:8083` | Go | 挂号预约、排队叫号 (gRPC:9083) |
| his-clinic | `http://localhost:8084` | Java | 门诊诊疗 (gRPC:9084) |
| his-prescription | `http://localhost:8085` | Java | 处方管理 (gRPC:9085) |
| his-billing | `http://localhost:8086` | Java | 收费结算 (gRPC:9086) |
| his-pharmacy | `http://localhost:8087` | Go | 药房管理 (gRPC:9087) |
| his-examination | `http://localhost:8088` | Go | 检查检验 (gRPC:9088) |
| his-inpatient | `http://localhost:8089` | Java | 住院管理 (gRPC:9089) |
| his-schedule | `http://localhost:8090` | Go | 排班管理 (gRPC:9090) |
| his-outpatient | `http://localhost:8091` | Go | 院外患者服务 (gRPC:9091) |
| his-followup | `http://localhost:8092` | Go | 随访管理 (gRPC:9092) |
| his-health-record | `http://localhost:8093` | Java | 健康档案 (gRPC:9093) |
| his-notification | `http://localhost:8094` | Go | 消息通知 (gRPC:9094) |
| his-statistics | `http://localhost:8095` | Go | 数据统计 (gRPC:9095) |
| his-system | `http://localhost:8096` | Java | 系统管理 (gRPC:9096) |
| his-emr | `http://localhost:8097` | Java | 电子病历 (gRPC:9097) |
| PostgreSQL | `localhost:5432` | - | `his_admin / change_me_123` |
| Redis | `localhost:6379` | - | 密码 `change_me_456` |
| RabbitMQ | `localhost:5672` | - | 管理端口 `15672`，`admin / change_me_789` |
| Nacos | `http://localhost:8848/nacos` | - | `nacos / nacos` |
| MinIO | `http://localhost:9001` | - | `minioadmin / change_me_012` |

## 微服务边界

| 服务 | 语言 | 数据库 | 主要职责 |
| ---- | ---- | ------ | -------- |
| Gateway | Go | 无状态 | 统一入口、路由转发、JWT 认证、CORS、限流 |
| his-auth | Java | `his_auth` | 登录认证、Token 签发/刷新（RS256）、角色权限管理 |
| his-user | Java | `his_user` | 患者档案、员工管理、科室树 |
| his-registration | Go | `his_registration` | 号源管理、挂号预约、排队叫号、Redis 分布式锁 |
| his-clinic | Java | `his_clinic` | 接诊登记、诊断录入（ICD-10）、检查申请、转诊 |
| his-emr | Java | `his_emr` | SOAP 结构化病历、模板引擎、三级质控、CDSS |
| his-prescription | Java | `his_prescription` | 处方开具、审核、退回、处方状态流转 |
| his-billing | Java | `his_billing` | 多类型费用合并结算、支付、退费审批、日报表 |
| his-pharmacy | Go | `his_pharmacy` | 药品库存、入库、发药、效期预警（cron 定时） |
| his-examination | Go | `his_examination` | 检查执行、报告录入、审核流程 |
| his-inpatient | Java | `his_inpatient` | 入院登记、床位分配、医嘱下达、护理记录、出院结算 |
| his-schedule | Go | `his_schedule` | 医生排班、诊室安排、号源生成（乐观锁） |
| his-outpatient | Go | `his_outpatient` | 在线问诊、消息记录、慢病签约、健康自测 |
| his-followup | Go | `his_followup` | 随访计划自动生成、执行记录、满意度调查 |
| his-health-record | Java | `his_health_record` | 全生命周期健康档案总览、时间轴 |
| his-notification | Go | `his_notification` | 通知模板管理、SMS/邮件/站内信发送 |
| his-statistics | Go | `his_statistics` | 运营报表、挂号/收入趋势、科室工作量、医疗质量 |
| his-system | Java | `his_system` | 字典类型/字典项管理、参数配置、操作日志审计 |

## 快速开始

### 环境要求

| 工具 | 最低版本 | 用途 |
| ---- | -------- | ---- |
| Go | 1.25+ | Go 服务编译与运行 |
| Java | 21 LTS | Java 服务编译与运行 |
| Node.js | 24 LTS | 前端构建与开发服务器 |
| Docker | 20.10+ | PostgreSQL, Redis, RabbitMQ, Nacos, MinIO 容器 |

一条命令检查所有必需工具：

```bash
go version && java --version && node --version && docker --version
```

### 启动完整后端技术栈

```bash
cd docker
cp .env.example .env

# 启动基础设施
docker compose up -d postgresql redis rabbitmq nacos minio

# 初始化数据库（等 PostgreSQL 就绪后）
cd ../backend/go
bash scripts/db_init.sh

# 构建并启动全部服务
cd ../../docker
docker compose up -d --build
```

### 逐个启动后端服务（开发调试）

先启动基础设施：

```bash
cd docker
docker compose up -d postgresql redis rabbitmq nacos minio
```

**启动 Go 服务**：

```bash
cd ../backend/go
go mod tidy
go run ./cmd/gateway &
go run ./cmd/registration &
go run ./cmd/pharmacy &
go run ./cmd/examination &
go run ./cmd/schedule &
go run ./cmd/outpatient &
go run ./cmd/followup &
go run ./cmd/notification &
go run ./cmd/statistics &
```

**启动 Java 服务**（新终端）：

```bash
cd backend/java
mvn spring-boot:run -pl his-auth &
# 其余 Java 服务类似启动
```

### 启动前端

```bash
# 管理端
cd frontend/his-web-admin && npm install && npm run dev

# 患者端
cd frontend/his-web-patient && npm install && npm run dev
```

> 前端通过 Vite 代理将 `/api` 请求转发至 `http://localhost:8080`（Gateway）。

## 默认验收账号

| 角色 | 用户名 | 密码 |
| ---- | ------ | ---- |
| 医生 | `demo-doctor` | `demo123` |
| 护士 | `demo-nurse` | `demo123` |
| 管理员 | `demo-admin` | `demo123` |

> 更多角色和权限数据见 `backend/go/sql/seed_data.sql`

## 健康检查自检

```bash
# Gateway 健康检查
curl http://localhost:8080/health

# 各服务独立健康检查
curl http://localhost:8081/health
curl http://localhost:8083/health
curl http://localhost:8097/health

# 测试 API 连通性
curl http://localhost:8080/api/ping
```

## 项目目录结构

本项目按职责划分为五个顶层子目录：`backend`（后端）、`frontend`（前端）、`docs`（文档）、`testing`（测试）、`docker`（部署）。

```
HIS-Mixed/
|
├── backend/                                 # 后端子项目
|   ├── proto/                               # 跨语言 Proto 接口定义（18 个服务）
|   |   ├── common/                          # 公共消息类型
|   |   ├── registration/                    # 挂号服务
|   |   └── ...                              # 其余 16 个服务 proto
|   |
|   ├── go/                                  # Go 服务（9 个）
|   |   ├── cmd/                             # 各服务入口 main.go
|   |   |   ├── gateway/                     # 网关服务
|   |   |   ├── registration/               # 挂号服务
|   |   |   ├── pharmacy/                   # 药房服务
|   |   |   ├── examination/                # 检查检验
|   |   |   ├── schedule/                   # 排班服务
|   |   |   ├── outpatient/                 # 院外服务
|   |   |   ├── followup/                   # 随访服务
|   |   |   ├── notification/               # 通知服务
|   |   |   └── statistics/                 # 统计服务
|   |   ├── internal/                        # 服务内部实现（handler/service/repository/model）
|   |   |   ├── gateway/                    # 网关内部（路由/反向代理/配置）
|   |   |   ├── registration/               # 挂号业务逻辑
|   |   |   └── ...
|   |   ├── pkg/                             # 公共模块
|   |   |   ├── common/                      # 雪花算法、校验、加密
|   |   |   ├── security/                    # JWT 解析、鉴权中间件
|   |   |   ├── database/                    # PostgreSQL 连接管理
|   |   |   ├── redis/                       # Redis 缓存、分布式锁
|   |   |   ├── mq/                          # RabbitMQ 封装
|   |   |   ├── grpc/                        # gRPC 客户端/拦截器
|   |   |   ├── logger/                      # Zap 日志
|   |   |   ├── config/                      # Viper 配置
|   |   |   ├── middleware/                   # CORS、限流、链路追踪、恢复
|   |   |   ├── errors/                      # 统一错误码
|   |   |   └── response/                    # 统一响应
|   |   ├── configs/                         # 配置文件
|   |   ├── sql/                             # 数据库初始化脚本
|   |   |   ├── init_all.sql                 # 全量建库脚本
|   |   |   └── seed_data.sql                # 基础数据/字典数据
|   |   ├── migrations/                      # 版本化数据库迁移
|   |   └── scripts/                         # Go 辅助脚本
|   |
|   └── java/                                # Java 服务（9 个）
|       ├── pom.xml                          # 父 POM
|       ├── common/                          # Java 公共模块
|       ├── his-auth/                        # 认证授权
|       ├── his-user/                        # 用户管理
|       ├── his-clinic/                      # 门诊诊疗
|       ├── his-emr/                         # 电子病历
|       ├── his-prescription/                # 处方管理
|       ├── his-billing/                     # 收费结算
|       ├── his-inpatient/                   # 住院管理
|       ├── his-health-record/               # 健康档案
|       └── his-system/                      # 系统管理
|
├── frontend/                                # 前端子项目
|   ├── his-web-admin/                       # 管理端（Vue3 + Ant Design Vue4）
|   |   └── src/views/                       # 19 个功能模块页面
|   └── his-web-patient/                     # 患者端（Vue3 + H5）
|       └── src/views/                       # 8 个功能模块页面
|
├── docs/                                    # 项目文档
|   ├── mixPlan.md                           # 混合架构规划
|   ├── 项目架构设计文档.md                     # 系统架构、微服务划分
|   ├── 技术选型文档.md                         # 技术选型说明
|   ├── API接口规范文档.md                      # REST + gRPC 接口规范
|   └── 数据库设计文档.md                        # 17 个数据库表结构
|
├── docker/                                  # Docker 部署配置
|   ├── docker-compose.yml                   # 基础设施编排
|   ├── docker-compose.go.yml                # Go 服务群
|   ├── docker-compose.java.yml              # Java 服务群
|   ├── Dockerfile.go                        # Go 多阶段构建
|   ├── Dockerfile.java                      # Java 多阶段构建
|   ├── nginx/                               # Nginx 配置
|   |   └── nginx.conf
|   ├── k8s/                                 # Kubernetes 部署清单（生产环境）
|   |   └── base/                            # 基础设施 + 微服务 YAML
|   └── .env.example                         # 环境变量模板
|
├── testing/                                 # 集成测试
|   ├── go.mod                               # 独立 Go module
|   ├── api/                                 # 集成测试用例
|   |   ├── client.go                        # HTTP 客户端封装
|   |   └── auth_flow_test.go                # 认证流程/鉴权验收
|   └── run.sh                               # 测试运行脚本
|
├── scripts/                                 # 顶层辅助脚本
|   ├── check.sh                             # Linux/macOS 质量检查
|   ├── check.ps1                            # Windows 质量检查
|   ├── proto-gen/                           # Proto 代码生成（Go + Java）
|   └── db/                                  # 数据库辅助脚本
|
├── .gitignore
├── Makefile                                 # 构建/检查快捷命令
└── README.md
```

### 目录说明

| 目录 | 说明 |
| ---- | ---- |
| `backend/` | 后端服务，含 `proto/`（共享 API 契约）、`go/`（Go 服务 9 个）和 `java/`（Java 服务 9 个） |
| `frontend/` | 前端项目，含管理端（`his-web-admin`）和患者端（`his-web-patient`）两套应用 |
| `docs/` | 项目文档，含架构设计、技术选型、API 规范、数据库设计 |
| `docker/` | Docker 部署配置，docker-compose.yml、Dockerfile、.env.example 及 K8s 部署清单 |
| `testing/` | 集成测试，独立 Go module，覆盖核心业务链路的 API 验收测试 |

## 数据库说明

每个微服务拥有独立的 PostgreSQL 数据库（Database per Service），共 17 个 database：

`his_auth` `his_user` `his_registration` `his_clinic` `his_emr` `his_prescription` `his_billing` `his_pharmacy` `his_examination` `his_inpatient` `his_schedule` `his_outpatient` `his_followup` `his_health_record` `his_notification` `his_statistics` `his_system`

建表脚本位于 `backend/go/sql/init_all.sql`，种子数据位于 `backend/go/sql/seed_data.sql`。

版本化迁移脚本位于 `backend/go/migrations/`，按编号顺序执行即可初始化全部表结构。

详细表结构设计见 [数据库设计文档](docs/数据库设计文档.md)。

## Go + Java 混合协作机制

本项目的核心挑战和亮点在于让 Go 和 Java 两种异构技术栈的微服务无缝协作。我们采用 **三种协作机制** 实现跨语言互通：

```
                    ┌─────────────────────────────────┐
                    │          Go API Gateway          │
                    │        (Gin + JWT + 限流)        │
                    └───────────────┬─────────────────┘
                                    │
              ┌─────────────────────┼─────────────────────┐
              │ gRPC                │ HTTP                │ gRPC
              ▼                     ▼                     ▼
   ┌──────────────────┐   ┌──────────────────┐   ┌──────────────────┐
   │  Go 挂号服务      │   │  Java 认证服务    │   │  Go 药房服务      │
   │  (高并发扣号源)    │   │  (JWT签发/RBAC)  │   │  (库存扣减)      │
   └────────┬─────────┘   └────────┬─────────┘   └────────┬─────────┘
            │                      │                      │
            └──────────────────────┼──────────────────────┘
                                   │
                        ┌──────────▼──────────┐
                        │     RabbitMQ         │
                        │  • 挂号 → Java 持久化 │
                        │  • 处方变更 → 通知    │
                        │  • 操作日志 → 审计    │
                        └──────────┬──────────┘
                                   │
                        ┌──────────▼──────────┐
                        │    Java 消费端        │
                        │  Spring Boot + MQ    │
                        │  复杂事务持久化        │
                        └─────────────────────┘
```

### 一、同步调用：gRPC + Protobuf

**核心原则**：Proto 文件是 Go 和 Java 之间的**唯一 API 契约**，双方共享 `backend/proto/` 下的同一份定义。

```
┌──────────────────────────────────────────────────┐
│            backend/proto/（单一来源）              │
│                                                   │
│  common/common.proto   ← 分页、用户上下文、错误    │
│  registration/...      ← 挂号服务接口              │
│  pharmacy/...          ← 药房服务接口              │
│  auth/...              ← 认证服务接口              │
│  ...（18 个服务共 18 个 proto 文件）                │
└──────────────────────────────────────────────────┘
         │                          │
         │ protoc --go_out          │ protoc --java_out
         ▼                          ▼
  backend/go/pkg/grpc/      backend/java/.../stub/
  (.pb.go 代码)              (Java stub 代码)
```

#### Go 调用 Java（同步查询患者信息）

挂号服务(Go)需要校验患者身份时，通过 gRPC 同步调用 Java 用户服务：

```go
// backend/go/internal/registration/service/registration.go

func (s *RegistrationService) Register(ctx context.Context, req *pb.RegisterRequest) (*pb.RegisterResponse, error) {
    // 1. 通过 Nacos 发现 Java 用户服务地址
    conn, _ := grpc.GetConn("his-user:9082")

    // 2. 同步调用 Java 服务获取患者信息
    userClient := userpb.NewUserServiceClient(conn)
    patient, err := userClient.GetPatient(ctx, &userpb.GetPatientRequest{
        PatientId: req.PatientId,
    })
    if err != nil || patient == nil {
        return nil, status.Error(codes.NotFound, "患者不存在")
    }

    // 3. Redis 分布式锁扣减号源
    lockKey := fmt.Sprintf("schedule_lock:%d", req.ScheduleId)
    redis.AcquireLock(ctx, lockKey, 30*time.Second)
    defer redis.ReleaseLock(ctx, lockKey)

    // 4. 扣减库存 → 返回挂号结果
    remaining := redis.DecrStock(ctx, req.ScheduleId)
    return &pb.RegisterResponse{
        RegistrationId: common.NextID(),
        QueueNumber:    remaining + 1,
    }, nil
}
```

#### Java 调用 Go（同步检查库存）

收费服务(Java)在结算前需要确认药房库存：

```java
// backend/java/his-billing/.../service/BillingService.java

@Service
public class BillingService {

    @Autowired
    private GrpcClientFactory grpcClient;

    public void processPayment(PaymentRequest request) {
        // 1. 同步调用 Go 药房服务检查库存
        var stub = grpcClient.createStub("his-pharmacy:9087",
            PharmacyServiceGrpc::newBlockingStub);
        var stock = stub.checkStock(CheckStockRequest.newBuilder()
            .setDrugId(request.getDrugId())
            .setQuantity(request.getQuantity())
            .build());

        if (stock.getRemaining() < request.getQuantity()) {
            throw new BusinessException(7001, "药品库存不足");
        }

        // 2. 库存充足，继续收费流程...
        paymentRepository.save(payment);
    }
}
```

### 二、异步解耦：RabbitMQ

**使用场景**：当一笔操作需要跨越 Go 和 Java 两个生态、且涉及耗时事务时，通过 MQ 解耦。

```
高并发 Go 服务 ──(发布消息)──▶ RabbitMQ ──(消费消息)──▶ Java 服务（复杂事务持久化）
```

#### 典型异步协作场景

| 场景 | 生产者 | 消费者 | 消息格式 | 交换机 |
| ---- | ------ | ------ | -------- | ------ |
| **挂号成功 → Java 持久化** | Go Registration | Java Consumer | `{registrationId, patientId, scheduleId}` | Topic |
| **挂号成功 → 短信通知** | Go Registration | Go Notification | `{registrationId, phone, templateCode}` | Topic |
| **处方审核通过 → 药房备药** | Java Prescription | Go Pharmacy | `{prescriptionId, drugItems[]}` | Topic |
| **处方状态变更 → 患者通知** | Java Prescription | Go Notification | `{prescriptionId, status, patientId}` | Topic |
| **异常指标 → 告警推送** | Go Examination | Go Notification | `{reportId, patientId, alertLevel}` | Direct |
| **全服务操作日志 → 审计** | Go/Java 全部 | Java System | `{userId, action, module, params}` | Fanout |

#### Go 发消息 → Java 消费（挂号持久化示例）

```go
// Go: 挂号成功后发布消息
func (s *RegistrationService) afterRegister(id, patientId, scheduleId int64) {
    publisher, _ := mq.NewPublisher("registration.topic")
    publisher.Publish(ctx, "registration.created", map[string]interface{}{
        "registrationId": id,
        "patientId":      patientId,
        "scheduleId":     scheduleId,
        "status":         "registered",
        "timestamp":      time.Now().Unix(),
    })
}
```

```java
// Java: 消费消息，事务写入 PostgreSQL
@Component
public class RegistrationConsumer {

    @Autowired
    private RegistrationRepository repo;
    @Autowired
    private PatientRepository patientRepo;

    @RabbitListener(queues = "registration.queue")
    @Transactional
    public void onRegistrationCreated(AppointmentMessage msg) {
        // Java 擅长的复杂事务操作
        Registration reg = new Registration();
        reg.setId(msg.getRegistrationId());
        reg.setPatientId(msg.getPatientId());
        reg.setStatus(msg.getStatus());
        repo.save(reg);

        // 更新患者最后就诊时间
        patientRepo.updateLastVisit(msg.getPatientId(), LocalDateTime.now());
    }
}
```

### 三、统一服务发现：Nacos

Go 和 Java 的服务全部注册到同一个 Nacos 实例，跨语言服务发现流程：

```
Java Auth 启动      Go Gateway 启动
     │                    │
     ▼                    ▼
 注册到 Nacos        注册到 Nacos
 服务名: his-auth    服务名: his-gateway
 IP:Port: 10.0.0.2:9081      IP:Port: 10.0.0.1:8080
     │                    │
     └────────┬───────────┘
              ▼
      Nacos 服务注册中心
   （同一命名空间: his-mixed）
              │
     ┌────────┴────────┐
     ▼                 ▼
  Go 查询 Java        Java 查询 Go
  gRPC 直连调用      gRPC 直连调用
```

- **Java** 通过 `spring-cloud-starter-alibaba-nacos-discovery` 自动注册/发现
- **Go** 通过 Nacos Go SDK 手动注册/健康检查/服务发现

### 四、跨语言数据一致性

| 场景 | 策略 | 说明 |
| ---- | ---- | ---- |
| 号源扣减 | Redis 原子操作 (DECR) | 高并发无状态，Go 处理 |
| 挂号写入 | Go → MQ → Java 事务写入 | 削峰填谷，最终一致性 |
| 处方状态流转 | Java 乐观锁 (version 字段) | 单服务内强一致 |
| 跨服务事务 | Seata AT 模式 (Java 侧) | 复杂分布式事务 |
| 消息幂等 | 雪花ID + 数据库唯一约束 | 防止重复消费 |

### 五、核心业务全链路示例：挂号流程

```
患者发起挂号
    │
    ▼
┌──────────────────┐
│ Go Gateway       │  ← JWT 鉴权 + 限流
│ (端口 8080)       │
└────────┬─────────┘
         │ 转发
         ▼
┌──────────────────┐
│ Go Registration  │  ← 高并发号源扣减
│ 1. gRPC → Java   │──────▶  Java User 校验患者身份
│    User 校验患者   │
│ 2. Redis DECR    │──────▶  Redis 扣减号源 (原子操作)
│    扣减号源       │
│ 3. 雪花算法生成ID  │
│ 4. MQ 发消息      │──────▶  RabbitMQ(registration.created)
└────────┬─────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌────────┐ ┌──────────┐
│  Java  │ │   Go     │
│Consumer│ │Notify    │
│写入PG  │ │发短信/推送│
│持久化   │ │          │
└────────┘ └──────────┘
```

### 六、消息可靠性保障

1. **生产者可靠发送**：本地消息表(Transactional Outbox) + Publisher Confirm + 定时补偿重试(2s/5s/10s)
2. **防止消息丢失**：交换机/队列/消息持久化 + Quorum Queue(Raft) + 消费者手动 ACK + 死信队列(DLQ)
3. **防止重复消费**：消息雪花ID + 数据库唯一约束 + Redis SETNX 防重标记 + 业务幂等(乐观锁/流水号)

详见 [项目架构设计文档](docs/项目架构设计文档.md) 第七章。

## 测试命令

### 后端单元测试

```bash
# Go 服务测试
cd backend/go && go test ./...

# Java 服务测试
cd ../java && mvn test
```

### 集成测试（需 Docker 环境启动后）

```bash
cd testing && bash run.sh
# 或手动指定目标地址
HIS_BASE_URL=http://localhost:8080 HIS_INTEGRATION_TEST=true go test -v ./...
```

### 编译

```bash
# Go
cd backend/go && go build -o bin/ ./cmd/...

# Java
cd ../java && mvn package -DskipTests
```

## 与原项目技术栈对比

| 维度 | 原项目（Java） | 本项目（混合架构） | HIS-Go（Go） |
| ---- | -------------- | ------------------ | ------------ |
| 网关 | Spring Cloud Gateway | **Go Gin** | **Go Gin** |
| HTTP 框架 | Spring Boot | **Go Gin + Spring Boot** | **Go Gin** |
| RPC | OpenFeign | **gRPC（跨语言）** | **gRPC** |
| ORM | MyBatis-Plus | **GORM + MyBatis-Plus** | **GORM** |
| 分布式事务 | Seata | **Seata（Java 侧）** | DTM（计划中） |
| 安全 | Spring Security | **Spring Security + Go JWT** | golang-jwt |
| 语言 | Java 21 | **Go 1.25 + Java 21** | **Go 1.25** |

## 生产部署注意事项

- 生产环境必须修改 `docker/.env` 中所有默认密码（数据库、Redis、RabbitMQ、MinIO）
- JWT 密钥对（RS256）需通过环境变量注入
- 不要提交 `docker/.env` 到版本仓库
- 生产环境 RabbitMQ 管理端口（15672）不应对外暴露
- 敏感字段（手机号、身份证号）写入日志前需脱敏处理
- 生产环境建议启用 HTTPS（Nginx 终结 TLS）

## 相关文档

| 文档 | 说明 |
| ---- | ---- |
| [项目架构设计文档](docs/项目架构设计文档.md) | 系统总体架构、技术选型、微服务划分、RabbitMQ 可靠性 |
| [技术选型文档](docs/技术选型文档.md) | 技术栈详解、版本兼容矩阵、选型理由 |
| [API 接口规范文档](docs/API接口规范文档.md) | REST + gRPC 全接口定义、错误码规范 |
| [数据库设计文档](docs/数据库设计文档.md) | 17 个数据库完整表结构设计 |
| [混合架构规划](docs/mixPlan.md) | Java + Go 服务分配与跨语言通信规划 |

## 后续规划

1. **第一阶段**：搭建基础框架，完成网关、认证、用户、挂号、诊疗、处方核心服务
2. **第二阶段**：完善院外患者服务（在线问诊、慢病管理、随访）
3. **第三阶段**：对接医保接口、第三方支付
4. **第四阶段**：引入分布式事务方案（跨语言 DTM 或 SAGA 模式）
5. **第五阶段**：Kubernetes 容器编排迁移，CI/CD 流水线搭建
6. **第六阶段**：接入 OpenTelemetry 全链路追踪 + Prometheus + Grafana 监控体系

## 参考项目

- [HIS-Go](https://github.com/Tangyd893/HIS-Go) — 纯 Go 版 HIS，Go 侧技术选型与模块划分的核心参考
- [Hospital-Information-System](https://github.com/Tangyd893/Hospital-Information-System) — 原 Spring Cloud Alibaba + Vue.js，业务逻辑与数据库设计的来源
