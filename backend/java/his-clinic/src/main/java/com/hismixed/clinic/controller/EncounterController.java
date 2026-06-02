package com.hismixed.clinic.controller;

import com.hismixed.clinic.entity.Diagnosis;
import com.hismixed.clinic.entity.Encounter;
import com.hismixed.clinic.service.EncounterService;
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
@RequestMapping("/api/encounters")
public class EncounterController {

    @Autowired
    private EncounterService encounterService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listEncounters(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Encounter> encounterPage = encounterService.listEncounters(patientId, doctorId, status, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("list", encounterPage.getContent());
        response.put("total", encounterPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Encounter> getEncounter(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(encounterService.getEncounterById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Encounter>> getEncountersByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(encounterService.getEncountersByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Encounter>> getEncountersByDoctor(
            @PathVariable Long doctorId,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(encounterService.getEncountersByDoctor(doctorId, status));
    }

    @PostMapping
    public ResponseEntity<Encounter> createEncounter(@RequestBody Encounter encounter) {
        return ResponseEntity.ok(encounterService.createEncounter(encounter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Encounter> updateEncounter(@PathVariable Long id, @RequestBody Encounter encounter) {
        try {
            return ResponseEntity.ok(encounterService.updateEncounter(id, encounter));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Encounter> completeEncounter(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(encounterService.completeEncounter(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/diagnoses")
    public ResponseEntity<Void> addDiagnosis(@PathVariable Long id, @RequestBody Diagnosis diagnosis) {
        encounterService.addDiagnosis(id, diagnosis);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/diagnoses")
    public ResponseEntity<Void> updateDiagnoses(@PathVariable Long id, @RequestBody List<Diagnosis> diagnoses) {
        encounterService.updateDiagnoses(id, diagnoses);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/diagnoses")
    public ResponseEntity<List<Diagnosis>> getDiagnoses(@PathVariable Long id) {
        return ResponseEntity.ok(encounterService.getDiagnoses(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countEncounters(@RequestParam(required = false) String status) {
        Map<String, Long> response = new HashMap<>();
        response.put("count", encounterService.countByStatus(status));
        return ResponseEntity.ok(response);
    }
}
