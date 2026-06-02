# HIS-Mixed API 接口规范文档

> Java + Go 混合架构 HIS 医院信息系统 — API 接口规范
>
> 版本: v1.0 | 更新日期: 2026-05

---

## 一、总体规范

### 1.1 通信协议

| 协议 | 用途 | 编码 |
| ---- | ---- | ---- |
| HTTP/1.1 REST | 前端 ↔ 网关 ↔ 后端服务 | JSON (application/json) |
| gRPC (HTTP/2) | 后端微服务间同步调用 | Protobuf |
| AMQP 0-9-1 | 异步消息协作 | JSON (消息体) |

### 1.2 接口前缀

```
/api/{service}/{resource}
```

| 前缀 | 说明 |
| ---- | ---- |
| `/api/auth/` | 认证授权 |
| `/api/user/` | 用户/患者/科室 |
| `/api/registration/` | 挂号预约 |
| `/api/clinic/` | 门诊诊疗 |
| `/api/emr/` | 电子病历 |
| `/api/prescription/` | 处方管理 |
| `/api/billing/` | 收费结算 |
| `/api/pharmacy/` | 药房管理 |
| `/api/examination/` | 检查检验 |
| `/api/inpatient/` | 住院管理 |
| `/api/schedule/` | 排班管理 |
| `/api/outpatient/` | 院外服务 |
| `/api/followup/` | 随访管理 |
| `/api/health-record/` | 健康档案 |
| `/api/notification/` | 消息通知 |
| `/api/statistics/` | 数据统计 |
| `/api/system/` | 系统管理 |

### 1.3 公共响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": 1715414400000
}
```

| 字段 | 类型 | 说明 |
| ---- | ---- | ---- |
| code | int | 业务状态码，200 表示成功 |
| message | string | 提示信息 |
| data | object/array/null | 响应数据 |
| timestamp | int64 | 响应时间戳（毫秒） |

### 1.4 公共请求头

| Header | 必填 | 说明 |
| ------ | ---- | ---- |
| Authorization | 是（登录接口除外） | Bearer {token}，JWT 认证令牌 |
| Content-Type | 是 | application/json |
| X-Request-Id | 否 | 请求追踪 ID |

### 1.5 分页规范

**请求参数**：

| 参数 | 类型 | 默认值 | 说明 |
| ---- | ---- | ------ | ---- |
| page | int | 1 | 页码，从 1 开始 |
| pageSize | int | 20 | 每页条数，最大 100 |
| sort | string | - | 排序字段 |
| order | string | asc | 排序方向 (asc/desc) |

**响应格式**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [],
    "total": 100,
    "page": 1,
    "pageSize": 20
  }
}
```

### 1.6 错误码规范

#### 1.6.1 错误码分类

| 分类 | 范围 | 说明 |
| ---- | ---- | ---- |
| 系统级错误 | 1-999 | 基础框架错误、HTTP 标准状态码 |
| 认证授权 | 1000-1999 | 登录、Token、权限相关 |
| 参数校验 | 2000-2999 | 请求参数缺失、格式错误 |
| 通用业务 | 4000-4999 | 跨模块通用业务错误 |
| 挂号模块 | 5000-5999 | 挂号预约、号源、排队 |
| 门诊模块 | 6000-6999 | 接诊、诊断、转诊 |
| 处方模块 | 7000-7999 | 处方开具、审核、流转 |
| 收费模块 | 8000-8999 | 费用计算、支付、退费 |
| 药房模块 | 9000-9999 | 药品库存、发药、效期 |
| 检查模块 | 10000-10999 | 检查申请、报告、审核 |
| 住院模块 | 11000-11999 | 入院、医嘱、床位、出院 |
| 排班模块 | 12000-12999 | 排班计划、号源生成 |
| 院外服务 | 13000-13999 | 在线问诊、慢病管理 |
| 随访模块 | 14000-14999 | 随访计划、满意度调查 |
| 健康档案 | 15000-15999 | 全生命周期健康档案 |
| 通知模块 | 16000-16999 | 模板管理、消息发送 |
| 统计模块 | 17000-17999 | 运营报表、数据大屏 |
| 系统管理 | 18000-18999 | 字典、参数、审计日志 |

#### 1.6.2 完整错误码对照表

##### 系统级错误（1-999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 200 | 200 | 成功 | 请求处理成功 |
| 400 | 400 | 请求参数错误 | 客户端请求参数有误 |
| 401 | 401 | 未认证 | 缺少或无效的认证凭证 |
| 403 | 403 | 无访问权限 | 已认证但无操作权限 |
| 404 | 404 | 资源不存在 | 请求的资源未找到 |
| 429 | 429 | 请求过于频繁 | 触发限流保护 |
| 500 | 500 | 内部服务器错误 | 服务端未知错误 |
| 503 | 503 | 服务不可用 | 服务暂时不可用 |

