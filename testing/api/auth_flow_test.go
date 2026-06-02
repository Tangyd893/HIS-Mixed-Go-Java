package api

import (
	"encoding/json"
	"fmt"
	"net/http"
	"os"
	"testing"

	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
)

func getBaseURL() string {
	if url := os.Getenv("HIS_BASE_URL"); url != "" {
		return url
	}
	return "http://localhost:8080"
}

// TestHealthCheck 测试健康检查端点
func TestHealthCheck(t *testing.T) {
	if os.Getenv("HIS_INTEGRATION_TEST") == "" {
		t.Skip("跳过集成测试，设置 HIS_INTEGRATION_TEST=true 启用")
	}

	client := NewClient(getBaseURL())
	resp, err := client.Get("/api/health")
	require.NoError(t, err)
	assert.Equal(t, http.StatusOK, resp.StatusCode)

	var result APIResponse
	err = ReadJSON(resp, &result)
	require.NoError(t, err)
	assert.Equal(t, 200, result.Code)
}

// TestPing 测试连通性
func TestPing(t *testing.T) {
	if os.Getenv("HIS_INTEGRATION_TEST") == "" {
		t.Skip("跳过集成测试，设置 HIS_INTEGRATION_TEST=true 启用")
	}

	client := NewClient(getBaseURL())
	resp, err := client.Get("/api/ping")
	require.NoError(t, err)
	assert.Equal(t, http.StatusOK, resp.StatusCode)
}

// TestAuthFlow 测试认证流程
func TestAuthFlow(t *testing.T) {
	if os.Getenv("HIS_INTEGRATION_TEST") == "" {
		t.Skip("跳过集成测试，设置 HIS_INTEGRATION_TEST=true 启用")
	}

	client := NewClient(getBaseURL())

	// 1. 登录
	loginReq := LoginRequest{
		Username: "demo-doctor",
		Password: "demo123",
	}
	resp, err := client.Post("/api/auth/login", loginReq)
	require.NoError(t, err)
	require.Equal(t, http.StatusOK, resp.StatusCode)

	var result APIResponse
	err = ReadJSON(resp, &result)
	require.NoError(t, err)
	assert.Equal(t, 200, result.Code)

	var loginData LoginResponse
	err = json.Unmarshal(result.Data, &loginData)
	require.NoError(t, err)
	require.NotEmpty(t, loginData.AccessToken)

	// 2. 使用 Token 调用受保护接口
	client.SetToken(loginData.AccessToken)
	resp, err = client.Get("/api/health")
	require.NoError(t, err)
	assert.Equal(t, http.StatusOK, resp.StatusCode)

	fmt.Printf("认证流程测试通过: 用户=%s, Token=%s...\n",
		loginData.UserInfo.Username, loginData.AccessToken[:20])
}

// TestUnauthorizedAccess 测试未认证访问被拦截
func TestUnauthorizedAccess(t *testing.T) {
	if os.Getenv("HIS_INTEGRATION_TEST") == "" {
		t.Skip("跳过集成测试，设置 HIS_INTEGRATION_TEST=true 启用")
	}

	client := NewClient(getBaseURL())

	resp, err := client.Get("/api/registration/schedules?deptId=1&date=2026-05-11")
	require.NoError(t, err)
	assert.Equal(t, http.StatusUnauthorized, resp.StatusCode)
}
