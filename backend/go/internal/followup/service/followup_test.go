package service

import (
	"testing"
	"time"

	"github.com/his-mixed/go/internal/followup/model"
	"github.com/his-mixed/go/internal/followup/repository"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)
	err = db.AutoMigrate(&model.FollowupPlan{}, &model.FollowupRecord{})
	require.NoError(t, err)
	return db
}

func newTestService(t *testing.T) (*FollowupService, *gorm.DB) {
	db := setupTestDB(t)
	repo := repository.NewFollowupRepository(db)
	svc := NewFollowupService(repo)
	return svc, db
}

func newFollowupPlan(patientID int64, diagnosis, followupType string) *model.FollowupPlan {
	return &model.FollowupPlan{
		PatientID:    patientID,
		Diagnosis:    diagnosis,
		FollowupType: followupType,
		StartDate:    time.Date(2026, 6, 1, 0, 0, 0, 0, time.UTC),
	}
}

func newFollowupRecord(planID, patientID int64, content string) *model.FollowupRecord {
	return &model.FollowupRecord{
		PlanID:           planID,
		PatientID:        patientID,
		FollowupDate:     time.Date(2026, 6, 15, 0, 0, 0, 0, time.UTC),
		FollowupMethod:   "PHONE",
		Content:          content,
		PatientCondition: "恢复良好",
		Advice:           "继续服药",
	}
}

func TestCreateFollowupPlan(t *testing.T) {
	svc, _ := newTestService(t)

	plan := newFollowupPlan(1, "高血压", "CHRONIC")
	err := svc.CreateFollowupPlan(plan)
	require.NoError(t, err)
	assert.NotZero(t, plan.ID)
	assert.Equal(t, "PENDING", plan.Status)
}

func TestGetFollowupPlanByID(t *testing.T) {
	svc, _ := newTestService(t)

	plan := newFollowupPlan(42, "糖尿病", "POST_SURGERY")
	require.NoError(t, svc.CreateFollowupPlan(plan))

	got, err := svc.GetFollowupPlanByID(plan.ID)
	require.NoError(t, err)
	assert.Equal(t, int64(42), got.PatientID)
	assert.Equal(t, "糖尿病", got.Diagnosis)
}

func TestListFollowupPlans(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 0; i < 5; i++ {
		plan := newFollowupPlan(200, "高血压", "CHRONIC")
		require.NoError(t, svc.CreateFollowupPlan(plan))
	}

	plans, total, err := svc.ListFollowupPlans(200, "", 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, plans, 5)
}

func TestUpdateFollowupPlanStatus(t *testing.T) {
	svc, _ := newTestService(t)

	plan := newFollowupPlan(1, "冠心病", "CHRONIC")
	require.NoError(t, svc.CreateFollowupPlan(plan))

	err := svc.UpdateFollowupPlanStatus(plan.ID, "IN_PROGRESS")
	require.NoError(t, err)

	got, err := svc.GetFollowupPlanByID(plan.ID)
	require.NoError(t, err)
	assert.Equal(t, "IN_PROGRESS", got.Status)
}

func TestCreateFollowupRecord(t *testing.T) {
	svc, _ := newTestService(t)

	plan := newFollowupPlan(1, "高血压", "CHRONIC")
	require.NoError(t, svc.CreateFollowupPlan(plan))

	record := newFollowupRecord(plan.ID, 1, "电话随访第一次")
	err := svc.CreateFollowupRecord(record)
	require.NoError(t, err)
	assert.NotZero(t, record.ID)
}

func TestGetFollowupRecords(t *testing.T) {
	svc, _ := newTestService(t)

	plan := newFollowupPlan(1, "糖尿病", "CHRONIC")
	require.NoError(t, svc.CreateFollowupPlan(plan))

	for i := 0; i < 3; i++ {
		record := newFollowupRecord(plan.ID, 1, "随访记录")
		require.NoError(t, svc.CreateFollowupRecord(record))
	}

	records, err := svc.GetFollowupRecords(plan.ID, 1)
	require.NoError(t, err)
	assert.Len(t, records, 3)
}
