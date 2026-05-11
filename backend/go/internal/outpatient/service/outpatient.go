package service

import "github.com/his-mixed/go/internal/outpatient/model"

// OutpatientService 院外问诊业务服务
type OutpatientService struct {
}

// NewOutpatientService 创建院外服务
func NewOutpatientService() *OutpatientService {
	return &OutpatientService{}
}

// CreateConsultation 创建在线问诊
func (s *OutpatientService) CreateConsultation(consultation *model.Consultation) error {
	return nil
}

// GetConsultationByID 按ID查询问诊
func (s *OutpatientService) GetConsultationByID(id int64) (*model.Consultation, error) {
	return nil, nil
}

// ListConsultations 分页查询问诊列表
func (s *OutpatientService) ListConsultations(patientID, doctorID int64, status string, page, size int) ([]model.Consultation, int64, error) {
	return nil, 0, nil
}

// AcceptConsultation 接诊
func (s *OutpatientService) AcceptConsultation(id, doctorID int64) error {
	return nil
}

// CloseConsultation 结束问诊
func (s *OutpatientService) CloseConsultation(id int64) error {
	return nil
}

// SendMessage 发送问诊消息
func (s *OutpatientService) SendMessage(msg *model.ConsultationMessage) error {
	return nil
}

// GetMessages 获取问诊消息列表
func (s *OutpatientService) GetMessages(consultationID int64) ([]model.ConsultationMessage, error) {
	return nil, nil
}
