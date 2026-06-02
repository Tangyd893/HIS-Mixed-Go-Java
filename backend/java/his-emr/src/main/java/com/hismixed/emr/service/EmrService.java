package com.hismixed.emr.service;

import com.hismixed.emr.entity.EmrRecord;
import com.hismixed.emr.repository.EmrRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmrService {

    @Autowired
    private EmrRecordRepository emrRecordRepository;

    public Page<EmrRecord> listEmrRecords(Long patientId, Long doctorId, String status, Pageable pageable) {
        return emrRecordRepository.findByConditions(patientId, doctorId, status, pageable);
    }

    public EmrRecord getEmrRecordById(Long id) {
        return emrRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("病历记录不存在"));
    }

    public List<EmrRecord> getEmrRecordsByPatient(Long patientId) {
        return emrRecordRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public List<EmrRecord> getEmrRecordsByEncounter(Long encounterId) {
        return emrRecordRepository.findByEncounterId(encounterId);
    }

    @Transactional
    public EmrRecord createEmrRecord(EmrRecord emrRecord) {
        emrRecord.setStatus("DRAFT");
        emrRecord.setQcLevel(0);
        return emrRecordRepository.save(emrRecord);
    }

    @Transactional
    public EmrRecord updateEmrRecord(Long id, EmrRecord emrRecordDetails) {
        EmrRecord emrRecord = getEmrRecordById(id);
        emrRecord.setSubjective(emrRecordDetails.getSubjective());
        emrRecord.setObjective(emrRecordDetails.getObjective());
        emrRecord.setAssessment(emrRecordDetails.getAssessment());
        emrRecord.setPlan(emrRecordDetails.getPlan());
        return emrRecordRepository.save(emrRecord);
    }

    @Transactional
    public EmrRecord submitEmrRecord(Long id) {
        EmrRecord emrRecord = getEmrRecordById(id);
        emrRecord.setStatus("SUBMITTED");
        return emrRecordRepository.save(emrRecord);
    }

    @Transactional
    public EmrRecord approveEmrRecord(Long id, Long checkerId, Integer qcLevel) {
        EmrRecord emrRecord = getEmrRecordById(id);
        emrRecord.setStatus("APPROVED");
        emrRecord.setQcLevel(qcLevel);
        return emrRecordRepository.save(emrRecord);
    }

    public long countByStatus(String status) {
        return emrRecordRepository.countByStatus(status);
    }
}
