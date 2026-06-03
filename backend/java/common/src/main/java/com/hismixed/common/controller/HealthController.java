package com.hismixed.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired(required = false)
    private DataSource dataSource;

    @GetMapping("/api/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/ready")
    public ResponseEntity<Map<String, Object>> ready() {
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", System.currentTimeMillis());

        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection()) {
                if (connection.isValid(1)) {
                    result.put("status", "UP");
                    result.put("database", "UP");
                    return ResponseEntity.ok(result);
                }
            } catch (SQLException e) {
                // Database connection failed
            }
            result.put("status", "DOWN");
            result.put("database", "DOWN");
            return ResponseEntity.status(503).body(result);
        }

        result.put("status", "UP");
        return ResponseEntity.ok(result);
    }
}
