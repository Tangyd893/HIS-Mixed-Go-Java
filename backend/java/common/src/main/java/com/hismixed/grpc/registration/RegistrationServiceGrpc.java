package com.hismixed.grpc.registration;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 挂号服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: registration/registration.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RegistrationServiceGrpc {

  private RegistrationServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "registration.RegistrationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.registration.Registration.RegisterRequest,
      com.hismixed.grpc.registration.Registration.RegisterResponse> getRegisterAppointmentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterAppointment",
      requestType = com.hismixed.grpc.registration.Registration.RegisterRequest.class,
      responseType = com.hismixed.grpc.registration.Registration.RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.registration.Registration.RegisterRequest,
      com.hismixed.grpc.registration.Registration.RegisterResponse> getRegisterAppointmentMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.registration.Registration.RegisterRequest, com.hismixed.grpc.registration.Registration.RegisterResponse> getRegisterAppointmentMethod;
    if ((getRegisterAppointmentMethod = RegistrationServiceGrpc.getRegisterAppointmentMethod) == null) {
      synchronized (RegistrationServiceGrpc.class) {
        if ((getRegisterAppointmentMethod = RegistrationServiceGrpc.getRegisterAppointmentMethod) == null) {
          RegistrationServiceGrpc.getRegisterAppointmentMethod = getRegisterAppointmentMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.registration.Registration.RegisterRequest, com.hismixed.grpc.registration.Registration.RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterAppointment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.registration.Registration.RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.registration.Registration.RegisterResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RegistrationServiceMethodDescriptorSupplier("RegisterAppointment"))
              .build();
        }
      }
    }
    return getRegisterAppointmentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.registration.Registration.GetSchedulesRequest,
      com.hismixed.grpc.registration.Registration.GetSchedulesResponse> getGetSchedulesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSchedules",
      requestType = com.hismixed.grpc.registration.Registration.GetSchedulesRequest.class,
      responseType = com.hismixed.grpc.registration.Registration.GetSchedulesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.registration.Registration.GetSchedulesRequest,
      com.hismixed.grpc.registration.Registration.GetSchedulesResponse> getGetSchedulesMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.registration.Registration.GetSchedulesRequest, com.hismixed.grpc.registration.Registration.GetSchedulesResponse> getGetSchedulesMethod;
    if ((getGetSchedulesMethod = RegistrationServiceGrpc.getGetSchedulesMethod) == null) {
      synchronized (RegistrationServiceGrpc.class) {
        if ((getGetSchedulesMethod = RegistrationServiceGrpc.getGetSchedulesMethod) == null) {
          RegistrationServiceGrpc.getGetSchedulesMethod = getGetSchedulesMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.registration.Registration.GetSchedulesRequest, com.hismixed.grpc.registration.Registration.GetSchedulesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSchedules"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.registration.Registration.GetSchedulesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.registration.Registration.GetSchedulesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RegistrationServiceMethodDescriptorSupplier("GetSchedules"))
              .build();
        }
      }
    }
    return getGetSchedulesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RegistrationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceStub>() {
        @java.lang.Override
        public RegistrationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RegistrationServiceStub(channel, callOptions);
        }
      };
    return RegistrationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RegistrationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceBlockingStub>() {
        @java.lang.Override
        public RegistrationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RegistrationServiceBlockingStub(channel, callOptions);
        }
      };
    return RegistrationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RegistrationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceFutureStub>() {
        @java.lang.Override
        public RegistrationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RegistrationServiceFutureStub(channel, callOptions);
        }
      };
    return RegistrationServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 挂号服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 预约挂号
     * </pre>
     */
    default void registerAppointment(com.hismixed.grpc.registration.Registration.RegisterRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.registration.Registration.RegisterResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterAppointmentMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询号源
     * </pre>
     */
    default void getSchedules(com.hismixed.grpc.registration.Registration.GetSchedulesRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.registration.Registration.GetSchedulesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSchedulesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service RegistrationService.
   * <pre>
   * 挂号服务
   * </pre>
   */
  public static abstract class RegistrationServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return RegistrationServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service RegistrationService.
   * <pre>
   * 挂号服务
   * </pre>
   */
  public static final class RegistrationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<RegistrationServiceStub> {
    private RegistrationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RegistrationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RegistrationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 预约挂号
     * </pre>
     */
    public void registerAppointment(com.hismixed.grpc.registration.Registration.RegisterRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.registration.Registration.RegisterResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterAppointmentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询号源
     * </pre>
     */
    public void getSchedules(com.hismixed.grpc.registration.Registration.GetSchedulesRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.registration.Registration.GetSchedulesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSchedulesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service RegistrationService.
   * <pre>
   * 挂号服务
   * </pre>
   */
  public static final class RegistrationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<RegistrationServiceBlockingStub> {
    private RegistrationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RegistrationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RegistrationServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 预约挂号
     * </pre>
     */
    public com.hismixed.grpc.registration.Registration.RegisterResponse registerAppointment(com.hismixed.grpc.registration.Registration.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterAppointmentMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询号源
     * </pre>
     */
    public com.hismixed.grpc.registration.Registration.GetSchedulesResponse getSchedules(com.hismixed.grpc.registration.Registration.GetSchedulesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSchedulesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service RegistrationService.
   * <pre>
   * 挂号服务
   * </pre>
   */
  public static final class RegistrationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<RegistrationServiceFutureStub> {
    private RegistrationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RegistrationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RegistrationServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 预约挂号
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.registration.Registration.RegisterResponse> registerAppointment(
        com.hismixed.grpc.registration.Registration.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterAppointmentMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询号源
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.registration.Registration.GetSchedulesResponse> getSchedules(
        com.hismixed.grpc.registration.Registration.GetSchedulesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSchedulesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_APPOINTMENT = 0;
  private static final int METHODID_GET_SCHEDULES = 1;

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
        case METHODID_REGISTER_APPOINTMENT:
          serviceImpl.registerAppointment((com.hismixed.grpc.registration.Registration.RegisterRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.registration.Registration.RegisterResponse>) responseObserver);
          break;
        case METHODID_GET_SCHEDULES:
          serviceImpl.getSchedules((com.hismixed.grpc.registration.Registration.GetSchedulesRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.registration.Registration.GetSchedulesResponse>) responseObserver);
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
          getRegisterAppointmentMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.registration.Registration.RegisterRequest,
              com.hismixed.grpc.registration.Registration.RegisterResponse>(
                service, METHODID_REGISTER_APPOINTMENT)))
        .addMethod(
          getGetSchedulesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.registration.Registration.GetSchedulesRequest,
              com.hismixed.grpc.registration.Registration.GetSchedulesResponse>(
                service, METHODID_GET_SCHEDULES)))
        .build();
  }

  private static abstract class RegistrationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RegistrationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.registration.Registration.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RegistrationService");
    }
  }

  private static final class RegistrationServiceFileDescriptorSupplier
      extends RegistrationServiceBaseDescriptorSupplier {
    RegistrationServiceFileDescriptorSupplier() {}
  }

  private static final class RegistrationServiceMethodDescriptorSupplier
      extends RegistrationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    RegistrationServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (RegistrationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RegistrationServiceFileDescriptorSupplier())
              .addMethod(getRegisterAppointmentMethod())
              .addMethod(getGetSchedulesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
