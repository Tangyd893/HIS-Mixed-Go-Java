package model

import (
	"time"
)

// NotificationTemplate 通知模板
type NotificationTemplate struct {
	ID           int64     `gorm:"primaryKey" json:"id"`
	TemplateCode string    `gorm:"size:50;uniqueIndex;not null" json:"templateCode"`
	TemplateName string    `gorm:"size:100;not null" json:"templateName"`
	Channel      string    `gorm:"size:10" json:"channel"`
	Title        string    `gorm:"size:200" json:"title"`
	Content      string    `gorm:"type:text;not null" json:"content"`
	IsActive     bool      `gorm:"default:true" json:"isActive"`
	CreatedAt    time.Time `json:"createdAt"`
	UpdatedAt    time.Time `json:"updatedAt"`
}

func (NotificationTemplate) TableName() string {
	return "notification_templates"
}

// NotificationMessage 通知消息记录
type NotificationMessage struct {
	ID           int64      `gorm:"primaryKey" json:"id"`
	TemplateCode string     `gorm:"size:50" json:"templateCode"`
	Channel      string     `gorm:"size:10;not null" json:"channel"`
	Recipient    string     `gorm:"size:200;not null" json:"recipient"`
	UserID       *int64     `gorm:"index" json:"userId"`
	Title        string     `gorm:"size:200" json:"title"`
	Content      string     `gorm:"type:text;not null" json:"content"`
	Params       string     `gorm:"type:jsonb" json:"params"`
	Status       string     `gorm:"size:20;index" json:"status"`
	IsRead       bool       `gorm:"default:false" json:"isRead"`
	SentAt       *time.Time `json:"sentAt"`
	CreatedAt    time.Time  `json:"createdAt"`
}

func (NotificationMessage) TableName() string {
	return "notification_messages"
}
