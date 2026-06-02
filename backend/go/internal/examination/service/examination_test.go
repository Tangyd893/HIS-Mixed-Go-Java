package service

import (
	"testing"

	"github.com/his-mixed/go/internal/examination/model"
	"github.com/his-mixed/go/internal/examination/repository"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)
	err = db.AutoMigrate(&model.ExamRequest{}, &model.ExamReport{})
	require.NoError(t, err)
	return db
}

func newTestService(t *testing.T) (*ExaminationService, *gorm.DB) {
	db := setupTestDB(t)
	repo := repository.NewExaminationRepository(db)
	svc := NewExaminationService(repo)
	return svc, db
}

func newExamRequest(patientID, doctorID int64, examType string) *model.ExamRequest {
	return &model.ExamRequest{
		PatientID:    patientID,
		DoctorID:     doctorID,
		ExamType:     examType,
		Urgency:      "NORMAL",
		ClinicalInfo: "患者症状描述",
	}
}

func newExamReport(requestID int64, reportNo string) *model.ExamReport {
	return &model.ExamReport{
		RequestID:      requestID,
		ReportNo:       reportNo,
		Findings:       "检查所见",
		Impression:     "印象",
		Conclusion:     "结论",
		ReferenceRange: "参考范围",
		IsAbnormal:     false,
	}
}

func TestCreateExamRequest(t *testing.T) {
	svc, _ := newTestService(t)

	req := newExamRequest(1, 100, "X光")
	err := svc.CreateExamRequest(req)
	require.NoError(t, err)
	assert.NotZero(t, req.ID)
	assert.Equal(t, "PENDING", req.Status)
}

func TestGetExamRequestByID(t *testing.T) {
	svc, _ := newTestService(t)

	req := newExamRequest(42, 100, "CT")
	require.NoError(t, svc.CreateExamRequest(req))

	got, err := svc.GetExamRequestByID(req.ID)
	require.NoError(t, err)
	assert.Equal(t, int64(42), got.PatientID)
	assert.Equal(t, "CT", got.ExamType)
}

func TestListExamRequests(t *testing.T) {
	svc, _ := newTestService(t)

	for i := 0; i < 5; i++ {
		req := newExamRequest(200, 100, "B超")
		require.NoError(t, svc.CreateExamRequest(req))
	}

	reqs, total, err := svc.ListExamRequests(200, "", 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, reqs, 5)
}

func TestUpdateExamRequestStatus(t *testing.T) {
	svc, _ := newTestService(t)

	req := newExamRequest(1, 100, "MRI")
	require.NoError(t, svc.CreateExamRequest(req))

	err := svc.UpdateExamRequestStatus(req.ID, "IN_PROGRESS")
	require.NoError(t, err)

	got, err := svc.GetExamRequestByID(req.ID)
	require.NoError(t, err)
	assert.Equal(t, "IN_PROGRESS", got.Status)
}

func TestCreateExamReport(t *testing.T) {
	svc, _ := newTestService(t)

	req := newExamRequest(1, 100, "X光")
	require.NoError(t, svc.CreateExamRequest(req))

	report := newExamReport(req.ID, "RPT-2026-001")
	err := svc.CreateExamReport(report)
	require.NoError(t, err)
	assert.NotZero(t, report.ID)
	assert.Equal(t, "DRAFT", report.Status)
}

func TestGetExamReportByID(t *testing.T) {
	svc, _ := newTestService(t)

	req := newExamRequest(1, 100, "CT")
	require.NoError(t, svc.CreateExamRequest(req))

	report := newExamReport(req.ID, "RPT-2026-002")
	require.NoError(t, svc.CreateExamReport(report))

	got, err := svc.GetExamReportByID(report.ID)
	require.NoError(t, err)
	assert.Equal(t, "RPT-2026-002", got.ReportNo)
	assert.Equal(t, "检查所见", got.Findings)
}

func TestGetExamReportByRequestID(t *testing.T) {
	svc, _ := newTestService(t)

	req := newExamRequest(1, 100, "MRI")
	require.NoError(t, svc.CreateExamRequest(req))

	report := newExamReport(req.ID, "RPT-2026-003")
	require.NoError(t, svc.CreateExamReport(report))

	got, err := svc.GetExamReportByRequestID(req.ID)
	require.NoError(t, err)
	assert.Equal(t, report.ID, got.ID)
}

func TestReviewExamReport(t *testing.T) {
	svc, _ := newTestService(t)

	req := newExamRequest(1, 100, "B超")
	require.NoError(t, svc.CreateExamRequest(req))

	report := newExamReport(req.ID, "RPT-2026-004")
	require.NoError(t, svc.CreateExamReport(report))

	reviewerID := int64(200)
	err := svc.ReviewExamReport(report.ID, reviewerID, "APPROVED")
	require.NoError(t, err)

	got, err := svc.GetExamReportByID(report.ID)
	require.NoError(t, err)
	assert.Equal(t, "APPROVED", got.Status)
	assert.Equal(t, &reviewerID, got.ReviewerID)
	assert.NotNil(t, got.ReviewedAt)
}
