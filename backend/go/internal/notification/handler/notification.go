package handler

import (
	"context"

	"github.com/his-mixed/go/internal/notification/service"
	pb "github.com/his-mixed/go/pkg/grpc/notification"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
)

// NotificationHandler gRPC handler
type NotificationHandler struct {
	pb.UnimplementedNotificationServiceServer
	svc *service.NotificationService
}

// NewNotificationHandler 创建gRPC handler
func NewNotificationHandler(svc *service.NotificationService) *NotificationHandler {
	return &NotificationHandler{svc: svc}
}

// SendMessage 发送消息
func (h *NotificationHandler) SendMessage(ctx context.Context, req *pb.SendMessageRequest) (*pb.SendMessageResponse, error) {
	if len(req.ReceiverIds) == 0 {
		return nil, status.Error(codes.InvalidArgument, "接收人ID列表不能为空")
	}

	var successCount int32
	var failCount int32

	for _, receiverID := range req.ReceiverIds {
		if req.TemplateCode != "" {
			// 使用模板发送
			err := h.svc.SendNotification(req.TemplateCode, req.Channel, "", req.Params)
			if err != nil {
				failCount++
			} else {
				successCount++
			}
		} else {
			// 直接发送站内信
			err := h.svc.SendSiteMessage(receiverID, req.Title, req.Content)
			if err != nil {
				failCount++
			} else {
				successCount++
			}
		}
	}

	return &pb.SendMessageResponse{
		MessageId:    0, // 简化处理
		SuccessCount: successCount,
		FailCount:    failCount,
	}, nil
}

// GetTemplates 获取消息模板
func (h *NotificationHandler) GetTemplates(ctx context.Context, req *pb.GetTemplatesRequest) (*pb.GetTemplatesResponse, error) {
	page := 1
	size := 20
	if req.Page != nil {
		if req.Page.Page > 0 {
			page = int(req.Page.Page)
		}
		if req.Page.PageSize > 0 {
			size = int(req.Page.PageSize)
		}
	}

	templates, total, err := h.svc.ListTemplates(req.Channel, page, size)
	if err != nil {
		return nil, status.Errorf(codes.Internal, "获取模板列表失败: %v", err)
	}

	var pbTemplates []*pb.MessageTemplate
	for _, t := range templates {
		pbTemplates = append(pbTemplates, &pb.MessageTemplate{
			TemplateId:      t.ID,
			TemplateCode:    t.TemplateCode,
			TemplateName:    t.TemplateName,
			Channel:         t.Channel,
			TitleTemplate:   t.Title,
			ContentTemplate: t.Content,
			Status:          "ACTIVE",
		})
	}

	return &pb.GetTemplatesResponse{
		Templates: pbTemplates,
		Page: &pb.PageResponse{
			Total: total,
		},
	}, nil
}
