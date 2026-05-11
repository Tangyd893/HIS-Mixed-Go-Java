// Package config 配置加载 — Viper + 默认配置
package config

import (
	"github.com/spf13/viper"
)

// ServerConfig 服务配置
type ServerConfig struct {
	HTTPPort int    `mapstructure:"http_port"`
	GRPCPort int    `mapstructure:"grpc_port"`
	Name     string `mapstructure:"name"`
}

// DBConfig 数据库配置
type DBConfig struct {
	Host     string `mapstructure:"host"`
	Port     int    `mapstructure:"port"`
	User     string `mapstructure:"user"`
	Password string `mapstructure:"password"`
	DBName   string `mapstructure:"dbname"`
	SSLMode  string `mapstructure:"sslmode"`
}

// RedisConfig Redis 配置
type RedisConfig struct {
	Addr     string `mapstructure:"addr"`
	Password string `mapstructure:"password"`
	DB       int    `mapstructure:"db"`
}

// RabbitMQConfig 消息队列配置
type RabbitMQConfig struct {
	URL string `mapstructure:"url"`
}

// Config 聚合配置
type Config struct {
	Server   ServerConfig   `mapstructure:"server"`
	Database DBConfig       `mapstructure:"database"`
	Redis    RedisConfig    `mapstructure:"redis"`
	RabbitMQ RabbitMQConfig `mapstructure:"rabbitmq"`
}

// Load 加载配置
func Load(configFile string) (*Config, error) {
	v := viper.New()
	v.SetConfigFile(configFile)
	v.SetConfigType("yaml")

	if err := v.ReadInConfig(); err != nil {
		return nil, err
	}

	v.AutomaticEnv()

	var cfg Config
	if err := v.Unmarshal(&cfg); err != nil {
		return nil, err
	}

	return &cfg, nil
}

// DefaultConfig 返回默认配置
func DefaultConfig() *Config {
	return &Config{
		Server: ServerConfig{
			HTTPPort: 8080,
			Name:     "his-gateway",
		},
		Database: DBConfig{
			Host:    "localhost",
			Port:    5432,
			User:    "his_admin",
			SSLMode: "disable",
		},
		Redis: RedisConfig{
			Addr: "localhost:6379",
			DB:   0,
		},
		RabbitMQ: RabbitMQConfig{
			URL: "amqp://admin:admin@localhost:5672/",
		},
	}
}