##### 认证授权（1000-1999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 1001 | 401 | Token已过期 | Access Token 超过有效期，需刷新 |
| 1002 | 401 | Token无效 | Token 签名验证失败或格式错误 |
| 1003 | 403 | 无操作权限 | 当前用户角色无此接口访问权限 |
| 1004 | 401 | 用户名或密码错误 | 登录凭证验证失败 |
| 1005 | 400 | 验证码错误 | 图形验证码校验不通过 |
| 1006 | 403 | 账号已禁用 | 账号被管理员停用 |

##### 参数校验（2000-2999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 2001 | 400 | 缺少必填参数 | 请求中未提供必填字段 |
| 2002 | 400 | 参数值无效 | 参数值不在允许范围内 |
| 2003 | 400 | 参数格式错误 | 参数格式不符合规范（如日期格式） |

##### 通用业务（4000-4999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 4001 | 404 | 资源不存在 | 请求的业务数据不存在 |
| 4002 | 400 | 当前状态不允许此操作 | 业务状态机不满足操作条件 |
| 4003 | 409 | 数据重复 | 违反唯一性约束 |

##### 挂号模块（5000-5999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 5001 | 200 | 号源已约满 | 当前时段号源已抢完 |
| 5002 | 200 | 请勿重复挂号 | 同一患者当日已挂此号 |
| 5003 | 200 | 排队已满 | 候诊队列已达上限 |
| 5004 | 200 | 号源未开放 | 当前号源不在开放预约时段 |
| 5005 | 200 | 挂号已取消 | 该挂号记录已取消 |
| 5006 | 200 | 挂号已过期 | 挂号日期已过就诊日 |

##### 门诊模块（6000-6999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 6001 | 400 | 患者未挂号 | 就诊前需先完成挂号 |
| 6002 | 400 | 诊断未完成 | 就诊记录缺少诊断信息 |
| 6003 | 400 | 就诊已结束 | 该次就诊流程已完成 |
| 6004 | 404 | 就诊记录不存在 | 指定的就诊记录未找到 |
| 6005 | 400 | ICD编码无效 | 诊断编码不在 ICD-10 库中 |

##### 处方模块（7000-7999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 7001 | 200 | 药品库存不足 | 处方中药品库存不够 |
| 7002 | 200 | 处方审核未通过 | 处方审核被上级医师驳回 |
| 7003 | 200 | 处方已审核 | 已审核的处方不能重复审核 |
| 7004 | 200 | 处方已作废 | 作废处方不可修改或提交 |
| 7005 | 200 | 处方中药品冲突 | 药物相互作用/过敏警告 |
| 7006 | 200 | 处方超量 | 单次处方剂量或天数超限 |
| 7007 | 200 | 处方未提交审核 | 需先提交审核才能执行后续操作 |

##### 收费模块（8000-8999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 8001 | 200 | 金额不符 | 支付金额与账单不一致 |
| 8002 | 200 | 已退费 | 该笔费用已退，不可重复操作 |
| 8003 | 200 | 已结算 | 该账单已支付完成 |
| 8004 | 200 | 医保结算失败 | 医保接口返回错误 |
| 8005 | 200 | 第三方支付失败 | 微信/支付宝支付接口异常 |
| 8006 | 200 | 退费审批未通过 | 退费申请被驳回 |

##### 药房模块（9000-9999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 9001 | 200 | 药品已过期 | 当前批次药品已过有效期 |
| 9002 | 200 | 库存不足 | 药品库存数量不够 |
| 9003 | 200 | 药品已停用 | 该药品已被标记为停用 |
| 9004 | 200 | 麻醉药品需双签名 | 特殊药品需两个人确认 |
| 9005 | 200 | 药品接近效期 | 距有效期不足90天，提示优先使用 |
| 9006 | 200 | 盘点中不可发药 | 正在盘点，药房暂锁 |

##### 检查模块（10000-10999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 10001 | 200 | 报告未完成 | 检查报告尚未录入 |
| 10002 | 200 | 已审核 | 报告已审核，不可修改 |
| 10003 | 200 | 检查未执行 | 需先完成检查再录入报告 |
| 10004 | 200 | 危急值上报中 | 报告含危急值，需上级审核 |
| 10005 | 200 | 报告已退回 | 审核不通过已退回修改 |

##### 住院模块（11000-11999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 11001 | 200 | 床位已满 | 病区无空闲床位 |
| 11002 | 200 | 未结算 | 患者出院前需完成费用结算 |
| 11003 | 200 | 医嘱已停止 | 已停止的医嘱不可再执行 |
| 11004 | 200 | 医嘱已执行 | 已执行的医嘱不能重复勾选 |
| 11005 | 200 | 床位维修中 | 该床位处于维护状态 |
| 11006 | 200 | 护理等级不满足 | 患者护理等级高于空余床位配置 |

