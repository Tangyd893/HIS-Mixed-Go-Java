package repository

import (
	"github.com/his-mixed/go/internal/followup/model"
	"gorm.io/gorm"
)

// FollowupRepository 随访数据访问层
type FollowupRepository struct {
	db *gorm.DB
}

// NewFollowupRepository 创建随访仓储
func NewFollowupRepository(db *gorm.DB) *FollowupRepository {
	return &FollowupRepository{db: db}
}

// CreateFollowupPlan 创建随访计划
func (r *FollowupRepository) CreateFollowupPlan(plan *model.FollowupPlan) error {
	return r.db.Create(plan).Error
}

// GetFollowupPlanByID 按ID查询随访计划
func (r *FollowupRepository) GetFollowupPlanByID(id int64) (*model.FollowupPlan, error) {
	var plan model.FollowupPlan
	err := r.db.First(&plan, id).Error
	if err != nil {
		return nil, err
	}
	return &plan, nil
}

// ListFollowupPlans 分页查询随访计划
func (r *FollowupRepository) ListFollowupPlans(patientID int64, page, size int) ([]model.FollowupPlan, int64, error) {
	var plans []model.FollowupPlan
	var total int64

	base := r.db.Where("patient_id = ?", patientID)
	base.Model(&model.FollowupPlan{}).Count(&total)

	offset := (page - 1) * size
	err := base.Offset(offset).Limit(size).Order("created_at DESC").Find(&plans).Error
	return plans, total, err
}

// UpdateFollowupPlan 更新随访计划
func (r *FollowupRepository) UpdateFollowupPlan(plan *model.FollowupPlan) error {
	return r.db.Save(plan).Error
}

// CreateFollowupRecord 创建随访记录
func (r *FollowupRepository) CreateFollowupRecord(record *model.FollowupRecord) error {
	return r.db.Create(record).Error
}

// GetFollowupRecords 查询随访记录
func (r *FollowupRepository) GetFollowupRecords(planID int64) ([]model.FollowupRecord, error) {
	var records []model.FollowupRecord
	err := r.db.Where("plan_id = ?", planID).Order("created_at DESC").Find(&records).Error
	return records, err
}

// ListFollowupPlansByPatientID 按患者ID查询随访计划
func (r *FollowupRepository) ListFollowupPlansByPatientID(patientID int64, page, size int) ([]model.FollowupPlan, int64, error) {
	var plans []model.FollowupPlan
	var total int64

	base := r.db.Where("patient_id = ?", patientID)
	base.Model(&model.FollowupPlan{}).Count(&total)

	offset := (page - 1) * size
	err := base.Offset(offset).Limit(size).Order("created_at DESC").Find(&plans).Error
	return plans, total, err
}
