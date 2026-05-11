package service

import "github.com/his-mixed/go/internal/schedule/model"

// ScheduleService 排班业务服务
type ScheduleService struct {
}

// NewScheduleService 创建排班服务
func NewScheduleService() *ScheduleService {
	return &ScheduleService{}
}

// CreateSchedulePlan 创建排班计划
func (s *ScheduleService) CreateSchedulePlan(plan *model.SchedulePlan) error {
	return nil
}

// GetSchedulePlanByID 按ID查询排班计划
func (s *ScheduleService) GetSchedulePlanByID(id int64) (*model.SchedulePlan, error) {
	return nil, nil
}

// ListSchedulePlans 查询排班计划列表
func (s *ScheduleService) ListSchedulePlans(doctorID, departmentID int64, page, size int) ([]model.SchedulePlan, int64, error) {
	return nil, 0, nil
}

// CreateScheduleSlot 创建号源时段
func (s *ScheduleService) CreateScheduleSlot(slot *model.ScheduleSlot) error {
	return nil
}

// GetScheduleSlots 查询指定日期的号源
func (s *ScheduleService) GetScheduleSlots(departmentID int64, scheduleDate string) ([]model.ScheduleSlot, error) {
	return nil, nil
}

// DeductQuota 扣减号源（乐观锁）
func (s *ScheduleService) DeductQuota(slotID int64) error {
	return nil
}

// ReleaseQuota 释放号源（乐观锁）
func (s *ScheduleService) ReleaseQuota(slotID int64) error {
	return nil
}
