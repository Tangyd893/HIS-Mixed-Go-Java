// Package handler 网关反向代理 handler
package handler

import (
	"fmt"
	"net/http"
	"net/http/httputil"
	"net/url"

	"github.com/gin-gonic/gin"
)

var serviceTargets = map[string]string{
	"auth":          "http://localhost:8081",
	"user":          "http://localhost:8082",
	"registration":  "http://localhost:8083",
	"clinic":        "http://localhost:8084",
	"prescription":  "http://localhost:8085",
	"billing":       "http://localhost:8086",
	"pharmacy":      "http://localhost:8087",
	"examination":   "http://localhost:8088",
	"inpatient":     "http://localhost:8089",
	"schedule":      "http://localhost:8090",
	"outpatient":    "http://localhost:8091",
	"followup":      "http://localhost:8092",
	"health-record": "http://localhost:8093",
	"notification":  "http://localhost:8094",
	"statistics":    "http://localhost:8095",
	"system":        "http://localhost:8096",
	"emr":           "http://localhost:8097",
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

var ProxyAuth = createProxy(serviceTargets["auth"])
var ProxyUser = createProxy(serviceTargets["user"])
var ProxyRegistration = createProxy(serviceTargets["registration"])
var ProxyClinic = createProxy(serviceTargets["clinic"])
var ProxyPrescription = createProxy(serviceTargets["prescription"])
var ProxyBilling = createProxy(serviceTargets["billing"])
var ProxyPharmacy = createProxy(serviceTargets["pharmacy"])
var ProxyExamination = createProxy(serviceTargets["examination"])
var ProxyInpatient = createProxy(serviceTargets["inpatient"])
var ProxySchedule = createProxy(serviceTargets["schedule"])
var ProxyOutpatient = createProxy(serviceTargets["outpatient"])
var ProxyFollowup = createProxy(serviceTargets["followup"])
var ProxyHealthRecord = createProxy(serviceTargets["health-record"])
var ProxyNotification = createProxy(serviceTargets["notification"])
var ProxyStatistics = createProxy(serviceTargets["statistics"])
var ProxySystem = createProxy(serviceTargets["system"])
var ProxyEMR = createProxy(serviceTargets["emr"])
