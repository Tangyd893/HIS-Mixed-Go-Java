package handler

import (
	"context"
	"fmt"
	"time"

	"github.com/his-mixed/go/internal/registration/model"
	"github.com/his-mixed/go/internal/registration/service"
	pb "github.com/his-mixed/go/pkg/grpc/registration"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/timestamppb"
)

// RegistrationHandler gRPC handler
type RegistrationHandler struct {
	pb.UnimplementedRegistrationServiceServer
	svc *service.RegistrationService
}

// NewRegistrationHandler 创建gRPC handler
func NewRegistrationHandler(svc *service.RegistrationService) *RegistrationHandler {
	return &RegistrationHandler{svc: svc}
}

// RegisterAppointment 预约挂号
func (h *RegistrationHandler) RegisterAppointment(ctx context.Context, req *pb.RegisterRequest) (*pb.RegisterResponse, error) {
	if req.PatientId == 0 || req.ScheduleId == 0 {
		return nil, status.Error(codes.InvalidArgument, "患者ID和排班ID不能为空")
	}

	// 解析就诊日期
	visitDate, err := time.Parse("2006-01-02", req.VisitDate)
	if err != nil {
		return nil, status.Errorf(codes.InvalidArgument, "日期格式错误: %v", err)
	}

	// 创建挂号记录
	reg := &model.Registration{
		PatientID:        req.PatientId,
		ScheduleID:       req.ScheduleId,
		DoctorID:         req.DoctorId,
		RegistrationType: req.CardType,
		Status:           "PENDING",
		Symptom:          req.Complaint,
		RegisterDate:     visitDate,
		CreatedAt:        time.Now(),
		UpdatedAt:        time.Now(),
	}

	if err := h.svc.CreateRegistration(reg); err != nil {
		return nil, status.Errorf(codes.Internal, "创建挂号记录失败: %v", err)
	}

	return &pb.RegisterResponse{
		AppointmentId: reg.ID,
		SerialNumber:  fmt.Sprintf("REG%06d", reg.ID),
		Status:        reg.Status,
		CreatedAt:     timestamppb.New(reg.CreatedAt),
	}, nil
}

// GetSchedules 查询号源
func (h *RegistrationHandler) GetSchedules(ctx context.Context, req *pb.GetSchedulesRequest) (*pb.GetSchedulesResponse, error) {
	// 这里需要调用Schedule服务获取号源信息
	// 暂时返回空列表，后续通过gRPC调用Schedule服务实现
	return &pb.GetSchedulesResponse{
		Schedules: []*pb.ScheduleInfo{},
	}, nil
}
