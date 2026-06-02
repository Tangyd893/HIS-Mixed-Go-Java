package com.hismixed.grpc.inpatient;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 住院服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: inpatient/inpatient.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class InpatientServiceGrpc {

  private InpatientServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "inpatient.InpatientService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest,
      com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse> getAdmitPatientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AdmitPatient",
      requestType = com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest.class,
      responseType = com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest,
      com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse> getAdmitPatientMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest, com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse> getAdmitPatientMethod;
    if ((getAdmitPatientMethod = InpatientServiceGrpc.getAdmitPatientMethod) == null) {
      synchronized (InpatientServiceGrpc.class) {
        if ((getAdmitPatientMethod = InpatientServiceGrpc.getAdmitPatientMethod) == null) {
          InpatientServiceGrpc.getAdmitPatientMethod = getAdmitPatientMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest, com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AdmitPatient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InpatientServiceMethodDescriptorSupplier("AdmitPatient"))
              .build();
        }
      }
    }
    return getAdmitPatientMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest,
      com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse> getDischargePatientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DischargePatient",
      requestType = com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest.class,
      responseType = com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest,
      com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse> getDischargePatientMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest, com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse> getDischargePatientMethod;
    if ((getDischargePatientMethod = InpatientServiceGrpc.getDischargePatientMethod) == null) {
      synchronized (InpatientServiceGrpc.class) {
        if ((getDischargePatientMethod = InpatientServiceGrpc.getDischargePatientMethod) == null) {
          InpatientServiceGrpc.getDischargePatientMethod = getDischargePatientMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest, com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DischargePatient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InpatientServiceMethodDescriptorSupplier("DischargePatient"))
              .build();
        }
      }
    }
    return getDischargePatientMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InpatientServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InpatientServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InpatientServiceStub>() {
        @java.lang.Override
        public InpatientServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InpatientServiceStub(channel, callOptions);
        }
      };
    return InpatientServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InpatientServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InpatientServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InpatientServiceBlockingStub>() {
        @java.lang.Override
        public InpatientServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InpatientServiceBlockingStub(channel, callOptions);
        }
      };
    return InpatientServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InpatientServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InpatientServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InpatientServiceFutureStub>() {
        @java.lang.Override
        public InpatientServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InpatientServiceFutureStub(channel, callOptions);
        }
      };
    return InpatientServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 住院服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 办理入院
     * </pre>
     */
    default void admitPatient(com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAdmitPatientMethod(), responseObserver);
    }

    /**
     * <pre>
     * 办理出院
     * </pre>
     */
    default void dischargePatient(com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDischargePatientMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service InpatientService.
   * <pre>
   * 住院服务
   * </pre>
   */
  public static abstract class InpatientServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return InpatientServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service InpatientService.
   * <pre>
   * 住院服务
   * </pre>
   */
  public static final class InpatientServiceStub
      extends io.grpc.stub.AbstractAsyncStub<InpatientServiceStub> {
    private InpatientServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InpatientServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InpatientServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 办理入院
     * </pre>
     */
    public void admitPatient(com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAdmitPatientMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 办理出院
     * </pre>
     */
    public void dischargePatient(com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDischargePatientMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service InpatientService.
   * <pre>
   * 住院服务
   * </pre>
   */
  public static final class InpatientServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<InpatientServiceBlockingStub> {
    private InpatientServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InpatientServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InpatientServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 办理入院
     * </pre>
     */
    public com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse admitPatient(com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAdmitPatientMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 办理出院
     * </pre>
     */
    public com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse dischargePatient(com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDischargePatientMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service InpatientService.
   * <pre>
   * 住院服务
   * </pre>
   */
  public static final class InpatientServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<InpatientServiceFutureStub> {
    private InpatientServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InpatientServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InpatientServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 办理入院
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse> admitPatient(
        com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAdmitPatientMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 办理出院
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse> dischargePatient(
        com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDischargePatientMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADMIT_PATIENT = 0;
  private static final int METHODID_DISCHARGE_PATIENT = 1;

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
        case METHODID_ADMIT_PATIENT:
          serviceImpl.admitPatient((com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse>) responseObserver);
          break;
        case METHODID_DISCHARGE_PATIENT:
          serviceImpl.dischargePatient((com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse>) responseObserver);
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
          getAdmitPatientMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.inpatient.Inpatient.AdmitPatientRequest,
              com.hismixed.grpc.inpatient.Inpatient.AdmitPatientResponse>(
                service, METHODID_ADMIT_PATIENT)))
        .addMethod(
          getDischargePatientMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.inpatient.Inpatient.DischargePatientRequest,
              com.hismixed.grpc.inpatient.Inpatient.DischargePatientResponse>(
                service, METHODID_DISCHARGE_PATIENT)))
        .build();
  }

  private static abstract class InpatientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InpatientServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.inpatient.Inpatient.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InpatientService");
    }
  }

  private static final class InpatientServiceFileDescriptorSupplier
      extends InpatientServiceBaseDescriptorSupplier {
    InpatientServiceFileDescriptorSupplier() {}
  }

  private static final class InpatientServiceMethodDescriptorSupplier
      extends InpatientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    InpatientServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (InpatientServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InpatientServiceFileDescriptorSupplier())
              .addMethod(getAdmitPatientMethod())
              .addMethod(getDischargePatientMethod())
              .build();
        }
      }
    }
    return result;
  }
}
