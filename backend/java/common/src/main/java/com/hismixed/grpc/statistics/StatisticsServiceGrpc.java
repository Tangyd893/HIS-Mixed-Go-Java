package com.hismixed.grpc.statistics;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 统计服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: statistics/statistics.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class StatisticsServiceGrpc {

  private StatisticsServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "statistics.StatisticsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.statistics.Statistics.GetDashboardRequest,
      com.hismixed.grpc.statistics.Statistics.GetDashboardResponse> getGetDashboardMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDashboard",
      requestType = com.hismixed.grpc.statistics.Statistics.GetDashboardRequest.class,
      responseType = com.hismixed.grpc.statistics.Statistics.GetDashboardResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.statistics.Statistics.GetDashboardRequest,
      com.hismixed.grpc.statistics.Statistics.GetDashboardResponse> getGetDashboardMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.statistics.Statistics.GetDashboardRequest, com.hismixed.grpc.statistics.Statistics.GetDashboardResponse> getGetDashboardMethod;
    if ((getGetDashboardMethod = StatisticsServiceGrpc.getGetDashboardMethod) == null) {
      synchronized (StatisticsServiceGrpc.class) {
        if ((getGetDashboardMethod = StatisticsServiceGrpc.getGetDashboardMethod) == null) {
          StatisticsServiceGrpc.getGetDashboardMethod = getGetDashboardMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.statistics.Statistics.GetDashboardRequest, com.hismixed.grpc.statistics.Statistics.GetDashboardResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDashboard"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.statistics.Statistics.GetDashboardRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.statistics.Statistics.GetDashboardResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StatisticsServiceMethodDescriptorSupplier("GetDashboard"))
              .build();
        }
      }
    }
    return getGetDashboardMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.statistics.Statistics.GetTrendRequest,
      com.hismixed.grpc.statistics.Statistics.GetTrendResponse> getGetTrendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTrend",
      requestType = com.hismixed.grpc.statistics.Statistics.GetTrendRequest.class,
      responseType = com.hismixed.grpc.statistics.Statistics.GetTrendResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.statistics.Statistics.GetTrendRequest,
      com.hismixed.grpc.statistics.Statistics.GetTrendResponse> getGetTrendMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.statistics.Statistics.GetTrendRequest, com.hismixed.grpc.statistics.Statistics.GetTrendResponse> getGetTrendMethod;
    if ((getGetTrendMethod = StatisticsServiceGrpc.getGetTrendMethod) == null) {
      synchronized (StatisticsServiceGrpc.class) {
        if ((getGetTrendMethod = StatisticsServiceGrpc.getGetTrendMethod) == null) {
          StatisticsServiceGrpc.getGetTrendMethod = getGetTrendMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.statistics.Statistics.GetTrendRequest, com.hismixed.grpc.statistics.Statistics.GetTrendResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTrend"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.statistics.Statistics.GetTrendRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.statistics.Statistics.GetTrendResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StatisticsServiceMethodDescriptorSupplier("GetTrend"))
              .build();
        }
      }
    }
    return getGetTrendMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StatisticsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StatisticsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StatisticsServiceStub>() {
        @java.lang.Override
        public StatisticsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StatisticsServiceStub(channel, callOptions);
        }
      };
    return StatisticsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StatisticsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StatisticsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StatisticsServiceBlockingStub>() {
        @java.lang.Override
        public StatisticsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StatisticsServiceBlockingStub(channel, callOptions);
        }
      };
    return StatisticsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StatisticsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StatisticsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StatisticsServiceFutureStub>() {
        @java.lang.Override
        public StatisticsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StatisticsServiceFutureStub(channel, callOptions);
        }
      };
    return StatisticsServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 统计服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 获取仪表盘数据
     * </pre>
     */
    default void getDashboard(com.hismixed.grpc.statistics.Statistics.GetDashboardRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.statistics.Statistics.GetDashboardResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDashboardMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取趋势数据
     * </pre>
     */
    default void getTrend(com.hismixed.grpc.statistics.Statistics.GetTrendRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.statistics.Statistics.GetTrendResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTrendMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service StatisticsService.
   * <pre>
   * 统计服务
   * </pre>
   */
  public static abstract class StatisticsServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return StatisticsServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service StatisticsService.
   * <pre>
   * 统计服务
   * </pre>
   */
  public static final class StatisticsServiceStub
      extends io.grpc.stub.AbstractAsyncStub<StatisticsServiceStub> {
    private StatisticsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StatisticsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StatisticsServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取仪表盘数据
     * </pre>
     */
    public void getDashboard(com.hismixed.grpc.statistics.Statistics.GetDashboardRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.statistics.Statistics.GetDashboardResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDashboardMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取趋势数据
     * </pre>
     */
    public void getTrend(com.hismixed.grpc.statistics.Statistics.GetTrendRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.statistics.Statistics.GetTrendResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTrendMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service StatisticsService.
   * <pre>
   * 统计服务
   * </pre>
   */
  public static final class StatisticsServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<StatisticsServiceBlockingStub> {
    private StatisticsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StatisticsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StatisticsServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取仪表盘数据
     * </pre>
     */
    public com.hismixed.grpc.statistics.Statistics.GetDashboardResponse getDashboard(com.hismixed.grpc.statistics.Statistics.GetDashboardRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDashboardMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取趋势数据
     * </pre>
     */
    public com.hismixed.grpc.statistics.Statistics.GetTrendResponse getTrend(com.hismixed.grpc.statistics.Statistics.GetTrendRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTrendMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service StatisticsService.
   * <pre>
   * 统计服务
   * </pre>
   */
  public static final class StatisticsServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<StatisticsServiceFutureStub> {
    private StatisticsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StatisticsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StatisticsServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取仪表盘数据
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.statistics.Statistics.GetDashboardResponse> getDashboard(
        com.hismixed.grpc.statistics.Statistics.GetDashboardRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDashboardMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取趋势数据
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.statistics.Statistics.GetTrendResponse> getTrend(
        com.hismixed.grpc.statistics.Statistics.GetTrendRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTrendMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_DASHBOARD = 0;
  private static final int METHODID_GET_TREND = 1;

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
        case METHODID_GET_DASHBOARD:
          serviceImpl.getDashboard((com.hismixed.grpc.statistics.Statistics.GetDashboardRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.statistics.Statistics.GetDashboardResponse>) responseObserver);
          break;
        case METHODID_GET_TREND:
          serviceImpl.getTrend((com.hismixed.grpc.statistics.Statistics.GetTrendRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.statistics.Statistics.GetTrendResponse>) responseObserver);
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
          getGetDashboardMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.statistics.Statistics.GetDashboardRequest,
              com.hismixed.grpc.statistics.Statistics.GetDashboardResponse>(
                service, METHODID_GET_DASHBOARD)))
        .addMethod(
          getGetTrendMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.statistics.Statistics.GetTrendRequest,
              com.hismixed.grpc.statistics.Statistics.GetTrendResponse>(
                service, METHODID_GET_TREND)))
        .build();
  }

  private static abstract class StatisticsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StatisticsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.statistics.Statistics.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StatisticsService");
    }
  }

  private static final class StatisticsServiceFileDescriptorSupplier
      extends StatisticsServiceBaseDescriptorSupplier {
    StatisticsServiceFileDescriptorSupplier() {}
  }

  private static final class StatisticsServiceMethodDescriptorSupplier
      extends StatisticsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    StatisticsServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (StatisticsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StatisticsServiceFileDescriptorSupplier())
              .addMethod(getGetDashboardMethod())
              .addMethod(getGetTrendMethod())
              .build();
        }
      }
    }
    return result;
  }
}
