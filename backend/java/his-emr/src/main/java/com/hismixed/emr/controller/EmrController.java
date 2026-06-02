package com.hismixed.emr.controller;

import com.hismixed.emr.entity.EmrRecord;
import com.hismixed.emr.service.EmrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emr")
public class EmrController {

    @Autowired
    private EmrService emrService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listEmrRecords(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<EmrRecord> emrPage = emrService.listEmrRecords(patientId, doctorId, status, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("list", emrPage.getContent());
        response.put("total", emrPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmrRecord> getEmrRecord(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(emrService.getEmrRecordById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<EmrRecord>> getEmrRecordsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(emrService.getEmrRecordsByPatient(patientId));
    }

    @GetMapping("/encounter/{encounterId}")
    public ResponseEntity<List<EmrRecord>> getEmrRecordsByEncounter(@PathVariable Long encounterId) {
        return ResponseEntity.ok(emrService.getEmrRecordsByEncounter(encounterId));
    }

    @PostMapping
    public ResponseEntity<EmrRecord> createEmrRecord(@RequestBody EmrRecord emrRecord) {
        return ResponseEntity.ok(emrService.createEmrRecord(emrRecord));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmrRecord> updateEmrRecord(@PathVariable Long id, @RequestBody EmrRecord emrRecord) {
        try {
            return ResponseEntity.ok(emrService.updateEmrRecord(id, emrRecord));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<EmrRecord> submitEmrRecord(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(emrService.submitEmrRecord(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<EmrRecord> approveEmrRecord(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Long checkerId = Long.valueOf(request.get("checkerId").toString());
            Integer qcLevel = Integer.valueOf(request.get("qcLevel").toString());
            return ResponseEntity.ok(emrService.approveEmrRecord(id, checkerId, qcLevel));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countEmrRecords(@RequestParam(required = false) String status) {
        Map<String, Long> response = new HashMap<>();
        response.put("count", emrService.countByStatus(status));
        return ResponseEntity.ok(response);
    }
}
