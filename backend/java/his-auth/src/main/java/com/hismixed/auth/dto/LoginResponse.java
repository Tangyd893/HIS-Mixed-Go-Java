package com.hismixed.auth.dto;

import lombok.Data;
import java.util.List;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private Long userId;
    private String username;
    private String realName;
    private List<String> roles;
    private List<String> permissions;
}