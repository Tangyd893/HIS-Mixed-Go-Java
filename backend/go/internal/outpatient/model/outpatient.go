package model

import (
	"time"

	"gorm.io/gorm"
)

// Consultation 在线问诊
type Consultation struct {
	ID           int64          `gorm:"primaryKey" json:"id"`
	PatientID    int64          `gorm:"index;not null" json:"patientId"`
	DoctorID     *int64         `gorm:"index" json:"doctorId"`
	DepartmentID *int64         `gorm:"index" json:"departmentId"`
	Complaint    string         `gorm:"type:text" json:"complaint"`
	Status       string         `gorm:"size:20;index" json:"status"`
	StartedAt    *time.Time     `json:"startedAt"`
	ClosedAt     *time.Time     `json:"closedAt"`
	CreatedAt    time.Time      `json:"createdAt"`
	UpdatedAt    time.Time      `json:"updatedAt"`
	DeletedAt    gorm.DeletedAt `gorm:"index" json:"-"`
}

func (Consultation) TableName() string {
	return "consultations"
}

// ConsultationMessage 问诊消息
type ConsultationMessage struct {
	ID             int64     `gorm:"primaryKey" json:"id"`
	ConsultationID int64     `gorm:"index;not null" json:"consultationId"`
	SenderID       int64     `gorm:"index" json:"senderId"`
	SenderType     string    `gorm:"size:10" json:"senderType"`
	MessageType    string    `gorm:"size:20" json:"messageType"`
	Content        string    `gorm:"type:text" json:"content"`
	AttachmentURL  string    `gorm:"size:500" json:"attachmentUrl"`
	CreatedAt      time.Time `json:"createdAt"`
}

func (ConsultationMessage) TableName() string {
	return "consultation_messages"
}
