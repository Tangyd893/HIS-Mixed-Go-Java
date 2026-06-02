package com.hismixed.prescription.controller;

import com.hismixed.prescription.entity.Prescription;
import com.hismixed.prescription.entity.PrescriptionItem;
import com.hismixed.prescription.service.PrescriptionService;
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
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listPrescriptions(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Prescription> prescriptionPage = prescriptionService.listPrescriptions(patientId, doctorId, status, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("list", prescriptionPage.getContent());
        response.put("total", prescriptionPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescription(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/no/{prescriptionNo}")
    public ResponseEntity<Prescription> getPrescriptionByNo(@PathVariable String prescriptionNo) {
        try {
            return ResponseEntity.ok(prescriptionService.getPrescriptionByNo(prescriptionNo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatient(patientId));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<PrescriptionItem>> getPrescriptionItems(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionItems(id));
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Map<String, Object> request) {
        Prescription prescription = new Prescription();
        prescription.setPatientId(Long.valueOf(request.get("patientId").toString()));
        prescription.setDoctorId(Long.valueOf(request.get("doctorId").toString()));
        if (request.containsKey("encounterId")) {
            prescription.setEncounterId(Long.valueOf(request.get("encounterId").toString()));
        }
        prescription.setDiagnosisSummary((String) request.get("diagnosisSummary"));
        prescription.setPrescriptionType((String) request.get("prescriptionType"));

        @SuppressWarnings("unchecked")
        List<PrescriptionItem> items = (List<PrescriptionItem>) request.get("items");
        
        return ResponseEntity.ok(prescriptionService.createPrescription(prescription, items));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Prescription prescription = new Prescription();
            prescription.setDiagnosisSummary((String) request.get("diagnosisSummary"));
            prescription.setPrescriptionType((String) request.get("prescriptionType"));

            @SuppressWarnings("unchecked")
            List<PrescriptionItem> items = (List<PrescriptionItem>) request.get("items");
            
            return ResponseEntity.ok(prescriptionService.updatePrescription(id, prescription, items));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<Prescription> reviewPrescription(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Long reviewerId = Long.valueOf(request.get("reviewerId").toString());
            String result = (String) request.get("result");
            String comment = (String) request.get("comment");
            return ResponseEntity.ok(prescriptionService.reviewPrescription(id, reviewerId, result, comment));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/dispense")
    public ResponseEntity<Prescription> dispensePrescription(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(prescriptionService.dispensePrescription(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countPrescriptions(@RequestParam(required = false) String status) {
        Map<String, Long> response = new HashMap<>();
        response.put("count", prescriptionService.countByStatus(status));
        return ResponseEntity.ok(response);
    }
}