##### 排班模块（12000-12999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 12001 | 200 | 排班冲突 | 医生/诊室同一时段已有排班 |
| 12002 | 200 | 号源未生成 | 当日号源还未生成 |
| 12003 | 200 | 号源并发冲突 | 多人同时操作同一号源，乐观锁冲突 |
| 12004 | 200 | 医生休假 | 指定日期医生标记为休假 |
| 12005 | 200 | 诊室已占用 | 该时段诊室已被其他医生使用 |

##### 院外服务（13000-13999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 13001 | 200 | 问诊已关闭 | 该问诊会话已被关闭 |
| 13002 | 200 | 慢病未签约 | 患者尚未签订慢病管理协议 |
| 13003 | 200 | 签约已过期 | 慢病签约时间已超期 |
| 13004 | 200 | 医生离线 | 当前医生不在线 |
| 13005 | 200 | 问诊排队中 | 已进入排队等待医生响应 |
| 13006 | 200 | 健康自测未完成 | 自测结果未达标 |

##### 随访模块（14000-14999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 14001 | 200 | 计划已过期 | 随访计划的结束日期已过 |
| 14002 | 200 | 未到随访时间 | 距离下次随访日期间隔最近 |
| 14003 | 200 | 计划已完成 | 所有随访任务已完成 |
| 14004 | 200 | 调查已提交 | 满意度调查已提交不能重复 |
| 14005 | 200 | 随访方式无效 | 不支持的随访方式 |

##### 健康档案（15000-15999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 15001 | 403 | 档案锁定 | 该档案被其他用户编辑中 |
| 15002 | 403 | 无访问权限 | 无权限查看该患者的档案 |
| 15003 | 404 | 档案不存在 | 该患者尚未建立健康档案 |
| 15004 | 200 | 档案数据不完整 | 缺少关键档案数据项 |

##### 通知模块（16000-16999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 16001 | 404 | 模板不存在 | 通知模板编码未找到 |
| 16002 | 200 | 发送失败 | 消息发送接口返回失败 |
| 16003 | 200 | 模板已禁用 | 当前模板状态为禁用 |
| 16004 | 200 | 渠道未配置 | 该通知渠道（短信/邮件/微信）未配置 |
| 16005 | 400 | 接收人格式错误 | 手机号/邮箱格式不正确 |

##### 统计模块（17000-17999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 17001 | 202 | 报表生成中 | 报表正在后台生成，稍后下载 |
| 17002 | 200 | 数据不足 | 指定统计区间数据量不足 |
| 17003 | 200 | 导出失败 | Excel/PDF 文件生成失败 |
| 17004 | 200 | 日期范围超限 | 统计日期区间超过最大允许范围 |

##### 系统管理（18000-18999）

| 错误码 | HTTP状态 | 消息 | 说明 |
| ------ | -------- | ---- | ---- |
| 18001 | 409 | 字典类型重复 | 字典类型编码已存在 |
| 18002 | 403 | 参数只读 | 系统级参数不允许手动修改 |
| 18003 | 409 | 字典项编码重复 | 同一字典类型下编码已存在 |
| 18004 | 400 | 字典类型被引用 | 被引用的字典类型不可删除 |
| 18005 | 503 | 操作日志写入失败 | 审计日志持久化异常 |

#### 1.6.3 错误码与 Go 常量映射

| 错误码常量名 | 错误码值 | 所属包 | 说明 |
| ------------ | -------- | ------ | ---- |
| `CodeSuccess` | 200 | pkg/errors | 成功 |
| `CodeInternal` | 500 | pkg/errors | 内部错误 |
| `CodeTokenExpired` | 1001 | pkg/errors | Token过期 |
| `CodeTokenInvalid` | 1002 | pkg/errors | Token无效 |
| `CodeNoPermission` | 1003 | pkg/errors | 无权限 |
| `CodeLoginFailed` | 1004 | pkg/errors | 登录失败 |
| `CodeCaptchaInvalid` | 1005 | pkg/errors | 验证码错误 |
| `CodeAccountDisabled` | 1006 | pkg/errors | 账号禁用 |
| `CodeParamMissing` | 2001 | pkg/errors | 参数缺失 |
| `CodeParamInvalid` | 2002 | pkg/errors | 参数无效 |
| `CodeParamFormatErr` | 2003 | pkg/errors | 参数格式错误 |
| `CodeResourceNotFound` | 4001 | pkg/errors | 资源不存在 |
| `CodeStatusNotAllowed` | 4002 | pkg/errors | 状态不允许 |
| `CodeDuplicate` | 4003 | pkg/errors | 数据重复 |
| `CodeScheduleFull` | 5001 | pkg/errors | 号源已满 |
| `CodeDuplicateRegister` | 5002 | pkg/errors | 重复挂号 |
| `CodeDrugStockLow` | 7001 | pkg/errors | 药品库存不足 |
| `CodePrescriptionAudit` | 7002 | pkg/errors | 处方审核未通过 |
| `CodeDrugExpired` | 9001 | pkg/errors | 药品过期 |
| `CodeStockLow` | 9002 | pkg/errors | 库存不足 |
| `CodeScheduleConflict` | 12001 | pkg/errors | 排班冲突 |
| `CodeSlotNotGenerated` | 12002 | pkg/errors | 号源未生成 |

