package com.hismixed.grpc.billing;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 收费服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: billing/billing.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BillingServiceGrpc {

  private BillingServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "billing.BillingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.billing.Billing.CalculateFeeRequest,
      com.hismixed.grpc.billing.Billing.CalculateFeeResponse> getCalculateFeeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CalculateFee",
      requestType = com.hismixed.grpc.billing.Billing.CalculateFeeRequest.class,
      responseType = com.hismixed.grpc.billing.Billing.CalculateFeeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.billing.Billing.CalculateFeeRequest,
      com.hismixed.grpc.billing.Billing.CalculateFeeResponse> getCalculateFeeMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.billing.Billing.CalculateFeeRequest, com.hismixed.grpc.billing.Billing.CalculateFeeResponse> getCalculateFeeMethod;
    if ((getCalculateFeeMethod = BillingServiceGrpc.getCalculateFeeMethod) == null) {
      synchronized (BillingServiceGrpc.class) {
        if ((getCalculateFeeMethod = BillingServiceGrpc.getCalculateFeeMethod) == null) {
          BillingServiceGrpc.getCalculateFeeMethod = getCalculateFeeMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.billing.Billing.CalculateFeeRequest, com.hismixed.grpc.billing.Billing.CalculateFeeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CalculateFee"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.billing.Billing.CalculateFeeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.billing.Billing.CalculateFeeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BillingServiceMethodDescriptorSupplier("CalculateFee"))
              .build();
        }
      }
    }
    return getCalculateFeeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.billing.Billing.ProcessPaymentRequest,
      com.hismixed.grpc.billing.Billing.ProcessPaymentResponse> getProcessPaymentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ProcessPayment",
      requestType = com.hismixed.grpc.billing.Billing.ProcessPaymentRequest.class,
      responseType = com.hismixed.grpc.billing.Billing.ProcessPaymentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.billing.Billing.ProcessPaymentRequest,
      com.hismixed.grpc.billing.Billing.ProcessPaymentResponse> getProcessPaymentMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.billing.Billing.ProcessPaymentRequest, com.hismixed.grpc.billing.Billing.ProcessPaymentResponse> getProcessPaymentMethod;
    if ((getProcessPaymentMethod = BillingServiceGrpc.getProcessPaymentMethod) == null) {
      synchronized (BillingServiceGrpc.class) {
        if ((getProcessPaymentMethod = BillingServiceGrpc.getProcessPaymentMethod) == null) {
          BillingServiceGrpc.getProcessPaymentMethod = getProcessPaymentMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.billing.Billing.ProcessPaymentRequest, com.hismixed.grpc.billing.Billing.ProcessPaymentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ProcessPayment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.billing.Billing.ProcessPaymentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.billing.Billing.ProcessPaymentResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BillingServiceMethodDescriptorSupplier("ProcessPayment"))
              .build();
        }
      }
    }
    return getProcessPaymentMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BillingServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BillingServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BillingServiceStub>() {
        @java.lang.Override
        public BillingServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BillingServiceStub(channel, callOptions);
        }
      };
    return BillingServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BillingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BillingServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BillingServiceBlockingStub>() {
        @java.lang.Override
        public BillingServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BillingServiceBlockingStub(channel, callOptions);
        }
      };
    return BillingServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BillingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BillingServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BillingServiceFutureStub>() {
        @java.lang.Override
        public BillingServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BillingServiceFutureStub(channel, callOptions);
        }
      };
    return BillingServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 收费服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 计算费用
     * </pre>
     */
    default void calculateFee(com.hismixed.grpc.billing.Billing.CalculateFeeRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.billing.Billing.CalculateFeeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCalculateFeeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 处理支付
     * </pre>
     */
    default void processPayment(com.hismixed.grpc.billing.Billing.ProcessPaymentRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.billing.Billing.ProcessPaymentResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getProcessPaymentMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service BillingService.
   * <pre>
   * 收费服务
   * </pre>
   */
  public static abstract class BillingServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return BillingServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service BillingService.
   * <pre>
   * 收费服务
   * </pre>
   */
  public static final class BillingServiceStub
      extends io.grpc.stub.AbstractAsyncStub<BillingServiceStub> {
    private BillingServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BillingServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BillingServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 计算费用
     * </pre>
     */
    public void calculateFee(com.hismixed.grpc.billing.Billing.CalculateFeeRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.billing.Billing.CalculateFeeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCalculateFeeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 处理支付
     * </pre>
     */
    public void processPayment(com.hismixed.grpc.billing.Billing.ProcessPaymentRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.billing.Billing.ProcessPaymentResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getProcessPaymentMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service BillingService.
   * <pre>
   * 收费服务
   * </pre>
   */
  public static final class BillingServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<BillingServiceBlockingStub> {
    private BillingServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BillingServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BillingServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 计算费用
     * </pre>
     */
    public com.hismixed.grpc.billing.Billing.CalculateFeeResponse calculateFee(com.hismixed.grpc.billing.Billing.CalculateFeeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCalculateFeeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 处理支付
     * </pre>
     */
    public com.hismixed.grpc.billing.Billing.ProcessPaymentResponse processPayment(com.hismixed.grpc.billing.Billing.ProcessPaymentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getProcessPaymentMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service BillingService.
   * <pre>
   * 收费服务
   * </pre>
   */
  public static final class BillingServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<BillingServiceFutureStub> {
    private BillingServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BillingServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BillingServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 计算费用
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.billing.Billing.CalculateFeeResponse> calculateFee(
        com.hismixed.grpc.billing.Billing.CalculateFeeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCalculateFeeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 处理支付
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.billing.Billing.ProcessPaymentResponse> processPayment(
        com.hismixed.grpc.billing.Billing.ProcessPaymentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getProcessPaymentMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CALCULATE_FEE = 0;
  private static final int METHODID_PROCESS_PAYMENT = 1;

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
        case METHODID_CALCULATE_FEE:
          serviceImpl.calculateFee((com.hismixed.grpc.billing.Billing.CalculateFeeRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.billing.Billing.CalculateFeeResponse>) responseObserver);
          break;
        case METHODID_PROCESS_PAYMENT:
          serviceImpl.processPayment((com.hismixed.grpc.billing.Billing.ProcessPaymentRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.billing.Billing.ProcessPaymentResponse>) responseObserver);
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
          getCalculateFeeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.billing.Billing.CalculateFeeRequest,
              com.hismixed.grpc.billing.Billing.CalculateFeeResponse>(
                service, METHODID_CALCULATE_FEE)))
        .addMethod(
          getProcessPaymentMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.billing.Billing.ProcessPaymentRequest,
              com.hismixed.grpc.billing.Billing.ProcessPaymentResponse>(
                service, METHODID_PROCESS_PAYMENT)))
        .build();
  }

  private static abstract class BillingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BillingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.billing.Billing.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BillingService");
    }
  }

  private static final class BillingServiceFileDescriptorSupplier
      extends BillingServiceBaseDescriptorSupplier {
    BillingServiceFileDescriptorSupplier() {}
  }

  private static final class BillingServiceMethodDescriptorSupplier
      extends BillingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    BillingServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (BillingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BillingServiceFileDescriptorSupplier())
              .addMethod(getCalculateFeeMethod())
              .addMethod(getProcessPaymentMethod())
              .build();
        }
      }
    }
    return result;
  }
}
