package handler

import (
	"context"
	"time"

	"github.com/his-mixed/go/internal/outpatient/model"
	"github.com/his-mixed/go/internal/outpatient/service"
	pb "github.com/his-mixed/go/pkg/grpc/outpatient"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/timestamppb"
)

// OutpatientHandler gRPC handler
type OutpatientHandler struct {
	pb.UnimplementedOutpatientServiceServer
	svc *service.OutpatientService
}

// NewOutpatientHandler 创建gRPC handler
func NewOutpatientHandler(svc *service.OutpatientService) *OutpatientHandler {
	return &OutpatientHandler{svc: svc}
}

// CreateConsultation 创建远程会诊
func (h *OutpatientHandler) CreateConsultation(ctx context.Context, req *pb.CreateConsultationRequest) (*pb.CreateConsultationResponse, error) {
	if req.PatientId == 0 {
		return nil, status.Error(codes.InvalidArgument, "患者ID不能为空")
	}

	consultation := &model.Consultation{
		PatientID:    req.PatientId,
		DepartmentID: &req.ConsultDeptId,
		Complaint:    req.Description,
		Status:       "PENDING",
		CreatedAt:    time.Now(),
		UpdatedAt:    time.Now(),
	}

	if err := h.svc.CreateConsultation(consultation); err != nil {
		return nil, status.Errorf(codes.Internal, "创建会诊失败: %v", err)
	}

	return &pb.CreateConsultationResponse{
		ConsultationId: consultation.ID,
		Status:         consultation.Status,
		CreatedAt:      timestamppb.New(consultation.CreatedAt),
	}, nil
}

// GetMessages 获取消息列表
func (h *OutpatientHandler) GetMessages(ctx context.Context, req *pb.GetMessagesRequest) (*pb.GetMessagesResponse, error) {
	if req.ConsultationId == 0 {
		return nil, status.Error(codes.InvalidArgument, "会诊ID不能为空")
	}

	msgs, err := h.svc.GetMessages(req.ConsultationId)
	if err != nil {
		return nil, status.Errorf(codes.Internal, "获取消息列表失败: %v", err)
	}

	var pbMsgs []*pb.ConsultationMessage
	for _, msg := range msgs {
		pbMsgs = append(pbMsgs, &pb.ConsultationMessage{
			MessageId:       msg.ID,
			ConsultationId:  msg.ConsultationID,
			SenderId:        msg.SenderID,
			Content:         msg.Content,
			MessageType:     msg.MessageType,
			SentAt:          timestamppb.New(msg.CreatedAt),
		})
	}

	return &pb.GetMessagesResponse{
		Messages: pbMsgs,
	}, nil
}