> 更多模块级错误码在各自服务的 Java/Go 实现中定义，本表仅列出已实现的核心常量。

---

## 二、认证授权接口 (Auth — Java)

> 端口: HTTP 8081, gRPC 9081

### 2.1 登录

```
POST /api/auth/login
```

**请求体**：

```json
{
  "username": "demo-doctor",
  "password": "demo123",
  "captcha": "a3f5",
  "captchaKey": "uuid-xxx"
}
```

**响应**：

```json
{
  "code": 200,
  "data": {
    "accessToken": "eyJhbG...",
    "refreshToken": "eyJhbG...",
    "expiresIn": 7200,
    "userInfo": {
      "userId": "10001",
      "username": "demo-doctor",
      "realName": "张医生",
      "roles": ["DOCTOR"],
      "permissions": ["prescription:write", "emr:read"]
    }
  }
}
```

### 2.2 Token 刷新

```
POST /api/auth/refresh
```

**请求体**：

```json
{
  "refreshToken": "eyJhbG..."
}
```

### 2.3 登出

```
POST /api/auth/logout
```

**请求头**: `Authorization: Bearer {token}`

### 2.4 获取验证码

```
GET /api/auth/captcha
```

**响应**：

```json
{
  "code": 200,
  "data": {
    "captchaKey": "uuid-xxx",
    "captchaImage": "data:image/png;base64,..."
  }
}
```

### 2.5 角色管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/auth/roles` | 角色列表 |
| POST | `/api/auth/roles` | 新增角色 |
| PUT | `/api/auth/roles/{id}` | 更新角色 |
| DELETE | `/api/auth/roles/{id}` | 删除角色 |
| PUT | `/api/auth/roles/{id}/permissions` | 分配权限 |

### 2.6 权限管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/auth/permissions` | 权限树 |
| PUT | `/api/auth/users/{id}/roles` | 分配用户角色 |

---

## 三、用户管理接口 (User — Java)

> 端口: HTTP 8082, gRPC 9082

### 3.1 患者档案

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/user/patients` | 患者列表（分页） |
| GET | `/api/user/patients/{id}` | 患者详情 |
| POST | `/api/user/patients` | 新建患者 |
| PUT | `/api/user/patients/{id}` | 更新患者 |
| DELETE | `/api/user/patients/{id}` | 删除患者（逻辑删除） |
| GET | `/api/user/patients/{id}/medical-history` | 患者病史摘要 |

### 3.2 员工管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/user/employees` | 员工列表 |
| GET | `/api/user/employees/{id}` | 员工详情 |
| POST | `/api/user/employees` | 新增员工 |
| PUT | `/api/user/employees/{id}` | 更新员工 |
| PUT | `/api/user/employees/{id}/status` | 变更状态（在职/离职） |

### 3.3 科室管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/user/departments` | 科室树 |
| GET | `/api/user/departments/{id}` | 科室详情 |
| POST | `/api/user/departments` | 新增科室 |
| PUT | `/api/user/departments/{id}` | 更新科室 |

---

## 四、挂号预约接口 (Registration — Go)

> 端口: HTTP 8083, gRPC 9083

### 4.1 号源查询

```
GET /api/registration/schedules?deptId=1&date=2026-05-11
```

**响应**：

```json
{
  "code": 200,
  "data": {
    "list": [
      {
        "scheduleId": "sched-001",
        "doctorId": "10001",
        "doctorName": "张医生",
        "deptName": "内科",
        "timeSlot": "09:00-09:30",
        "totalQuota": 30,
        "remaining": 15,
        "fee": 20.00
      }
    ]
  }
}
```

### 4.2 预约挂号

```
POST /api/registration/appointments
```

**请求体**：

```json
{
  "scheduleId": "sched-001",
  "patientId": "p-10001",
  "visitType": "FIRST_VISIT",
  "symptom": "头痛"
}
```

### 4.3 排号查询

```
GET /api/registration/queue?deptId=1&date=2026-05-11
```

### 4.4 取消挂号

```
PUT /api/registration/appointments/{id}/cancel
```

