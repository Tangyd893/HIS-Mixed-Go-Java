package com.hismixed.clinic.controller;

import com.hismixed.clinic.entity.Referral;
import com.hismixed.clinic.service.ReferralService;
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
@RequestMapping("/api/referrals")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listReferrals(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Referral> referralPage = referralService.listReferrals(patientId, status, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("list", referralPage.getContent());
        response.put("total", referralPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Referral> getReferral(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(referralService.getReferralById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Referral>> getReferralsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(referralService.getReferralsByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Referral>> getReferralsByDoctor(
            @PathVariable Long doctorId,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(referralService.getReferralsByDoctor(doctorId, status));
    }

    @PostMapping
    public ResponseEntity<Referral> createReferral(@RequestBody Referral referral) {
        return ResponseEntity.ok(referralService.createReferral(referral));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Referral> acceptReferral(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(referralService.acceptReferral(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Referral> rejectReferral(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            return ResponseEntity.ok(referralService.rejectReferral(id, request.get("reason")));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Referral> completeReferral(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(referralService.completeReferral(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countReferrals(@RequestParam(required = false) String status) {
        Map<String, Long> response = new HashMap<>();
        response.put("count", referralService.countByStatus(status));
        return ResponseEntity.ok(response);
    }
}
