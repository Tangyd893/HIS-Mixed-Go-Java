package service

import (
	"github.com/his-mixed/go/internal/registration/model"
	"github.com/his-mixed/go/internal/registration/repository"
)

// RegistrationService 挂号业务服务
type RegistrationService struct {
	repo *repository.RegistrationRepository
}

// NewRegistrationService 创建挂号服务
func NewRegistrationService(repo *repository.RegistrationRepository) *RegistrationService {
	return &RegistrationService{repo: repo}
}

// CreateRegistration 创建挂号记录
func (s *RegistrationService) CreateRegistration(reg *model.Registration) error {
	return s.repo.Create(reg)
}

// CancelRegistration 取消挂号
func (s *RegistrationService) CancelRegistration(id int64) error {
	reg, err := s.repo.GetByID(id)
	if err != nil {
		return err
	}
	reg.Status = "CANCELLED"
	return s.repo.Update(reg)
}

// CompleteRegistration 完成挂号
func (s *RegistrationService) CompleteRegistration(id int64) error {
	reg, err := s.repo.GetByID(id)
	if err != nil {
		return err
	}
	reg.Status = "COMPLETED"
	return s.repo.Update(reg)
}

// GetRegistrationByID 按ID查询挂号记录
func (s *RegistrationService) GetRegistrationByID(id int64) (*model.Registration, error) {
	return s.repo.GetByID(id)
}

// ListRegistrations 分页查询挂号记录
func (s *RegistrationService) ListRegistrations(patientID int64, status string, page, size int) ([]model.Registration, int64, error) {
	return s.repo.ListByPatientID(patientID, page, size)
}

// CreateQueueItem 创建排队叫号
func (s *RegistrationService) CreateQueueItem(item *model.QueueItem) error {
	return s.repo.CreateQueueItem(item)
}

// CallQueueItem 叫号
func (s *RegistrationService) CallQueueItem(id int64, roomID int64) error {
	return nil
}

// GetQueueItems 按科室查询排队列表
func (s *RegistrationService) GetQueueItems(departmentID int64) ([]model.QueueItem, error) {
	return s.repo.GetQueueItems(departmentID)
}