### 4.5 挂号记录

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/registration/appointments` | 挂号记录列表 |
| GET | `/api/registration/appointments/{id}` | 挂号详情 |

---

## 五、门诊诊疗接口 (Clinic — Java)

> 端口: HTTP 8084, gRPC 9084

### 5.1 接诊登记

```
POST /api/clinic/encounters
```

**请求体**：

```json
{
  "registrationId": "reg-001",
  "chiefComplaint": "头痛3天",
  "presentIllness": "患者3天前无明显诱因出现头痛...",
  "pastHistory": "高血压病史2年",
  "physicalExam": {
    "temperature": 36.5,
    "pulse": 78,
    "bloodPressure": "120/80"
  }
}
```

### 5.2 诊断录入

```
POST /api/clinic/diagnoses
```

**请求体**：

```json
{
  "encounterId": "enc-001",
  "diagnoses": [
    {
      "icdCode": "G44.1",
      "diagnosisName": "血管性头痛",
      "diagnosisType": "PRIMARY"
    }
  ]
}
```

### 5.3 检查申请

```
POST /api/clinic/exam-requests
```

**请求体**：

```json
{
  "encounterId": "enc-001",
  "examType": "CT",
  "examItem": "头颅CT平扫",
  "urgency": "ROUTINE",
  "clinicalInfo": "头痛待查"
}
```

### 5.4 转诊

```
POST /api/clinic/referrals
```

### 5.5 查询接口

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/clinic/encounters` | 就诊记录列表 |
| GET | `/api/clinic/encounters/{id}` | 就诊详情 |
| GET | `/api/clinic/today` | 今日待诊列表 |

---

## 六、电子病历接口 (EMR — Java)

> 端口: HTTP 8097, gRPC 9097

### 6.1 病历创建

```
POST /api/emr/records
```

**请求体**：

```json
{
  "encounterId": "enc-001",
  "templateCode": "SOAP_STANDARD",
  "subjective": {
    "chiefComplaint": "头痛3天",
    "historyOfPresentIllness": "...",
    "pastMedicalHistory": "高血压2年"
  },
  "objective": {
    "vitalSigns": { "temperature": 36.5, "pulse": 78, "bp": "120/80" },
    "physicalExam": "..."
  },
  "assessment": {
    "primaryDiagnosis": "血管性头痛",
    "icdCode": "G44.1"
  },
  "plan": {
    "treatmentPlan": "药物治疗",
    "prescriptions": [],
    "followUp": "1周后复诊"
  }
}
```

### 6.2 病历质控

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| POST | `/api/emr/records/{id}/quality-check` | 提交质控 |
| PUT | `/api/emr/records/{id}/quality-control` | 质控审核（三级） |
| GET | `/api/emr/records/{id}/quality-report` | 质控报告 |

### 6.3 模板管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/emr/templates` | 模板列表 |
| GET | `/api/emr/templates/{code}` | 模板详情（含字段定义） |
| POST | `/api/emr/templates` | 新建模板 |

### 6.4 CDSS 决策支持

```
POST /api/emr/cdss/check
```

**请求体**：

```json
{
  "patientId": "p-10001",
  "diagnosis": {
    "icdCode": "G44.1",
    "diagnosisName": "血管性头痛"
  },
  "prescriptions": [
    {
      "drugId": "d-001",
      "drugName": "布洛芬",
      "dosage": "200mg",
      "frequency": "bid"
    }
  ]
}
```

**响应包含**: 药物过敏警告、相互作用警告、剂量校验、诊断关联建议。

---

## 七、处方管理接口 (Prescription — Java)

> 端口: HTTP 8085, gRPC 9085

### 7.1 处方开具

```
POST /api/prescription/prescriptions
```

**请求体**：

```json
{
  "encounterId": "enc-001",
  "prescriptionType": "WESTERN",
  "items": [
    {
      "drugId": "d-001",
      "drugName": "布洛芬缓释胶囊",
      "specification": "200mg*20粒",
      "quantity": 1,
      "unit": "盒",
      "usage": "口服",
      "dosage": "200mg",
      "frequency": "bid",
      "days": 7
    }
  ]
}
```

### 7.2 处方流转

| 操作 | 方法 | 路径 |
| ---- | ---- | ---- |
| 提交审核 | PUT | `/api/prescription/prescriptions/{id}/submit` |
| 审核通过 | PUT | `/api/prescription/prescriptions/{id}/approve` |
| 审核退回 | PUT | `/api/prescription/prescriptions/{id}/reject` |
| 废止处方 | PUT | `/api/prescription/prescriptions/{id}/void` |

