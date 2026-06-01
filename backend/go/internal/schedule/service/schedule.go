package service

import (
	"github.com/his-mixed/go/internal/schedule/model"
	"github.com/his-mixed/go/internal/schedule/repository"
)

type ScheduleService struct {
	repo *repository.ScheduleRepository
}

func NewScheduleService(repo *repository.ScheduleRepository) *ScheduleService {
	return &ScheduleService{repo: repo}
}

func (s *ScheduleService) CreateSchedulePlan(plan *model.SchedulePlan) error {
	return s.repo.CreatePlan(plan)
}

func (s *ScheduleService) GetSchedulePlanByID(id int64) (*model.SchedulePlan, error) {
	return s.repo.GetPlanByID(id)
}

func (s *ScheduleService) ListSchedulePlans(doctorID, departmentID int64, page, size int) ([]model.SchedulePlan, int64, error) {
	return s.repo.ListPlans(doctorID, departmentID, page, size)
}

func (s *ScheduleService) CreateScheduleSlot(slot *model.ScheduleSlot) error {
	return s.repo.CreateSlot(slot)
}

func (s *ScheduleService) GetScheduleSlots(departmentID int64, scheduleDate string) ([]model.ScheduleSlot, error) {
	return s.repo.GetSlots(departmentID, scheduleDate)
}

func (s *ScheduleService) DeductQuota(slotID int64) error {
	return s.repo.DeductQuota(slotID)
}

func (s *ScheduleService) ReleaseQuota(slotID int64) error {
	return s.repo.ReleaseQuota(slotID)
}
