package repository

import (
	"github.com/his-mixed/go/internal/outpatient/model"
	"gorm.io/gorm"
)

// OutpatientRepository 院外问诊数据访问层
type OutpatientRepository struct {
	db *gorm.DB
}

// NewOutpatientRepository 创建院外问诊仓储
func NewOutpatientRepository(db *gorm.DB) *OutpatientRepository {
	return &OutpatientRepository{db: db}
}

// CreateConsultation 创建在线问诊
func (r *OutpatientRepository) CreateConsultation(consultation *model.Consultation) error {
	return r.db.Create(consultation).Error
}

// GetConsultationByID 按ID查询问诊
func (r *OutpatientRepository) GetConsultationByID(id int64) (*model.Consultation, error) {
	var consultation model.Consultation
	err := r.db.First(&consultation, id).Error
	if err != nil {
		return nil, err
	}
	return &consultation, nil
}

// ListConsultations 分页查询问诊列表
func (r *OutpatientRepository) ListConsultations(patientID, doctorID int64, page, size int) ([]model.Consultation, int64, error) {
	var consultations []model.Consultation
	var total int64

	base := r.db
	if patientID > 0 {
		base = base.Where("patient_id = ?", patientID)
	}
	if doctorID > 0 {
		base = base.Where("doctor_id = ?", doctorID)
	}
	base.Model(&model.Consultation{}).Count(&total)

	offset := (page - 1) * size
	err := base.Offset(offset).Limit(size).Order("created_at DESC").Find(&consultations).Error
	return consultations, total, err
}

// UpdateConsultation 更新问诊
func (r *OutpatientRepository) UpdateConsultation(consultation *model.Consultation) error {
	return r.db.Save(consultation).Error
}

// CreateMessage 创建问诊消息
func (r *OutpatientRepository) CreateMessage(msg *model.ConsultationMessage) error {
	return r.db.Create(msg).Error
}

// GetMessages 获取问诊消息列表
func (r *OutpatientRepository) GetMessages(consultationID int64) ([]model.ConsultationMessage, error) {
	var msgs []model.ConsultationMessage
	err := r.db.Where("consultation_id = ?", consultationID).Order("created_at ASC").Find(&msgs).Error
	return msgs, err
}