### 7.3 处方查询

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/prescription/prescriptions` | 处方列表 |
| GET | `/api/prescription/prescriptions/{id}` | 处方详情 |
| GET | `/api/prescription/patient/{patientId}` | 患者所有处方 |

---

## 八、收费结算接口 (Billing — Java)

> 端口: HTTP 8086, gRPC 9086

### 8.1 费用计算

```
POST /api/billing/calculate
```

**请求体**：

```json
{
  "patientId": "p-10001",
  "items": [
    { "type": "REGISTRATION", "refId": "reg-001" },
    { "type": "PRESCRIPTION", "refId": "pres-001" },
    { "type": "EXAMINATION", "refId": "exam-001" }
  ]
}
```

### 8.2 收费结算

```
POST /api/billing/payments
```

**请求体**：

```json
{
  "billItems": ["bill-001", "bill-002"],
  "paymentMethod": "WECHAT",
  "totalAmount": 156.50
}
```

### 8.3 退费

```
POST /api/billing/refunds
```

**请求体**：

```json
{
  "paymentId": "pay-001",
  "refundItems": ["bill-001"],
  "refundReason": "药品过敏"
}
```

### 8.4 查询接口

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/billing/bills` | 账单列表 |
| GET | `/api/billing/bills/{id}` | 账单详情 |
| GET | `/api/billing/payments` | 支付记录 |
| GET | `/api/billing/daily-report` | 日结报表 |

---

## 九、药房管理接口 (Pharmacy — Go)

> 端口: HTTP 8087, gRPC 9087

### 9.1 药品库存

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/pharmacy/drugs` | 药品列表 |
| GET | `/api/pharmacy/drugs/{id}` | 药品详情 |
| POST | `/api/pharmacy/drugs` | 新增药品 |
| PUT | `/api/pharmacy/drugs/{id}` | 更新药品 |
| GET | `/api/pharmacy/drugs/expiring` | 近效期药品（30天内） |

### 9.2 库存管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| POST | `/api/pharmacy/inbound` | 入库 |
| GET | `/api/pharmacy/inbound/{id}` | 入库单详情 |
| POST | `/api/pharmacy/inventory-check` | 盘点 |
| GET | `/api/pharmacy/stock-log` | 库存流水 |

### 9.3 发药管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/pharmacy/dispense-queue` | 待发药列表 |
| POST | `/api/pharmacy/dispense` | 发药确认 |
| PUT | `/api/pharmacy/dispense/{id}/return` | 退药 |

---

## 十、检查检验接口 (Examination — Go)

> 端口: HTTP 8088, gRPC 9088

### 10.1 检查申请

```
GET /api/examination/requests?status=PENDING
```

### 10.2 报告管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| POST | `/api/examination/reports` | 录入报告 |
| GET | `/api/examination/reports/{id}` | 报告详情 |
| PUT | `/api/examination/reports/{id}/submit` | 提交审核 |
| PUT | `/api/examination/reports/{id}/approve` | 审核通过 |

### 10.3 结果查询

```
GET /api/examination/results?patientId=p-10001&startDate=2026-01-01&endDate=2026-05-11
```

### 10.4 检查项目

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/examination/items` | 检查项目列表 |
| GET | `/api/examination/items/{id}` | 检查项目详情 |

---

## 十一、住院管理接口 (Inpatient — Java)

> 端口: HTTP 8089, gRPC 9089

### 11.1 入院管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| POST | `/api/inpatient/admissions` | 办理入院 |
| PUT | `/api/inpatient/admissions/{id}` | 更新入院信息 |
| GET | `/api/inpatient/admissions/{id}` | 入院详情 |

### 11.2 床位管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/inpatient/beds` | 床位列表 |
| GET | `/api/inpatient/beds/available` | 空闲床位 |
| PUT | `/api/inpatient/beds/{id}/assign` | 分配床位 |
| PUT | `/api/inpatient/beds/{id}/release` | 释放床位 |

### 11.3 医嘱管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| POST | `/api/inpatient/orders` | 下达医嘱 |
| GET | `/api/inpatient/orders` | 医嘱列表 |
| PUT | `/api/inpatient/orders/{id}/execute` | 执行医嘱 |
| PUT | `/api/inpatient/orders/{id}/stop` | 停止医嘱 |

### 11.4 护理记录

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| POST | `/api/inpatient/nursing-records` | 新增护理记录 |
| GET | `/api/inpatient/nursing-records` | 护理记录列表 |

### 11.5 出院结算

```
POST /api/inpatient/discharges
```

**请求体**：

```json
{
  "admissionId": "adm-001",
  "dischargeType": "CURED",
  "dischargeSummary": "患者经治疗后好转出院"
}
```

---

## 十二、排班管理接口 (Schedule — Go)

> 端口: HTTP 8090, gRPC 9090

