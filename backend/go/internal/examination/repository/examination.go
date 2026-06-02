package repository

import (
	"github.com/his-mixed/go/internal/examination/model"
	"gorm.io/gorm"
)

// ExaminationRepository 检查检验数据访问层
type ExaminationRepository struct {
	db *gorm.DB
}

// NewExaminationRepository 创建检查仓储
func NewExaminationRepository(db *gorm.DB) *ExaminationRepository {
	return &ExaminationRepository{db: db}
}

// CreateExamRequest 创建检查申请
func (r *ExaminationRepository) CreateExamRequest(req *model.ExamRequest) error {
	return r.db.Create(req).Error
}

// GetExamRequestByID 按ID查询检查申请
func (r *ExaminationRepository) GetExamRequestByID(id int64) (*model.ExamRequest, error) {
	var req model.ExamRequest
	err := r.db.First(&req, id).Error
	if err != nil {
		return nil, err
	}
	return &req, nil
}

// ListExamRequests 分页查询检查申请
func (r *ExaminationRepository) ListExamRequests(patientID int64, page, size int) ([]model.ExamRequest, int64, error) {
	var reqs []model.ExamRequest
	var total int64

	base := r.db.Where("patient_id = ?", patientID)
	base.Model(&model.ExamRequest{}).Count(&total)

	offset := (page - 1) * size
	err := base.Offset(offset).Limit(size).Order("created_at DESC").Find(&reqs).Error
	return reqs, total, err
}

// UpdateExamRequest 更新检查申请
func (r *ExaminationRepository) UpdateExamRequest(req *model.ExamRequest) error {
	return r.db.Save(req).Error
}

// CreateExamReport 创建检查报告
func (r *ExaminationRepository) CreateExamReport(report *model.ExamReport) error {
	return r.db.Create(report).Error
}

// GetExamReportByID 按ID查询检查报告
func (r *ExaminationRepository) GetExamReportByID(id int64) (*model.ExamReport, error) {
	var report model.ExamReport
	err := r.db.First(&report, id).Error
	if err != nil {
		return nil, err
	}
	return &report, nil
}

// GetExamReportByRequestID 按申请ID查询报告
func (r *ExaminationRepository) GetExamReportByRequestID(requestID int64) (*model.ExamReport, error) {
	var report model.ExamReport
	err := r.db.Where("request_id = ?", requestID).First(&report).Error
	if err != nil {
		return nil, err
	}
	return &report, nil
}

// UpdateExamReport 更新检查报告
func (r *ExaminationRepository) UpdateExamReport(report *model.ExamReport) error {
	return r.db.Save(report).Error
}
