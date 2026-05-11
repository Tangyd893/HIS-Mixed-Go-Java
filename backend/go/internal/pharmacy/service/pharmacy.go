package service

import "github.com/his-mixed/go/internal/pharmacy/model"

// PharmacyService 药房业务服务
type PharmacyService struct {
}

// NewPharmacyService 创建药房服务
func NewPharmacyService() *PharmacyService {
	return &PharmacyService{}
}

// GetDrugByID 按ID查询药品
func (s *PharmacyService) GetDrugByID(id int64) (*model.Drug, error) {
	return nil, nil
}

// ListDrugs 分页查询药品字典
func (s *PharmacyService) ListDrugs(keyword string, page, size int) ([]model.Drug, int64, error) {
	return nil, 0, nil
}

// GetDrugInventory 查询药品库存
func (s *PharmacyService) GetDrugInventory(drugID int64) ([]model.DrugInventory, error) {
	return nil, nil
}

// DeductInventory 扣减库存（乐观锁）
func (s *PharmacyService) DeductInventory(inventoryID int64, quantity int) error {
	return nil
}

// CreateDispenseRecord 创建发药记录
func (s *PharmacyService) CreateDispenseRecord(record *model.DispenseRecord) error {
	return nil
}

// DispenseDrug 发药（扣库存+记录）
func (s *PharmacyService) DispenseDrug(prescriptionID, drugID, patientID, dispenserID int64, quantity int) error {
	return nil
}
