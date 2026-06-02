package handler

import (
	"context"
	"time"

	"github.com/his-mixed/go/internal/pharmacy/service"
	pb "github.com/his-mixed/go/pkg/grpc/pharmacy"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/timestamppb"
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
	if len(req.Items) == 0 {
		return nil, status.Error(codes.InvalidArgument, "校验列表不能为空")
	}

	var results []*pb.StockResult
	allSufficient := true

	for _, item := range req.Items {
		// 查询药品信息
		drug, err := h.svc.GetDrugByID(item.DrugId)
		if err != nil {
			return nil, status.Errorf(codes.NotFound, "药品不存在: %d", item.DrugId)
		}

		// 查询库存
		inventory, err := h.svc.GetDrugInventory(item.DrugId)
		if err != nil {
			return nil, status.Errorf(codes.Internal, "查询库存失败: %v", err)
		}

		available := 0
		for _, inv := range inventory {
			available += inv.Quantity
		}

		enough := available >= int(item.Quantity)
		if !enough {
			allSufficient = false
		}

		results = append(results, &pb.StockResult{
			DrugId:    item.DrugId,
			DrugName:  drug.DrugName,
			Available: int32(available),
			Required:  item.Quantity,
			Enough:    enough,
		})
	}

	return &pb.CheckStockResponse{
		Sufficient: allSufficient,
		Results:    results,
	}, nil
}

// DispenseDrug 发药
func (h *PharmacyHandler) DispenseDrug(ctx context.Context, req *pb.DispenseDrugRequest) (*pb.DispenseDrugResponse, error) {
	if req.PrescriptionId == 0 || req.PatientId == 0 {
		return nil, status.Error(codes.InvalidArgument, "处方ID和患者ID不能为空")
	}

	if len(req.Items) == 0 {
		return nil, status.Error(codes.InvalidArgument, "发药明细不能为空")
	}

	// 逐项发药
	for _, item := range req.Items {
		err := h.svc.DispenseDrug(
			req.PrescriptionId,
			item.DrugId,
			req.PatientId,
			req.Operator.UserId,
			int(item.Quantity),
		)
		if err != nil {
			return nil, status.Errorf(codes.Internal, "发药失败: %v", err)
		}
	}

	return &pb.DispenseDrugResponse{
		DispenseId:  req.PrescriptionId, // 简化处理，实际应该生成发药记录ID
		Status:      "DISPENSED",
		DispensedAt: timestamppb.New(time.Now()),
	}, nil
}
