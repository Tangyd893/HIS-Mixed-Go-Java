# HIS-Mixed 混合架构规划

> Java + Go 混合架构 HIS 医院信息系统的规划文档，详细设计请参阅 [项目架构设计文档](./项目架构设计文档.md)

---

## 一、核心决策：Java + Go 各司其职

| 语言 | 负责场景 | 优势 |
| ---- | -------- | ---- |
| **Go** | 高并发 I/O、网关、缓存、消息消费 | 协程模型、低内存、快速启动 |
| **Java** | 复杂业务逻辑、事务、安全认证 | Spring 生态成熟、事务/ORM/安全开箱即用 |

---

## 二、服务分配

### Go 服务（9 个）

| 服务 | 说明 | 端口(HTTP/gRPC) |
| ---- | ---- | --------------- |
| Gateway | 统一入口、JWT 鉴权、限流、CORS | 8080/- |
| Registration | 挂号预约、号源扣减、排队叫号 | 8083/9083 |
| Pharmacy | 药品库存、发药盘点、效期预警 | 8087/9087 |
| Examination | 检查申请、报告录入、结果查询 | 8088/9088 |
| Schedule | 医生排班、诊室安排、号源生成 | 8090/9090 |
| Outpatient | 在线问诊、慢病管理、WebSocket | 8091/9091 |
| Follow-up | 诊后随访、康复计划、满意度调查 | 8092/9092 |
| Notification | 短信/邮件/站内信/微信模板消息 | 8094/9094 |
| Statistics | 运营报表、医疗质量统计、数据大屏 | 8095/9095 |

### Java 服务（9 个）

| 服务 | 说明 | 端口(HTTP/gRPC) |
| ---- | ---- | --------------- |
| Auth | 登录认证、JWT 签发/刷新、RBAC 权限 | 8081/9081 |
| User | 患者档案、员工管理、科室部门 | 8082/9082 |
| Clinic | 接诊登记、诊断录入(ICD-10)、检查申请 | 8084/9084 |
| EMR | SOAP 结构化病历、模板引擎、三级质控 | 8097/9097 |
| Prescription | 处方开具、审核、状态流转 | 8085/9085 |
| Billing | 费用计算、收费退费、发票、医保对接 | 8086/9086 |
| Inpatient | 入院登记、床位管理、医嘱执行、出院结算 | 8089/9089 |
| Health Record | 患者全生命周期健康档案 | 8093/9093 |
| System | 字典管理、参数配置、操作日志审计 | 8096/9096 |

---

## 三、跨语言通信

```
同步调用（gRPC）
  Java ←→ Go：通过 proto 契约 + Nacos 服务发现直连调用

异步协作（RabbitMQ）
  Go → MQ → Java：高并发写入后异步持久化
  Java → MQ → Go：处方状态变更 → 通知推送
```

| 机制 | 选型 | 用途 |
| ---- | ---- | ---- |
| 同步调用 | gRPC + Protobuf | 跨语言服务直连，proto 作为唯一 API 契约 |
| 异步解耦 | RabbitMQ | 高并发写入与复杂事务分离、事件通知 |
| 服务发现 | Nacos | Go 和 Java 统一注册与发现 |

---

## 四、技术栈速览

### Go 侧
| 组件 | 选型 |
| ---- | ---- |
| HTTP 框架 | Gin |
| RPC | gRPC |
| ORM | GORM |
| 日志 | Zap |
| 配置 | Viper |
| 依赖注入 | Wire |
| ID 生成 | 雪花算法 |
| 定时任务 | robfig/cron |

### Java 侧
| 组件 | 选型 |
| ---- | ---- |
| 框架 | Spring Boot 3.3+ |
| 微服务 | Spring Cloud Alibaba |
| ORM | MyBatis-Plus |
| 安全 | Spring Security |
| 事务 | Seata (AT 模式) |

### 基础设施
| 组件 | 版本 |
| ---- | ---- |
| 数据库 | PostgreSQL 17 |
| 缓存 | Redis 7 |
| 消息队列 | RabbitMQ 4.0 |
| 服务发现 | Nacos 2.4.3 |
| 对象存储 | MinIO |
| 容器化 | Docker + Docker Compose / K8s |
| 前端 | Vue 3 + TypeScript + Ant Design Vue |

---

## 五、目录结构（规划）

```
his-mixed/
├── docs/                           # 项目文档
│   ├── mixPlan.md                  # 混合架构规划（本文档）
│   ├── 项目架构设计文档.md           # 系统架构、微服务划分、RabbitMQ 可靠性
│   ├── 技术选型文档.md               # 技术选型详解
│   ├── API接口规范文档.md            # REST + gRPC 接口规范
│   └── 数据库设计文档.md             # 数据库表结构设计
│
├── backend/                         # 后端服务根目录
│   ├── proto/                      # 跨语言 API 定义（18 个服务，共享 proto）
│   ├── go/                         # Go 服务
│   │   ├── cmd/                    # 9 个服务入口
│   │   ├── internal/               # 服务内部实现
│   │   └── pkg/                    # 公共库
│   ├── java/                       # Java 服务
│   │   ├── pom.xml                 # 父 POM
│   │   ├── his-*/                  # 9 个子模块
│   │   └── common/                 # 公共模块
│   └── sql/                        # 数据库脚本
│
├── frontend/                       # Vue 3 前端
│   ├── his-web-admin/              # 管理端
│   └── his-web-patient/            # 患者端
│
├── docker/                         # Docker 部署配置
│   ├── docker-compose.yml          # 基础设施编排
│   ├── docker-compose.go.yml       # Go 服务群
│   ├── docker-compose.java.yml     # Java 服务群
│   ├── Dockerfile.go               # Go 多阶段构建
│   ├── Dockerfile.java             # Java 多阶段构建
│   └── .env.example
│
├── testing/                        # 集成测试
│   ├── api/                        # API 测试用例
│   └── run.sh                      # 测试运行脚本
│
└── scripts/                        # 辅助脚本
```

详细目录结构请参阅 [项目架构设计文档 - 第十章](./项目架构设计文档.md#十项目目录结构)。

---

## 六、参考项目

- [HIS-Go](https://github.com/Tangyd893/HIS-Go) — 纯 Go 版 HIS，Go 侧技术参考
- [Hospital-Information-System](https://github.com/Tangyd893/Hospital-Information-System) — 原 Spring Cloud Alibaba 版，业务逻辑来源
