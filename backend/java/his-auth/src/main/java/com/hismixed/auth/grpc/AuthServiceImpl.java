package com.hismixed.auth.grpc;

import com.hismixed.auth.dto.LoginRequest;
import com.hismixed.auth.dto.LoginResponse;
import com.hismixed.auth.service.AuthService;
import com.hismixed.grpc.auth.AuthServiceGrpc;
import com.hismixed.grpc.auth.Auth;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private final AuthService authService;

    @Override
    public void login(Auth.LoginRequest request, StreamObserver<Auth.LoginResponse> responseObserver) {
        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(request.getUsername());
            loginRequest.setPassword(request.getPassword());

            LoginResponse loginResponse = authService.login(loginRequest);

            Auth.LoginResponse response = Auth.LoginResponse.newBuilder()
                .setAccessToken(loginResponse.getAccessToken())
                .setRefreshToken(loginResponse.getRefreshToken())
                .setTokenType(loginResponse.getTokenType())
                .setExpiresIn(loginResponse.getExpiresIn())
                .setUserId(loginResponse.getUserId())
                .setUsername(loginResponse.getUsername())
                .setRealName(loginResponse.getRealName())
                .addAllRoles(loginResponse.getRoles())
                .addAllPermissions(loginResponse.getPermissions())
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.UNAUTHENTICATED.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void refreshToken(Auth.RefreshTokenRequest request, StreamObserver<Auth.RefreshTokenResponse> responseObserver) {
        try {
            LoginResponse loginResponse = authService.refreshToken(request.getRefreshToken());

            Auth.RefreshTokenResponse response = Auth.RefreshTokenResponse.newBuilder()
                .setAccessToken(loginResponse.getAccessToken())
                .setRefreshToken(loginResponse.getRefreshToken())
                .setTokenType(loginResponse.getTokenType())
                .setExpiresIn(loginResponse.getExpiresIn())
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.UNAUTHENTICATED.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}