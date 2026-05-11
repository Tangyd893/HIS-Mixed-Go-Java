package model

import (
	"time"

	"gorm.io/gorm"
)

// ExamRequest 检查申请
type ExamRequest struct {
	ID           int64          `gorm:"primaryKey" json:"id"`
	EncounterID  *int64         `json:"encounterId"`
	PatientID    int64          `gorm:"index;not null" json:"patientId"`
	DoctorID     int64          `gorm:"index;not null" json:"doctorId"`
	ExamType     string         `gorm:"size:30" json:"examType"`
	ExamItemID   *int64         `json:"examItemId"`
	Urgency      string         `gorm:"size:20" json:"urgency"`
	ClinicalInfo string         `gorm:"type:text" json:"clinicalInfo"`
	Status       string         `gorm:"size:20;index" json:"status"`
	CreatedAt    time.Time      `json:"createdAt"`
	UpdatedAt    time.Time      `json:"updatedAt"`
	DeletedAt    gorm.DeletedAt `gorm:"index" json:"-"`
}

func (ExamRequest) TableName() string {
	return "exam_requests"
}

// ExamReport 检查报告
type ExamReport struct {
	ID             int64      `gorm:"primaryKey" json:"id"`
	RequestID      int64      `gorm:"index;not null" json:"requestId"`
	ReportNo       string     `gorm:"size:50;uniqueIndex" json:"reportNo"`
	Findings       string     `gorm:"type:text" json:"findings"`
	Impression     string     `gorm:"type:text" json:"impression"`
	Conclusion     string     `gorm:"type:text" json:"conclusion"`
	ReferenceRange string     `gorm:"type:text" json:"referenceRange"`
	IsAbnormal     bool       `gorm:"default:false" json:"isAbnormal"`
	TechnicianID   *int64     `json:"technicianId"`
	ReviewerID     *int64     `json:"reviewerId"`
	Status         string     `gorm:"size:20" json:"status"`
	ExecutedAt     *time.Time `json:"executedAt"`
	ReviewedAt     *time.Time `json:"reviewedAt"`
	CreatedAt      time.Time  `json:"createdAt"`
	UpdatedAt      time.Time  `json:"updatedAt"`
}

func (ExamReport) TableName() string {
	return "exam_reports"
}
