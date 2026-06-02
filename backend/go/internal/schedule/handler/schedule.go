package handler

import (
	"context"
	"time"

	"github.com/his-mixed/go/internal/schedule/model"
	"github.com/his-mixed/go/internal/schedule/service"
	pb "github.com/his-mixed/go/pkg/grpc/schedule"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
)

type ScheduleHandler struct {
	pb.UnimplementedScheduleServiceServer
	svc *service.ScheduleService
}

func NewScheduleHandler(svc *service.ScheduleService) *ScheduleHandler {
	return &ScheduleHandler{svc: svc}
}

func (h *ScheduleHandler) GenerateSlots(ctx context.Context, req *pb.GenerateSlotsRequest) (*pb.GenerateSlotsResponse, error) {
	if req.DoctorId == 0 || req.DepartmentId == 0 {
		return nil, status.Error(codes.InvalidArgument, "医生ID和科室ID不能为空")
	}

	startDate, err := time.Parse("2006-01-02", req.StartDate)
	if err != nil {
		return nil, status.Errorf(codes.InvalidArgument, "开始日期格式错误: %v", err)
	}

	endDate, err := time.Parse("2006-01-02", req.EndDate)
	if err != nil {
		return nil, status.Errorf(codes.InvalidArgument, "结束日期格式错误: %v", err)
	}

	var generatedCount int32
	var dates []string

	for d := startDate; !d.After(endDate); d = d.AddDate(0, 0, 1) {
		dateStr := d.Format("2006-01-02")

		// 创建排班计划
		plan := &model.SchedulePlan{
			DoctorID:     req.DoctorId,
			DepartmentID: req.DepartmentId,
			Title:        "排班计划",
			PlanType:     req.SlotRule,
			StartDate:    d,
			EndDate:      d,
			IsActive:     true,
			CreatedAt:    time.Now(),
			UpdatedAt:    time.Now(),
		}

		if err := h.svc.CreateSchedulePlan(plan); err != nil {
			return nil, status.Errorf(codes.Internal, "创建排班计划失败: %v", err)
		}

		// 创建排班时段
		slot := &model.ScheduleSlot{
			DoctorID:     req.DoctorId,
			DepartmentID: req.DepartmentId,
			PlanID:       plan.ID,
			ScheduleDate: d,
			StartTime:    "08:00",
			EndTime:      "12:00",
			TotalQuota:   int(req.MaxPerSlot),
			Remaining:    int(req.MaxPerSlot),
			IsActive:     true,
			CreatedAt:    time.Now(),
			UpdatedAt:    time.Now(),
		}

		if err := h.svc.CreateScheduleSlot(slot); err != nil {
			return nil, status.Errorf(codes.Internal, "创建排班时段失败: %v", err)
		}

		generatedCount++
		dates = append(dates, dateStr)
	}

	return &pb.GenerateSlotsResponse{
		GeneratedCount: generatedCount,
		Dates:          dates,
	}, nil
}

func (h *ScheduleHandler) GetSlots(ctx context.Context, req *pb.GetSlotsRequest) (*pb.GetSlotsResponse, error) {
	if req.DepartmentId == 0 {
		return nil, status.Error(codes.InvalidArgument, "科室ID不能为空")
	}

	slots, err := h.svc.GetScheduleSlots(req.DepartmentId, req.Date)
	if err != nil {
		return nil, status.Errorf(codes.Internal, "查询排班时段失败: %v", err)
	}

	var slotInfos []*pb.SlotInfo
	for _, slot := range slots {
		slotInfos = append(slotInfos, &pb.SlotInfo{
			SlotId:        slot.ID,
			DoctorId:      slot.DoctorID,
			DepartmentId:  slot.DepartmentID,
			Date:          slot.ScheduleDate.Format("2006-01-02"),
			TimeSlot:      slot.StartTime + "-" + slot.EndTime,
			MaxCount:      int32(slot.TotalQuota),
			BookedCount:   int32(slot.TotalQuota - slot.Remaining),
			Status:        "ACTIVE",
		})
	}

	return &pb.GetSlotsResponse{
		Slots: slotInfos,
	}, nil
}
