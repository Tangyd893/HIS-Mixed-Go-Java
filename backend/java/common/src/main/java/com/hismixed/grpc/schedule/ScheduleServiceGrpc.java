package com.hismixed.grpc.schedule;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 排班服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: schedule/schedule.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ScheduleServiceGrpc {

  private ScheduleServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "schedule.ScheduleService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest,
      com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse> getGenerateSlotsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateSlots",
      requestType = com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest.class,
      responseType = com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest,
      com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse> getGenerateSlotsMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest, com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse> getGenerateSlotsMethod;
    if ((getGenerateSlotsMethod = ScheduleServiceGrpc.getGenerateSlotsMethod) == null) {
      synchronized (ScheduleServiceGrpc.class) {
        if ((getGenerateSlotsMethod = ScheduleServiceGrpc.getGenerateSlotsMethod) == null) {
          ScheduleServiceGrpc.getGenerateSlotsMethod = getGenerateSlotsMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest, com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GenerateSlots"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleServiceMethodDescriptorSupplier("GenerateSlots"))
              .build();
        }
      }
    }
    return getGenerateSlotsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.schedule.Schedule.GetSlotsRequest,
      com.hismixed.grpc.schedule.Schedule.GetSlotsResponse> getGetSlotsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSlots",
      requestType = com.hismixed.grpc.schedule.Schedule.GetSlotsRequest.class,
      responseType = com.hismixed.grpc.schedule.Schedule.GetSlotsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.schedule.Schedule.GetSlotsRequest,
      com.hismixed.grpc.schedule.Schedule.GetSlotsResponse> getGetSlotsMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.schedule.Schedule.GetSlotsRequest, com.hismixed.grpc.schedule.Schedule.GetSlotsResponse> getGetSlotsMethod;
    if ((getGetSlotsMethod = ScheduleServiceGrpc.getGetSlotsMethod) == null) {
      synchronized (ScheduleServiceGrpc.class) {
        if ((getGetSlotsMethod = ScheduleServiceGrpc.getGetSlotsMethod) == null) {
          ScheduleServiceGrpc.getGetSlotsMethod = getGetSlotsMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.schedule.Schedule.GetSlotsRequest, com.hismixed.grpc.schedule.Schedule.GetSlotsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSlots"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.schedule.Schedule.GetSlotsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.schedule.Schedule.GetSlotsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleServiceMethodDescriptorSupplier("GetSlots"))
              .build();
        }
      }
    }
    return getGetSlotsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ScheduleServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceStub>() {
        @java.lang.Override
        public ScheduleServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ScheduleServiceStub(channel, callOptions);
        }
      };
    return ScheduleServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ScheduleServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceBlockingStub>() {
        @java.lang.Override
        public ScheduleServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ScheduleServiceBlockingStub(channel, callOptions);
        }
      };
    return ScheduleServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ScheduleServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceFutureStub>() {
        @java.lang.Override
        public ScheduleServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ScheduleServiceFutureStub(channel, callOptions);
        }
      };
    return ScheduleServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 排班服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 生成排班时段
     * </pre>
     */
    default void generateSlots(com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGenerateSlotsMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取排班列表
     * </pre>
     */
    default void getSlots(com.hismixed.grpc.schedule.Schedule.GetSlotsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.schedule.Schedule.GetSlotsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSlotsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ScheduleService.
   * <pre>
   * 排班服务
   * </pre>
   */
  public static abstract class ScheduleServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ScheduleServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ScheduleService.
   * <pre>
   * 排班服务
   * </pre>
   */
  public static final class ScheduleServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ScheduleServiceStub> {
    private ScheduleServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ScheduleServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ScheduleServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 生成排班时段
     * </pre>
     */
    public void generateSlots(com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGenerateSlotsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取排班列表
     * </pre>
     */
    public void getSlots(com.hismixed.grpc.schedule.Schedule.GetSlotsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.schedule.Schedule.GetSlotsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSlotsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ScheduleService.
   * <pre>
   * 排班服务
   * </pre>
   */
  public static final class ScheduleServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ScheduleServiceBlockingStub> {
    private ScheduleServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ScheduleServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ScheduleServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 生成排班时段
     * </pre>
     */
    public com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse generateSlots(com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGenerateSlotsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取排班列表
     * </pre>
     */
    public com.hismixed.grpc.schedule.Schedule.GetSlotsResponse getSlots(com.hismixed.grpc.schedule.Schedule.GetSlotsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSlotsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ScheduleService.
   * <pre>
   * 排班服务
   * </pre>
   */
  public static final class ScheduleServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ScheduleServiceFutureStub> {
    private ScheduleServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ScheduleServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ScheduleServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 生成排班时段
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse> generateSlots(
        com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGenerateSlotsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取排班列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.schedule.Schedule.GetSlotsResponse> getSlots(
        com.hismixed.grpc.schedule.Schedule.GetSlotsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSlotsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GENERATE_SLOTS = 0;
  private static final int METHODID_GET_SLOTS = 1;

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
        case METHODID_GENERATE_SLOTS:
          serviceImpl.generateSlots((com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse>) responseObserver);
          break;
        case METHODID_GET_SLOTS:
          serviceImpl.getSlots((com.hismixed.grpc.schedule.Schedule.GetSlotsRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.schedule.Schedule.GetSlotsResponse>) responseObserver);
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
          getGenerateSlotsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.schedule.Schedule.GenerateSlotsRequest,
              com.hismixed.grpc.schedule.Schedule.GenerateSlotsResponse>(
                service, METHODID_GENERATE_SLOTS)))
        .addMethod(
          getGetSlotsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.schedule.Schedule.GetSlotsRequest,
              com.hismixed.grpc.schedule.Schedule.GetSlotsResponse>(
                service, METHODID_GET_SLOTS)))
        .build();
  }

  private static abstract class ScheduleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ScheduleServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.schedule.Schedule.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ScheduleService");
    }
  }

  private static final class ScheduleServiceFileDescriptorSupplier
      extends ScheduleServiceBaseDescriptorSupplier {
    ScheduleServiceFileDescriptorSupplier() {}
  }

  private static final class ScheduleServiceMethodDescriptorSupplier
      extends ScheduleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ScheduleServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ScheduleServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ScheduleServiceFileDescriptorSupplier())
              .addMethod(getGenerateSlotsMethod())
              .addMethod(getGetSlotsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
