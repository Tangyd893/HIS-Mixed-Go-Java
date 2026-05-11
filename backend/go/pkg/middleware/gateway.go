// Package middleware 网关专用中间件
package middleware

import (
	"net/http"
	"strings"
	"sync"
	"time"

	"github.com/gin-gonic/gin"
	perrors "github.com/his-mixed/go/pkg/errors"
	"github.com/his-mixed/go/pkg/response"
	"github.com/his-mixed/go/pkg/security"
)

// 白名单路由（无需认证）
var whitelist = map[string]bool{
	"/api/auth/login":   true,
	"/api/auth/refresh": true,
	"/api/auth/captcha": true,
	"/api/health":       true,
	"/api/ping":         true,
}

// Auth JWT 鉴权中间件
func Auth() gin.HandlerFunc {
	return func(c *gin.Context) {
		if whitelist[c.Request.URL.Path] {
			c.Next()
			return
		}

		authHeader := c.GetHeader("Authorization")
		if authHeader == "" {
			response.Error(c, http.StatusUnauthorized, perrors.CodeUnauthorized)
			c.Abort()
			return
		}

		tokenStr := strings.TrimPrefix(authHeader, "Bearer ")
		claims, err := security.ParseToken(tokenStr)
		if err != nil {
			response.Error(c, http.StatusUnauthorized, perrors.CodeTokenInvalid)
			c.Abort()
			return
		}

		c.Set("userId", claims.UserID)
		c.Set("username", claims.Username)
		c.Set("realName", claims.RealName)
		c.Set("roles", claims.Roles)
		c.Set("permissions", claims.Permissions)
		c.Set("deptId", claims.DeptID)
		c.Next()
	}
}

// RateLimiter 滑动窗口限流中间件
type RateLimiter struct {
	mu       sync.Mutex
	counters map[string]*windowCounter
}

type windowCounter struct {
	count     int
	resetTime int64
}

func NewRateLimiter() *RateLimiter {
	return &RateLimiter{
		counters: make(map[string]*windowCounter),
	}
}

// Allow 检查是否允许请求
func (rl *RateLimiter) Allow(key string, limit int, windowSeconds int64) gin.HandlerFunc {
	return func(c *gin.Context) {
		rl.mu.Lock()
		defer rl.mu.Unlock()

		now := time.Now().Unix()
		wc, ok := rl.counters[key]
		if !ok || now >= wc.resetTime {
			rl.counters[key] = &windowCounter{count: 1, resetTime: now + windowSeconds}
			c.Next()
			return
		}

		if wc.count >= limit {
			response.Error(c, http.StatusTooManyRequests, 429)
			c.Abort()
			return
		}

		wc.count++
		c.Next()
	}
}
