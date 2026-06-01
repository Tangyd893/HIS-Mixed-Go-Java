package repository

import (
	"fmt"

	"github.com/his-mixed/go/internal/pharmacy/model"
	"gorm.io/gorm"
)

// PharmacyRepository 药房数据访问层
type PharmacyRepository struct {
	db *gorm.DB
}

// NewPharmacyRepository 创建药房仓储
func NewPharmacyRepository(db *gorm.DB) *PharmacyRepository {
	return &PharmacyRepository{db: db}
}

// GetDrugByID 按ID查询药品
func (r *PharmacyRepository) GetDrugByID(id int64) (*model.Drug, error) {
	var drug model.Drug
	err := r.db.First(&drug, id).Error
	if err != nil {
		return nil, err
	}
	return &drug, nil
}

// ListDrugs 分页查询药品字典
func (r *PharmacyRepository) ListDrugs(keyword string, page, size int) ([]model.Drug, int64, error) {
	var drugs []model.Drug
	var total int64

	query := r.db.Model(&model.Drug{})
	if keyword != "" {
		query = query.Where("drug_name LIKE ? OR drug_code LIKE ?", "%"+keyword+"%", "%"+keyword+"%")
	}

	query.Count(&total)
	offset := (page - 1) * size
	err := query.Offset(offset).Limit(size).Order("created_at DESC").Find(&drugs).Error
	return drugs, total, err
}

// GetDrugInventory 查询药品库存
func (r *PharmacyRepository) GetDrugInventory(drugID int64) ([]model.DrugInventory, error) {
	var inventory []model.DrugInventory
	err := r.db.Where("drug_id = ? AND status = ? AND quantity > 0", drugID, "AVAILABLE").
		Order("expiry_date ASC").Find(&inventory).Error
	return inventory, err
}

// DeductInventory 扣减库存（乐观锁）
func (r *PharmacyRepository) DeductInventory(inventoryID int64, quantity int) error {
	result := r.db.Model(&model.DrugInventory{}).
		Where("id = ? AND quantity >= ? AND version = (SELECT version FROM drug_inventory WHERE id = ?)", inventoryID, quantity, inventoryID).
		Updates(map[string]interface{}{
			"quantity": gorm.Expr("quantity - ?", quantity),
			"version":  gorm.Expr("version + 1"),
		})
	if result.RowsAffected == 0 {
		return fmt.Errorf("库存不足或并发冲突")
	}
	return result.Error
}

// CreateDispenseRecord 创建发药记录
func (r *PharmacyRepository) CreateDispenseRecord(record *model.DispenseRecord) error {
	return r.db.Create(record).Error
}
