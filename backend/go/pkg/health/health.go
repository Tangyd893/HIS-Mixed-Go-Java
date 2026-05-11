// Package health 健康检查端点
package health

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

// Handler 存活检查
func Handler(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"status": "UP",
	})
}

// ReadyHandler 就绪检查
func ReadyHandler(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"status": "READY",
	})
}

// PingHandler API 连通性测试
func PingHandler(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"message": "pong",
	})
}
