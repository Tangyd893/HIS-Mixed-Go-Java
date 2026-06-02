package com.hismixed.grpc.clinic;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 门诊服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: clinic/clinic.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ClinicServiceGrpc {

  private ClinicServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "clinic.ClinicService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest,
      com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse> getCreateEncounterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateEncounter",
      requestType = com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest.class,
      responseType = com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest,
      com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse> getCreateEncounterMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest, com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse> getCreateEncounterMethod;
    if ((getCreateEncounterMethod = ClinicServiceGrpc.getCreateEncounterMethod) == null) {
      synchronized (ClinicServiceGrpc.class) {
        if ((getCreateEncounterMethod = ClinicServiceGrpc.getCreateEncounterMethod) == null) {
          ClinicServiceGrpc.getCreateEncounterMethod = getCreateEncounterMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest, com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateEncounter"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ClinicServiceMethodDescriptorSupplier("CreateEncounter"))
              .build();
        }
      }
    }
    return getCreateEncounterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest,
      com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse> getAddDiagnosisMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddDiagnosis",
      requestType = com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest.class,
      responseType = com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest,
      com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse> getAddDiagnosisMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest, com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse> getAddDiagnosisMethod;
    if ((getAddDiagnosisMethod = ClinicServiceGrpc.getAddDiagnosisMethod) == null) {
      synchronized (ClinicServiceGrpc.class) {
        if ((getAddDiagnosisMethod = ClinicServiceGrpc.getAddDiagnosisMethod) == null) {
          ClinicServiceGrpc.getAddDiagnosisMethod = getAddDiagnosisMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest, com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddDiagnosis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ClinicServiceMethodDescriptorSupplier("AddDiagnosis"))
              .build();
        }
      }
    }
    return getAddDiagnosisMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ClinicServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClinicServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClinicServiceStub>() {
        @java.lang.Override
        public ClinicServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClinicServiceStub(channel, callOptions);
        }
      };
    return ClinicServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ClinicServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClinicServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClinicServiceBlockingStub>() {
        @java.lang.Override
        public ClinicServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClinicServiceBlockingStub(channel, callOptions);
        }
      };
    return ClinicServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ClinicServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClinicServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClinicServiceFutureStub>() {
        @java.lang.Override
        public ClinicServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClinicServiceFutureStub(channel, callOptions);
        }
      };
    return ClinicServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 门诊服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 创建就诊记录
     * </pre>
     */
    default void createEncounter(com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateEncounterMethod(), responseObserver);
    }

    /**
     * <pre>
     * 添加诊断
     * </pre>
     */
    default void addDiagnosis(com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddDiagnosisMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ClinicService.
   * <pre>
   * 门诊服务
   * </pre>
   */
  public static abstract class ClinicServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ClinicServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ClinicService.
   * <pre>
   * 门诊服务
   * </pre>
   */
  public static final class ClinicServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ClinicServiceStub> {
    private ClinicServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClinicServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClinicServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建就诊记录
     * </pre>
     */
    public void createEncounter(com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateEncounterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 添加诊断
     * </pre>
     */
    public void addDiagnosis(com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddDiagnosisMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ClinicService.
   * <pre>
   * 门诊服务
   * </pre>
   */
  public static final class ClinicServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ClinicServiceBlockingStub> {
    private ClinicServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClinicServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClinicServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建就诊记录
     * </pre>
     */
    public com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse createEncounter(com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateEncounterMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 添加诊断
     * </pre>
     */
    public com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse addDiagnosis(com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddDiagnosisMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ClinicService.
   * <pre>
   * 门诊服务
   * </pre>
   */
  public static final class ClinicServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ClinicServiceFutureStub> {
    private ClinicServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClinicServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClinicServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建就诊记录
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse> createEncounter(
        com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateEncounterMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 添加诊断
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse> addDiagnosis(
        com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddDiagnosisMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_ENCOUNTER = 0;
  private static final int METHODID_ADD_DIAGNOSIS = 1;

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
        case METHODID_CREATE_ENCOUNTER:
          serviceImpl.createEncounter((com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse>) responseObserver);
          break;
        case METHODID_ADD_DIAGNOSIS:
          serviceImpl.addDiagnosis((com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse>) responseObserver);
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
          getCreateEncounterMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.clinic.Clinic.CreateEncounterRequest,
              com.hismixed.grpc.clinic.Clinic.CreateEncounterResponse>(
                service, METHODID_CREATE_ENCOUNTER)))
        .addMethod(
          getAddDiagnosisMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.clinic.Clinic.AddDiagnosisRequest,
              com.hismixed.grpc.clinic.Clinic.AddDiagnosisResponse>(
                service, METHODID_ADD_DIAGNOSIS)))
        .build();
  }

  private static abstract class ClinicServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ClinicServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.clinic.Clinic.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ClinicService");
    }
  }

  private static final class ClinicServiceFileDescriptorSupplier
      extends ClinicServiceBaseDescriptorSupplier {
    ClinicServiceFileDescriptorSupplier() {}
  }

  private static final class ClinicServiceMethodDescriptorSupplier
      extends ClinicServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ClinicServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ClinicServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ClinicServiceFileDescriptorSupplier())
              .addMethod(getCreateEncounterMethod())
              .addMethod(getAddDiagnosisMethod())
              .build();
        }
      }
    }
    return result;
  }
}
