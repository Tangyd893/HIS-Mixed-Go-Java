package com.hismixed.grpc.followup;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 随访服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: followup/followup.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FollowupServiceGrpc {

  private FollowupServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "followup.FollowupService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.followup.Followup.CreatePlanRequest,
      com.hismixed.grpc.followup.Followup.CreatePlanResponse> getCreatePlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreatePlan",
      requestType = com.hismixed.grpc.followup.Followup.CreatePlanRequest.class,
      responseType = com.hismixed.grpc.followup.Followup.CreatePlanResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.followup.Followup.CreatePlanRequest,
      com.hismixed.grpc.followup.Followup.CreatePlanResponse> getCreatePlanMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.followup.Followup.CreatePlanRequest, com.hismixed.grpc.followup.Followup.CreatePlanResponse> getCreatePlanMethod;
    if ((getCreatePlanMethod = FollowupServiceGrpc.getCreatePlanMethod) == null) {
      synchronized (FollowupServiceGrpc.class) {
        if ((getCreatePlanMethod = FollowupServiceGrpc.getCreatePlanMethod) == null) {
          FollowupServiceGrpc.getCreatePlanMethod = getCreatePlanMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.followup.Followup.CreatePlanRequest, com.hismixed.grpc.followup.Followup.CreatePlanResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreatePlan"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.followup.Followup.CreatePlanRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.followup.Followup.CreatePlanResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FollowupServiceMethodDescriptorSupplier("CreatePlan"))
              .build();
        }
      }
    }
    return getCreatePlanMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.followup.Followup.AddRecordRequest,
      com.hismixed.grpc.followup.Followup.AddRecordResponse> getAddRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddRecord",
      requestType = com.hismixed.grpc.followup.Followup.AddRecordRequest.class,
      responseType = com.hismixed.grpc.followup.Followup.AddRecordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.followup.Followup.AddRecordRequest,
      com.hismixed.grpc.followup.Followup.AddRecordResponse> getAddRecordMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.followup.Followup.AddRecordRequest, com.hismixed.grpc.followup.Followup.AddRecordResponse> getAddRecordMethod;
    if ((getAddRecordMethod = FollowupServiceGrpc.getAddRecordMethod) == null) {
      synchronized (FollowupServiceGrpc.class) {
        if ((getAddRecordMethod = FollowupServiceGrpc.getAddRecordMethod) == null) {
          FollowupServiceGrpc.getAddRecordMethod = getAddRecordMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.followup.Followup.AddRecordRequest, com.hismixed.grpc.followup.Followup.AddRecordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.followup.Followup.AddRecordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.followup.Followup.AddRecordResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FollowupServiceMethodDescriptorSupplier("AddRecord"))
              .build();
        }
      }
    }
    return getAddRecordMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FollowupServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FollowupServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FollowupServiceStub>() {
        @java.lang.Override
        public FollowupServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FollowupServiceStub(channel, callOptions);
        }
      };
    return FollowupServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FollowupServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FollowupServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FollowupServiceBlockingStub>() {
        @java.lang.Override
        public FollowupServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FollowupServiceBlockingStub(channel, callOptions);
        }
      };
    return FollowupServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FollowupServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FollowupServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FollowupServiceFutureStub>() {
        @java.lang.Override
        public FollowupServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FollowupServiceFutureStub(channel, callOptions);
        }
      };
    return FollowupServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 随访服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 创建随访计划
     * </pre>
     */
    default void createPlan(com.hismixed.grpc.followup.Followup.CreatePlanRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.followup.Followup.CreatePlanResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreatePlanMethod(), responseObserver);
    }

    /**
     * <pre>
     * 添加随访记录
     * </pre>
     */
    default void addRecord(com.hismixed.grpc.followup.Followup.AddRecordRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.followup.Followup.AddRecordResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddRecordMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FollowupService.
   * <pre>
   * 随访服务
   * </pre>
   */
  public static abstract class FollowupServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FollowupServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FollowupService.
   * <pre>
   * 随访服务
   * </pre>
   */
  public static final class FollowupServiceStub
      extends io.grpc.stub.AbstractAsyncStub<FollowupServiceStub> {
    private FollowupServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FollowupServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FollowupServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建随访计划
     * </pre>
     */
    public void createPlan(com.hismixed.grpc.followup.Followup.CreatePlanRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.followup.Followup.CreatePlanResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreatePlanMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 添加随访记录
     * </pre>
     */
    public void addRecord(com.hismixed.grpc.followup.Followup.AddRecordRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.followup.Followup.AddRecordResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddRecordMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FollowupService.
   * <pre>
   * 随访服务
   * </pre>
   */
  public static final class FollowupServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FollowupServiceBlockingStub> {
    private FollowupServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FollowupServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FollowupServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建随访计划
     * </pre>
     */
    public com.hismixed.grpc.followup.Followup.CreatePlanResponse createPlan(com.hismixed.grpc.followup.Followup.CreatePlanRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreatePlanMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 添加随访记录
     * </pre>
     */
    public com.hismixed.grpc.followup.Followup.AddRecordResponse addRecord(com.hismixed.grpc.followup.Followup.AddRecordRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddRecordMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FollowupService.
   * <pre>
   * 随访服务
   * </pre>
   */
  public static final class FollowupServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<FollowupServiceFutureStub> {
    private FollowupServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FollowupServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FollowupServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建随访计划
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.followup.Followup.CreatePlanResponse> createPlan(
        com.hismixed.grpc.followup.Followup.CreatePlanRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreatePlanMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 添加随访记录
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.followup.Followup.AddRecordResponse> addRecord(
        com.hismixed.grpc.followup.Followup.AddRecordRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddRecordMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_PLAN = 0;
  private static final int METHODID_ADD_RECORD = 1;

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
        case METHODID_CREATE_PLAN:
          serviceImpl.createPlan((com.hismixed.grpc.followup.Followup.CreatePlanRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.followup.Followup.CreatePlanResponse>) responseObserver);
          break;
        case METHODID_ADD_RECORD:
          serviceImpl.addRecord((com.hismixed.grpc.followup.Followup.AddRecordRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.followup.Followup.AddRecordResponse>) responseObserver);
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
          getCreatePlanMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.followup.Followup.CreatePlanRequest,
              com.hismixed.grpc.followup.Followup.CreatePlanResponse>(
                service, METHODID_CREATE_PLAN)))
        .addMethod(
          getAddRecordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.followup.Followup.AddRecordRequest,
              com.hismixed.grpc.followup.Followup.AddRecordResponse>(
                service, METHODID_ADD_RECORD)))
        .build();
  }

  private static abstract class FollowupServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FollowupServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.followup.Followup.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FollowupService");
    }
  }

  private static final class FollowupServiceFileDescriptorSupplier
      extends FollowupServiceBaseDescriptorSupplier {
    FollowupServiceFileDescriptorSupplier() {}
  }

  private static final class FollowupServiceMethodDescriptorSupplier
      extends FollowupServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FollowupServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FollowupServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FollowupServiceFileDescriptorSupplier())
              .addMethod(getCreatePlanMethod())
              .addMethod(getAddRecordMethod())
              .build();
        }
      }
    }
    return result;
  }
}
