package config

import (
	"github.com/spf13/viper"
)

type GatewayConfig struct {
	Server struct {
		Name     string `mapstructure:"name"`
		HTTPPort int    `mapstructure:"http_port"`
	} `mapstructure:"server"`
	Routes map[string]string `mapstructure:"routes"`
}

func Load(configFile string) (*GatewayConfig, error) {
	v := viper.New()
	v.SetConfigFile(configFile)
	v.SetConfigType("yaml")

	v.SetDefault("server.http_port", 8080)
	v.SetDefault("routes", map[string]string{
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
	})

	if err := v.ReadInConfig(); err != nil {
		return nil, err
	}

	v.AutomaticEnv()

	var cfg GatewayConfig
	if err := v.Unmarshal(&cfg); err != nil {
		return nil, err
	}

	return &cfg, nil
}
