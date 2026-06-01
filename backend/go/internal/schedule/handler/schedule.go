package handler

import (
	"context"

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
	return nil, status.Error(codes.Unimplemented, "方法未实现")
}

func (h *ScheduleHandler) GetSlots(ctx context.Context, req *pb.GetSlotsRequest) (*pb.GetSlotsResponse, error) {
	return nil, status.Error(codes.Unimplemented, "方法未实现")
}
