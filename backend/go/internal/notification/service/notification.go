package service

import "github.com/his-mixed/go/internal/notification/model"

// NotificationService 消息通知业务服务
type NotificationService struct {
}

// NewNotificationService 创建通知服务
func NewNotificationService() *NotificationService {
	return &NotificationService{}
}

// SendNotification 发送通知
func (s *NotificationService) SendNotification(templateCode, channel, recipient string, params map[string]string) error {
	return nil
}

// SendSiteMessage 发送站内信
func (s *NotificationService) SendSiteMessage(userID int64, title, content string) error {
	return nil
}

// MarkAsRead 标记已读
func (s *NotificationService) MarkAsRead(id int64) error {
	return nil
}

// GetUnreadMessages 获取未读消息
func (s *NotificationService) GetUnreadMessages(userID int64) ([]model.NotificationMessage, error) {
	return nil, nil
}

// ListMessages 分页查询消息记录
func (s *NotificationService) ListMessages(userID int64, channel string, page, size int) ([]model.NotificationMessage, int64, error) {
	return nil, 0, nil
}

// CreateTemplate 创建通知模板
func (s *NotificationService) CreateTemplate(template *model.NotificationTemplate) error {
	return nil
}

// GetTemplateByCode 按编码查询模板
func (s *NotificationService) GetTemplateByCode(code string) (*model.NotificationTemplate, error) {
	return nil, nil
}
