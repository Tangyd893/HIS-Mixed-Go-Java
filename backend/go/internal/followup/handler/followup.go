package handler

import (
	"context"
	"time"

	"github.com/his-mixed/go/internal/followup/model"
	"github.com/his-mixed/go/internal/followup/service"
	pb "github.com/his-mixed/go/pkg/grpc/followup"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
)

// FollowupHandler gRPC handler
type FollowupHandler struct {
	pb.UnimplementedFollowupServiceServer
	svc *service.FollowupService
}

// NewFollowupHandler 创建gRPC handler
func NewFollowupHandler(svc *service.FollowupService) *FollowupHandler {
	return &FollowupHandler{svc: svc}
}

// CreatePlan 创建随访计划
func (h *FollowupHandler) CreatePlan(ctx context.Context, req *pb.CreatePlanRequest) (*pb.CreatePlanResponse, error) {
	if req.PatientId == 0 {
		return nil, status.Error(codes.InvalidArgument, "患者ID不能为空")
	}

	startDate, err := time.Parse("2006-01-02", req.StartDate)
	if err != nil {
		return nil, status.Errorf(codes.InvalidArgument, "开始日期格式错误: %v", err)
	}

	endDate, err := time.Parse("2006-01-02", req.EndDate)
	if err != nil {
		return nil, status.Errorf(codes.InvalidArgument, "结束日期格式错误: %v", err)
	}

	plan := &model.FollowupPlan{
		PatientID:    req.PatientId,
		FollowupType: req.FollowupType,
		StartDate:    startDate,
		EndDate:      &endDate,
		Status:       "PENDING",
		CreatedAt:    time.Now(),
		UpdatedAt:    time.Now(),
	}

	if err := h.svc.CreateFollowupPlan(plan); err != nil {
		return nil, status.Errorf(codes.Internal, "创建随访计划失败: %v", err)
	}

	return &pb.CreatePlanResponse{
		PlanId: plan.ID,
		Status: plan.Status,
	}, nil
}

// AddRecord 添加随访记录
func (h *FollowupHandler) AddRecord(ctx context.Context, req *pb.AddRecordRequest) (*pb.AddRecordResponse, error) {
	if req.PlanId == 0 || req.PatientId == 0 {
		return nil, status.Error(codes.InvalidArgument, "计划ID和患者ID不能为空")
	}

	record := &model.FollowupRecord{
		PlanID:           req.PlanId,
		PatientID:        req.PatientId,
		FollowupMethod:   req.RecordType,
		Content:          req.Content,
		PatientCondition: req.PatientCondition,
		Advice:           req.Advice,
		CreatedAt:        time.Now(),
	}

	if req.FollowupTime != nil {
		record.FollowupDate = req.FollowupTime.AsTime()
	}

	if err := h.svc.CreateFollowupRecord(record); err != nil {
		return nil, status.Errorf(codes.Internal, "添加随访记录失败: %v", err)
	}

	return &pb.AddRecordResponse{
		RecordId: record.ID,
		Status:   "RECORDED",
	}, nil
}
