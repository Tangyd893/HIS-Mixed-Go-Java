package service

import (
	"testing"

	"github.com/his-mixed/go/internal/notification/model"
	"github.com/his-mixed/go/internal/notification/repository"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)
	err = db.AutoMigrate(&model.NotificationTemplate{}, &model.NotificationMessage{})
	require.NoError(t, err)
	return db
}

func newTestService(t *testing.T) (*NotificationService, *gorm.DB) {
	db := setupTestDB(t)
	repo := repository.NewNotificationRepository(db)
	svc := NewNotificationService(repo)
	return svc, db
}

func newTemplate(code, name, channel string) *model.NotificationTemplate {
	return &model.NotificationTemplate{
		TemplateCode: code,
		TemplateName: name,
		Channel:      channel,
		Title:        "测试模板",
		Content:      "测试内容",
		IsActive:     true,
	}
}

func TestCreateTemplate(t *testing.T) {
	svc, _ := newTestService(t)

	tmpl := newTemplate("REG_SUCCESS", "挂号成功通知", "SITE")
	err := svc.CreateTemplate(tmpl)
	require.NoError(t, err)
	assert.NotZero(t, tmpl.ID)
}

func TestGetTemplateByCode(t *testing.T) {
	svc, _ := newTestService(t)

	tmpl := newTemplate("APPOINTMENT", "预约提醒", "SMS")
	require.NoError(t, svc.CreateTemplate(tmpl))

	got, err := svc.GetTemplateByCode("APPOINTMENT")
	require.NoError(t, err)
	assert.Equal(t, "预约提醒", got.TemplateName)
	assert.Equal(t, "SMS", got.Channel)
}

func TestListTemplates(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 0; i < 5; i++ {
		tmpl := newTemplate("TMPL_"+string(rune('A'+i)), "模板", "SITE")
		require.NoError(t, svc.CreateTemplate(tmpl))
	}

	templates, total, err := svc.ListTemplates("", 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, templates, 5)
}

func TestSendSiteMessage(t *testing.T) {
	svc, _ := newTestService(t)

	err := svc.SendSiteMessage(1, "系统通知", "您有一条新消息")
	require.NoError(t, err)
}

func TestGetUnreadMessages(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 0; i < 3; i++ {
		require.NoError(t, svc.SendSiteMessage(100, "通知", "内容"))
	}

	msgs, err := svc.GetUnreadMessages(100)
	require.NoError(t, err)
	assert.Len(t, msgs, 3)
}

func TestMarkAsRead(t *testing.T) {
	svc, _ := newTestService(t)

	require.NoError(t, svc.SendSiteMessage(1, "测试", "内容"))

	msgs, err := svc.GetUnreadMessages(1)
	require.NoError(t, err)
	require.Len(t, msgs, 1)

	err = svc.MarkAsRead(msgs[0].ID)
	require.NoError(t, err)

	msgs, err = svc.GetUnreadMessages(1)
	require.NoError(t, err)
	assert.Len(t, msgs, 0)
}

func TestListMessages(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 0; i < 5; i++ {
		require.NoError(t, svc.SendSiteMessage(200, "消息", "内容"))
	}

	msgs, total, err := svc.ListMessages(200, "", 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, msgs, 5)
}
