package com.hismixed.grpc.outpatient;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 院外服务（远程会诊/院外协作）
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: outpatient/outpatient.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OutpatientServiceGrpc {

  private OutpatientServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "outpatient.OutpatientService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest,
      com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse> getCreateConsultationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateConsultation",
      requestType = com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest.class,
      responseType = com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest,
      com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse> getCreateConsultationMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest, com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse> getCreateConsultationMethod;
    if ((getCreateConsultationMethod = OutpatientServiceGrpc.getCreateConsultationMethod) == null) {
      synchronized (OutpatientServiceGrpc.class) {
        if ((getCreateConsultationMethod = OutpatientServiceGrpc.getCreateConsultationMethod) == null) {
          OutpatientServiceGrpc.getCreateConsultationMethod = getCreateConsultationMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest, com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateConsultation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OutpatientServiceMethodDescriptorSupplier("CreateConsultation"))
              .build();
        }
      }
    }
    return getCreateConsultationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest,
      com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse> getGetMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMessages",
      requestType = com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest.class,
      responseType = com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest,
      com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse> getGetMessagesMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest, com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse> getGetMessagesMethod;
    if ((getGetMessagesMethod = OutpatientServiceGrpc.getGetMessagesMethod) == null) {
      synchronized (OutpatientServiceGrpc.class) {
        if ((getGetMessagesMethod = OutpatientServiceGrpc.getGetMessagesMethod) == null) {
          OutpatientServiceGrpc.getGetMessagesMethod = getGetMessagesMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest, com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OutpatientServiceMethodDescriptorSupplier("GetMessages"))
              .build();
        }
      }
    }
    return getGetMessagesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OutpatientServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OutpatientServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OutpatientServiceStub>() {
        @java.lang.Override
        public OutpatientServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OutpatientServiceStub(channel, callOptions);
        }
      };
    return OutpatientServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OutpatientServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OutpatientServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OutpatientServiceBlockingStub>() {
        @java.lang.Override
        public OutpatientServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OutpatientServiceBlockingStub(channel, callOptions);
        }
      };
    return OutpatientServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OutpatientServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OutpatientServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OutpatientServiceFutureStub>() {
        @java.lang.Override
        public OutpatientServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OutpatientServiceFutureStub(channel, callOptions);
        }
      };
    return OutpatientServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 院外服务（远程会诊/院外协作）
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 创建远程会诊
     * </pre>
     */
    default void createConsultation(com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateConsultationMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取消息列表
     * </pre>
     */
    default void getMessages(com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMessagesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service OutpatientService.
   * <pre>
   * 院外服务（远程会诊/院外协作）
   * </pre>
   */
  public static abstract class OutpatientServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return OutpatientServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service OutpatientService.
   * <pre>
   * 院外服务（远程会诊/院外协作）
   * </pre>
   */
  public static final class OutpatientServiceStub
      extends io.grpc.stub.AbstractAsyncStub<OutpatientServiceStub> {
    private OutpatientServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OutpatientServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OutpatientServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建远程会诊
     * </pre>
     */
    public void createConsultation(com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateConsultationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取消息列表
     * </pre>
     */
    public void getMessages(com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMessagesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service OutpatientService.
   * <pre>
   * 院外服务（远程会诊/院外协作）
   * </pre>
   */
  public static final class OutpatientServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<OutpatientServiceBlockingStub> {
    private OutpatientServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OutpatientServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OutpatientServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建远程会诊
     * </pre>
     */
    public com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse createConsultation(com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateConsultationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取消息列表
     * </pre>
     */
    public com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse getMessages(com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMessagesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service OutpatientService.
   * <pre>
   * 院外服务（远程会诊/院外协作）
   * </pre>
   */
  public static final class OutpatientServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<OutpatientServiceFutureStub> {
    private OutpatientServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OutpatientServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OutpatientServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建远程会诊
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse> createConsultation(
        com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateConsultationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取消息列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse> getMessages(
        com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMessagesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_CONSULTATION = 0;
  private static final int METHODID_GET_MESSAGES = 1;

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
        case METHODID_CREATE_CONSULTATION:
          serviceImpl.createConsultation((com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse>) responseObserver);
          break;
        case METHODID_GET_MESSAGES:
          serviceImpl.getMessages((com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse>) responseObserver);
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
          getCreateConsultationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.outpatient.Outpatient.CreateConsultationRequest,
              com.hismixed.grpc.outpatient.Outpatient.CreateConsultationResponse>(
                service, METHODID_CREATE_CONSULTATION)))
        .addMethod(
          getGetMessagesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.outpatient.Outpatient.GetMessagesRequest,
              com.hismixed.grpc.outpatient.Outpatient.GetMessagesResponse>(
                service, METHODID_GET_MESSAGES)))
        .build();
  }

  private static abstract class OutpatientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OutpatientServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.outpatient.Outpatient.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OutpatientService");
    }
  }

  private static final class OutpatientServiceFileDescriptorSupplier
      extends OutpatientServiceBaseDescriptorSupplier {
    OutpatientServiceFileDescriptorSupplier() {}
  }

  private static final class OutpatientServiceMethodDescriptorSupplier
      extends OutpatientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    OutpatientServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (OutpatientServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OutpatientServiceFileDescriptorSupplier())
              .addMethod(getCreateConsultationMethod())
              .addMethod(getGetMessagesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
