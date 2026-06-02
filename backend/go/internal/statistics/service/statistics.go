package service

import (
	"fmt"
	"time"

	"github.com/his-mixed/go/internal/statistics/model"
	"github.com/his-mixed/go/internal/statistics/repository"
)

// StatisticsService 统计报表业务服务
type StatisticsService struct {
	repo *repository.StatisticsRepository
}

// NewStatisticsService 创建统计服务
func NewStatisticsService(repo *repository.StatisticsRepository) *StatisticsService {
	return &StatisticsService{repo: repo}
}

// GenerateSnapshot 生成统计快照
func (s *StatisticsService) GenerateSnapshot(statType, statDate string) error {
	// 获取统计数据
	var data map[string]interface{}
	var err error

	switch statType {
	case "REGISTRATION":
		data, err = s.repo.GetRegistrationStats(statDate, statDate)
	case "PHARMACY":
		data, err = s.repo.GetPharmacyStats(statDate, statDate)
	case "CLINIC":
		data, err = s.repo.GetClinicStats(statDate, statDate)
	default:
		data = make(map[string]interface{})
	}

	if err != nil {
		return err
	}

	// 解析日期
	parsedDate, err := time.Parse("2006-01-02", statDate)
	if err != nil {
		return err
	}

	// 创建快照
	snapshot := &model.StatSnapshot{
		StatType:  statType,
		StatDate:  parsedDate,
		StatData:  fmt.Sprintf("%v", data), // 简化处理
		CreatedAt: time.Now(),
	}

	return s.repo.CreateSnapshot(snapshot)
}

// GetSnapshot 查询统计快照
func (s *StatisticsService) GetSnapshot(statType, statDate string) (*model.StatSnapshot, error) {
	return s.repo.GetSnapshot(statType, statDate)
}

// ListSnapshots 查询统计快照列表
func (s *StatisticsService) ListSnapshots(statType string, startDate, endDate string) ([]model.StatSnapshot, error) {
	return s.repo.ListSnapshots(statType, startDate, endDate)
}

// GetRegistrationStats 挂号统计
func (s *StatisticsService) GetRegistrationStats(startDate, endDate string) (map[string]interface{}, error) {
	return s.repo.GetRegistrationStats(startDate, endDate)
}

// GetPharmacyStats 药房统计
func (s *StatisticsService) GetPharmacyStats(startDate, endDate string) (map[string]interface{}, error) {
	return s.repo.GetPharmacyStats(startDate, endDate)
}

// GetClinicStats 门诊统计
func (s *StatisticsService) GetClinicStats(startDate, endDate string) (map[string]interface{}, error) {
	return s.repo.GetClinicStats(startDate, endDate)
}

// GetDashboardStats 获取仪表盘统计数据
func (s *StatisticsService) GetDashboardStats(period string, departmentID int64) (map[string]interface{}, error) {
	return s.repo.GetDashboardStats(period, departmentID)
}

// GetTrendData 获取趋势数据
func (s *StatisticsService) GetTrendData(metric, startDate, endDate, granularity string, departmentID int64) ([]map[string]interface{}, error) {
	return s.repo.GetTrendData(metric, startDate, endDate, granularity, departmentID)
}

