package handler

import (
	"fmt"
	"net/http"
	"net/http/httputil"
	"net/url"

	"github.com/gin-gonic/gin"
)

func InitProxies(routes map[string]string) map[string]gin.HandlerFunc {
	proxies := make(map[string]gin.HandlerFunc)
	for name, target := range routes {
		proxies[name] = createProxy(target)
	}
	return proxies
}

func createProxy(target string) gin.HandlerFunc {
	targetURL, _ := url.Parse(target)
	return func(c *gin.Context) {
		proxy := httputil.NewSingleHostReverseProxy(targetURL)
		proxy.Director = func(req *http.Request) {
			req.URL.Scheme = targetURL.Scheme
			req.URL.Host = targetURL.Host
			req.URL.Path = c.Request.URL.Path
			req.Host = targetURL.Host
			if userID, exists := c.Get("userId"); exists {
				req.Header.Set("X-User-Id", fmt.Sprintf("%v", userID))
			}
			if username, exists := c.Get("username"); exists {
				req.Header.Set("X-Username", fmt.Sprintf("%v", username))
			}
			if realName, exists := c.Get("realName"); exists {
				req.Header.Set("X-Real-Name", fmt.Sprintf("%v", realName))
			}
		}
		proxy.ErrorHandler = func(w http.ResponseWriter, r *http.Request, err error) {
			c.JSON(http.StatusBadGateway, gin.H{
				"code":    503,
				"message": fmt.Sprintf("服务不可用: %v", err),
			})
		}
		proxy.ServeHTTP(c.Writer, c.Request)
	}
}
