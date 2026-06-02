package service

import (
	"time"

	"github.com/his-mixed/go/internal/examination/model"
	"github.com/his-mixed/go/internal/examination/repository"
)

// ExaminationService 检查检验业务服务
type ExaminationService struct {
	repo *repository.ExaminationRepository
}

// NewExaminationService 创建检查服务
func NewExaminationService(repo *repository.ExaminationRepository) *ExaminationService {
	return &ExaminationService{repo: repo}
}

// CreateExamRequest 创建检查申请
func (s *ExaminationService) CreateExamRequest(req *model.ExamRequest) error {
	req.Status = "PENDING"
	req.CreatedAt = time.Now()
	req.UpdatedAt = time.Now()
	return s.repo.CreateExamRequest(req)
}

// GetExamRequestByID 按ID查询检查申请
func (s *ExaminationService) GetExamRequestByID(id int64) (*model.ExamRequest, error) {
	return s.repo.GetExamRequestByID(id)
}

// ListExamRequests 分页查询检查申请
func (s *ExaminationService) ListExamRequests(patientID int64, status string, page, size int) ([]model.ExamRequest, int64, error) {
	return s.repo.ListExamRequests(patientID, page, size)
}

// UpdateExamRequestStatus 更新申请状态
func (s *ExaminationService) UpdateExamRequestStatus(id int64, status string) error {
	req, err := s.repo.GetExamRequestByID(id)
	if err != nil {
		return err
	}
	req.Status = status
	req.UpdatedAt = time.Now()
	return s.repo.UpdateExamRequest(req)
}

// CreateExamReport 创建检查报告
func (s *ExaminationService) CreateExamReport(report *model.ExamReport) error {
	report.Status = "DRAFT"
	report.CreatedAt = time.Now()
	report.UpdatedAt = time.Now()
	return s.repo.CreateExamReport(report)
}

// GetExamReportByID 按ID查询检查报告
func (s *ExaminationService) GetExamReportByID(id int64) (*model.ExamReport, error) {
	return s.repo.GetExamReportByID(id)
}

// GetExamReportByRequestID 按申请ID查询报告
func (s *ExaminationService) GetExamReportByRequestID(requestID int64) (*model.ExamReport, error) {
	return s.repo.GetExamReportByRequestID(requestID)
}

// ReviewExamReport 审核检查报告
func (s *ExaminationService) ReviewExamReport(id int64, reviewerID int64, status string) error {
	report, err := s.repo.GetExamReportByID(id)
	if err != nil {
		return err
	}
	report.Status = status
	report.ReviewerID = &reviewerID
	now := time.Now()
	report.ReviewedAt = &now
	report.UpdatedAt = now
	return s.repo.UpdateExamReport(report)
}