### 12.1 排班计划

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/schedule/plans` | 排班列表 |
| POST | `/api/schedule/plans` | 创建排班 |
| PUT | `/api/schedule/plans/{id}` | 更新排班 |
| DELETE | `/api/schedule/plans/{id}` | 删除排班 |

### 12.2 号源生成

```
POST /api/schedule/slots/generate
```

**请求体**：

```json
{
  "doctorId": "10001",
  "deptId": "1",
  "date": "2026-05-11",
  "timeSlots": [
    { "startTime": "08:00", "endTime": "12:00", "quota": 30, "intervalMin": 10 }
  ]
}
```

### 12.3 查询接口

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/schedule/slots` | 号源查询（按科室/日期） |
| GET | `/api/schedule/doctors` | 可排班医生列表 |

---

## 十三、院外服务接口 (Outpatient — Go)

> 端口: HTTP 8091, gRPC 9091

### 13.1 在线问诊

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| POST | `/api/outpatient/consultations` | 发起问诊 |
| GET | `/api/outpatient/consultations/{id}` | 问诊详情 |
| GET | `/api/outpatient/consultations/{id}/messages` | 问诊消息记录 |
| POST | `/api/outpatient/consultations/{id}/messages` | 发送消息 |
| PUT | `/api/outpatient/consultations/{id}/close` | 结束问诊 |

### 13.2 WebSocket 实时通信

```
ws://{host}:8080/api/outpatient/ws?token={jwt_token}
```

消息格式：

```json
{
  "type": "MESSAGE",
  "consultationId": "cons-001",
  "content": "医生您好，我最近...",
  "timestamp": 1715414400000
}
```

### 13.3 慢病管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| POST | `/api/outpatient/chronic/sign` | 签约 |
| GET | `/api/outpatient/chronic/records` | 签约记录 |
| POST | `/api/outpatient/chronic/health-report` | 健康自测 |
| GET | `/api/outpatient/chronic/alerts` | 异常告警 |

---

## 十四、随访管理接口 (Follow-up — Go)

> 端口: HTTP 8092, gRPC 9092

### 14.1 随访计划

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/followup/plans` | 计划列表 |
| POST | `/api/followup/plans` | 创建计划 |
| GET | `/api/followup/plans/{id}` | 计划详情 |
| PUT | `/api/followup/plans/{id}` | 更新计划 |

### 14.2 随访执行

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| POST | `/api/followup/records` | 添加随访记录 |
| GET | `/api/followup/records` | 记录列表 |
| GET | `/api/followup/today` | 今日待随访 |
| GET | `/api/followup/overdue` | 逾期未随访 |

### 14.3 满意度调查

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/followup/surveys` | 调查模板 |
| POST | `/api/followup/surveys/answers` | 提交调查 |
| GET | `/api/followup/surveys/stats` | 调查统计 |

---

## 十五、健康档案接口 (Health Record — Java)

> 端口: HTTP 8093, gRPC 9093

### 15.1 档案总览

```
GET /api/health-record/patients/{patientId}/overview
```

**响应**：

```json
{
  "code": 200,
  "data": {
    "basicInfo": {},
    "timeline": [
      { "date": "2026-05-11", "event": "门诊-内科-张医生", "type": "CLINIC" },
      { "date": "2026-05-10", "event": "处方-布洛芬", "type": "PRESCRIPTION" }
    ],
    "summary": {
      "visitCount": 12,
      "diagnosisSummary": ["血管性头痛", "高血压"],
      "allergySummary": ["青霉素"]
    }
  }
}
```

### 15.2 分类查询

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/health-record/patients/{id}/visits` | 就诊历史 |
| GET | `/api/health-record/patients/{id}/prescriptions` | 处方记录 |
| GET | `/api/health-record/patients/{id}/examinations` | 检查记录 |
| GET | `/api/health-record/patients/{id}/inpatient` | 住院记录 |

---

## 十六、消息通知接口 (Notification — Go)

> 端口: HTTP 8094, gRPC 9094

### 16.1 模板管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/notification/templates` | 模板列表 |
| POST | `/api/notification/templates` | 新增模板 |
| PUT | `/api/notification/templates/{id}` | 更新模板 |

### 16.2 消息发送

```
POST /api/notification/send
```

**请求体**：

```json
{
  "channel": "SMS",
  "templateCode": "REGISTRATION_SUCCESS",
  "recipient": "13800138000",
  "params": {
    "name": "张三",
    "dept": "内科",
    "time": "2026-05-11 09:00"
  }
}
```

| channel 可选值 | 说明 |
| -------------- | ---- |
| SMS | 短信 |
| EMAIL | 邮件 |
| SITE | 站内信 |
| WECHAT | 微信模板消息 |

