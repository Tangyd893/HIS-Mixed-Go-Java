package handler

import (
	"context"

	"github.com/his-mixed/go/internal/pharmacy/service"
	pb "github.com/his-mixed/go/pkg/grpc/pharmacy"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
)

// PharmacyHandler gRPC handler
type PharmacyHandler struct {
	pb.UnimplementedPharmacyServiceServer
	svc *service.PharmacyService
}

// NewPharmacyHandler 创建gRPC handler
func NewPharmacyHandler(svc *service.PharmacyService) *PharmacyHandler {
	return &PharmacyHandler{svc: svc}
}

// CheckStock 校验库存
func (h *PharmacyHandler) CheckStock(ctx context.Context, req *pb.CheckStockRequest) (*pb.CheckStockResponse, error) {
	return nil, status.Error(codes.Unimplemented, "方法未实现")
}

// DispenseDrug 发药
func (h *PharmacyHandler) DispenseDrug(ctx context.Context, req *pb.DispenseDrugRequest) (*pb.DispenseDrugResponse, error) {
	return nil, status.Error(codes.Unimplemented, "方法未实现")
}
