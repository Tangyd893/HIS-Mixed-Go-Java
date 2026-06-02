package service

import (
	"testing"
	"time"

	"github.com/his-mixed/go/internal/registration/model"
	"github.com/his-mixed/go/internal/registration/repository"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)
	err = db.AutoMigrate(&model.Registration{}, &model.QueueItem{})
	require.NoError(t, err)
	return db
}

func newTestService(t *testing.T) (*RegistrationService, *gorm.DB) {
	db := setupTestDB(t)
	repo := repository.NewRegistrationRepository(db)
	svc := NewRegistrationService(repo)
	return svc, db
}

func newRegistration(patientID int64, status string) *model.Registration {
	return &model.Registration{
		PatientID:        patientID,
		ScheduleID:       1,
		DepartmentID:     10,
		DoctorID:         100,
		VisitType:        "NORMAL",
		RegistrationType: "APPOINTMENT",
		Status:           status,
		QueueNumber:      1,
		Symptom:          "头痛",
		RegisterDate:     time.Date(2026, 6, 1, 0, 0, 0, 0, time.UTC),
		TimeSlot:         "08:00-09:00",
		Fee:              25.50,
	}
}

func TestCreateRegistration(t *testing.T) {
	svc, _ := newTestService(t)

	reg := newRegistration(1, "PENDING")
	err := svc.CreateRegistration(reg)
	require.NoError(t, err)
	assert.NotZero(t, reg.ID)
}

func TestCancelRegistration(t *testing.T) {
	svc, _ := newTestService(t)

	reg := newRegistration(1, "PENDING")
	require.NoError(t, svc.CreateRegistration(reg))

	err := svc.CancelRegistration(reg.ID)
	require.NoError(t, err)

	got, err := svc.GetRegistrationByID(reg.ID)
	require.NoError(t, err)
	assert.Equal(t, "CANCELLED", got.Status)
}

func TestCompleteRegistration(t *testing.T) {
	svc, _ := newTestService(t)

	reg := newRegistration(1, "PENDING")
	require.NoError(t, svc.CreateRegistration(reg))

	err := svc.CompleteRegistration(reg.ID)
	require.NoError(t, err)

	got, err := svc.GetRegistrationByID(reg.ID)
	require.NoError(t, err)
	assert.Equal(t, "COMPLETED", got.Status)
}

func TestGetRegistrationByID(t *testing.T) {
	svc, _ := newTestService(t)

	reg := newRegistration(42, "PENDING")
	reg.Symptom = "发烧"
	require.NoError(t, svc.CreateRegistration(reg))

	got, err := svc.GetRegistrationByID(reg.ID)
	require.NoError(t, err)
	assert.Equal(t, int64(42), got.PatientID)
	assert.Equal(t, "发烧", got.Symptom)
}

func TestListRegistrations(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 0; i < 5; i++ {
		reg := newRegistration(200, "PENDING")
		require.NoError(t, svc.CreateRegistration(reg))
	}

	regs, total, err := svc.ListRegistrations(200, "", 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, regs, 5)
}

func TestCreateQueueItem(t *testing.T) {
	svc, _ := newTestService(t)

	item := &model.QueueItem{
		RegistrationID: 1,
		DepartmentID:   10,
		DoctorID:       100,
		QueueNumber:    1,
		Status:         "WAITING",
	}
	err := svc.CreateQueueItem(item)
	require.NoError(t, err)
	assert.NotZero(t, item.ID)
}

func TestGetQueueItems(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 1; i <= 3; i++ {
		item := &model.QueueItem{
			RegistrationID: int64(i),
			DepartmentID:   20,
			DoctorID:       200,
			QueueNumber:    i,
			Status:         "WAITING",
		}
		require.NoError(t, svc.CreateQueueItem(item))
	}

	items, err := svc.GetQueueItems(20)
	require.NoError(t, err)
	assert.Len(t, items, 3)
	assert.Equal(t, 1, items[0].QueueNumber)
	assert.Equal(t, 2, items[1].QueueNumber)
	assert.Equal(t, 3, items[2].QueueNumber)
}
