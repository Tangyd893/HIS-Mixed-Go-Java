package service

import "github.com/his-mixed/go/internal/examination/model"

// ExaminationService 检查检验业务服务
type ExaminationService struct {
}

// NewExaminationService 创建检查服务
func NewExaminationService() *ExaminationService {
	return &ExaminationService{}
}

// CreateExamRequest 创建检查申请
func (s *ExaminationService) CreateExamRequest(req *model.ExamRequest) error {
	return nil
}

// GetExamRequestByID 按ID查询检查申请
func (s *ExaminationService) GetExamRequestByID(id int64) (*model.ExamRequest, error) {
	return nil, nil
}

// ListExamRequests 分页查询检查申请
func (s *ExaminationService) ListExamRequests(patientID int64, status string, page, size int) ([]model.ExamRequest, int64, error) {
	return nil, 0, nil
}

// UpdateExamRequestStatus 更新申请状态
func (s *ExaminationService) UpdateExamRequestStatus(id int64, status string) error {
	return nil
}

// CreateExamReport 创建检查报告
func (s *ExaminationService) CreateExamReport(report *model.ExamReport) error {
	return nil
}

// GetExamReportByID 按ID查询检查报告
func (s *ExaminationService) GetExamReportByID(id int64) (*model.ExamReport, error) {
	return nil, nil
}

// GetExamReportByRequestID 按申请ID查询报告
func (s *ExaminationService) GetExamReportByRequestID(requestID int64) (*model.ExamReport, error) {
	return nil, nil
}

// ReviewExamReport 审核检查报告
func (s *ExaminationService) ReviewExamReport(id int64, reviewerID int64, status string) error {
	return nil
}
