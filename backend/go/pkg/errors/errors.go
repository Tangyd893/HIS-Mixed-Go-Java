// Package errors 统一错误码定义与处理
package errors

import "fmt"

// 错误码分类
const (
	// 系统级错误 1-999
	CodeSuccess      = 200
	CodeInternal     = 500
	CodeUnavailable  = 503
	CodeBadRequest   = 400
	CodeNotFound     = 404
	CodeForbidden    = 403
	CodeUnauthorized = 401

	// 认证授权 1000-1999
	CodeTokenExpired    = 1001
	CodeTokenInvalid    = 1002
	CodeNoPermission    = 1003
	CodeLoginFailed     = 1004
	CodeCaptchaInvalid  = 1005
	CodeAccountDisabled = 1006

	// 参数校验 2000-2999
	CodeParamMissing   = 2001
	CodeParamInvalid   = 2002
	CodeParamFormatErr = 2003

	// 业务通用 4000-4999
	CodeResourceNotFound = 4001
	CodeStatusNotAllowed = 4002
	CodeDuplicate        = 4003

	// 挂号模块 5000-5999
	CodeScheduleFull      = 5001
	CodeDuplicateRegister = 5002
	CodeQueueFull         = 5003

	// 处方模块 7000-7999
	CodeDrugStockLow      = 7001
	CodePrescriptionAudit = 7002

	// 药房模块 9000-9999
	CodeDrugExpired = 9001
	CodeStockLow    = 9002

	// 排班模块 12000-12999
	CodeScheduleConflict = 12001
	CodeSlotNotGenerated = 12002
)

var codeMessages = map[int]string{
	CodeSuccess:      "成功",
	CodeInternal:     "内部服务器错误",
	CodeUnavailable:  "服务不可用",
	CodeBadRequest:   "请求参数错误",
	CodeNotFound:     "资源不存在",
	CodeForbidden:    "无访问权限",
	CodeUnauthorized: "未认证",

	CodeTokenExpired:    "Token已过期",
	CodeTokenInvalid:    "Token无效",
	CodeNoPermission:    "无操作权限",
	CodeLoginFailed:     "用户名或密码错误",
	CodeCaptchaInvalid:  "验证码错误",
	CodeAccountDisabled: "账号已禁用",

	CodeParamMissing:   "缺少必填参数",
	CodeParamInvalid:   "参数值无效",
	CodeParamFormatErr: "参数格式错误",

	CodeResourceNotFound: "资源不存在",
	CodeStatusNotAllowed: "当前状态不允许此操作",
	CodeDuplicate:        "数据重复",

	CodeScheduleFull:      "号源已约满",
	CodeDuplicateRegister: "请勿重复挂号",

	CodeDrugStockLow:      "药品库存不足",
	CodePrescriptionAudit: "处方审核未通过",

	CodeDrugExpired: "药品已过期",
	CodeStockLow:    "库存不足",

	CodeScheduleConflict: "排班冲突",
	CodeSlotNotGenerated: "号源未生成",
}

// GetMessage 获取错误码对应消息
func GetMessage(code int) string {
	if msg, ok := codeMessages[code]; ok {
		return msg
	}
	return "未知错误"
}

// BizError 业务错误
type BizError struct {
	Code    int
	Message string
}

func (e *BizError) Error() string {
	return fmt.Sprintf("[%d] %s", e.Code, e.Message)
}

// New 创建业务错误
func New(code int) *BizError {
	return &BizError{Code: code, Message: GetMessage(code)}
}

// Newf 创建带格式化消息的业务错误
func Newf(code int, format string, args ...interface{}) *BizError {
	return &BizError{Code: code, Message: fmt.Sprintf(format, args...)}
}
