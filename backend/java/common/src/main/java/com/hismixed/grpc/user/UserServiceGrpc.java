package com.hismixed.grpc.user;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 用户服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: user/user.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UserServiceGrpc {

  private UserServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "user.UserService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.user.User.GetPatientRequest,
      com.hismixed.grpc.user.User.GetPatientResponse> getGetPatientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPatient",
      requestType = com.hismixed.grpc.user.User.GetPatientRequest.class,
      responseType = com.hismixed.grpc.user.User.GetPatientResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.user.User.GetPatientRequest,
      com.hismixed.grpc.user.User.GetPatientResponse> getGetPatientMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.user.User.GetPatientRequest, com.hismixed.grpc.user.User.GetPatientResponse> getGetPatientMethod;
    if ((getGetPatientMethod = UserServiceGrpc.getGetPatientMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetPatientMethod = UserServiceGrpc.getGetPatientMethod) == null) {
          UserServiceGrpc.getGetPatientMethod = getGetPatientMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.user.User.GetPatientRequest, com.hismixed.grpc.user.User.GetPatientResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPatient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.user.User.GetPatientRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.user.User.GetPatientResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("GetPatient"))
              .build();
        }
      }
    }
    return getGetPatientMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.user.User.ListPatientsRequest,
      com.hismixed.grpc.user.User.ListPatientsResponse> getListPatientsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListPatients",
      requestType = com.hismixed.grpc.user.User.ListPatientsRequest.class,
      responseType = com.hismixed.grpc.user.User.ListPatientsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.user.User.ListPatientsRequest,
      com.hismixed.grpc.user.User.ListPatientsResponse> getListPatientsMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.user.User.ListPatientsRequest, com.hismixed.grpc.user.User.ListPatientsResponse> getListPatientsMethod;
    if ((getListPatientsMethod = UserServiceGrpc.getListPatientsMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getListPatientsMethod = UserServiceGrpc.getListPatientsMethod) == null) {
          UserServiceGrpc.getListPatientsMethod = getListPatientsMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.user.User.ListPatientsRequest, com.hismixed.grpc.user.User.ListPatientsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListPatients"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.user.User.ListPatientsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.user.User.ListPatientsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("ListPatients"))
              .build();
        }
      }
    }
    return getListPatientsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceStub>() {
        @java.lang.Override
        public UserServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceStub(channel, callOptions);
        }
      };
    return UserServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceBlockingStub>() {
        @java.lang.Override
        public UserServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceBlockingStub(channel, callOptions);
        }
      };
    return UserServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceFutureStub>() {
        @java.lang.Override
        public UserServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceFutureStub(channel, callOptions);
        }
      };
    return UserServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 用户服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 获取患者信息
     * </pre>
     */
    default void getPatient(com.hismixed.grpc.user.User.GetPatientRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.user.User.GetPatientResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPatientMethod(), responseObserver);
    }

    /**
     * <pre>
     * 患者列表
     * </pre>
     */
    default void listPatients(com.hismixed.grpc.user.User.ListPatientsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.user.User.ListPatientsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListPatientsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service UserService.
   * <pre>
   * 用户服务
   * </pre>
   */
  public static abstract class UserServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return UserServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service UserService.
   * <pre>
   * 用户服务
   * </pre>
   */
  public static final class UserServiceStub
      extends io.grpc.stub.AbstractAsyncStub<UserServiceStub> {
    private UserServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取患者信息
     * </pre>
     */
    public void getPatient(com.hismixed.grpc.user.User.GetPatientRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.user.User.GetPatientResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPatientMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 患者列表
     * </pre>
     */
    public void listPatients(com.hismixed.grpc.user.User.ListPatientsRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.user.User.ListPatientsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListPatientsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service UserService.
   * <pre>
   * 用户服务
   * </pre>
   */
  public static final class UserServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<UserServiceBlockingStub> {
    private UserServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取患者信息
     * </pre>
     */
    public com.hismixed.grpc.user.User.GetPatientResponse getPatient(com.hismixed.grpc.user.User.GetPatientRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPatientMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 患者列表
     * </pre>
     */
    public com.hismixed.grpc.user.User.ListPatientsResponse listPatients(com.hismixed.grpc.user.User.ListPatientsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListPatientsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service UserService.
   * <pre>
   * 用户服务
   * </pre>
   */
  public static final class UserServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<UserServiceFutureStub> {
    private UserServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取患者信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.user.User.GetPatientResponse> getPatient(
        com.hismixed.grpc.user.User.GetPatientRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPatientMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 患者列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.user.User.ListPatientsResponse> listPatients(
        com.hismixed.grpc.user.User.ListPatientsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListPatientsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PATIENT = 0;
  private static final int METHODID_LIST_PATIENTS = 1;

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
        case METHODID_GET_PATIENT:
          serviceImpl.getPatient((com.hismixed.grpc.user.User.GetPatientRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.user.User.GetPatientResponse>) responseObserver);
          break;
        case METHODID_LIST_PATIENTS:
          serviceImpl.listPatients((com.hismixed.grpc.user.User.ListPatientsRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.user.User.ListPatientsResponse>) responseObserver);
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
          getGetPatientMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.user.User.GetPatientRequest,
              com.hismixed.grpc.user.User.GetPatientResponse>(
                service, METHODID_GET_PATIENT)))
        .addMethod(
          getListPatientsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.user.User.ListPatientsRequest,
              com.hismixed.grpc.user.User.ListPatientsResponse>(
                service, METHODID_LIST_PATIENTS)))
        .build();
  }

  private static abstract class UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.user.User.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserService");
    }
  }

  private static final class UserServiceFileDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier {
    UserServiceFileDescriptorSupplier() {}
  }

  private static final class UserServiceMethodDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    UserServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (UserServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserServiceFileDescriptorSupplier())
              .addMethod(getGetPatientMethod())
              .addMethod(getListPatientsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
