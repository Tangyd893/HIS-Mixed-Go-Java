package com.hismixed.auth.controller;

import com.hismixed.auth.dto.RegisterRequest;
import com.hismixed.auth.entity.User;
import com.hismixed.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // 检查用户名是否已存在
            User existingUser = userService.findByUsername(request.getUsername());
            if (existingUser != null) {
                Map<String, Object> error = new HashMap<>();
                error.put("code", 400);
                error.put("message", "用户名已存在");
                return ResponseEntity.badRequest().body(error);
            }

            // 创建新用户
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            user.setRealName(request.getRealName());
            user.setPhone(request.getPhone());
            user.setEmail(request.getEmail());
            user.setStatus(1);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            userService.createUser(user);

            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "注册成功");
            result.put("data", Map.of("userId", user.getId()));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 500);
            error.put("message", "注册失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            if (user == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("code", 404);
                error.put("message", "用户不存在");
                return ResponseEntity.status(404).body(error);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("message", "success");
            result.put("data", user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 500);
            error.put("message", "查询失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/debug/password")
    public ResponseEntity<?> debugPassword(@RequestParam String raw, @RequestParam String encoded) {
        Map<String, Object> result = new HashMap<>();
        result.put("matches", passwordEncoder.matches(raw, encoded));
        result.put("encoderClass", passwordEncoder.getClass().getName());
        return ResponseEntity.ok(result);
    }
}
