// Package api HTTP 客户端封装 — 集成测试用
package api

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"time"
)

// Client HTTP 测试客户端
type Client struct {
	BaseURL    string
	HTTPClient *http.Client
	Token      string
}

// NewClient 创建测试客户端
func NewClient(baseURL string) *Client {
	return &Client{
		BaseURL: baseURL,
		HTTPClient: &http.Client{
			Timeout: 10 * time.Second,
		},
	}
}

// SetToken 设置认证 Token
func (c *Client) SetToken(token string) {
	c.Token = token
}

// Get 发送 GET 请求
func (c *Client) Get(path string) (*http.Response, error) {
	req, err := http.NewRequest("GET", c.BaseURL+path, nil)
	if err != nil {
		return nil, err
	}
	c.setHeaders(req)
	return c.HTTPClient.Do(req)
}

// Post 发送 POST 请求
func (c *Client) Post(path string, body interface{}) (*http.Response, error) {
	data, err := json.Marshal(body)
	if err != nil {
		return nil, err
	}
	req, err := http.NewRequest("POST", c.BaseURL+path, bytes.NewReader(data))
	if err != nil {
		return nil, err
	}
	c.setHeaders(req)
	return c.HTTPClient.Do(req)
}

func (c *Client) setHeaders(req *http.Request) {
	req.Header.Set("Content-Type", "application/json")
	if c.Token != "" {
		req.Header.Set("Authorization", "Bearer "+c.Token)
	}
}

// ReadBody 读取响应体
func ReadBody(resp *http.Response) ([]byte, error) {
	defer resp.Body.Close()
	return io.ReadAll(resp.Body)
}

// ReadJSON 将响应体解析为 JSON
func ReadJSON(resp *http.Response, v interface{}) error {
	defer resp.Body.Close()
	return json.NewDecoder(resp.Body).Decode(v)
}

// APIResponse 统一响应结构
type APIResponse struct {
	Code      int             `json:"code"`
	Message   string          `json:"message"`
	Data      json.RawMessage `json:"data"`
	Timestamp int64           `json:"timestamp"`
}

// LoginRequest 登录请求
type LoginRequest struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

// LoginResponse 登录响应
type LoginResponse struct {
	AccessToken  string   `json:"accessToken"`
	RefreshToken string   `json:"refreshToken"`
	ExpiresIn    int      `json:"expiresIn"`
	UserInfo     struct {
		UserID   int64    `json:"userId"`
		Username string   `json:"username"`
		RealName string   `json:"realName"`
		Roles    []string `json:"roles"`
	} `json:"userInfo"`
}

// PrintResponse 打印响应信息（调试用）
func PrintResponse(resp *http.Response) {
	body, _ := ReadBody(resp)
	fmt.Printf("Status: %d\nBody: %s\n", resp.StatusCode, string(body))
}
