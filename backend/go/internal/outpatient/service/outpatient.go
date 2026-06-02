package service

import (
	"time"

	"github.com/his-mixed/go/internal/outpatient/model"
	"github.com/his-mixed/go/internal/outpatient/repository"
)

// OutpatientService 院外问诊业务服务
type OutpatientService struct {
	repo *repository.OutpatientRepository
}

// NewOutpatientService 创建院外服务
func NewOutpatientService(repo *repository.OutpatientRepository) *OutpatientService {
	return &OutpatientService{repo: repo}
}

// CreateConsultation 创建在线问诊
func (s *OutpatientService) CreateConsultation(consultation *model.Consultation) error {
	consultation.Status = "PENDING"
	consultation.CreatedAt = time.Now()
	consultation.UpdatedAt = time.Now()
	return s.repo.CreateConsultation(consultation)
}

// GetConsultationByID 按ID查询问诊
func (s *OutpatientService) GetConsultationByID(id int64) (*model.Consultation, error) {
	return s.repo.GetConsultationByID(id)
}

// ListConsultations 分页查询问诊列表
func (s *OutpatientService) ListConsultations(patientID, doctorID int64, status string, page, size int) ([]model.Consultation, int64, error) {
	return s.repo.ListConsultations(patientID, doctorID, page, size)
}

// AcceptConsultation 接诊
func (s *OutpatientService) AcceptConsultation(id, doctorID int64) error {
	consultation, err := s.repo.GetConsultationByID(id)
	if err != nil {
		return err
	}
	consultation.DoctorID = &doctorID
	consultation.Status = "IN_PROGRESS"
	now := time.Now()
	consultation.StartedAt = &now
	consultation.UpdatedAt = now
	return s.repo.UpdateConsultation(consultation)
}

// CloseConsultation 结束问诊
func (s *OutpatientService) CloseConsultation(id int64) error {
	consultation, err := s.repo.GetConsultationByID(id)
	if err != nil {
		return err
	}
	consultation.Status = "CLOSED"
	now := time.Now()
	consultation.ClosedAt = &now
	consultation.UpdatedAt = now
	return s.repo.UpdateConsultation(consultation)
}

// SendMessage 发送问诊消息
func (s *OutpatientService) SendMessage(msg *model.ConsultationMessage) error {
	msg.CreatedAt = time.Now()
	return s.repo.CreateMessage(msg)
}

// GetMessages 获取问诊消息列表
func (s *OutpatientService) GetMessages(consultationID int64) ([]model.ConsultationMessage, error) {
	return s.repo.GetMessages(consultationID)
}

