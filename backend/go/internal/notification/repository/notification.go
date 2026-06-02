package repository

import (
	"github.com/his-mixed/go/internal/notification/model"
	"gorm.io/gorm"
)

// NotificationRepository 通知数据访问层
type NotificationRepository struct {
	db *gorm.DB
}

// NewNotificationRepository 创建通知仓储
func NewNotificationRepository(db *gorm.DB) *NotificationRepository {
	return &NotificationRepository{db: db}
}

// CreateMessage 创建通知消息
func (r *NotificationRepository) CreateMessage(msg *model.NotificationMessage) error {
	return r.db.Create(msg).Error
}

// GetMessageByID 按ID查询消息
func (r *NotificationRepository) GetMessageByID(id int64) (*model.NotificationMessage, error) {
	var msg model.NotificationMessage
	err := r.db.First(&msg, id).Error
	if err != nil {
		return nil, err
	}
	return &msg, nil
}

// UpdateMessage 更新消息
func (r *NotificationRepository) UpdateMessage(msg *model.NotificationMessage) error {
	return r.db.Save(msg).Error
}

// GetUnreadMessages 获取未读消息
func (r *NotificationRepository) GetUnreadMessages(userID int64) ([]model.NotificationMessage, error) {
	var msgs []model.NotificationMessage
	err := r.db.Where("user_id = ? AND is_read = ?", userID, false).Order("created_at DESC").Find(&msgs).Error
	return msgs, err
}

// ListMessages 分页查询消息记录
func (r *NotificationRepository) ListMessages(userID int64, channel string, page, size int) ([]model.NotificationMessage, int64, error) {
	var msgs []model.NotificationMessage
	var total int64

	base := r.db.Where("user_id = ?", userID)
	if channel != "" {
		base = base.Where("channel = ?", channel)
	}
	base.Model(&model.NotificationMessage{}).Count(&total)

	offset := (page - 1) * size
	err := base.Offset(offset).Limit(size).Order("created_at DESC").Find(&msgs).Error
	return msgs, total, err
}

// CreateTemplate 创建通知模板
func (r *NotificationRepository) CreateTemplate(template *model.NotificationTemplate) error {
	return r.db.Create(template).Error
}

// GetTemplateByCode 按编码查询模板
func (r *NotificationRepository) GetTemplateByCode(code string) (*model.NotificationTemplate, error) {
	var template model.NotificationTemplate
	err := r.db.Where("code = ?", code).First(&template).Error
	if err != nil {
		return nil, err
	}
	return &template, nil
}
