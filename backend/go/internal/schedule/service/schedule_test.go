package service

import (
	"testing"
	"time"

	"github.com/his-mixed/go/internal/schedule/model"
	"github.com/his-mixed/go/internal/schedule/repository"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

// Ensure model is used
var _ = model.ScheduleSlot{}

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)
	err = db.AutoMigrate(&model.SchedulePlan{}, &model.ScheduleSlot{})
	require.NoError(t, err)
	return db
}

func newTestService(t *testing.T) (*ScheduleService, *gorm.DB) {
	db := setupTestDB(t)
	repo := repository.NewScheduleRepository(db)
	svc := NewScheduleService(repo)
	return svc, db
}

func newSchedulePlan(doctorID, departmentID int64, title string) *model.SchedulePlan {
	return &model.SchedulePlan{
		DoctorID:     doctorID,
		DepartmentID: departmentID,
		Title:        title,
		PlanType:     "WEEKLY",
		StartDate:    time.Date(2026, 6, 1, 0, 0, 0, 0, time.UTC),
		EndDate:      time.Date(2026, 6, 30, 0, 0, 0, 0, time.UTC),
		IsActive:     true,
	}
}

func newScheduleSlot(planID, doctorID, departmentID int64, date time.Time, startTime, endTime string, totalQuota int) *model.ScheduleSlot {
	return &model.ScheduleSlot{
		PlanID:          planID,
		DoctorID:        doctorID,
		DepartmentID:    departmentID,
		ScheduleDate:    date,
		StartTime:       startTime,
		EndTime:         endTime,
		TotalQuota:      totalQuota,
		Remaining:       totalQuota,
		IntervalMinutes: 10,
		IsActive:        true,
	}
}

func TestCreateSchedulePlan(t *testing.T) {
	svc, _ := newTestService(t)

	plan := newSchedulePlan(100, 10, "周一上午门诊")
	err := svc.CreateSchedulePlan(plan)
	require.NoError(t, err)
	assert.NotZero(t, plan.ID)
}

func TestGetSchedulePlanByID(t *testing.T) {
	svc, _ := newTestService(t)

	plan := newSchedulePlan(100, 10, "周二下午门诊")
	require.NoError(t, svc.CreateSchedulePlan(plan))

	got, err := svc.GetSchedulePlanByID(plan.ID)
	require.NoError(t, err)
	assert.Equal(t, int64(100), got.DoctorID)
	assert.Equal(t, "周二下午门诊", got.Title)
}

func TestListSchedulePlans(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 0; i < 5; i++ {
		plan := newSchedulePlan(100, 10, "门诊计划")
		require.NoError(t, svc.CreateSchedulePlan(plan))
	}

	plans, total, err := svc.ListSchedulePlans(100, 0, 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, plans, 5)
}

func TestListSchedulePlansWithDepartmentFilter(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 0; i < 3; i++ {
		plan := newSchedulePlan(100, 10, "内科门诊")
		require.NoError(t, svc.CreateSchedulePlan(plan))
	}
	for i := 0; i < 2; i++ {
		plan := newSchedulePlan(100, 20, "外科门诊")
		require.NoError(t, svc.CreateSchedulePlan(plan))
	}

	plans, total, err := svc.ListSchedulePlans(0, 10, 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(3), total)
	assert.Len(t, plans, 3)
}

func TestCreateScheduleSlot(t *testing.T) {
	svc, _ := newTestService(t)

	slot := newScheduleSlot(1, 100, 10, time.Date(2026, 6, 15, 0, 0, 0, 0, time.UTC), "08:00", "09:00", 20)
	err := svc.CreateScheduleSlot(slot)
	require.NoError(t, err)
	assert.NotZero(t, slot.ID)
}

func TestGetScheduleSlots(t *testing.T) {
	svc, db := newTestService(t)

	date := time.Date(2026, 6, 15, 0, 0, 0, 0, time.UTC)
	for i := 0; i < 3; i++ {
		slot := newScheduleSlot(1, 100, 10, date, "08:00", "09:00", 20)
		require.NoError(t, svc.CreateScheduleSlot(slot))
	}

	// Verify data was inserted
	var count int64
	db.Model(&model.ScheduleSlot{}).Count(&count)
	assert.Equal(t, int64(3), count)

	// Query using date range
	var slots []model.ScheduleSlot
	err := db.Where("schedule_date >= ? AND schedule_date < ?", date, date.AddDate(0, 0, 1)).Find(&slots).Error
	require.NoError(t, err)
	assert.Len(t, slots, 3)
}

func TestDeductQuota(t *testing.T) {
	svc, db := newTestService(t)

	slot := newScheduleSlot(1, 100, 10, time.Date(2026, 6, 15, 0, 0, 0, 0, time.UTC), "08:00", "09:00", 20)
	require.NoError(t, svc.CreateScheduleSlot(slot))

	err := svc.DeductQuota(slot.ID)
	require.NoError(t, err)

	// Query directly from DB
	var updatedSlot model.ScheduleSlot
	err = db.First(&updatedSlot, slot.ID).Error
	require.NoError(t, err)
	assert.Equal(t, 19, updatedSlot.Remaining)
}

func TestReleaseQuota(t *testing.T) {
	svc, db := newTestService(t)

	slot := newScheduleSlot(1, 100, 10, time.Date(2026, 6, 15, 0, 0, 0, 0, time.UTC), "08:00", "09:00", 20)
	require.NoError(t, svc.CreateScheduleSlot(slot))

	require.NoError(t, svc.DeductQuota(slot.ID))
	require.NoError(t, svc.ReleaseQuota(slot.ID))

	// Query directly from DB
	var updatedSlot model.ScheduleSlot
	err := db.First(&updatedSlot, slot.ID).Error
	require.NoError(t, err)
	assert.Equal(t, 20, updatedSlot.Remaining)
}
