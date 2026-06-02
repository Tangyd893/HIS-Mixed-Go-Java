package com.hismixed.inpatient.service;

import com.hismixed.inpatient.entity.InpatientAdmission;
import com.hismixed.inpatient.repository.InpatientAdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InpatientService {

    @Autowired
    private InpatientAdmissionRepository admissionRepository;

    private static final AtomicLong admissionCounter = new AtomicLong(0);

    public Page<InpatientAdmission> listAdmissions(Long patientId, String status, Pageable pageable) {
        return admissionRepository.findByConditions(patientId, status, pageable);
    }

    public InpatientAdmission getAdmissionById(Long id) {
        return admissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("住院记录不存在"));
    }

    public InpatientAdmission getAdmissionByNo(String admissionNo) {
        return admissionRepository.findByAdmissionNo(admissionNo)
                .orElseThrow(() -> new RuntimeException("住院记录不存在"));
    }

    public List<InpatientAdmission> getAdmissionsByPatient(Long patientId) {
        return admissionRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public List<InpatientAdmission> getAdmissionsByStatus(String status) {
        return admissionRepository.findByStatus(status);
    }

    @Transactional
    public InpatientAdmission admitPatient(Long patientId, Long admissionDeptId, Long wardId,
                                           String wardName, String bedNo, String admissionDiagnosis,
                                           String admissionType, String conditionLevel, Long attendingDoctorId) {
        InpatientAdmission admission = new InpatientAdmission();
        admission.setAdmissionNo(generateAdmissionNo());
        admission.setPatientId(patientId);
        admission.setAdmissionDeptId(admissionDeptId);
        admission.setWardId(wardId);
        admission.setWardName(wardName);
        admission.setBedNo(bedNo);
        admission.setAdmissionDiagnosis(admissionDiagnosis);
        admission.setAdmissionType(admissionType);
        admission.setConditionLevel(conditionLevel);
        admission.setAttendingDoctorId(attendingDoctorId);
        admission.setStatus("ADMITTED");
        admission.setAdmittedAt(LocalDateTime.now());

        return admissionRepository.save(admission);
    }

    @Transactional
    public InpatientAdmission dischargePatient(Long inpatientId, String dischargeType,
                                               String dischargeDiagnosis, String dischargeSummary) {
        InpatientAdmission admission = getAdmissionById(inpatientId);

        if (!"ADMITTED".equals(admission.getStatus())) {
            throw new RuntimeException("患者当前状态不允许出院");
        }

        admission.setDischargeType(dischargeType);
        admission.setDischargeDiagnosis(dischargeDiagnosis);
        admission.setDischargeSummary(dischargeSummary);
        admission.setStatus("DISCHARGED");
        admission.setDischargedAt(LocalDateTime.now());

        return admissionRepository.save(admission);
    }

    public long countCurrentInpatients() {
        return admissionRepository.countCurrentInpatients();
    }

    public long countByStatus(String status) {
        return admissionRepository.countByStatus(status);
    }

    private String generateAdmissionNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = admissionCounter.incrementAndGet() % 10000;
        return String.format("INP%s%04d", dateStr, seq);
    }
}
