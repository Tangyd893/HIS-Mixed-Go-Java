package service

import (
	"fmt"

	"github.com/his-mixed/go/internal/pharmacy/model"
	"github.com/his-mixed/go/internal/pharmacy/repository"
)

// PharmacyService 药房业务服务
type PharmacyService struct {
	repo *repository.PharmacyRepository
}

// NewPharmacyService 创建药房服务
func NewPharmacyService(repo *repository.PharmacyRepository) *PharmacyService {
	return &PharmacyService{repo: repo}
}

// GetDrugByID 按ID查询药品
func (s *PharmacyService) GetDrugByID(id int64) (*model.Drug, error) {
	return s.repo.GetDrugByID(id)
}

// ListDrugs 分页查询药品字典
func (s *PharmacyService) ListDrugs(keyword string, page, size int) ([]model.Drug, int64, error) {
	return s.repo.ListDrugs(keyword, page, size)
}

// GetDrugInventory 查询药品库存
func (s *PharmacyService) GetDrugInventory(drugID int64) ([]model.DrugInventory, error) {
	return s.repo.GetDrugInventory(drugID)
}

// DeductInventory 扣减库存（乐观锁）
func (s *PharmacyService) DeductInventory(inventoryID int64, quantity int) error {
	return s.repo.DeductInventory(inventoryID, quantity)
}

// CreateDispenseRecord 创建发药记录
func (s *PharmacyService) CreateDispenseRecord(record *model.DispenseRecord) error {
	return s.repo.CreateDispenseRecord(record)
}

// DispenseDrug 发药（扣库存+记录）
func (s *PharmacyService) DispenseDrug(prescriptionID, drugID, patientID, dispenserID int64, quantity int) error {
	// 查询库存
	inventory, err := s.repo.GetDrugInventory(drugID)
	if err != nil {
		return err
	}
	if len(inventory) == 0 {
		return fmt.Errorf("药品库存不足")
	}

	// 扣减库存
	err = s.repo.DeductInventory(inventory[0].ID, quantity)
	if err != nil {
		return err
	}

	// 创建发药记录
	record := &model.DispenseRecord{
		PrescriptionID: prescriptionID,
		PatientID:      patientID,
		DrugID:         drugID,
		InventoryID:    inventory[0].ID,
		Quantity:       quantity,
		DispenserID:    dispenserID,
	}
	return s.repo.CreateDispenseRecord(record)
}
