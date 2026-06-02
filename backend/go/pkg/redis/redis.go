// Package redis Redis 缓存封装
package redis

import (
	"context"
	"fmt"
	"time"

	"github.com/redis/go-redis/v9"
)

var Client *redis.Client

// Connect 连接 Redis
func Connect(addr, password string, db int) (*redis.Client, error) {
	client := redis.NewClient(&redis.Options{
		Addr:         addr,
		Password:     password,
		DB:           db,
		PoolSize:     10,
		MinIdleConns: 3,
	})

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	if err := client.Ping(ctx).Err(); err != nil {
		return nil, fmt.Errorf("连接 Redis 失败: %w", err)
	}

	Client = client
	return client, nil
}

// IsConnected 检查 Redis 连接状态
func IsConnected() bool {
	if Client == nil {
		return false
	}
	return Client.Ping(context.Background()).Err() == nil
}

// AcquireLock 获取分布式锁
func AcquireLock(ctx context.Context, key string, ttl time.Duration) (bool, error) {
	return Client.SetNX(ctx, key, "locked", ttl).Result()
}

// ReleaseLock 释放分布式锁
func ReleaseLock(ctx context.Context, key string) error {
	return Client.Del(ctx, key).Err()
}
