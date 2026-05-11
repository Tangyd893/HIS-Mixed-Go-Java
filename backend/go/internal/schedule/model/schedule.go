package model

import (
	"time"

	"gorm.io/gorm"
)

// SchedulePlan 排班计划
type SchedulePlan struct {
	ID           int64          `gorm:"primaryKey" json:"id"`
	DoctorID     int64          `gorm:"index;not null" json:"doctorId"`
	DepartmentID int64          `gorm:"index" json:"departmentId"`
	Title        string         `gorm:"size:200" json:"title"`
	PlanType     string         `gorm:"size:30" json:"planType"`
	StartDate    time.Time      `gorm:"type:date;not null" json:"startDate"`
	EndDate      time.Time      `gorm:"type:date;not null" json:"endDate"`
	IsActive     bool           `gorm:"default:true" json:"isActive"`
	CreatedAt    time.Time      `json:"createdAt"`
	UpdatedAt    time.Time      `json:"updatedAt"`
	DeletedAt    gorm.DeletedAt `gorm:"index" json:"-"`
}

func (SchedulePlan) TableName() string {
	return "schedule_plans"
}

// ScheduleSlot 号源时段
type ScheduleSlot struct {
	ID              int64     `gorm:"primaryKey" json:"id"`
	PlanID          int64     `gorm:"index;not null" json:"planId"`
	DoctorID        int64     `gorm:"index;not null" json:"doctorId"`
	DepartmentID    int64     `gorm:"index" json:"departmentId"`
	RoomID          *int64    `json:"roomId"`
	ScheduleDate    time.Time `gorm:"type:date;index;not null" json:"scheduleDate"`
	DayOfWeek       *int      `json:"dayOfWeek"`
	StartTime       string    `gorm:"size:5;not null" json:"startTime"`
	EndTime         string    `gorm:"size:5;not null" json:"endTime"`
	TotalQuota      int       `gorm:"not null" json:"totalQuota"`
	Remaining       int       `gorm:"not null" json:"remaining"`
	IntervalMinutes int       `gorm:"default:10" json:"intervalMinutes"`
	IsActive        bool      `gorm:"default:true" json:"isActive"`
	Version         int       `gorm:"default:0" json:"version"`
	CreatedAt       time.Time `json:"createdAt"`
	UpdatedAt       time.Time `json:"updatedAt"`
}

func (ScheduleSlot) TableName() string {
	return "schedule_slots"
}
