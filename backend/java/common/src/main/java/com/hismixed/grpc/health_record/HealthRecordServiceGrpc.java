package com.hismixed.grpc.health_record;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 健康档案服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: health_record/health_record.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HealthRecordServiceGrpc {

  private HealthRecordServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "health_record.HealthRecordService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest,
      com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse> getGetOverviewMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOverview",
      requestType = com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest.class,
      responseType = com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest,
      com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse> getGetOverviewMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest, com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse> getGetOverviewMethod;
    if ((getGetOverviewMethod = HealthRecordServiceGrpc.getGetOverviewMethod) == null) {
      synchronized (HealthRecordServiceGrpc.class) {
        if ((getGetOverviewMethod = HealthRecordServiceGrpc.getGetOverviewMethod) == null) {
          HealthRecordServiceGrpc.getGetOverviewMethod = getGetOverviewMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest, com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOverview"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthRecordServiceMethodDescriptorSupplier("GetOverview"))
              .build();
        }
      }
    }
    return getGetOverviewMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest,
      com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse> getGetTimelineMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTimeline",
      requestType = com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest.class,
      responseType = com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest,
      com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse> getGetTimelineMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest, com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse> getGetTimelineMethod;
    if ((getGetTimelineMethod = HealthRecordServiceGrpc.getGetTimelineMethod) == null) {
      synchronized (HealthRecordServiceGrpc.class) {
        if ((getGetTimelineMethod = HealthRecordServiceGrpc.getGetTimelineMethod) == null) {
          HealthRecordServiceGrpc.getGetTimelineMethod = getGetTimelineMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest, com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTimeline"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HealthRecordServiceMethodDescriptorSupplier("GetTimeline"))
              .build();
        }
      }
    }
    return getGetTimelineMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HealthRecordServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthRecordServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthRecordServiceStub>() {
        @java.lang.Override
        public HealthRecordServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthRecordServiceStub(channel, callOptions);
        }
      };
    return HealthRecordServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HealthRecordServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthRecordServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthRecordServiceBlockingStub>() {
        @java.lang.Override
        public HealthRecordServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthRecordServiceBlockingStub(channel, callOptions);
        }
      };
    return HealthRecordServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HealthRecordServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HealthRecordServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HealthRecordServiceFutureStub>() {
        @java.lang.Override
        public HealthRecordServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HealthRecordServiceFutureStub(channel, callOptions);
        }
      };
    return HealthRecordServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 健康档案服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 获取健康概况
     * </pre>
     */
    default void getOverview(com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOverviewMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取健康时间线
     * </pre>
     */
    default void getTimeline(com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTimelineMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service HealthRecordService.
   * <pre>
   * 健康档案服务
   * </pre>
   */
  public static abstract class HealthRecordServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return HealthRecordServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service HealthRecordService.
   * <pre>
   * 健康档案服务
   * </pre>
   */
  public static final class HealthRecordServiceStub
      extends io.grpc.stub.AbstractAsyncStub<HealthRecordServiceStub> {
    private HealthRecordServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthRecordServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthRecordServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取健康概况
     * </pre>
     */
    public void getOverview(com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOverviewMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取健康时间线
     * </pre>
     */
    public void getTimeline(com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTimelineMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service HealthRecordService.
   * <pre>
   * 健康档案服务
   * </pre>
   */
  public static final class HealthRecordServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HealthRecordServiceBlockingStub> {
    private HealthRecordServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthRecordServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthRecordServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取健康概况
     * </pre>
     */
    public com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse getOverview(com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOverviewMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取健康时间线
     * </pre>
     */
    public com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse getTimeline(com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTimelineMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service HealthRecordService.
   * <pre>
   * 健康档案服务
   * </pre>
   */
  public static final class HealthRecordServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<HealthRecordServiceFutureStub> {
    private HealthRecordServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HealthRecordServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HealthRecordServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取健康概况
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse> getOverview(
        com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOverviewMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取健康时间线
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse> getTimeline(
        com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTimelineMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_OVERVIEW = 0;
  private static final int METHODID_GET_TIMELINE = 1;

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
        case METHODID_GET_OVERVIEW:
          serviceImpl.getOverview((com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse>) responseObserver);
          break;
        case METHODID_GET_TIMELINE:
          serviceImpl.getTimeline((com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse>) responseObserver);
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
          getGetOverviewMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.health_record.HealthRecord.GetOverviewRequest,
              com.hismixed.grpc.health_record.HealthRecord.GetOverviewResponse>(
                service, METHODID_GET_OVERVIEW)))
        .addMethod(
          getGetTimelineMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.health_record.HealthRecord.GetTimelineRequest,
              com.hismixed.grpc.health_record.HealthRecord.GetTimelineResponse>(
                service, METHODID_GET_TIMELINE)))
        .build();
  }

  private static abstract class HealthRecordServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HealthRecordServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.health_record.HealthRecord.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HealthRecordService");
    }
  }

  private static final class HealthRecordServiceFileDescriptorSupplier
      extends HealthRecordServiceBaseDescriptorSupplier {
    HealthRecordServiceFileDescriptorSupplier() {}
  }

  private static final class HealthRecordServiceMethodDescriptorSupplier
      extends HealthRecordServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    HealthRecordServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (HealthRecordServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HealthRecordServiceFileDescriptorSupplier())
              .addMethod(getGetOverviewMethod())
              .addMethod(getGetTimelineMethod())
              .build();
        }
      }
    }
    return result;
  }
}
