package com.hismixed.grpc.prescription;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 处方服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: prescription/prescription.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PrescriptionServiceGrpc {

  private PrescriptionServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "prescription.PrescriptionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest,
      com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse> getCreatePrescriptionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreatePrescription",
      requestType = com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest.class,
      responseType = com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest,
      com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse> getCreatePrescriptionMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest, com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse> getCreatePrescriptionMethod;
    if ((getCreatePrescriptionMethod = PrescriptionServiceGrpc.getCreatePrescriptionMethod) == null) {
      synchronized (PrescriptionServiceGrpc.class) {
        if ((getCreatePrescriptionMethod = PrescriptionServiceGrpc.getCreatePrescriptionMethod) == null) {
          PrescriptionServiceGrpc.getCreatePrescriptionMethod = getCreatePrescriptionMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest, com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreatePrescription"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PrescriptionServiceMethodDescriptorSupplier("CreatePrescription"))
              .build();
        }
      }
    }
    return getCreatePrescriptionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest,
      com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse> getSubmitReviewMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitReview",
      requestType = com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest.class,
      responseType = com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest,
      com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse> getSubmitReviewMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest, com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse> getSubmitReviewMethod;
    if ((getSubmitReviewMethod = PrescriptionServiceGrpc.getSubmitReviewMethod) == null) {
      synchronized (PrescriptionServiceGrpc.class) {
        if ((getSubmitReviewMethod = PrescriptionServiceGrpc.getSubmitReviewMethod) == null) {
          PrescriptionServiceGrpc.getSubmitReviewMethod = getSubmitReviewMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest, com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitReview"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PrescriptionServiceMethodDescriptorSupplier("SubmitReview"))
              .build();
        }
      }
    }
    return getSubmitReviewMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PrescriptionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PrescriptionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PrescriptionServiceStub>() {
        @java.lang.Override
        public PrescriptionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PrescriptionServiceStub(channel, callOptions);
        }
      };
    return PrescriptionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PrescriptionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PrescriptionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PrescriptionServiceBlockingStub>() {
        @java.lang.Override
        public PrescriptionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PrescriptionServiceBlockingStub(channel, callOptions);
        }
      };
    return PrescriptionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PrescriptionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PrescriptionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PrescriptionServiceFutureStub>() {
        @java.lang.Override
        public PrescriptionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PrescriptionServiceFutureStub(channel, callOptions);
        }
      };
    return PrescriptionServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 处方服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 创建处方
     * </pre>
     */
    default void createPrescription(com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreatePrescriptionMethod(), responseObserver);
    }

    /**
     * <pre>
     * 提交审核
     * </pre>
     */
    default void submitReview(com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubmitReviewMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PrescriptionService.
   * <pre>
   * 处方服务
   * </pre>
   */
  public static abstract class PrescriptionServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PrescriptionServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PrescriptionService.
   * <pre>
   * 处方服务
   * </pre>
   */
  public static final class PrescriptionServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PrescriptionServiceStub> {
    private PrescriptionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PrescriptionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PrescriptionServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建处方
     * </pre>
     */
    public void createPrescription(com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreatePrescriptionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 提交审核
     * </pre>
     */
    public void submitReview(com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubmitReviewMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PrescriptionService.
   * <pre>
   * 处方服务
   * </pre>
   */
  public static final class PrescriptionServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PrescriptionServiceBlockingStub> {
    private PrescriptionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PrescriptionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PrescriptionServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建处方
     * </pre>
     */
    public com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse createPrescription(com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreatePrescriptionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 提交审核
     * </pre>
     */
    public com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse submitReview(com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubmitReviewMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PrescriptionService.
   * <pre>
   * 处方服务
   * </pre>
   */
  public static final class PrescriptionServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PrescriptionServiceFutureStub> {
    private PrescriptionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PrescriptionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PrescriptionServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建处方
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse> createPrescription(
        com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreatePrescriptionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 提交审核
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse> submitReview(
        com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubmitReviewMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_PRESCRIPTION = 0;
  private static final int METHODID_SUBMIT_REVIEW = 1;

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
        case METHODID_CREATE_PRESCRIPTION:
          serviceImpl.createPrescription((com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse>) responseObserver);
          break;
        case METHODID_SUBMIT_REVIEW:
          serviceImpl.submitReview((com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse>) responseObserver);
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
          getCreatePrescriptionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.prescription.Prescription.CreatePrescriptionRequest,
              com.hismixed.grpc.prescription.Prescription.CreatePrescriptionResponse>(
                service, METHODID_CREATE_PRESCRIPTION)))
        .addMethod(
          getSubmitReviewMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.prescription.Prescription.SubmitReviewRequest,
              com.hismixed.grpc.prescription.Prescription.SubmitReviewResponse>(
                service, METHODID_SUBMIT_REVIEW)))
        .build();
  }

  private static abstract class PrescriptionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PrescriptionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.prescription.Prescription.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PrescriptionService");
    }
  }

  private static final class PrescriptionServiceFileDescriptorSupplier
      extends PrescriptionServiceBaseDescriptorSupplier {
    PrescriptionServiceFileDescriptorSupplier() {}
  }

  private static final class PrescriptionServiceMethodDescriptorSupplier
      extends PrescriptionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PrescriptionServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (PrescriptionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PrescriptionServiceFileDescriptorSupplier())
              .addMethod(getCreatePrescriptionMethod())
              .addMethod(getSubmitReviewMethod())
              .build();
        }
      }
    }
    return result;
  }
}
