// Package logger Zap 日志封装
package logger

import (
	"go.uber.org/zap"
	"go.uber.org/zap/zapcore"
)

var (
	// Logger 全局日志器
	Logger *zap.Logger
	// SugaredLogger 语法糖日志器
	Sugar *zap.SugaredLogger
)

func init() {
	cfg := zap.NewProductionConfig()
	cfg.EncoderConfig.TimeKey = "timestamp"
	cfg.EncoderConfig.EncodeTime = zapcore.ISO8601TimeEncoder
	cfg.Level = zap.NewAtomicLevelAt(zap.InfoLevel)

	logger, err := cfg.Build()
	if err != nil {
		panic("初始化日志失败: " + err.Error())
	}

	Logger = logger
	Sugar = logger.Sugar()
}

// SetLevel 设置日志级别
func SetLevel(level string) {
	var l zapcore.Level
	switch level {
	case "debug":
		l = zapcore.DebugLevel
	case "info":
		l = zapcore.InfoLevel
	case "warn":
		l = zapcore.WarnLevel
	case "error":
		l = zapcore.ErrorLevel
	default:
		l = zapcore.InfoLevel
	}
	Logger.Core().Enabled(l)
}

// Sync 刷新日志缓冲
func Sync() {
	_ = Logger.Sync()
}
