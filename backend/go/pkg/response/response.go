// Package response 统一 HTTP 响应格式
package response

import (
	"net/http"

	"github.com/gin-gonic/gin"
	perrors "github.com/his-mixed/go/pkg/errors"

	"time"
)

// Result 统一响应结构
type Result struct {
	Code      int         `json:"code"`
	Message   string      `json:"message"`
	Data      interface{} `json:"data"`
	Timestamp int64       `json:"timestamp"`
}

// PageResult 分页响应
type PageResult struct {
	List     interface{} `json:"list"`
	Total    int64       `json:"total"`
	Page     int         `json:"page"`
	PageSize int         `json:"pageSize"`
}

// Success 成功响应
func Success(c *gin.Context, data interface{}) {
	c.JSON(http.StatusOK, &Result{
		Code:      200,
		Message:   "success",
		Data:      data,
		Timestamp: time.Now().UnixMilli(),
	})
}

// SuccessPage 分页成功响应
func SuccessPage(c *gin.Context, list interface{}, total int64, page, pageSize int) {
	c.JSON(http.StatusOK, &Result{
		Code:    200,
		Message: "success",
		Data: &PageResult{
			List:     list,
			Total:    total,
			Page:     page,
			PageSize: pageSize,
		},
		Timestamp: time.Now().UnixMilli(),
	})
}

// Error 错误响应
func Error(c *gin.Context, httpStatus, bizCode int) {
	c.JSON(httpStatus, &Result{
		Code:      bizCode,
		Message:   perrors.GetMessage(bizCode),
		Data:      nil,
		Timestamp: time.Now().UnixMilli(),
	})
}

// ErrorMsg 带自定义消息的错误响应
func ErrorMsg(c *gin.Context, httpStatus, bizCode int, msg string) {
	c.JSON(httpStatus, &Result{
		Code:      bizCode,
		Message:   msg,
		Data:      nil,
		Timestamp: time.Now().UnixMilli(),
	})
}

// Errorf 格式化错误响应
func Errorf(c *gin.Context, httpStatus, bizCode int, format string, args ...interface{}) {
	msg := format
	if len(args) > 0 {
		msg = format
	}
	c.JSON(httpStatus, &Result{
		Code:      bizCode,
		Message:   msg,
		Data:      nil,
		Timestamp: time.Now().UnixMilli(),
	})
}
