package com.hismixed.system.controller;

import com.hismixed.system.entity.AuditLog;
import com.hismixed.system.entity.DictItem;
import com.hismixed.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    // 字典管理接口

    @GetMapping("/dicts")
    public ResponseEntity<Page<DictItem>> listDicts(
            @RequestParam(required = false) String dictType,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "false") boolean includeDisabled,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(systemService.listDicts(dictType, keyword, includeDisabled, pageable));
    }

    @GetMapping("/dicts/type/{dictType}")
    public ResponseEntity<List<DictItem>> getDictByType(@PathVariable String dictType) {
        return ResponseEntity.ok(systemService.getDictByType(dictType));
    }

    @GetMapping("/dicts/{id}")
    public ResponseEntity<DictItem> getDict(@PathVariable Long id) {
        return ResponseEntity.ok(systemService.getDictById(id));
    }

    @PostMapping("/dicts")
    public ResponseEntity<DictItem> createDict(@RequestBody DictItem dictItem) {
        return ResponseEntity.ok(systemService.createDict(dictItem));
    }

    @PutMapping("/dicts/{id}")
    public ResponseEntity<DictItem> updateDict(@PathVariable Long id, @RequestBody DictItem dictItem) {
        return ResponseEntity.ok(systemService.updateDict(id, dictItem));
    }

    @DeleteMapping("/dicts/{id}")
    public ResponseEntity<Void> deleteDict(@PathVariable Long id) {
        systemService.deleteDict(id);
        return ResponseEntity.ok().build();
    }

    // 审计日志接口

    @GetMapping("/audit-logs")
    public ResponseEntity<Page<AuditLog>> getAuditLogs(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String resourceType,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(systemService.getAuditLogs(userId, action, resourceType, startTime, endTime, pageable));
    }

    @PostMapping("/audit-logs")
    public ResponseEntity<AuditLog> createAuditLog(@RequestBody AuditLog auditLog) {
        return ResponseEntity.ok(systemService.createAuditLog(auditLog));
    }
}
