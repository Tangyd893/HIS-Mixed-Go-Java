package service

import (
	"testing"
	"time"

	"github.com/his-mixed/go/internal/statistics/model"
	"github.com/his-mixed/go/internal/statistics/repository"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)

	err = db.AutoMigrate(&model.StatSnapshot{})
	require.NoError(t, err)

	db.Exec(`CREATE TABLE IF NOT EXISTS registrations (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		patient_id INTEGER,
		status TEXT,
		department_id INTEGER,
		created_at DATETIME DEFAULT CURRENT_TIMESTAMP
	)`)
	db.Exec(`CREATE TABLE IF NOT EXISTS encounters (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		patient_id INTEGER,
		department_id INTEGER,
		created_at DATETIME DEFAULT CURRENT_TIMESTAMP
	)`)
	db.Exec(`CREATE TABLE IF NOT EXISTS prescriptions (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		total_amount REAL DEFAULT 0,
		created_at DATETIME DEFAULT CURRENT_TIMESTAMP
	)`)
	db.Exec(`CREATE TABLE IF NOT EXISTS payments (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		amount REAL DEFAULT 0,
		created_at DATETIME DEFAULT CURRENT_TIMESTAMP
	)`)
	db.Exec(`CREATE TABLE IF NOT EXISTS drugs (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		name TEXT
	)`)

	return db
}

func TestCreateAndGetSnapshot(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewStatisticsRepository(db)
	svc := NewStatisticsService(repo)

	date := time.Date(2026, 6, 1, 0, 0, 0, 0, time.UTC)
	snapshot := &model.StatSnapshot{
		StatType:  "REGISTRATION",
		StatDate:  date,
		StatData:  `{"total": 10}`,
		CreatedAt: time.Now(),
	}

	err := svc.repo.CreateSnapshot(snapshot)
	require.NoError(t, err)
	require.NotZero(t, snapshot.ID)

	result, err := svc.GetSnapshot("REGISTRATION", "2026-06-01")
	require.NoError(t, err)
	require.Equal(t, "REGISTRATION", result.StatType)
	require.Equal(t, `{"total": 10}`, result.StatData)
}

func TestGetRegistrationStats(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewStatisticsRepository(db)
	svc := NewStatisticsService(repo)

	today := time.Now().Format("2006-01-02")
	db.Exec("INSERT INTO registrations (patient_id, status, department_id, created_at) VALUES (?, ?, ?, ?)",
		1, "COMPLETED", 1, today+" 09:00:00")
	db.Exec("INSERT INTO registrations (patient_id, status, department_id, created_at) VALUES (?, ?, ?, ?)",
		2, "COMPLETED", 2, today+" 10:00:00")
	db.Exec("INSERT INTO registrations (patient_id, status, department_id, created_at) VALUES (?, ?, ?, ?)",
		3, "CANCELLED", 1, today+" 11:00:00")
	db.Exec("INSERT INTO registrations (patient_id, status, department_id, created_at) VALUES (?, ?, ?, ?)",
		4, "PENDING", 3, "2025-01-01 08:00:00")

	stats, err := svc.GetRegistrationStats(today, today)
	require.NoError(t, err)
	require.Equal(t, int64(3), stats["total"])
	require.Equal(t, int64(2), stats["completed"])
	require.Equal(t, int64(1), stats["cancelled"])
}

func TestGetDashboardStats(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewStatisticsRepository(db)
	svc := NewStatisticsService(repo)

	today := time.Now().Format("2006-01-02")
	db.Exec("INSERT INTO registrations (patient_id, status, department_id, created_at) VALUES (?, ?, ?, ?)",
		1, "COMPLETED", 1, today+" 09:00:00")
	db.Exec("INSERT INTO registrations (patient_id, status, department_id, created_at) VALUES (?, ?, ?, ?)",
		2, "COMPLETED", 2, today+" 10:00:00")
	db.Exec("INSERT INTO encounters (patient_id, department_id, created_at) VALUES (?, ?, ?)",
		1, 1, today+" 09:30:00")
	db.Exec("INSERT INTO prescriptions (total_amount, created_at) VALUES (?, ?)",
		150.50, today+" 10:00:00")
	db.Exec("INSERT INTO payments (amount, created_at) VALUES (?, ?)",
		200.0, today+" 10:30:00")
	db.Exec("INSERT INTO payments (amount, created_at) VALUES (?, ?)",
		300.0, today+" 11:00:00")

	stats, err := svc.GetDashboardStats("今天", 0)
	require.NoError(t, err)
	require.Equal(t, int64(2), stats["total_registrations"])
	require.Equal(t, int64(1), stats["total_outpatients"])
	require.Equal(t, int64(1), stats["total_prescriptions"])
	require.InDelta(t, 500.0, stats["total_revenue"], 0.01)
}

func TestGetTrendData(t *testing.T) {
	db := setupTestDB(t)
	repo := repository.NewStatisticsRepository(db)
	svc := NewStatisticsService(repo)

	db.Exec("INSERT INTO encounters (patient_id, department_id, created_at) VALUES (?, ?, ?)", 1, 1, "2026-06-01 09:00:00")
	db.Exec("INSERT INTO encounters (patient_id, department_id, created_at) VALUES (?, ?, ?)", 2, 1, "2026-06-01 10:00:00")
	db.Exec("INSERT INTO encounters (patient_id, department_id, created_at) VALUES (?, ?, ?)", 3, 2, "2026-06-01 11:00:00")
	db.Exec("INSERT INTO encounters (patient_id, department_id, created_at) VALUES (?, ?, ?)", 4, 1, "2026-06-02 09:00:00")

	results, err := svc.GetTrendData("门诊量", "2026-06-01", "2026-06-02", "day", 1)
	require.NoError(t, err)
	require.Len(t, results, 2)

	d := results[0]
	require.Equal(t, "2026-06-01", d["date"])
	_, ok := d["value"].(float64)
	require.True(t, ok, "value should be float64, got %T", d["value"])
	require.InDelta(t, 2.0, d["value"], 0.001)

	d = results[1]
	require.Equal(t, "2026-06-02", d["date"])
	require.InDelta(t, 1.0, d["value"], 0.001)
}
