package repository

import (
	"github.com/his-mixed/go/internal/statistics/model"
	"gorm.io/gorm"
)

// StatisticsRepository 统计数据访问层
type StatisticsRepository struct {
	db *gorm.DB
}

// NewStatisticsRepository 创建统计仓储
func NewStatisticsRepository(db *gorm.DB) *StatisticsRepository {
	return &StatisticsRepository{db: db}
}

// CreateSnapshot 创建统计快照
func (r *StatisticsRepository) CreateSnapshot(snapshot *model.StatSnapshot) error {
	return r.db.Create(snapshot).Error
}

// GetSnapshot 查询统计快照
func (r *StatisticsRepository) GetSnapshot(statType, statDate string) (*model.StatSnapshot, error) {
	var snapshot model.StatSnapshot
	err := r.db.Where("stat_type = ? AND stat_date = ?", statType, statDate).First(&snapshot).Error
	if err != nil {
		return nil, err
	}
	return &snapshot, nil
}

// ListSnapshots 查询统计快照列表
func (r *StatisticsRepository) ListSnapshots(statType string, startDate, endDate string) ([]model.StatSnapshot, error) {
	var snapshots []model.StatSnapshot
	query := r.db.Where("stat_type = ?", statType)
	if startDate != "" {
		query = query.Where("stat_date >= ?", startDate)
	}
	if endDate != "" {
		query = query.Where("stat_date <= ?", endDate)
	}
	err := query.Order("stat_date DESC").Find(&snapshots).Error
	return snapshots, err
}

// GetRegistrationStats 挂号统计
func (r *StatisticsRepository) GetRegistrationStats(startDate, endDate string) (map[string]interface{}, error) {
	// 简化处理，返回空统计
	return map[string]interface{}{
		"total":     0,
		"completed": 0,
		"cancelled": 0,
	}, nil
}

// GetPharmacyStats 药房统计
func (r *StatisticsRepository) GetPharmacyStats(startDate, endDate string) (map[string]interface{}, error) {
	// 简化处理，返回空统计
	return map[string]interface{}{
		"total_drugs":     0,
		"total_dispenses": 0,
		"total_amount":    0,
	}, nil
}

// GetClinicStats 门诊统计
func (r *StatisticsRepository) GetClinicStats(startDate, endDate string) (map[string]interface{}, error) {
	// 简化处理，返回空统计
	return map[string]interface{}{
		"total_visits":    0,
		"total_patients":  0,
		"avg_wait_time":   0,
	}, nil
}
