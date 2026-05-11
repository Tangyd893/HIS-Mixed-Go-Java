// Package config 网关配置
package config

// GatewayConfig 网关配置
type GatewayConfig struct {
	Port   int               `yaml:"port"`
	Routes map[string]string `yaml:"routes"`
}

// DefaultConfig 返回默认网关配置
func DefaultConfig() *GatewayConfig {
	return &GatewayConfig{
		Port: 8080,
		Routes: map[string]string{
			"/api/auth":          "http://localhost:8081",
			"/api/user":          "http://localhost:8082",
			"/api/registration":  "http://localhost:8083",
			"/api/clinic":        "http://localhost:8084",
			"/api/prescription":  "http://localhost:8085",
			"/api/billing":       "http://localhost:8086",
			"/api/pharmacy":      "http://localhost:8087",
			"/api/examination":   "http://localhost:8088",
			"/api/inpatient":     "http://localhost:8089",
			"/api/schedule":      "http://localhost:8090",
			"/api/outpatient":    "http://localhost:8091",
			"/api/followup":      "http://localhost:8092",
			"/api/health-record": "http://localhost:8093",
			"/api/notification":  "http://localhost:8094",
			"/api/statistics":    "http://localhost:8095",
			"/api/system":        "http://localhost:8096",
			"/api/emr":           "http://localhost:8097",
		},
	}
}
