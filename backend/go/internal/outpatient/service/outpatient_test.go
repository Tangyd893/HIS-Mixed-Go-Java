package service

import (
	"testing"

	"github.com/his-mixed/go/internal/outpatient/model"
	"github.com/his-mixed/go/internal/outpatient/repository"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)
	err = db.AutoMigrate(&model.Consultation{}, &model.ConsultationMessage{})
	require.NoError(t, err)
	return db
}

func newTestService(t *testing.T) (*OutpatientService, *gorm.DB) {
	db := setupTestDB(t)
	repo := repository.NewOutpatientRepository(db)
	svc := NewOutpatientService(repo)
	return svc, db
}

func newConsultation(patientID int64, complaint string) *model.Consultation {
	return &model.Consultation{
		PatientID: patientID,
		Complaint: complaint,
	}
}

func newConsultationMessage(consultationID, senderID int64, senderType, content string) *model.ConsultationMessage {
	return &model.ConsultationMessage{
		ConsultationID: consultationID,
		SenderID:       senderID,
		SenderType:     senderType,
		MessageType:    "TEXT",
		Content:        content,
	}
}

func TestCreateConsultation(t *testing.T) {
	svc, _ := newTestService(t)

	consultation := newConsultation(1, "头痛发烧两天")
	err := svc.CreateConsultation(consultation)
	require.NoError(t, err)
	assert.NotZero(t, consultation.ID)
	assert.Equal(t, "PENDING", consultation.Status)
}

func TestGetConsultationByID(t *testing.T) {
	svc, _ := newTestService(t)

	consultation := newConsultation(42, "咳嗽流涕")
	require.NoError(t, svc.CreateConsultation(consultation))

	got, err := svc.GetConsultationByID(consultation.ID)
	require.NoError(t, err)
	assert.Equal(t, int64(42), got.PatientID)
	assert.Equal(t, "咳嗽流涕", got.Complaint)
}

func TestListConsultations(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 0; i < 5; i++ {
		consultation := newConsultation(200, "症状描述")
		require.NoError(t, svc.CreateConsultation(consultation))
	}

	consultations, total, err := svc.ListConsultations(200, 0, "", 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, consultations, 5)
}

func TestAcceptConsultation(t *testing.T) {
	svc, _ := newTestService(t)

	consultation := newConsultation(1, "腹痛")
	require.NoError(t, svc.CreateConsultation(consultation))

	doctorID := int64(100)
	err := svc.AcceptConsultation(consultation.ID, doctorID)
	require.NoError(t, err)

	got, err := svc.GetConsultationByID(consultation.ID)
	require.NoError(t, err)
	assert.Equal(t, "IN_PROGRESS", got.Status)
	assert.Equal(t, &doctorID, got.DoctorID)
	assert.NotNil(t, got.StartedAt)
}

func TestCloseConsultation(t *testing.T) {
	svc, _ := newTestService(t)

	consultation := newConsultation(1, "皮疹")
	require.NoError(t, svc.CreateConsultation(consultation))

	doctorID := int64(100)
	require.NoError(t, svc.AcceptConsultation(consultation.ID, doctorID))
	require.NoError(t, svc.CloseConsultation(consultation.ID))

	got, err := svc.GetConsultationByID(consultation.ID)
	require.NoError(t, err)
	assert.Equal(t, "CLOSED", got.Status)
	assert.NotNil(t, got.ClosedAt)
}

func TestSendMessage(t *testing.T) {
	svc, _ := newTestService(t)

	consultation := newConsultation(1, "头痛")
	require.NoError(t, svc.CreateConsultation(consultation))

	msg := newConsultationMessage(consultation.ID, 1, "PATIENT", "头痛两天了")
	err := svc.SendMessage(msg)
	require.NoError(t, err)
	assert.NotZero(t, msg.ID)
}

func TestGetMessages(t *testing.T) {
	svc, _ := newTestService(t)

	consultation := newConsultation(1, "发烧")
	require.NoError(t, svc.CreateConsultation(consultation))

	for i := 0; i < 3; i++ {
		msg := newConsultationMessage(consultation.ID, 1, "PATIENT", "消息内容")
		require.NoError(t, svc.SendMessage(msg))
	}

	msgs, err := svc.GetMessages(consultation.ID)
	require.NoError(t, err)
	assert.Len(t, msgs, 3)
}
