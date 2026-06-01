package repository

import (
	"fmt"

	"github.com/his-mixed/go/internal/schedule/model"
	"gorm.io/gorm"
)

type ScheduleRepository struct {
	db *gorm.DB
}

func NewScheduleRepository(db *gorm.DB) *ScheduleRepository {
	return &ScheduleRepository{db: db}
}

func (r *ScheduleRepository) CreatePlan(plan *model.SchedulePlan) error {
	return r.db.Create(plan).Error
}

func (r *ScheduleRepository) GetPlanByID(id int64) (*model.SchedulePlan, error) {
	var plan model.SchedulePlan
	err := r.db.First(&plan, id).Error
	if err != nil {
		return nil, err
	}
	return &plan, nil
}

func (r *ScheduleRepository) ListPlans(doctorID, departmentID int64, page, size int) ([]model.SchedulePlan, int64, error) {
	var plans []model.SchedulePlan
	var total int64

	query := r.db.Model(&model.SchedulePlan{})
	if doctorID > 0 {
		query = query.Where("doctor_id = ?", doctorID)
	}
	if departmentID > 0 {
		query = query.Where("department_id = ?", departmentID)
	}

	query.Count(&total)
	offset := (page - 1) * size
	err := query.Offset(offset).Limit(size).Order("created_at DESC").Find(&plans).Error
	return plans, total, err
}

func (r *ScheduleRepository) CreateSlot(slot *model.ScheduleSlot) error {
	return r.db.Create(slot).Error
}

func (r *ScheduleRepository) GetSlots(departmentID int64, scheduleDate string) ([]model.ScheduleSlot, error) {
	var slots []model.ScheduleSlot
	query := r.db.Where("schedule_date = ?", scheduleDate)
	if departmentID > 0 {
		query = query.Where("department_id = ?", departmentID)
	}
	err := query.Find(&slots).Error
	return slots, err
}

func (r *ScheduleRepository) GetSlotsByDoctor(doctorID int64, startDate, endDate string) ([]model.ScheduleSlot, error) {
	var slots []model.ScheduleSlot
	query := r.db.Where("doctor_id = ? AND schedule_date BETWEEN ? AND ?", doctorID, startDate, endDate)
	err := query.Order("schedule_date ASC, start_time ASC").Find(&slots).Error
	return slots, err
}

func (r *ScheduleRepository) DeductQuota(slotID int64) error {
	result := r.db.Model(&model.ScheduleSlot{}).
		Where("id = ? AND remaining > 0 AND version = (SELECT version FROM schedule_slots WHERE id = ?)", slotID, slotID).
		Updates(map[string]interface{}{
			"remaining": gorm.Expr("remaining - 1"),
			"version":   gorm.Expr("version + 1"),
		})
	if result.RowsAffected == 0 {
		return fmt.Errorf("号源已满或并发冲突")
	}
	return result.Error
}

func (r *ScheduleRepository) ReleaseQuota(slotID int64) error {
	result := r.db.Model(&model.ScheduleSlot{}).
		Where("id = ?", slotID).
		Updates(map[string]interface{}{
			"remaining": gorm.Expr("remaining + 1"),
			"version":   gorm.Expr("version + 1"),
		})
	return result.Error
}
