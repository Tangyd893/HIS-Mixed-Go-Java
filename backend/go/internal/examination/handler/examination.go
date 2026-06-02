package handler

import (
	"context"
	"fmt"
	"time"

	"github.com/his-mixed/go/internal/examination/model"
	"github.com/his-mixed/go/internal/examination/service"
	pb "github.com/his-mixed/go/pkg/grpc/examination"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/timestamppb"
)

// ExaminationHandler gRPC handler
type ExaminationHandler struct {
	pb.UnimplementedExaminationServiceServer
	svc *service.ExaminationService
}

// NewExaminationHandler 创建gRPC handler
func NewExaminationHandler(svc *service.ExaminationService) *ExaminationHandler {
	return &ExaminationHandler{svc: svc}
}

// CreateReport 创建检查报告
func (h *ExaminationHandler) CreateReport(ctx context.Context, req *pb.CreateReportRequest) (*pb.CreateReportResponse, error) {
	if req.OrderId == 0 || req.PatientId == 0 {
		return nil, status.Error(codes.InvalidArgument, "医嘱ID和患者ID不能为空")
	}

	report := &model.ExamReport{
		RequestID:  req.OrderId,
		Findings:   req.Findings,
		Impression: req.Impression,
		Status:     "DRAFT",
		CreatedAt:  time.Now(),
		UpdatedAt:  time.Now(),
	}

	if err := h.svc.CreateExamReport(report); err != nil {
		return nil, status.Errorf(codes.Internal, "创建检查报告失败: %v", err)
	}

	return &pb.CreateReportResponse{
		ReportId: report.ID,
		ReportNo: fmt.Sprintf("RPT%06d", report.ID),
		Status:   report.Status,
	}, nil
}

// GetReport 获取检查报告
func (h *ExaminationHandler) GetReport(ctx context.Context, req *pb.GetReportRequest) (*pb.GetReportResponse, error) {
	if req.ReportId == 0 && req.OrderId == 0 {
		return nil, status.Error(codes.InvalidArgument, "报告ID或医嘱ID不能为空")
	}

	var report *model.ExamReport
	var err error

	if req.ReportId != 0 {
		report, err = h.svc.GetExamReportByID(req.ReportId)
	} else {
		report, err = h.svc.GetExamReportByRequestID(req.OrderId)
	}

	if err != nil {
		return nil, status.Errorf(codes.NotFound, "检查报告不存在: %v", err)
	}

	return &pb.GetReportResponse{
		Report: &pb.ExaminationReport{
			ReportId:   report.ID,
			Findings:   report.Findings,
			Impression: report.Impression,
			Conclusion: report.Conclusion,
			Status:     report.Status,
			ReportTime: timestamppb.New(report.CreatedAt),
		},
	}, nil
}
