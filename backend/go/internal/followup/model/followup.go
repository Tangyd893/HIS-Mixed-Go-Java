package model

import (
	"time"

	"gorm.io/gorm"
)

// FollowupPlan 随访计划
type FollowupPlan struct {
	ID             int64          `gorm:"primaryKey" json:"id"`
	PatientID      int64          `gorm:"index;not null" json:"patientId"`
	DoctorID       *int64         `gorm:"index" json:"doctorId"`
	Diagnosis      string         `gorm:"size:500" json:"diagnosis"`
	FollowupType   string         `gorm:"size:30" json:"followupType"`
	StartDate      time.Time      `gorm:"type:date;not null" json:"startDate"`
	EndDate        *time.Time     `gorm:"type:date" json:"endDate"`
	IntervalDays   *int           `json:"intervalDays"`
	TotalTimes     *int           `json:"totalTimes"`
	CompletedTimes int            `gorm:"default:0" json:"completedTimes"`
	Status         string         `gorm:"size:20;index" json:"status"`
	CreatedAt      time.Time      `json:"createdAt"`
	UpdatedAt      time.Time      `json:"updatedAt"`
	DeletedAt      gorm.DeletedAt `gorm:"index" json:"-"`
}

func (FollowupPlan) TableName() string {
	return "followup_plans"
}

// FollowupRecord 随访记录
type FollowupRecord struct {
	ID               int64      `gorm:"primaryKey" json:"id"`
	PlanID           int64      `gorm:"index;not null" json:"planId"`
	PatientID        int64      `gorm:"index;not null" json:"patientId"`
	ExecutorID       *int64     `json:"executorId"`
	FollowupDate     time.Time  `gorm:"type:date;index;not null" json:"followupDate"`
	FollowupMethod   string     `gorm:"size:20" json:"followupMethod"`
	Content          string     `gorm:"type:text" json:"content"`
	PatientCondition string     `gorm:"type:text" json:"patientCondition"`
	Advice           string     `gorm:"type:text" json:"advice"`
	NextFollowupDate *time.Time `gorm:"type:date" json:"nextFollowupDate"`
	CreatedAt        time.Time  `json:"createdAt"`
}

func (FollowupRecord) TableName() string {
	return "followup_records"
}
