package com.hismixed.grpc.emr;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 电子病历服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: emr/emr.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EmrServiceGrpc {

  private EmrServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "emr.EmrService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.emr.Emr.CreateRecordRequest,
      com.hismixed.grpc.emr.Emr.CreateRecordResponse> getCreateRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateRecord",
      requestType = com.hismixed.grpc.emr.Emr.CreateRecordRequest.class,
      responseType = com.hismixed.grpc.emr.Emr.CreateRecordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.emr.Emr.CreateRecordRequest,
      com.hismixed.grpc.emr.Emr.CreateRecordResponse> getCreateRecordMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.emr.Emr.CreateRecordRequest, com.hismixed.grpc.emr.Emr.CreateRecordResponse> getCreateRecordMethod;
    if ((getCreateRecordMethod = EmrServiceGrpc.getCreateRecordMethod) == null) {
      synchronized (EmrServiceGrpc.class) {
        if ((getCreateRecordMethod = EmrServiceGrpc.getCreateRecordMethod) == null) {
          EmrServiceGrpc.getCreateRecordMethod = getCreateRecordMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.emr.Emr.CreateRecordRequest, com.hismixed.grpc.emr.Emr.CreateRecordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.emr.Emr.CreateRecordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.emr.Emr.CreateRecordResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EmrServiceMethodDescriptorSupplier("CreateRecord"))
              .build();
        }
      }
    }
    return getCreateRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.emr.Emr.GetRecordRequest,
      com.hismixed.grpc.emr.Emr.GetRecordResponse> getGetRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRecord",
      requestType = com.hismixed.grpc.emr.Emr.GetRecordRequest.class,
      responseType = com.hismixed.grpc.emr.Emr.GetRecordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.emr.Emr.GetRecordRequest,
      com.hismixed.grpc.emr.Emr.GetRecordResponse> getGetRecordMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.emr.Emr.GetRecordRequest, com.hismixed.grpc.emr.Emr.GetRecordResponse> getGetRecordMethod;
    if ((getGetRecordMethod = EmrServiceGrpc.getGetRecordMethod) == null) {
      synchronized (EmrServiceGrpc.class) {
        if ((getGetRecordMethod = EmrServiceGrpc.getGetRecordMethod) == null) {
          EmrServiceGrpc.getGetRecordMethod = getGetRecordMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.emr.Emr.GetRecordRequest, com.hismixed.grpc.emr.Emr.GetRecordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.emr.Emr.GetRecordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.emr.Emr.GetRecordResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EmrServiceMethodDescriptorSupplier("GetRecord"))
              .build();
        }
      }
    }
    return getGetRecordMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EmrServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EmrServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EmrServiceStub>() {
        @java.lang.Override
        public EmrServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EmrServiceStub(channel, callOptions);
        }
      };
    return EmrServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EmrServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EmrServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EmrServiceBlockingStub>() {
        @java.lang.Override
        public EmrServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EmrServiceBlockingStub(channel, callOptions);
        }
      };
    return EmrServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EmrServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EmrServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EmrServiceFutureStub>() {
        @java.lang.Override
        public EmrServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EmrServiceFutureStub(channel, callOptions);
        }
      };
    return EmrServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 电子病历服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 创建病历记录
     * </pre>
     */
    default void createRecord(com.hismixed.grpc.emr.Emr.CreateRecordRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.emr.Emr.CreateRecordResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateRecordMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取病历记录
     * </pre>
     */
    default void getRecord(com.hismixed.grpc.emr.Emr.GetRecordRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.emr.Emr.GetRecordResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRecordMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EmrService.
   * <pre>
   * 电子病历服务
   * </pre>
   */
  public static abstract class EmrServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return EmrServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service EmrService.
   * <pre>
   * 电子病历服务
   * </pre>
   */
  public static final class EmrServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EmrServiceStub> {
    private EmrServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmrServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EmrServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建病历记录
     * </pre>
     */
    public void createRecord(com.hismixed.grpc.emr.Emr.CreateRecordRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.emr.Emr.CreateRecordResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateRecordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取病历记录
     * </pre>
     */
    public void getRecord(com.hismixed.grpc.emr.Emr.GetRecordRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.emr.Emr.GetRecordResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRecordMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service EmrService.
   * <pre>
   * 电子病历服务
   * </pre>
   */
  public static final class EmrServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EmrServiceBlockingStub> {
    private EmrServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmrServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EmrServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建病历记录
     * </pre>
     */
    public com.hismixed.grpc.emr.Emr.CreateRecordResponse createRecord(com.hismixed.grpc.emr.Emr.CreateRecordRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateRecordMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取病历记录
     * </pre>
     */
    public com.hismixed.grpc.emr.Emr.GetRecordResponse getRecord(com.hismixed.grpc.emr.Emr.GetRecordRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRecordMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service EmrService.
   * <pre>
   * 电子病历服务
   * </pre>
   */
  public static final class EmrServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EmrServiceFutureStub> {
    private EmrServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmrServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EmrServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建病历记录
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.emr.Emr.CreateRecordResponse> createRecord(
        com.hismixed.grpc.emr.Emr.CreateRecordRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateRecordMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取病历记录
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.emr.Emr.GetRecordResponse> getRecord(
        com.hismixed.grpc.emr.Emr.GetRecordRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRecordMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_RECORD = 0;
  private static final int METHODID_GET_RECORD = 1;

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
        case METHODID_CREATE_RECORD:
          serviceImpl.createRecord((com.hismixed.grpc.emr.Emr.CreateRecordRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.emr.Emr.CreateRecordResponse>) responseObserver);
          break;
        case METHODID_GET_RECORD:
          serviceImpl.getRecord((com.hismixed.grpc.emr.Emr.GetRecordRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.emr.Emr.GetRecordResponse>) responseObserver);
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
          getCreateRecordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.emr.Emr.CreateRecordRequest,
              com.hismixed.grpc.emr.Emr.CreateRecordResponse>(
                service, METHODID_CREATE_RECORD)))
        .addMethod(
          getGetRecordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.emr.Emr.GetRecordRequest,
              com.hismixed.grpc.emr.Emr.GetRecordResponse>(
                service, METHODID_GET_RECORD)))
        .build();
  }

  private static abstract class EmrServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EmrServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.emr.Emr.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EmrService");
    }
  }

  private static final class EmrServiceFileDescriptorSupplier
      extends EmrServiceBaseDescriptorSupplier {
    EmrServiceFileDescriptorSupplier() {}
  }

  private static final class EmrServiceMethodDescriptorSupplier
      extends EmrServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    EmrServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (EmrServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EmrServiceFileDescriptorSupplier())
              .addMethod(getCreateRecordMethod())
              .addMethod(getGetRecordMethod())
              .build();
        }
      }
    }
    return result;
  }
}
