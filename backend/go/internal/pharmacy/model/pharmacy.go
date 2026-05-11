package model

import (
	"time"

	"gorm.io/gorm"
)

// Drug 药品字典
type Drug struct {
	ID             int64          `gorm:"primaryKey" json:"id"`
	DrugCode       string         `gorm:"size:50;uniqueIndex;not null" json:"drugCode"`
	DrugName       string         `gorm:"size:200;not null" json:"drugName"`
	TradeName      string         `gorm:"size:200" json:"tradeName"`
	DrugType       string         `gorm:"size:20" json:"drugType"`
	Specification  string         `gorm:"size:100" json:"specification"`
	Unit           string         `gorm:"size:20" json:"unit"`
	Category       string         `gorm:"size:50" json:"category"`
	IsPrescription bool           `gorm:"default:true" json:"isPrescription"`
	IsNarcotic     bool           `gorm:"default:false" json:"isNarcotic"`
	RetailPrice    float64        `gorm:"type:decimal(10,2)" json:"retailPrice"`
	Manufacturer   string         `gorm:"size:200" json:"manufacturer"`
	ApprovalNo     string         `gorm:"size:100" json:"approvalNo"`
	CreatedAt      time.Time      `json:"createdAt"`
	UpdatedAt      time.Time      `json:"updatedAt"`
	DeletedAt      gorm.DeletedAt `gorm:"index" json:"-"`
}

func (Drug) TableName() string {
	return "drugs"
}

// DrugInventory 药品库存
type DrugInventory struct {
	ID             int64      `gorm:"primaryKey" json:"id"`
	DrugID         int64      `gorm:"index;not null" json:"drugId"`
	BatchNo        string     `gorm:"size:50" json:"batchNo"`
	Quantity       int        `gorm:"not null" json:"quantity"`
	Unit           string     `gorm:"size:20" json:"unit"`
	PurchasePrice  float64    `gorm:"type:decimal(10,2)" json:"purchasePrice"`
	ProductionDate *time.Time `gorm:"type:date" json:"productionDate"`
	ExpiryDate     time.Time  `gorm:"type:date;not null" json:"expiryDate"`
	Supplier       string     `gorm:"size:200" json:"supplier"`
	Status         string     `gorm:"size:20" json:"status"`
	Version        int        `gorm:"default:0" json:"version"`
	CreatedAt      time.Time  `json:"createdAt"`
	UpdatedAt      time.Time  `json:"updatedAt"`
}

func (DrugInventory) TableName() string {
	return "drug_inventory"
}

// DispenseRecord 发药记录
type DispenseRecord struct {
	ID             int64      `gorm:"primaryKey" json:"id"`
	PrescriptionID int64      `gorm:"index" json:"prescriptionId"`
	PatientID      int64      `gorm:"index;not null" json:"patientId"`
	DrugID         int64      `gorm:"index;not null" json:"drugId"`
	InventoryID    int64      `gorm:"index" json:"inventoryId"`
	Quantity       int        `gorm:"not null" json:"quantity"`
	DispenserID    int64      `json:"dispenserId"`
	DispensedAt    *time.Time `json:"dispensedAt"`
	CreatedAt      time.Time  `json:"createdAt"`
}

func (DispenseRecord) TableName() string {
	return "dispense_records"
}
