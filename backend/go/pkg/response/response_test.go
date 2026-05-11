package response_test

import (
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/gin-gonic/gin"
	perrors "github.com/his-mixed/go/pkg/errors"
	"github.com/his-mixed/go/pkg/response"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
)

func setupRouter(handlers ...gin.HandlerFunc) *gin.Engine {
	gin.SetMode(gin.TestMode)
	r := gin.New()
	for _, h := range handlers {
		r.Use(h)
	}
	return r
}

func TestSuccess(t *testing.T) {
	r := gin.New()
	r.GET("/test", func(c *gin.Context) {
		response.Success(c, gin.H{"name": "张三"})
	})

	w := httptest.NewRecorder()
	req, _ := http.NewRequest(http.MethodGet, "/test", nil)
	r.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)

	var result response.Result
	err := json.Unmarshal(w.Body.Bytes(), &result)
	require.NoError(t, err)
	assert.Equal(t, 200, result.Code)
	assert.Equal(t, "success", result.Message)
	assert.Greater(t, result.Timestamp, int64(0))

	data, ok := result.Data.(map[string]interface{})
	require.True(t, ok)
	assert.Equal(t, "张三", data["name"])
}

func TestSuccessPage(t *testing.T) {
	r := gin.New()
	r.GET("/test", func(c *gin.Context) {
		response.SuccessPage(c, []string{"a", "b", "c"}, 100, 1, 20)
	})

	w := httptest.NewRecorder()
	req, _ := http.NewRequest(http.MethodGet, "/test", nil)
	r.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)

	var result response.Result
	err := json.Unmarshal(w.Body.Bytes(), &result)
	require.NoError(t, err)
	assert.Equal(t, 200, result.Code)

	pageData, ok := result.Data.(map[string]interface{})
	require.True(t, ok)
	assert.Equal(t, float64(100), pageData["total"])
	assert.Equal(t, float64(1), pageData["page"])
	assert.Equal(t, float64(20), pageData["pageSize"])
}

func TestError(t *testing.T) {
	r := gin.New()
	r.GET("/test", func(c *gin.Context) {
		response.Error(c, http.StatusBadRequest, perrors.CodeBadRequest)
	})

	w := httptest.NewRecorder()
	req, _ := http.NewRequest(http.MethodGet, "/test", nil)
	r.ServeHTTP(w, req)

	assert.Equal(t, http.StatusBadRequest, w.Code)

	var result response.Result
	err := json.Unmarshal(w.Body.Bytes(), &result)
	require.NoError(t, err)
	assert.Equal(t, perrors.CodeBadRequest, result.Code)
	assert.Equal(t, "请求参数错误", result.Message)
	assert.Nil(t, result.Data)
}
