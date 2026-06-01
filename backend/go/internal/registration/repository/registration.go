package repository

import (
	"github.com/his-mixed/go/internal/registration/model"
	"gorm.io/gorm"
)

// RegistrationRepository 挂号数据访问层
type RegistrationRepository struct {
	db *gorm.DB
}

// NewRegistrationRepository 创建挂号仓储
func NewRegistrationRepository(db *gorm.DB) *RegistrationRepository {
	return &RegistrationRepository{db: db}
}

// Create 创建挂号记录
func (r *RegistrationRepository) Create(reg *model.Registration) error {
	return r.db.Create(reg).Error
}

// GetByID 按ID查询
func (r *RegistrationRepository) GetByID(id int64) (*model.Registration, error) {
	var reg model.Registration
	err := r.db.First(&reg, id).Error
	if err != nil {
		return nil, err
	}
	return &reg, nil
}

// Update 更新挂号记录
func (r *RegistrationRepository) Update(reg *model.Registration) error {
	return r.db.Save(reg).Error
}

// ListByPatientID 按患者ID查询
func (r *RegistrationRepository) ListByPatientID(patientID int64, page, size int) ([]model.Registration, int64, error) {
	var regs []model.Registration
	var total int64

	base := r.db.Where("patient_id = ?", patientID)
	base.Model(&model.Registration{}).Count(&total)

	offset := (page - 1) * size
	err := base.Offset(offset).Limit(size).Order("created_at DESC").Find(&regs).Error
	return regs, total, err
}

// CreateQueueItem 创建排队项
func (r *RegistrationRepository) CreateQueueItem(item *model.QueueItem) error {
	return r.db.Create(item).Error
}

// GetQueueItems 按科室查询排队列表
func (r *RegistrationRepository) GetQueueItems(departmentID int64) ([]model.QueueItem, error) {
	var items []model.QueueItem
	err := r.db.Where("department_id = ? AND status = ?", departmentID, "WAITING").
		Order("queue_number ASC").Find(&items).Error
	return items, err
}