### 16.3 消息记录

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/notification/messages` | 发送记录 |
| GET | `/api/notification/messages/{id}` | 消息详情 |
| PUT | `/api/notification/messages/{id}/read` | 标记已读 |

---

## 十七、数据统计接口 (Statistics — Go)

> 端口: HTTP 8095, gRPC 9095

### 17.1 运营报表

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/statistics/registration-trend` | 挂号趋势 |
| GET | `/api/statistics/revenue-trend` | 收入趋势 |
| GET | `/api/statistics/dept-workload` | 科室工作量 |
| GET | `/api/statistics/medical-quality` | 医疗质量指标 |

### 17.2 数据大屏

```
GET /api/statistics/dashboard
```

**响应**：实时院内数据（今日挂号数、候诊人数、在院人数、空床数、收入概览等）。

### 17.3 报表导出

```
POST /api/statistics/export
```

**请求体**：

```json
{
  "reportType": "REVENUE_MONTHLY",
  "format": "EXCEL",
  "params": { "year": 2026, "month": 5 }
}
```

---

## 十八、系统管理接口 (System — Java)

> 端口: HTTP 8096, gRPC 9096

### 18.1 字典管理

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/system/dict/types` | 字典类型列表 |
| POST | `/api/system/dict/types` | 新增字典类型 |
| GET | `/api/system/dict/types/{type}/items` | 字典项列表 |
| POST | `/api/system/dict/types/{type}/items` | 新增字典项 |
| PUT | `/api/system/dict/items/{id}` | 更新字典项 |

### 18.2 参数配置

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/system/configs` | 参数列表 |
| PUT | `/api/system/configs/{key}` | 更新参数值 |
| GET | `/api/system/configs/{key}` | 参数详情 |

### 18.3 操作日志

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/system/audit-logs` | 操作日志列表 |
| GET | `/api/system/audit-logs/{id}` | 日志详情 |

### 18.4 健康检查

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET | `/api/ping` | 网关连通性测试 |
| GET | `/api/health` | 各服务健康检查 |

---

## 十九、gRPC 接口清单

### 19.1 Go 服务 gRPC 接口

| 服务 | proto 文件 | RPC 方法数 |
| ---- | ---------- | ---------- |
| his-gateway | 无（纯 HTTP 网关） | - |
| his-registration | `registration/registration.proto` | ~8 |
| his-pharmacy | `pharmacy/pharmacy.proto` | ~10 |
| his-examination | `examination/examination.proto` | ~6 |
| his-schedule | `schedule/schedule.proto` | ~6 |
| his-outpatient | `outpatient/outpatient.proto` | ~8 |
| his-followup | `followup/followup.proto` | ~8 |
| his-notification | `notification/notification.proto` | ~6 |
| his-statistics | `statistics/statistics.proto` | ~8 |

### 19.2 Java 服务 gRPC 接口

| 服务 | proto 文件 | RPC 方法数 |
| ---- | ---------- | ---------- |
| his-auth | `auth/auth.proto` | ~8 |
| his-user | `user/user.proto` | ~10 |
| his-clinic | `clinic/clinic.proto` | ~8 |
| his-emr | `emr/emr.proto` | ~8 |
| his-prescription | `prescription/prescription.proto` | ~8 |
| his-billing | `billing/billing.proto` | ~8 |
| his-inpatient | `inpatient/inpatient.proto` | ~10 |
| his-health-record | `health_record/health_record.proto` | ~6 |
| his-system | `system/system.proto` | ~8 |

---

## 二十、接口安全

### 20.1 JWT 认证

- **签名算法**: RS256 (非对称加密)
- **Token 格式**: `Authorization: Bearer {token}`
- **Access Token 有效期**: 2 小时
- **Refresh Token 有效期**: 7 天
- **白名单路径**（无需认证）:
  - `POST /api/auth/login`
  - `POST /api/auth/refresh`
  - `GET /api/auth/captcha`
  - `GET /api/health`
  - `GET /api/ping`

### 20.2 限流策略

| 接口类型 | 限制策略 | 说明 |
| -------- | -------- | ---- |
| 登录接口 | 5次/分钟/用户名 | 防暴力破解 |
| 验证码 | 1次/分钟/IP | 防刷验证码 |
| 挂号接口 | 10次/秒/用户 | 防恶意刷号 |
| 通用业务接口 | 100次/秒/用户 | 滑动窗口限流 |

### 20.3 数据脱敏

| 字段 | 脱敏规则 | 示例 |
| ---- | -------- | ---- |
| 手机号 | 保留前3后4 | 138****8000 |
| 身份证号 | 保留前3后4 | 320****1234 |
| 银行卡号 | 保留后4位 | ****1234 |

---

## 参考项目

- [HIS-Go](https://github.com/Tangyd893/HIS-Go) — 纯 Go 版 HIS，接口设计参考
- [Hospital-Information-System](https://github.com/Tangyd893/Hospital-Information-System) — 原 Spring Cloud Alibaba 版，业务接口来源
