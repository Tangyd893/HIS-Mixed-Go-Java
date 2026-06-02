package middleware_test

import (
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/pkg/middleware"
	"github.com/stretchr/testify/assert"
)

func setupRouter(handlers ...gin.HandlerFunc) *gin.Engine {
	gin.SetMode(gin.TestMode)
	r := gin.New()
	for _, h := range handlers {
		r.Use(h)
	}
	return r
}

func TestAuthMiddlewareWhitelist(t *testing.T) {
	r := setupRouter(middleware.Auth())
	r.GET("/api/health", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"status": "UP"})
	})

	w := httptest.NewRecorder()
	req, _ := http.NewRequest(http.MethodGet, "/api/health", nil)
	r.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)
}

func TestAuthMiddlewareNoToken(t *testing.T) {
	r := setupRouter(middleware.Auth())
	r.GET("/api/user/info", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{})
	})

	w := httptest.NewRecorder()
	req, _ := http.NewRequest(http.MethodGet, "/api/user/info", nil)
	r.ServeHTTP(w, req)

	assert.Equal(t, http.StatusUnauthorized, w.Code)
}

func TestRequestIDMiddleware(t *testing.T) {
	r := setupRouter(middleware.RequestID())
	r.GET("/api/test", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{})
	})

	w := httptest.NewRecorder()
	req, _ := http.NewRequest(http.MethodGet, "/api/test", nil)
	r.ServeHTTP(w, req)

	reqID := w.Header().Get("X-Request-Id")
	assert.NotEmpty(t, reqID)
	assert.Contains(t, reqID, "req-")
}

func TestCORSMiddleware(t *testing.T) {
	r := setupRouter(middleware.CORS())
	r.GET("/api/test", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{})
	})

	w := httptest.NewRecorder()
	req, _ := http.NewRequest(http.MethodOptions, "/api/test", nil)
	r.ServeHTTP(w, req)

	assert.Equal(t, http.StatusNoContent, w.Code)
}

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
