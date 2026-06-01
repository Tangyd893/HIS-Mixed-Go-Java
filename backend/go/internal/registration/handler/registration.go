package handler

import (
	"context"

	"github.com/his-mixed/go/internal/registration/service"
	pb "github.com/his-mixed/go/pkg/grpc/registration"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
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
	return nil, status.Error(codes.Unimplemented, "方法未实现")
}

// GetSchedules 查询号源
func (h *RegistrationHandler) GetSchedules(ctx context.Context, req *pb.GetSchedulesRequest) (*pb.GetSchedulesResponse, error) {
	return nil, status.Error(codes.Unimplemented, "方法未实现")
}
