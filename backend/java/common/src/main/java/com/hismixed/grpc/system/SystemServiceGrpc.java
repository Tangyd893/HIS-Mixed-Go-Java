package com.hismixed.grpc.system;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 系统管理服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: system/system.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SystemServiceGrpc {

  private SystemServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "system.SystemService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.system.System.ListDictsRequest,
      com.hismixed.grpc.system.System.ListDictsResponse> getListDictsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListDicts",
      requestType = com.hismixed.grpc.system.System.ListDictsRequest.class,
      responseType = com.hismixed.grpc.system.System.ListDictsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.system.System.ListDictsRequest,
      com.hismixed.grpc.system.System.ListDictsResponse> getListDictsMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.system.System.ListDictsRequest, com.hismixed.grpc.system.System.ListDictsResponse> getListDictsMethod;
    if ((getListDictsMethod = SystemServiceGrpc.getListDictsMethod) == null) {
      synchronized (SystemServiceGrpc.class) {
        if ((getListDictsMethod = SystemServiceGrpc.getListDictsMethod) == null) {
          SystemServiceGrpc.getListDictsMethod = getListDictsMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.system.System.ListDictsRequest, com.hismixed.grpc.system.System.ListDictsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListDicts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.system.System.ListDictsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.system.System.ListDictsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SystemServiceMethodDescriptorSupplier("ListDicts"))
              .build();
        }
      }
    }
    return getListDictsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.system.System.GetAuditLogsRequest,
      com.hismixed.grpc.system.System.GetAuditLogsResponse> getGetAuditLogsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAuditLogs",
      requestType = com.hismixed.grpc.system.System.GetAuditLogsRequest.class,
      responseType = com.hismixed.grpc.system.System.GetAuditLogsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.system.System.GetAuditLogsRequest,
      com.hismixed.grpc.system.System.GetAuditLogsResponse> getGetAuditLogsMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.system.System.GetAuditLogsRequest, com.hismixed.grpc.system.System.GetAuditLogsResponse> getGetAuditLogsMethod;
    if ((getGetAuditLogsMethod = SystemServiceGrpc.getGetAuditLogsMethod) == null) {
      synchronized (SystemServiceGrpc.class) {
        if ((getGetAuditLogsMethod = SystemServiceGrpc.getGetAuditLogsMethod) == null) {
          SystemServiceGrpc.getGetAuditLogsMethod = getGetAuditLogsMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.system.System.GetAuditLogsRequest, com.hismixed.grpc.system.System.GetAuditLogsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAuditLogs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.system.System.GetAuditLogsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.system.System.GetAuditLogsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SystemServiceMethodDescriptorSupplier("GetAuditLogs"))
              .build();
        }
      }
    }
    return getGetAuditLogsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SystemServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemServiceStub>() {
        @java.lang.Override
        public SystemServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemServiceStub(channel, callOptions);
        }
      };
    return SystemServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SystemServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemServiceBlockingStub>() {
        @java.lang.Override
        public SystemServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemServiceBlockingStub(channel, callOptions);
        }
      };
    return SystemServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SystemServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemServiceFutureStub>() {
        @java.lang.Override
        public SystemServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemServiceFutureStub(channel, callOptions);
        }
      };
    return SystemServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 系统管理服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 获取字典列表
     * </pre>
     */
    default void listDicts(com.hismixed.grpc.system.System.ListDictsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.system.System.ListDictsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListDictsMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取审计日志
     * </pre>
     */
    default void getAuditLogs(com.hismixed.grpc.system.System.GetAuditLogsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.system.System.GetAuditLogsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAuditLogsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SystemService.
   * <pre>
   * 系统管理服务
   * </pre>
   */
  public static abstract class SystemServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SystemServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SystemService.
   * <pre>
   * 系统管理服务
   * </pre>
   */
  public static final class SystemServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SystemServiceStub> {
    private SystemServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取字典列表
     * </pre>
     */
    public void listDicts(com.hismixed.grpc.system.System.ListDictsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.system.System.ListDictsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListDictsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取审计日志
     * </pre>
     */
    public void getAuditLogs(com.hismixed.grpc.system.System.GetAuditLogsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.system.System.GetAuditLogsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAuditLogsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SystemService.
   * <pre>
   * 系统管理服务
   * </pre>
   */
  public static final class SystemServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SystemServiceBlockingStub> {
    private SystemServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取字典列表
     * </pre>
     */
    public com.hismixed.grpc.system.System.ListDictsResponse listDicts(com.hismixed.grpc.system.System.ListDictsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListDictsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取审计日志
     * </pre>
     */
    public com.hismixed.grpc.system.System.GetAuditLogsResponse getAuditLogs(com.hismixed.grpc.system.System.GetAuditLogsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAuditLogsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SystemService.
   * <pre>
   * 系统管理服务
   * </pre>
   */
  public static final class SystemServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SystemServiceFutureStub> {
    private SystemServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取字典列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.system.System.ListDictsResponse> listDicts(
        com.hismixed.grpc.system.System.ListDictsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListDictsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取审计日志
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.system.System.GetAuditLogsResponse> getAuditLogs(
        com.hismixed.grpc.system.System.GetAuditLogsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAuditLogsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_DICTS = 0;
  private static final int METHODID_GET_AUDIT_LOGS = 1;

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
        case METHODID_LIST_DICTS:
          serviceImpl.listDicts((com.hismixed.grpc.system.System.ListDictsRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.system.System.ListDictsResponse>) responseObserver);
          break;
        case METHODID_GET_AUDIT_LOGS:
          serviceImpl.getAuditLogs((com.hismixed.grpc.system.System.GetAuditLogsRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.system.System.GetAuditLogsResponse>) responseObserver);
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
          getListDictsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.system.System.ListDictsRequest,
              com.hismixed.grpc.system.System.ListDictsResponse>(
                service, METHODID_LIST_DICTS)))
        .addMethod(
          getGetAuditLogsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.system.System.GetAuditLogsRequest,
              com.hismixed.grpc.system.System.GetAuditLogsResponse>(
                service, METHODID_GET_AUDIT_LOGS)))
        .build();
  }

  private static abstract class SystemServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SystemServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.system.System.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SystemService");
    }
  }

  private static final class SystemServiceFileDescriptorSupplier
      extends SystemServiceBaseDescriptorSupplier {
    SystemServiceFileDescriptorSupplier() {}
  }

  private static final class SystemServiceMethodDescriptorSupplier
      extends SystemServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SystemServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SystemServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SystemServiceFileDescriptorSupplier())
              .addMethod(getListDictsMethod())
              .addMethod(getGetAuditLogsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
