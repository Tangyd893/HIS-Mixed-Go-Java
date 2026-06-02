package service

import (
	"time"

	"github.com/his-mixed/go/internal/notification/model"
	"github.com/his-mixed/go/internal/notification/repository"
)

// NotificationService 消息通知业务服务
type NotificationService struct {
	repo *repository.NotificationRepository
}

// NewNotificationService 创建通知服务
func NewNotificationService(repo *repository.NotificationRepository) *NotificationService {
	return &NotificationService{repo: repo}
}

// SendNotification 发送通知
func (s *NotificationService) SendNotification(templateCode, channel, recipient string, params map[string]string) error {
	// 获取模板
	template, err := s.repo.GetTemplateByCode(templateCode)
	if err != nil {
		return err
	}

	// 创建消息
	msg := &model.NotificationMessage{
		TemplateCode: templateCode,
		Channel:      channel,
		Recipient:    recipient,
		Content:      template.Content, // 简化处理，实际应该替换模板参数
		Status:       "SENT",
		CreatedAt:    time.Now(),
	}

	return s.repo.CreateMessage(msg)
}

// SendSiteMessage 发送站内信
func (s *NotificationService) SendSiteMessage(userID int64, title, content string) error {
	msg := &model.NotificationMessage{
		Channel:   "SITE",
		UserID:    &userID,
		Title:     title,
		Content:   content,
		Status:    "SENT",
		IsRead:    false,
		CreatedAt: time.Now(),
	}

	return s.repo.CreateMessage(msg)
}

// MarkAsRead 标记已读
func (s *NotificationService) MarkAsRead(id int64) error {
	msg, err := s.repo.GetMessageByID(id)
	if err != nil {
		return err
	}
	msg.IsRead = true
	now := time.Now()
	msg.SentAt = &now
	return s.repo.UpdateMessage(msg)
}

// GetUnreadMessages 获取未读消息
func (s *NotificationService) GetUnreadMessages(userID int64) ([]model.NotificationMessage, error) {
	return s.repo.GetUnreadMessages(userID)
}

// ListMessages 分页查询消息记录
func (s *NotificationService) ListMessages(userID int64, channel string, page, size int) ([]model.NotificationMessage, int64, error) {
	return s.repo.ListMessages(userID, channel, page, size)
}

// CreateTemplate 创建通知模板
func (s *NotificationService) CreateTemplate(template *model.NotificationTemplate) error {
	template.CreatedAt = time.Now()
	template.UpdatedAt = time.Now()
	return s.repo.CreateTemplate(template)
}

// GetTemplateByCode 按编码查询模板
func (s *NotificationService) GetTemplateByCode(code string) (*model.NotificationTemplate, error) {
	return s.repo.GetTemplateByCode(code)
}

// ListTemplates 分页查询模板列表
func (s *NotificationService) ListTemplates(channel string, page, size int) ([]model.NotificationTemplate, int64, error) {
	if page <= 0 {
		page = 1
	}
	if size <= 0 {
		size = 20
	}
	return s.repo.ListTemplates(channel, page, size)
}

