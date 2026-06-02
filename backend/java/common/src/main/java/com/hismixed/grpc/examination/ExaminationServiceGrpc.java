package com.hismixed.grpc.examination;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 检查服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: examination/examination.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ExaminationServiceGrpc {

  private ExaminationServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "examination.ExaminationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.examination.Examination.CreateReportRequest,
      com.hismixed.grpc.examination.Examination.CreateReportResponse> getCreateReportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateReport",
      requestType = com.hismixed.grpc.examination.Examination.CreateReportRequest.class,
      responseType = com.hismixed.grpc.examination.Examination.CreateReportResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.examination.Examination.CreateReportRequest,
      com.hismixed.grpc.examination.Examination.CreateReportResponse> getCreateReportMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.examination.Examination.CreateReportRequest, com.hismixed.grpc.examination.Examination.CreateReportResponse> getCreateReportMethod;
    if ((getCreateReportMethod = ExaminationServiceGrpc.getCreateReportMethod) == null) {
      synchronized (ExaminationServiceGrpc.class) {
        if ((getCreateReportMethod = ExaminationServiceGrpc.getCreateReportMethod) == null) {
          ExaminationServiceGrpc.getCreateReportMethod = getCreateReportMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.examination.Examination.CreateReportRequest, com.hismixed.grpc.examination.Examination.CreateReportResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateReport"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.examination.Examination.CreateReportRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.examination.Examination.CreateReportResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ExaminationServiceMethodDescriptorSupplier("CreateReport"))
              .build();
        }
      }
    }
    return getCreateReportMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.examination.Examination.GetReportRequest,
      com.hismixed.grpc.examination.Examination.GetReportResponse> getGetReportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetReport",
      requestType = com.hismixed.grpc.examination.Examination.GetReportRequest.class,
      responseType = com.hismixed.grpc.examination.Examination.GetReportResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.examination.Examination.GetReportRequest,
      com.hismixed.grpc.examination.Examination.GetReportResponse> getGetReportMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.examination.Examination.GetReportRequest, com.hismixed.grpc.examination.Examination.GetReportResponse> getGetReportMethod;
    if ((getGetReportMethod = ExaminationServiceGrpc.getGetReportMethod) == null) {
      synchronized (ExaminationServiceGrpc.class) {
        if ((getGetReportMethod = ExaminationServiceGrpc.getGetReportMethod) == null) {
          ExaminationServiceGrpc.getGetReportMethod = getGetReportMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.examination.Examination.GetReportRequest, com.hismixed.grpc.examination.Examination.GetReportResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetReport"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.examination.Examination.GetReportRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.examination.Examination.GetReportResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ExaminationServiceMethodDescriptorSupplier("GetReport"))
              .build();
        }
      }
    }
    return getGetReportMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExaminationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExaminationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExaminationServiceStub>() {
        @java.lang.Override
        public ExaminationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExaminationServiceStub(channel, callOptions);
        }
      };
    return ExaminationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExaminationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExaminationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExaminationServiceBlockingStub>() {
        @java.lang.Override
        public ExaminationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExaminationServiceBlockingStub(channel, callOptions);
        }
      };
    return ExaminationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ExaminationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExaminationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExaminationServiceFutureStub>() {
        @java.lang.Override
        public ExaminationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExaminationServiceFutureStub(channel, callOptions);
        }
      };
    return ExaminationServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 检查服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 创建检查报告
     * </pre>
     */
    default void createReport(com.hismixed.grpc.examination.Examination.CreateReportRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.examination.Examination.CreateReportResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateReportMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取检查报告
     * </pre>
     */
    default void getReport(com.hismixed.grpc.examination.Examination.GetReportRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.examination.Examination.GetReportResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetReportMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ExaminationService.
   * <pre>
   * 检查服务
   * </pre>
   */
  public static abstract class ExaminationServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ExaminationServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ExaminationService.
   * <pre>
   * 检查服务
   * </pre>
   */
  public static final class ExaminationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ExaminationServiceStub> {
    private ExaminationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExaminationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExaminationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建检查报告
     * </pre>
     */
    public void createReport(com.hismixed.grpc.examination.Examination.CreateReportRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.examination.Examination.CreateReportResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateReportMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取检查报告
     * </pre>
     */
    public void getReport(com.hismixed.grpc.examination.Examination.GetReportRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.examination.Examination.GetReportResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetReportMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ExaminationService.
   * <pre>
   * 检查服务
   * </pre>
   */
  public static final class ExaminationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ExaminationServiceBlockingStub> {
    private ExaminationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExaminationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExaminationServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建检查报告
     * </pre>
     */
    public com.hismixed.grpc.examination.Examination.CreateReportResponse createReport(com.hismixed.grpc.examination.Examination.CreateReportRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateReportMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取检查报告
     * </pre>
     */
    public com.hismixed.grpc.examination.Examination.GetReportResponse getReport(com.hismixed.grpc.examination.Examination.GetReportRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetReportMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ExaminationService.
   * <pre>
   * 检查服务
   * </pre>
   */
  public static final class ExaminationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ExaminationServiceFutureStub> {
    private ExaminationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExaminationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExaminationServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建检查报告
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.examination.Examination.CreateReportResponse> createReport(
        com.hismixed.grpc.examination.Examination.CreateReportRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateReportMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取检查报告
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.examination.Examination.GetReportResponse> getReport(
        com.hismixed.grpc.examination.Examination.GetReportRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetReportMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_REPORT = 0;
  private static final int METHODID_GET_REPORT = 1;

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
        case METHODID_CREATE_REPORT:
          serviceImpl.createReport((com.hismixed.grpc.examination.Examination.CreateReportRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.examination.Examination.CreateReportResponse>) responseObserver);
          break;
        case METHODID_GET_REPORT:
          serviceImpl.getReport((com.hismixed.grpc.examination.Examination.GetReportRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.examination.Examination.GetReportResponse>) responseObserver);
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
          getCreateReportMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.examination.Examination.CreateReportRequest,
              com.hismixed.grpc.examination.Examination.CreateReportResponse>(
                service, METHODID_CREATE_REPORT)))
        .addMethod(
          getGetReportMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.examination.Examination.GetReportRequest,
              com.hismixed.grpc.examination.Examination.GetReportResponse>(
                service, METHODID_GET_REPORT)))
        .build();
  }

  private static abstract class ExaminationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ExaminationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.examination.Examination.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ExaminationService");
    }
  }

  private static final class ExaminationServiceFileDescriptorSupplier
      extends ExaminationServiceBaseDescriptorSupplier {
    ExaminationServiceFileDescriptorSupplier() {}
  }

  private static final class ExaminationServiceMethodDescriptorSupplier
      extends ExaminationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ExaminationServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ExaminationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ExaminationServiceFileDescriptorSupplier())
              .addMethod(getCreateReportMethod())
              .addMethod(getGetReportMethod())
              .build();
        }
      }
    }
    return result;
  }
}
