package service

import "github.com/his-mixed/go/internal/followup/model"

// FollowupService 随访业务服务
type FollowupService struct {
}

// NewFollowupService 创建随访服务
func NewFollowupService() *FollowupService {
	return &FollowupService{}
}

// CreateFollowupPlan 创建随访计划
func (s *FollowupService) CreateFollowupPlan(plan *model.FollowupPlan) error {
	return nil
}

// GetFollowupPlanByID 按ID查询随访计划
func (s *FollowupService) GetFollowupPlanByID(id int64) (*model.FollowupPlan, error) {
	return nil, nil
}

// ListFollowupPlans 分页查询随访计划
func (s *FollowupService) ListFollowupPlans(patientID int64, status string, page, size int) ([]model.FollowupPlan, int64, error) {
	return nil, 0, nil
}

// UpdateFollowupPlanStatus 更新随访计划状态
func (s *FollowupService) UpdateFollowupPlanStatus(id int64, status string) error {
	return nil
}

// CreateFollowupRecord 创建随访记录
func (s *FollowupService) CreateFollowupRecord(record *model.FollowupRecord) error {
	return nil
}

// GetFollowupRecords 查询随访记录
func (s *FollowupService) GetFollowupRecords(planID, patientID int64) ([]model.FollowupRecord, error) {
	return nil, nil
}
