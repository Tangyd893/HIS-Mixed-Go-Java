package com.hismixed.grpc.pharmacy;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 药房服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: pharmacy/pharmacy.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PharmacyServiceGrpc {

  private PharmacyServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "pharmacy.PharmacyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest,
      com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse> getCheckStockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckStock",
      requestType = com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest.class,
      responseType = com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest,
      com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse> getCheckStockMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest, com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse> getCheckStockMethod;
    if ((getCheckStockMethod = PharmacyServiceGrpc.getCheckStockMethod) == null) {
      synchronized (PharmacyServiceGrpc.class) {
        if ((getCheckStockMethod = PharmacyServiceGrpc.getCheckStockMethod) == null) {
          PharmacyServiceGrpc.getCheckStockMethod = getCheckStockMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest, com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckStock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PharmacyServiceMethodDescriptorSupplier("CheckStock"))
              .build();
        }
      }
    }
    return getCheckStockMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest,
      com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse> getDispenseDrugMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DispenseDrug",
      requestType = com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest.class,
      responseType = com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest,
      com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse> getDispenseDrugMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest, com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse> getDispenseDrugMethod;
    if ((getDispenseDrugMethod = PharmacyServiceGrpc.getDispenseDrugMethod) == null) {
      synchronized (PharmacyServiceGrpc.class) {
        if ((getDispenseDrugMethod = PharmacyServiceGrpc.getDispenseDrugMethod) == null) {
          PharmacyServiceGrpc.getDispenseDrugMethod = getDispenseDrugMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest, com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DispenseDrug"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PharmacyServiceMethodDescriptorSupplier("DispenseDrug"))
              .build();
        }
      }
    }
    return getDispenseDrugMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PharmacyServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PharmacyServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PharmacyServiceStub>() {
        @java.lang.Override
        public PharmacyServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PharmacyServiceStub(channel, callOptions);
        }
      };
    return PharmacyServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PharmacyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PharmacyServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PharmacyServiceBlockingStub>() {
        @java.lang.Override
        public PharmacyServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PharmacyServiceBlockingStub(channel, callOptions);
        }
      };
    return PharmacyServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PharmacyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PharmacyServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PharmacyServiceFutureStub>() {
        @java.lang.Override
        public PharmacyServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PharmacyServiceFutureStub(channel, callOptions);
        }
      };
    return PharmacyServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 药房服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 校验库存
     * </pre>
     */
    default void checkStock(com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckStockMethod(), responseObserver);
    }

    /**
     * <pre>
     * 发药
     * </pre>
     */
    default void dispenseDrug(com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDispenseDrugMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PharmacyService.
   * <pre>
   * 药房服务
   * </pre>
   */
  public static abstract class PharmacyServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PharmacyServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PharmacyService.
   * <pre>
   * 药房服务
   * </pre>
   */
  public static final class PharmacyServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PharmacyServiceStub> {
    private PharmacyServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PharmacyServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PharmacyServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 校验库存
     * </pre>
     */
    public void checkStock(com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckStockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 发药
     * </pre>
     */
    public void dispenseDrug(com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDispenseDrugMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PharmacyService.
   * <pre>
   * 药房服务
   * </pre>
   */
  public static final class PharmacyServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PharmacyServiceBlockingStub> {
    private PharmacyServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PharmacyServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PharmacyServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 校验库存
     * </pre>
     */
    public com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse checkStock(com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckStockMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 发药
     * </pre>
     */
    public com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse dispenseDrug(com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDispenseDrugMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PharmacyService.
   * <pre>
   * 药房服务
   * </pre>
   */
  public static final class PharmacyServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PharmacyServiceFutureStub> {
    private PharmacyServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PharmacyServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PharmacyServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 校验库存
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse> checkStock(
        com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckStockMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 发药
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse> dispenseDrug(
        com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDispenseDrugMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK_STOCK = 0;
  private static final int METHODID_DISPENSE_DRUG = 1;

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
        case METHODID_CHECK_STOCK:
          serviceImpl.checkStock((com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse>) responseObserver);
          break;
        case METHODID_DISPENSE_DRUG:
          serviceImpl.dispenseDrug((com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse>) responseObserver);
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
          getCheckStockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.pharmacy.Pharmacy.CheckStockRequest,
              com.hismixed.grpc.pharmacy.Pharmacy.CheckStockResponse>(
                service, METHODID_CHECK_STOCK)))
        .addMethod(
          getDispenseDrugMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugRequest,
              com.hismixed.grpc.pharmacy.Pharmacy.DispenseDrugResponse>(
                service, METHODID_DISPENSE_DRUG)))
        .build();
  }

  private static abstract class PharmacyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PharmacyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.pharmacy.Pharmacy.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PharmacyService");
    }
  }

  private static final class PharmacyServiceFileDescriptorSupplier
      extends PharmacyServiceBaseDescriptorSupplier {
    PharmacyServiceFileDescriptorSupplier() {}
  }

  private static final class PharmacyServiceMethodDescriptorSupplier
      extends PharmacyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PharmacyServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (PharmacyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PharmacyServiceFileDescriptorSupplier())
              .addMethod(getCheckStockMethod())
              .addMethod(getDispenseDrugMethod())
              .build();
        }
      }
    }
    return result;
  }
}
