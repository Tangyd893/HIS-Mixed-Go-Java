package com.hismixed.grpc.auth;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 认证服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: auth/auth.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AuthServiceGrpc {

  private AuthServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "auth.AuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.auth.Auth.LoginRequest,
      com.hismixed.grpc.auth.Auth.LoginResponse> getLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Login",
      requestType = com.hismixed.grpc.auth.Auth.LoginRequest.class,
      responseType = com.hismixed.grpc.auth.Auth.LoginResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.auth.Auth.LoginRequest,
      com.hismixed.grpc.auth.Auth.LoginResponse> getLoginMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.auth.Auth.LoginRequest, com.hismixed.grpc.auth.Auth.LoginResponse> getLoginMethod;
    if ((getLoginMethod = AuthServiceGrpc.getLoginMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getLoginMethod = AuthServiceGrpc.getLoginMethod) == null) {
          AuthServiceGrpc.getLoginMethod = getLoginMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.auth.Auth.LoginRequest, com.hismixed.grpc.auth.Auth.LoginResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.auth.Auth.LoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.auth.Auth.LoginResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("Login"))
              .build();
        }
      }
    }
    return getLoginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hismixed.grpc.auth.Auth.RefreshTokenRequest,
      com.hismixed.grpc.auth.Auth.RefreshTokenResponse> getRefreshTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RefreshToken",
      requestType = com.hismixed.grpc.auth.Auth.RefreshTokenRequest.class,
      responseType = com.hismixed.grpc.auth.Auth.RefreshTokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hismixed.grpc.auth.Auth.RefreshTokenRequest,
      com.hismixed.grpc.auth.Auth.RefreshTokenResponse> getRefreshTokenMethod() {
    io.grpc.MethodDescriptor<com.hismixed.grpc.auth.Auth.RefreshTokenRequest, com.hismixed.grpc.auth.Auth.RefreshTokenResponse> getRefreshTokenMethod;
    if ((getRefreshTokenMethod = AuthServiceGrpc.getRefreshTokenMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getRefreshTokenMethod = AuthServiceGrpc.getRefreshTokenMethod) == null) {
          AuthServiceGrpc.getRefreshTokenMethod = getRefreshTokenMethod =
              io.grpc.MethodDescriptor.<com.hismixed.grpc.auth.Auth.RefreshTokenRequest, com.hismixed.grpc.auth.Auth.RefreshTokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RefreshToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.auth.Auth.RefreshTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hismixed.grpc.auth.Auth.RefreshTokenResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("RefreshToken"))
              .build();
        }
      }
    }
    return getRefreshTokenMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub>() {
        @java.lang.Override
        public AuthServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceStub(channel, callOptions);
        }
      };
    return AuthServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub>() {
        @java.lang.Override
        public AuthServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceBlockingStub(channel, callOptions);
        }
      };
    return AuthServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub>() {
        @java.lang.Override
        public AuthServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceFutureStub(channel, callOptions);
        }
      };
    return AuthServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 认证服务
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 登录
     * </pre>
     */
    default void login(com.hismixed.grpc.auth.Auth.LoginRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.auth.Auth.LoginResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginMethod(), responseObserver);
    }

    /**
     * <pre>
     * 刷新令牌
     * </pre>
     */
    default void refreshToken(com.hismixed.grpc.auth.Auth.RefreshTokenRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.auth.Auth.RefreshTokenResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRefreshTokenMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AuthService.
   * <pre>
   * 认证服务
   * </pre>
   */
  public static abstract class AuthServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AuthServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AuthService.
   * <pre>
   * 认证服务
   * </pre>
   */
  public static final class AuthServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AuthServiceStub> {
    private AuthServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 登录
     * </pre>
     */
    public void login(com.hismixed.grpc.auth.Auth.LoginRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.auth.Auth.LoginResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 刷新令牌
     * </pre>
     */
    public void refreshToken(com.hismixed.grpc.auth.Auth.RefreshTokenRequest request,
        io.grpc.stub.StreamObserver<com.hismixed.grpc.auth.Auth.RefreshTokenResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRefreshTokenMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AuthService.
   * <pre>
   * 认证服务
   * </pre>
   */
  public static final class AuthServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AuthServiceBlockingStub> {
    private AuthServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 登录
     * </pre>
     */
    public com.hismixed.grpc.auth.Auth.LoginResponse login(com.hismixed.grpc.auth.Auth.LoginRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 刷新令牌
     * </pre>
     */
    public com.hismixed.grpc.auth.Auth.RefreshTokenResponse refreshToken(com.hismixed.grpc.auth.Auth.RefreshTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRefreshTokenMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AuthService.
   * <pre>
   * 认证服务
   * </pre>
   */
  public static final class AuthServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AuthServiceFutureStub> {
    private AuthServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 登录
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.auth.Auth.LoginResponse> login(
        com.hismixed.grpc.auth.Auth.LoginRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 刷新令牌
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hismixed.grpc.auth.Auth.RefreshTokenResponse> refreshToken(
        com.hismixed.grpc.auth.Auth.RefreshTokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRefreshTokenMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN = 0;
  private static final int METHODID_REFRESH_TOKEN = 1;

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
        case METHODID_LOGIN:
          serviceImpl.login((com.hismixed.grpc.auth.Auth.LoginRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.auth.Auth.LoginResponse>) responseObserver);
          break;
        case METHODID_REFRESH_TOKEN:
          serviceImpl.refreshToken((com.hismixed.grpc.auth.Auth.RefreshTokenRequest) request,
              (io.grpc.stub.StreamObserver<com.hismixed.grpc.auth.Auth.RefreshTokenResponse>) responseObserver);
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
          getLoginMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.auth.Auth.LoginRequest,
              com.hismixed.grpc.auth.Auth.LoginResponse>(
                service, METHODID_LOGIN)))
        .addMethod(
          getRefreshTokenMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hismixed.grpc.auth.Auth.RefreshTokenRequest,
              com.hismixed.grpc.auth.Auth.RefreshTokenResponse>(
                service, METHODID_REFRESH_TOKEN)))
        .build();
  }

  private static abstract class AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hismixed.grpc.auth.Auth.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthService");
    }
  }

  private static final class AuthServiceFileDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier {
    AuthServiceFileDescriptorSupplier() {}
  }

  private static final class AuthServiceMethodDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AuthServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AuthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthServiceFileDescriptorSupplier())
              .addMethod(getLoginMethod())
              .addMethod(getRefreshTokenMethod())
              .build();
        }
      }
    }
    return result;
  }
}
