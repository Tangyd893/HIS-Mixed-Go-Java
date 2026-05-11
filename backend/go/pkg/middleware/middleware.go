// Package middleware 通用 HTTP 中间件 — CORS、限流、链路追踪、恢复
package middleware

import (
	"log"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

// CORS 跨域中间件
func CORS() gin.HandlerFunc {
	return func(c *gin.Context) {
		c.Header("Access-Control-Allow-Origin", "*")
		c.Header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS")
		c.Header("Access-Control-Allow-Headers", "Origin,Content-Type,Authorization,X-Request-Id")
		c.Header("Access-Control-Expose-Headers", "Content-Length,X-Request-Id")
		c.Header("Access-Control-Max-Age", "86400")

		if c.Request.Method == http.MethodOptions {
			c.AbortWithStatus(http.StatusNoContent)
			return
		}
		c.Next()
	}
}

// RequestID 请求 ID 中间件
func RequestID() gin.HandlerFunc {
	return func(c *gin.Context) {
		reqID := c.GetHeader("X-Request-Id")
		if reqID == "" {
			reqID = generateID()
		}
		c.Set("RequestID", reqID)
		c.Header("X-Request-Id", reqID)
		c.Next()
	}
}

// Recovery 恢复中间件
func Recovery() gin.HandlerFunc {
	return func(c *gin.Context) {
		defer func() {
			if err := recover(); err != nil {
				log.Printf("请求处理 panic: %v", err)
				c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{
					"code":    500,
					"message": "内部服务器错误",
				})
			}
		}()
		c.Next()
	}
}

// Logger 请求日志中间件
func Logger() gin.HandlerFunc {
	return func(c *gin.Context) {
		start := time.Now()
		path := c.Request.URL.Path
		c.Next()
		latency := time.Since(start)
		log.Printf("[%s] %s %s %d %v",
			c.Request.Method, path, c.ClientIP(),
			c.Writer.Status(), latency,
		)
	}
}

func generateID() string {
	return "req-" + time.Now().Format("20060102150405")
}
