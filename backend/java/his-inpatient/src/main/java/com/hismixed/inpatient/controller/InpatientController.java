package com.hismixed.inpatient.controller;

import com.hismixed.inpatient.entity.InpatientAdmission;
import com.hismixed.inpatient.service.InpatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inpatient")
public class InpatientController {

    @Autowired
    private InpatientService inpatientService;

    @GetMapping("/admissions")
    public ResponseEntity<Page<InpatientAdmission>> listAdmissions(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(inpatientService.listAdmissions(patientId, status, pageable));
    }

    @GetMapping("/admissions/{id}")
    public ResponseEntity<InpatientAdmission> getAdmission(@PathVariable Long id) {
        return ResponseEntity.ok(inpatientService.getAdmissionById(id));
    }

    @GetMapping("/admissions/no/{admissionNo}")
    public ResponseEntity<InpatientAdmission> getAdmissionByNo(@PathVariable String admissionNo) {
        return ResponseEntity.ok(inpatientService.getAdmissionByNo(admissionNo));
    }

    @GetMapping("/admissions/patient/{patientId}")
    public ResponseEntity<List<InpatientAdmission>> getAdmissionsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(inpatientService.getAdmissionsByPatient(patientId));
    }

    @GetMapping("/admissions/status/{status}")
    public ResponseEntity<List<InpatientAdmission>> getAdmissionsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(inpatientService.getAdmissionsByStatus(status));
    }

    @PostMapping("/admit")
    public ResponseEntity<InpatientAdmission> admitPatient(
            @RequestParam Long patientId,
            @RequestParam Long admissionDeptId,
            @RequestParam(required = false) Long wardId,
            @RequestParam(required = false) String wardName,
            @RequestParam(required = false) String bedNo,
            @RequestParam(required = false) String admissionDiagnosis,
            @RequestParam(required = false) String admissionType,
            @RequestParam(required = false) String conditionLevel,
            @RequestParam(required = false) Long attendingDoctorId) {
        return ResponseEntity.ok(inpatientService.admitPatient(
                patientId, admissionDeptId, wardId, wardName, bedNo,
                admissionDiagnosis, admissionType, conditionLevel, attendingDoctorId));
    }

    @PostMapping("/discharge/{inpatientId}")
    public ResponseEntity<InpatientAdmission> dischargePatient(
            @PathVariable Long inpatientId,
            @RequestParam(required = false) String dischargeType,
            @RequestParam(required = false) String dischargeDiagnosis,
            @RequestParam(required = false) String dischargeSummary) {
        return ResponseEntity.ok(inpatientService.dischargePatient(
                inpatientId, dischargeType, dischargeDiagnosis, dischargeSummary));
    }

    @GetMapping("/count/current")
    public ResponseEntity<Map<String, Long>> countCurrentInpatients() {
        return ResponseEntity.ok(Map.of("count", inpatientService.countCurrentInpatients()));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countByStatus(@RequestParam String status) {
        return ResponseEntity.ok(Map.of("count", inpatientService.countByStatus(status)));
    }
}
