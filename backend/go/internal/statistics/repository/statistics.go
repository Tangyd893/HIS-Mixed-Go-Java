package repository

import (
	"time"

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
	result := map[string]interface{}{
		"total":     0,
		"completed": 0,
		"cancelled": 0,
	}

	query := r.db.Table("registrations")
	if startDate != "" {
		query = query.Where("created_at >= ?", startDate)
	}
	if endDate != "" {
		query = query.Where("created_at <= ?", endDate+" 23:59:59")
	}

	var total int64
	query.Count(&total)
	result["total"] = total

	var completed int64
	query.Where("status = ?", "COMPLETED").Count(&completed)
	result["completed"] = completed

	var cancelled int64
	query.Where("status = ?", "CANCELLED").Count(&cancelled)
	result["cancelled"] = cancelled

	return result, nil
}

// GetPharmacyStats 药房统计
func (r *StatisticsRepository) GetPharmacyStats(startDate, endDate string) (map[string]interface{}, error) {
	result := map[string]interface{}{
		"total_drugs":     0,
		"total_dispenses": 0,
		"total_amount":    0.0,
	}

	// 药品总数
	var totalDrugs int64
	r.db.Table("drugs").Count(&totalDrugs)
	result["total_drugs"] = totalDrugs

	// 发药统计
	query := r.db.Table("prescriptions")
	if startDate != "" {
		query = query.Where("created_at >= ?", startDate)
	}
	if endDate != "" {
		query = query.Where("created_at <= ?", endDate+" 23:59:59")
	}

	var totalDispenses int64
	query.Count(&totalDispenses)
	result["total_dispenses"] = totalDispenses

	var totalAmount float64
	query.Select("COALESCE(SUM(total_amount), 0)").Scan(&totalAmount)
	result["total_amount"] = totalAmount

	return result, nil
}

// GetClinicStats 门诊统计
func (r *StatisticsRepository) GetClinicStats(startDate, endDate string) (map[string]interface{}, error) {
	result := map[string]interface{}{
		"total_visits":   0,
		"total_patients": 0,
		"avg_wait_time":  0.0,
	}

	query := r.db.Table("encounters")
	if startDate != "" {
		query = query.Where("created_at >= ?", startDate)
	}
	if endDate != "" {
		query = query.Where("created_at <= ?", endDate+" 23:59:59")
	}

	var totalVisits int64
	query.Count(&totalVisits)
	result["total_visits"] = totalVisits

	var totalPatients int64
	query.Distinct("patient_id").Count(&totalPatients)
	result["total_patients"] = totalPatients

	return result, nil
}

// GetDashboardStats 获取仪表盘统计数据
func (r *StatisticsRepository) GetDashboardStats(period string, departmentID int64) (map[string]interface{}, error) {
	startDate, endDate := calculatePeriodRange(period)

	result := map[string]interface{}{
		"total_registrations": 0,
		"total_outpatients":   0,
		"total_inpatients":    0,
		"total_prescriptions": 0,
		"total_revenue":       0.0,
		"dept_stats":          []map[string]interface{}{},
	}

	// 挂号总量
	var totalRegistrations int64
	regQuery := r.db.Table("registrations").Where("created_at BETWEEN ? AND ?", startDate, endDate)
	if departmentID > 0 {
		regQuery = regQuery.Where("department_id = ?", departmentID)
	}
	regQuery.Count(&totalRegistrations)
	result["total_registrations"] = totalRegistrations

	// 门诊量
	var totalOutpatients int64
	encQuery := r.db.Table("encounters").Where("created_at BETWEEN ? AND ?", startDate, endDate)
	if departmentID > 0 {
		encQuery = encQuery.Where("department_id = ?", departmentID)
	}
	encQuery.Count(&totalOutpatients)
	result["total_outpatients"] = totalOutpatients

	// 处方量
	var totalPrescriptions int64
	rxQuery := r.db.Table("prescriptions").Where("created_at BETWEEN ? AND ?", startDate, endDate)
	rxQuery.Count(&totalPrescriptions)
	result["total_prescriptions"] = totalPrescriptions

	// 总收入
	var totalRevenue float64
	r.db.Table("payments").Where("created_at BETWEEN ? AND ?", startDate, endDate).
		Select("COALESCE(SUM(amount), 0)").Scan(&totalRevenue)
	result["total_revenue"] = totalRevenue

	return result, nil
}

// GetTrendData 获取趋势数据
func (r *StatisticsRepository) GetTrendData(metric, startDate, endDate, granularity string, departmentID int64) ([]map[string]interface{}, error) {
	var results []map[string]interface{}

	switch metric {
	case "门诊量":
		var data []struct {
			Date  string
			Count int64
		}
		query := r.db.Table("encounters").
			Select("DATE(created_at) as date, COUNT(*) as count").
			Where("created_at BETWEEN ? AND ?", startDate, endDate+" 23:59:59")
		if departmentID > 0 {
			query = query.Where("department_id = ?", departmentID)
		}
		query.Group("DATE(created_at)").Order("date").Scan(&data)

		for _, d := range data {
			results = append(results, map[string]interface{}{
				"date":  d.Date,
				"value": d.Count,
			})
		}

	case "收入":
		var data []struct {
			Date   string
			Amount float64
		}
		r.db.Table("payments").
			Select("DATE(created_at) as date, COALESCE(SUM(amount), 0) as amount").
			Where("created_at BETWEEN ? AND ?", startDate, endDate+" 23:59:59").
			Group("DATE(created_at)").Order("date").Scan(&data)

		for _, d := range data {
			results = append(results, map[string]interface{}{
				"date":  d.Date,
				"value": d.Amount,
			})
		}

	case "处方量":
		var data []struct {
			Date  string
			Count int64
		}
		r.db.Table("prescriptions").
			Select("DATE(created_at) as date, COUNT(*) as count").
			Where("created_at BETWEEN ? AND ?", startDate, endDate+" 23:59:59").
			Group("DATE(created_at)").Order("date").Scan(&data)

		for _, d := range data {
			results = append(results, map[string]interface{}{
				"date":  d.Date,
				"value": d.Count,
			})
		}
	}

	return results, nil
}

// calculatePeriodRange 计算周期范围
func calculatePeriodRange(period string) (string, string) {
	now := time.Now()
	var startDate, endDate string

	switch period {
	case "今天":
		startDate = now.Format("2006-01-02")
		endDate = now.Format("2006-01-02") + " 23:59:59"
	case "本周":
		weekday := int(now.Weekday())
		if weekday == 0 {
			weekday = 7
		}
		startDate = now.AddDate(0, 0, -weekday+1).Format("2006-01-02")
		endDate = now.Format("2006-01-02") + " 23:59:59"
	case "本月":
		startDate = now.Format("2006-01") + "-01"
		endDate = now.Format("2006-01-02") + " 23:59:59"
	default:
		startDate = now.Format("2006-01-02")
		endDate = now.Format("2006-01-02") + " 23:59:59"
	}

	return startDate, endDate
}
