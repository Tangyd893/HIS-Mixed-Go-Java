package service

import (
	"time"

	"github.com/his-mixed/go/internal/followup/model"
	"github.com/his-mixed/go/internal/followup/repository"
)

// FollowupService 随访业务服务
type FollowupService struct {
	repo *repository.FollowupRepository
}

// NewFollowupService 创建随访服务
func NewFollowupService(repo *repository.FollowupRepository) *FollowupService {
	return &FollowupService{repo: repo}
}

// CreateFollowupPlan 创建随访计划
func (s *FollowupService) CreateFollowupPlan(plan *model.FollowupPlan) error {
	plan.Status = "PENDING"
	plan.CreatedAt = time.Now()
	plan.UpdatedAt = time.Now()
	return s.repo.CreateFollowupPlan(plan)
}

// GetFollowupPlanByID 按ID查询随访计划
func (s *FollowupService) GetFollowupPlanByID(id int64) (*model.FollowupPlan, error) {
	return s.repo.GetFollowupPlanByID(id)
}

// ListFollowupPlans 分页查询随访计划
func (s *FollowupService) ListFollowupPlans(patientID int64, status string, page, size int) ([]model.FollowupPlan, int64, error) {
	return s.repo.ListFollowupPlans(patientID, page, size)
}

// UpdateFollowupPlanStatus 更新随访计划状态
func (s *FollowupService) UpdateFollowupPlanStatus(id int64, status string) error {
	plan, err := s.repo.GetFollowupPlanByID(id)
	if err != nil {
		return err
	}
	plan.Status = status
	plan.UpdatedAt = time.Now()
	return s.repo.UpdateFollowupPlan(plan)
}

// CreateFollowupRecord 创建随访记录
func (s *FollowupService) CreateFollowupRecord(record *model.FollowupRecord) error {
	record.CreatedAt = time.Now()
	return s.repo.CreateFollowupRecord(record)
}

// GetFollowupRecords 查询随访记录
func (s *FollowupService) GetFollowupRecords(planID, patientID int64) ([]model.FollowupRecord, error) {
	return s.repo.GetFollowupRecords(planID)
}

