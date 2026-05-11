package model

import (
	"time"

	"gorm.io/gorm"
)

// Registration 挂号记录
type Registration struct {
	ID               int64          `gorm:"primaryKey" json:"id"`
	PatientID        int64          `gorm:"index;not null" json:"patientId"`
	ScheduleID       int64          `gorm:"index" json:"scheduleId"`
	DepartmentID     int64          `gorm:"index" json:"departmentId"`
	DoctorID         int64          `gorm:"index" json:"doctorId"`
	VisitType        string         `gorm:"size:20" json:"visitType"`
	RegistrationType string         `gorm:"size:20" json:"registrationType"`
	Status           string         `gorm:"size:20;index;not null" json:"status"`
	QueueNumber      int            `json:"queueNumber"`
	Symptom          string         `gorm:"type:text" json:"symptom"`
	RegisterDate     time.Time      `gorm:"type:date;index;not null" json:"registerDate"`
	TimeSlot         string         `gorm:"size:30" json:"timeSlot"`
	Fee              float64        `gorm:"type:decimal(10,2)" json:"fee"`
	CreatedAt        time.Time      `json:"createdAt"`
	UpdatedAt        time.Time      `json:"updatedAt"`
	DeletedAt        gorm.DeletedAt `gorm:"index" json:"-"`
}

func (Registration) TableName() string {
	return "registrations"
}

// QueueItem 排队叫号记录
type QueueItem struct {
	ID             int64      `gorm:"primaryKey" json:"id"`
	RegistrationID int64      `gorm:"index;not null" json:"registrationId"`
	DepartmentID   int64      `gorm:"index" json:"departmentId"`
	DoctorID       int64      `gorm:"index" json:"doctorId"`
	RoomID         int64      `json:"roomId"`
	QueueNumber    int        `gorm:"not null" json:"queueNumber"`
	Status         string     `gorm:"size:20" json:"status"`
	CalledAt       *time.Time `json:"calledAt"`
	CreatedAt      time.Time  `json:"createdAt"`
}

func (QueueItem) TableName() string {
	return "queue_items"
}
