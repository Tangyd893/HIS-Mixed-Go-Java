package com.hismixed.auth.service;

import com.hismixed.auth.dto.LoginRequest;
import com.hismixed.auth.dto.LoginResponse;
import com.hismixed.auth.entity.RefreshToken;
import com.hismixed.auth.entity.Role;
import com.hismixed.auth.entity.User;
import com.hismixed.auth.repository.RefreshTokenRepository;
import com.hismixed.auth.repository.RoleRepository;
import com.hismixed.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.secret:his-mixed-jwt-secret-key-2026-secure}")
    private String jwtSecret;

    @Value("${jwt.expiration:7200}")
    private Long jwtExpiration;

    public LoginResponse login(LoginRequest request) {
        // 查询用户
        User user = userRepository.findByUsername(request.getUsername())
            .orElse(null);

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        if (user.getStatus() != 1) {
            throw new RuntimeException("账号已禁用");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 查询角色
        List<Role> roles = roleRepository.selectRolesByUserId(user.getId());
        List<String> roleCodes = roles.stream().map(Role::getCode).toList();

        // 生成Token
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        String accessToken = Jwts.builder()
            .setSubject(user.getUsername())
            .claim("userId", user.getId())
            .claim("realName", user.getRealName())
            .claim("roles", roleCodes)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        String refreshToken = UUID.randomUUID().toString().replace("-", "");

        // 保存刷新令牌
        RefreshToken token = new RefreshToken();
        token.setUserId(user.getId());
        token.setToken(refreshToken);
        token.setExpiresAt(LocalDateTime.now().plusDays(7));
        token.setRevoked(false);
        refreshTokenRepository.save(token);

        // 更新最后登录时间
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setTokenType("Bearer");
        response.setExpiresIn(jwtExpiration);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRealName(user.getRealName());
        response.setRoles(roleCodes);
        response.setPermissions(new ArrayList<>());

        return response;
    }

    public LoginResponse refreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByTokenAndRevoked(refreshToken, false)
            .orElse(null);

        if (token == null || token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("刷新令牌无效或已过期");
        }

        User user = userRepository.findById(token.getUserId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (user.getStatus() != 1) {
            throw new RuntimeException("账号已禁用");
        }

        // 撤销旧令牌
        token.setRevoked(true);
        refreshTokenRepository.save(token);

        // 查询角色
        List<Role> roles = roleRepository.selectRolesByUserId(user.getId());
        List<String> roleCodes = roles.stream().map(Role::getCode).toList();

        // 生成新 AccessToken
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        String accessToken = Jwts.builder()
            .setSubject(user.getUsername())
            .claim("userId", user.getId())
            .claim("realName", user.getRealName())
            .claim("roles", roleCodes)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        // 生成新 RefreshToken
        String newRefreshToken = UUID.randomUUID().toString().replace("-", "");
        RefreshToken newToken = new RefreshToken();
        newToken.setUserId(user.getId());
        newToken.setToken(newRefreshToken);
        newToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        newToken.setRevoked(false);
        refreshTokenRepository.save(newToken);

        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(newRefreshToken);
        response.setTokenType("Bearer");
        response.setExpiresIn(jwtExpiration);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRealName(user.getRealName());
        response.setRoles(roleCodes);
        response.setPermissions(new ArrayList<>());

        return response;
    }
}