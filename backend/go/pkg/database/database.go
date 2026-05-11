// Package database PostgreSQL 连接管理 + GORM 配置
package database

import (
	"fmt"
	"log"
	"time"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	gormlogger "gorm.io/gorm/logger"
)

// DB 全局数据库连接
var DB *gorm.DB

// Connect 连接数据库
func Connect(host string, port int, user, password, dbname, sslmode string) (*gorm.DB, error) {
	dsn := fmt.Sprintf(
		"host=%s port=%d user=%s password=%s dbname=%s sslmode=%s TimeZone=Asia/Shanghai",
		host, port, user, password, dbname, sslmode,
	)

	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{
		Logger: gormlogger.Default.LogMode(gormlogger.Warn),
	})
	if err != nil {
		return nil, fmt.Errorf("连接数据库失败: %w", err)
	}

	sqlDB, err := db.DB()
	if err != nil {
		return nil, err
	}

	// 连接池配置
	sqlDB.SetMaxOpenConns(25)
	sqlDB.SetMaxIdleConns(10)
	sqlDB.SetConnMaxLifetime(5 * time.Minute)

	// 验证连接
	if err := sqlDB.Ping(); err != nil {
		return nil, fmt.Errorf("数据库 Ping 失败: %w", err)
	}

	DB = db
	log.Printf("数据库连接成功: %s:%d/%s", host, port, dbname)
	return db, nil
}

// Close 关闭数据库连接
func Close() {
	if DB != nil {
		sqlDB, _ := DB.DB()
		if sqlDB != nil {
			sqlDB.Close()
		}
	}
}

// IsConnected 检查数据库连接状态
func IsConnected() bool {
	if DB == nil {
		return false
	}
	sqlDB, err := DB.DB()
	if err != nil {
		return false
	}
	return sqlDB.Ping() == nil
}
