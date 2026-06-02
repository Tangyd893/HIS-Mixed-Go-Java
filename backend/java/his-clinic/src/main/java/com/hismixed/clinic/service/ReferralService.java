package com.hismixed.clinic.service;

import com.hismixed.clinic.entity.Referral;
import com.hismixed.clinic.repository.ReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReferralService {

    @Autowired
    private ReferralRepository referralRepository;

    public Page<Referral> listReferrals(Long patientId, String status, Pageable pageable) {
        return referralRepository.findByConditions(patientId, status, pageable);
    }

    public Referral getReferralById(Long id) {
        return referralRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("转诊记录不存在"));
    }

    public List<Referral> getReferralsByPatient(Long patientId) {
        return referralRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public List<Referral> getReferralsByDoctor(Long doctorId, String status) {
        return referralRepository.findByToDoctorIdAndStatusOrderByCreatedAtDesc(doctorId, status);
    }

    @Transactional
    public Referral createReferral(Referral referral) {
        referral.setStatus("PENDING");
        referral.setReferredAt(LocalDateTime.now());
        return referralRepository.save(referral);
    }

    @Transactional
    public Referral acceptReferral(Long id) {
        Referral referral = getReferralById(id);
        referral.setStatus("ACCEPTED");
        referral.setReceivedAt(LocalDateTime.now());
        return referralRepository.save(referral);
    }

    @Transactional
    public Referral rejectReferral(Long id, String reason) {
        Referral referral = getReferralById(id);
        referral.setStatus("REJECTED");
        return referralRepository.save(referral);
    }

    @Transactional
    public Referral completeReferral(Long id) {
        Referral referral = getReferralById(id);
        referral.setStatus("COMPLETED");
        return referralRepository.save(referral);
    }

    public long countByStatus(String status) {
        return referralRepository.countByStatus(status);
    }
}
