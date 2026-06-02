package com.hismixed.grpc.notification;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 通知服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: notification/notification.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NotificationServiceGrpc {

  private NotificationServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "notification.NotificationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.notification.Notification.SendMessageRequest,
      com.hismixed.grpc.notification.Notification.SendMessageResponse> getSendMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendMessage",
      requestType = com.hismixed.grpc.notification.Notification.SendMessageRequest.class,
      responseType = com.hismixed.grpc.notification.Notification.SendMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.notification.Notification.SendMessageRequest,
      com.hismixed.grpc.notification.Notification.SendMessageResponse> getSendMessageMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.notification.Notification.SendMessageRequest, com.hismixed.grpc.notification.Notification.SendMessageResponse> getSendMessageMethod;
    if ((getSendMessageMethod = NotificationServiceGrpc.getSendMessageMethod) == null) {
      synchronized (NotificationServiceGrpc.class) {
        if ((getSendMessageMethod = NotificationServiceGrpc.getSendMessageMethod) == null) {
          NotificationServiceGrpc.getSendMessageMethod = getSendMessageMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.notification.Notification.SendMessageRequest, com.hismixed.grpc.notification.Notification.SendMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.notification.Notification.SendMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.notification.Notification.SendMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NotificationServiceMethodDescriptorSupplier("SendMessage"))
              .build();
        }
      }
    }
    return getSendMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.notification.Notification.GetTemplatesRequest,
      com.hismixed.grpc.notification.Notification.GetTemplatesResponse> getGetTemplatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTemplates",
      requestType = com.hismixed.grpc.notification.Notification.GetTemplatesRequest.class,
      responseType = com.hismixed.grpc.notification.Notification.GetTemplatesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.notification.Notification.GetTemplatesRequest,
      com.hismixed.grpc.notification.Notification.GetTemplatesResponse> getGetTemplatesMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.notification.Notification.GetTemplatesRequest, com.hismixed.grpc.notification.Notification.GetTemplatesResponse> getGetTemplatesMethod;
    if ((getGetTemplatesMethod = NotificationServiceGrpc.getGetTemplatesMethod) == null) {
      synchronized (NotificationServiceGrpc.class) {
        if ((getGetTemplatesMethod = NotificationServiceGrpc.getGetTemplatesMethod) == null) {
          NotificationServiceGrpc.getGetTemplatesMethod = getGetTemplatesMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.notification.Notification.GetTemplatesRequest, com.hismixed.grpc.notification.Notification.GetTemplatesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTemplates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.notification.Notification.GetTemplatesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.notification.Notification.GetTemplatesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NotificationServiceMethodDescriptorSupplier("GetTemplates"))
              .build();
        }
      }
    }
    return getGetTemplatesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NotificationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NotificationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NotificationServiceStub>() {
        @java.lang.Override
        public NotificationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NotificationServiceStub(channel, callOptions);
        }
      };
    return NotificationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NotificationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NotificationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NotificationServiceBlockingStub>() {
        @java.lang.Override
        public NotificationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NotificationServiceBlockingStub(channel, callOptions);
        }
      };
    return NotificationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NotificationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NotificationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NotificationServiceFutureStub>() {
        @java.lang.Override
        public NotificationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NotificationServiceFutureStub(channel, callOptions);
        }
      };
    return NotificationServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 通知服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 发送消息
     * </pre>
     */
    default void sendMessage(com.hismixed.grpc.notification.Notification.SendMessageRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.notification.Notification.SendMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMessageMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取消息模板
     * </pre>
     */
    default void getTemplates(com.hismixed.grpc.notification.Notification.GetTemplatesRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.notification.Notification.GetTemplatesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTemplatesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service NotificationService.
   * <pre>
   * 通知服务
   * </pre>
   */
  public static abstract class NotificationServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return NotificationServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service NotificationService.
   * <pre>
   * 通知服务
   * </pre>
   */
  public static final class NotificationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<NotificationServiceStub> {
    private NotificationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotificationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NotificationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 发送消息
     * </pre>
     */
    public void sendMessage(com.hismixed.grpc.notification.Notification.SendMessageRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.notification.Notification.SendMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取消息模板
     * </pre>
     */
    public void getTemplates(com.hismixed.grpc.notification.Notification.GetTemplatesRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.notification.Notification.GetTemplatesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTemplatesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service NotificationService.
   * <pre>
   * 通知服务
   * </pre>
   */
  public static final class NotificationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<NotificationServiceBlockingStub> {
    private NotificationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotificationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NotificationServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 发送消息
     * </pre>
     */
    public com.hismixed.grpc.notification.Notification.SendMessageResponse sendMessage(com.hismixed.grpc.notification.Notification.SendMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMessageMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取消息模板
     * </pre>
     */
    public com.hismixed.grpc.notification.Notification.GetTemplatesResponse getTemplates(com.hismixed.grpc.notification.Notification.GetTemplatesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTemplatesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service NotificationService.
   * <pre>
   * 通知服务
   * </pre>
   */
  public static final class NotificationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<NotificationServiceFutureStub> {
    private NotificationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotificationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NotificationServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 发送消息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.notification.Notification.SendMessageResponse> sendMessage(
        com.hismixed.grpc.notification.Notification.SendMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取消息模板
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.notification.Notification.GetTemplatesResponse> getTemplates(
        com.hismixed.grpc.notification.Notification.GetTemplatesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTemplatesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_MESSAGE = 0;
  private static final int METHODID_GET_TEMPLATES = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_MESSAGE:
          serviceImpl.sendMessage((com.hismixed.grpc.notification.Notification.SendMessageRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.notification.Notification.SendMessageResponse>) responseObserver);
          break;
        case METHODID_GET_TEMPLATES:
          serviceImpl.getTemplates((com.hismixed.grpc.notification.Notification.GetTemplatesRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.notification.Notification.GetTemplatesResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSendMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.notification.Notification.SendMessageRequest,
              com.hismixed.grpc.notification.Notification.SendMessageResponse>(
                service, METHODID_SEND_MESSAGE)))
        .addMethod(
          getGetTemplatesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.notification.Notification.GetTemplatesRequest,
              com.hismixed.grpc.notification.Notification.GetTemplatesResponse>(
                service, METHODID_GET_TEMPLATES)))
        .build();
  }

  private static abstract class NotificationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NotificationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.notification.Notification.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NotificationService");
    }
  }

  private static final class NotificationServiceFileDescriptorSupplier
      extends NotificationServiceBaseDescriptorSupplier {
    NotificationServiceFileDescriptorSupplier() {}
  }

  private static final class NotificationServiceMethodDescriptorSupplier
      extends NotificationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    NotificationServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NotificationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NotificationServiceFileDescriptorSupplier())
              .addMethod(getSendMessageMethod())
              .addMethod(getGetTemplatesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
