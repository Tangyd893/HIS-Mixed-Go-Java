package service

import (
	"testing"
	"time"

	"github.com/his-mixed/go/internal/pharmacy/model"
	"github.com/his-mixed/go/internal/pharmacy/repository"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func setupTestDB(t *testing.T) *gorm.DB {
	db, err := gorm.Open(sqlite.Open(":memory:"), &gorm.Config{})
	require.NoError(t, err)
	err = db.AutoMigrate(&model.Drug{}, &model.DrugInventory{}, &model.DispenseRecord{})
	require.NoError(t, err)
	return db
}

func newTestService(t *testing.T) (*PharmacyService, *gorm.DB) {
	db := setupTestDB(t)
	repo := repository.NewPharmacyRepository(db)
	svc := NewPharmacyService(repo)
	return svc, db
}

func newDrug(code, name string, price float64) *model.Drug {
	return &model.Drug{
		DrugCode:     code,
		DrugName:     name,
		TradeName:    name,
		DrugType:     "WESTERN",
		Specification: "10mg*20片",
		Unit:         "盒",
		Category:     "处方药",
		RetailPrice:  price,
		Manufacturer: "测试药厂",
		ApprovalNo:   "H20230001",
	}
}

func createDrug(t *testing.T, db *gorm.DB, code, name string, price float64) *model.Drug {
	drug := newDrug(code, name, price)
	require.NoError(t, db.Create(drug).Error)
	return drug
}

func createInventory(t *testing.T, db *gorm.DB, drugID int64, quantity int, status string) *model.DrugInventory {
	inv := &model.DrugInventory{
		DrugID:        drugID,
		BatchNo:       "B20230601",
		Quantity:      quantity,
		Unit:          "盒",
		PurchasePrice: 10.0,
		ExpiryDate:    time.Date(2027, 12, 31, 0, 0, 0, 0, time.UTC),
		Supplier:      "测试供应商",
		Status:        status,
	}
	require.NoError(t, db.Create(inv).Error)
	return inv
}

func TestGetDrugByID(t *testing.T) {
	svc, db := newTestService(t)

	drug := createDrug(t, db, "D001", "阿莫西林", 15.50)

	got, err := svc.GetDrugByID(drug.ID)
	require.NoError(t, err)
	assert.Equal(t, drug.ID, got.ID)
	assert.Equal(t, "D001", got.DrugCode)
	assert.Equal(t, "阿莫西林", got.DrugName)
	assert.Equal(t, 15.50, got.RetailPrice)
}

func TestGetDrugByID_NotFound(t *testing.T) {
	svc, _ := newTestService(t)

	_, err := svc.GetDrugByID(9999)
	assert.Error(t, err)
}

func TestListDrugs(t *testing.T) {
	svc, db := newTestService(t)

	createDrug(t, db, "D001", "阿莫西林", 15.50)
	createDrug(t, db, "D002", "布洛芬", 12.00)
	createDrug(t, db, "D003", "头孢克肟", 25.00)
	createDrug(t, db, "D004", "对乙酰氨基酚", 8.50)
	createDrug(t, db, "D005", "阿司匹林", 6.00)

	// 查询全部，每页10条
	list, total, err := svc.ListDrugs("", 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, list, 5)

	// 分页：第1页，每页2条
	list, total, err = svc.ListDrugs("", 1, 2)
	require.NoError(t, err)
	assert.Equal(t, int64(5), total)
	assert.Len(t, list, 2)

	// 关键词搜索
	list, total, err = svc.ListDrugs("阿莫", 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(1), total)
	assert.Len(t, list, 1)
	assert.Equal(t, "阿莫西林", list[0].DrugName)

	// 按药品编码搜索
	list, total, err = svc.ListDrugs("D003", 1, 10)
	require.NoError(t, err)
	assert.Equal(t, int64(1), total)
	assert.Len(t, list, 1)
	assert.Equal(t, "头孢克肟", list[0].DrugName)
}

func TestCheckStock_Sufficient(t *testing.T) {
	svc, db := newTestService(t)

	drug := createDrug(t, db, "D001", "阿莫西林", 15.50)
	createInventory(t, db, drug.ID, 100, "AVAILABLE")

	inventory, err := svc.GetDrugInventory(drug.ID)
	require.NoError(t, err)
	assert.NotEmpty(t, inventory)
	assert.Equal(t, 100, inventory[0].Quantity)
}

func TestCheckStock_Insufficient(t *testing.T) {
	svc, db := newTestService(t)

	drug := createDrug(t, db, "D001", "阿莫西林", 15.50)
	// 库存为0，状态AVAILABLE，不应被查出（quantity > 0条件）
	createInventory(t, db, drug.ID, 0, "AVAILABLE")

	inventory, err := svc.GetDrugInventory(drug.ID)
	require.NoError(t, err)
	assert.Empty(t, inventory)
}

func TestCheckStock_ExpiredNotReturned(t *testing.T) {
	svc, db := newTestService(t)

	drug := createDrug(t, db, "D001", "阿莫西林", 15.50)
	// 状态为EXPIRED的库存不应被查出
	createInventory(t, db, drug.ID, 50, "EXPIRED")

	inventory, err := svc.GetDrugInventory(drug.ID)
	require.NoError(t, err)
	assert.Empty(t, inventory)
}

func TestCheckStock_DeductInventory(t *testing.T) {
	svc, db := newTestService(t)

	drug := createDrug(t, db, "D001", "阿莫西林", 15.50)
	inv := createInventory(t, db, drug.ID, 10, "AVAILABLE")

	err := svc.DeductInventory(inv.ID, 3)
	require.NoError(t, err)

	// 验证扣减后库存
	var updated model.DrugInventory
	db.First(&updated, inv.ID)
	assert.Equal(t, 7, updated.Quantity)
	assert.Equal(t, 1, updated.Version)
}

func TestCheckStock_DeductInsufficient(t *testing.T) {
	svc, db := newTestService(t)

	drug := createDrug(t, db, "D001", "阿莫西林", 15.50)
	inv := createInventory(t, db, drug.ID, 2, "AVAILABLE")

	err := svc.DeductInventory(inv.ID, 5)
	assert.Error(t, err)
	assert.Contains(t, err.Error(), "库存不足")
}

func TestDispenseDrug(t *testing.T) {
	svc, db := newTestService(t)

	drug := createDrug(t, db, "D001", "阿莫西林", 15.50)
	createInventory(t, db, drug.ID, 50, "AVAILABLE")

	err := svc.DispenseDrug(1001, drug.ID, 2001, 3001, 5)
	require.NoError(t, err)

	// 验证库存扣减
	inventory, err := svc.GetDrugInventory(drug.ID)
	require.NoError(t, err)
	assert.Equal(t, 45, inventory[0].Quantity)

	// 验证发药记录
	var records []model.DispenseRecord
	db.Where("drug_id = ?", drug.ID).Find(&records)
	assert.Len(t, records, 1)
	assert.Equal(t, int64(1001), records[0].PrescriptionID)
	assert.Equal(t, int64(2001), records[0].PatientID)
	assert.Equal(t, 5, records[0].Quantity)
}

func TestDispenseDrug_NoInventory(t *testing.T) {
	svc, db := newTestService(t)

	drug := createDrug(t, db, "D001", "阿莫西林", 15.50)

	err := svc.DispenseDrug(1001, drug.ID, 2001, 3001, 5)
	assert.Error(t, err)
	assert.Contains(t, err.Error(), "药品库存不足")
}
