package handler

import (
	"context"

	"github.com/his-mixed/go/internal/statistics/service"
	pb "github.com/his-mixed/go/pkg/grpc/statistics"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
)

// StatisticsHandler gRPC handler
type StatisticsHandler struct {
	pb.UnimplementedStatisticsServiceServer
	svc *service.StatisticsService
}

// NewStatisticsHandler 创建gRPC handler
func NewStatisticsHandler(svc *service.StatisticsService) *StatisticsHandler {
	return &StatisticsHandler{svc: svc}
}

// GetDashboard 获取仪表盘数据
func (h *StatisticsHandler) GetDashboard(ctx context.Context, req *pb.GetDashboardRequest) (*pb.GetDashboardResponse, error) {
	if req.Period == "" {
		return nil, status.Error(codes.InvalidArgument, "统计周期不能为空")
	}

	stats, err := h.svc.GetDashboardStats(req.Period, req.DepartmentId)
	if err != nil {
		return nil, status.Errorf(codes.Internal, "获取仪表盘数据失败: %v", err)
	}

	// 转换科室统计
	var deptStats []*pb.DepartmentStat
	if deptList, ok := stats["dept_stats"].([]map[string]interface{}); ok {
		for _, dept := range deptList {
			deptStat := &pb.DepartmentStat{
				DepartmentId:   dept["department_id"].(int64),
				DepartmentName: dept["department_name"].(string),
				VisitCount:     dept["visit_count"].(int64),
				Revenue:        dept["revenue"].(float64),
			}
			deptStats = append(deptStats, deptStat)
		}
	}

	return &pb.GetDashboardResponse{
		TotalRegistrations: stats["total_registrations"].(int64),
		TotalOutpatients:   stats["total_outpatients"].(int64),
		TotalInpatients:    stats["total_inpatients"].(int64),
		TotalPrescriptions: stats["total_prescriptions"].(int64),
		TotalRevenue:       stats["total_revenue"].(float64),
		DeptStats:          deptStats,
	}, nil
}

// GetTrend 获取趋势数据
func (h *StatisticsHandler) GetTrend(ctx context.Context, req *pb.GetTrendRequest) (*pb.GetTrendResponse, error) {
	if req.Metric == "" || req.StartDate == "" || req.EndDate == "" {
		return nil, status.Error(codes.InvalidArgument, "指标、开始日期和结束日期不能为空")
	}

	data, err := h.svc.GetTrendData(req.Metric, req.StartDate, req.EndDate, req.Granularity, req.DepartmentId)
	if err != nil {
		return nil, status.Errorf(codes.Internal, "获取趋势数据失败: %v", err)
	}

	var points []*pb.TrendPoint
	for _, d := range data {
		point := &pb.TrendPoint{
			Date:  d["date"].(string),
			Value: d["value"].(float64),
		}
		points = append(points, point)
	}

	return &pb.GetTrendResponse{
		Metric: req.Metric,
		Points: points,
	}, nil
}
