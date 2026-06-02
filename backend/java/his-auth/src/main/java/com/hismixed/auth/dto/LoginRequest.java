package com.hismixed.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String grantType;
    private String clientId;
}