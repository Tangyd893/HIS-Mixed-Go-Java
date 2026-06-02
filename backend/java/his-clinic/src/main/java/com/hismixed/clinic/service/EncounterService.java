package com.hismixed.clinic.service;

import com.hismixed.clinic.entity.Diagnosis;
import com.hismixed.clinic.entity.Encounter;
import com.hismixed.clinic.repository.DiagnosisRepository;
import com.hismixed.clinic.repository.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EncounterService {

    @Autowired
    private EncounterRepository encounterRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public Page<Encounter> listEncounters(Long patientId, Long doctorId, String status, Pageable pageable) {
        return encounterRepository.findByConditions(patientId, doctorId, status, pageable);
    }

    public Encounter getEncounterById(Long id) {
        return encounterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("就诊记录不存在"));
    }

    public List<Encounter> getEncountersByPatient(Long patientId) {
        return encounterRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public List<Encounter> getEncountersByDoctor(Long doctorId, String status) {
        return encounterRepository.findByDoctorIdAndStatusOrderByCreatedAtDesc(doctorId, status);
    }

    @Transactional
    public Encounter createEncounter(Encounter encounter) {
        encounter.setStatus("IN_PROGRESS");
        return encounterRepository.save(encounter);
    }

    @Transactional
    public Encounter updateEncounter(Long id, Encounter encounterDetails) {
        Encounter encounter = getEncounterById(id);
        encounter.setChiefComplaint(encounterDetails.getChiefComplaint());
        encounter.setPresentIllness(encounterDetails.getPresentIllness());
        encounter.setPastHistory(encounterDetails.getPastHistory());
        encounter.setPhysicalExam(encounterDetails.getPhysicalExam());
        return encounterRepository.save(encounter);
    }

    @Transactional
    public Encounter completeEncounter(Long id) {
        Encounter encounter = getEncounterById(id);
        encounter.setStatus("COMPLETED");
        return encounterRepository.save(encounter);
    }

    @Transactional
    public void addDiagnosis(Long encounterId, Diagnosis diagnosis) {
        diagnosis.setEncounterId(encounterId);
        diagnosisRepository.save(diagnosis);
    }

    @Transactional
    public void updateDiagnoses(Long encounterId, List<Diagnosis> diagnoses) {
        diagnosisRepository.deleteByEncounterId(encounterId);
        diagnoses.forEach(d -> d.setEncounterId(encounterId));
        diagnosisRepository.saveAll(diagnoses);
    }

    public List<Diagnosis> getDiagnoses(Long encounterId) {
        return diagnosisRepository.findByEncounterIdOrderBySequenceAsc(encounterId);
    }

    public long countByStatus(String status) {
        return encounterRepository.countByStatus(status);
    }
}
