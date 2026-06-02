// Package mq RabbitMQ 消息封装
package mq

import (
	"context"
	"encoding/json"
	"fmt"
	"log"

	amqp "github.com/rabbitmq/amqp091-go"
)

var Conn *amqp.Connection

// Connect 连接 RabbitMQ
func Connect(url string) (*amqp.Connection, error) {
	conn, err := amqp.Dial(url)
	if err != nil {
		return nil, fmt.Errorf("连接 RabbitMQ 失败: %w", err)
	}
	Conn = conn
	log.Println("RabbitMQ 连接成功")
	return conn, nil
}

// IsConnected 检查连接状态
func IsConnected() bool {
	return Conn != nil && !Conn.IsClosed()
}

// Publisher 消息发布器
type Publisher struct {
	channel  *amqp.Channel
	exchange string
}

// NewPublisher 创建发布器
func NewPublisher(exchange string) (*Publisher, error) {
	ch, err := Conn.Channel()
	if err != nil {
		return nil, err
	}

	err = ch.ExchangeDeclare(
		exchange, "topic", true, false, false, false, nil,
	)
	if err != nil {
		return nil, err
	}

	return &Publisher{channel: ch, exchange: exchange}, nil
}

// Publish 发布消息
func (p *Publisher) Publish(ctx context.Context, routingKey string, msg interface{}) error {
	data, err := json.Marshal(msg)
	if err != nil {
		return err
	}

	return p.channel.PublishWithContext(ctx,
		p.exchange, routingKey,
		false, false,
		amqp.Publishing{
			ContentType:  "application/json",
			DeliveryMode: amqp.Persistent,
			Body:         data,
		},
	)
}

// Close 关闭
func Close() {
	if Conn != nil {
		Conn.Close()
	}
}
