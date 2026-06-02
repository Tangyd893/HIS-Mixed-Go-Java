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

	// 获取统计数据
	registrationStats, err := h.svc.GetRegistrationStats("", "")
	if err != nil {
		return nil, status.Errorf(codes.Internal, "获取挂号统计失败: %v", err)
	}

	clinicStats, err := h.svc.GetClinicStats("", "")
	if err != nil {
		return nil, status.Errorf(codes.Internal, "获取门诊统计失败: %v", err)
	}

	pharmacyStats, err := h.svc.GetPharmacyStats("", "")
	if err != nil {
		return nil, status.Errorf(codes.Internal, "获取药房统计失败: %v", err)
	}

	return &pb.GetDashboardResponse{
		TotalRegistrations: int64(registrationStats["total"].(int)),
		TotalOutpatients:   int64(clinicStats["total_visits"].(int)),
		TotalPrescriptions: int64(pharmacyStats["total_dispenses"].(int)),
		TotalRevenue:       0, // 简化处理
		DeptStats:          []*pb.DepartmentStat{},
	}, nil
}

// GetTrend 获取趋势数据
func (h *StatisticsHandler) GetTrend(ctx context.Context, req *pb.GetTrendRequest) (*pb.GetTrendResponse, error) {
	if req.Metric == "" || req.StartDate == "" || req.EndDate == "" {
		return nil, status.Error(codes.InvalidArgument, "指标、开始日期和结束日期不能为空")
	}

	// 简化处理，返回空趋势数据
	return &pb.GetTrendResponse{
		Metric: req.Metric,
		Points: []*pb.TrendPoint{},
	}, nil
}
