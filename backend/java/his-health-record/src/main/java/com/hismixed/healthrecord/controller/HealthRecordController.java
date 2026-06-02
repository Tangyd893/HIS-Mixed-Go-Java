package com.hismixed.healthrecord.controller;

import com.hismixed.healthrecord.entity.HealthRecord;
import com.hismixed.healthrecord.entity.HealthTimeline;
import com.hismixed.healthrecord.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/health-record")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<HealthRecord> getRecord(@PathVariable Long patientId) {
        return ResponseEntity.ok(healthRecordService.getRecordByPatientId(patientId));
    }

    @PostMapping
    public ResponseEntity<HealthRecord> createOrUpdateRecord(@RequestBody HealthRecord record) {
        return ResponseEntity.ok(healthRecordService.createOrUpdateRecord(record));
    }

    @GetMapping("/overview/{patientId}")
    public ResponseEntity<Map<String, Object>> getOverview(@PathVariable Long patientId) {
        return ResponseEntity.ok(healthRecordService.getHealthOverview(patientId));
    }

    @GetMapping("/timeline/{patientId}")
    public ResponseEntity<Page<HealthTimeline>> getTimeline(
            @PathVariable Long patientId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(healthRecordService.getTimeline(patientId, startDate, endDate, pageable));
    }

    @PostMapping("/timeline")
    public ResponseEntity<HealthTimeline> addTimelineEntry(@RequestBody HealthTimeline entry) {
        return ResponseEntity.ok(healthRecordService.addTimelineEntry(entry));
    }
}
