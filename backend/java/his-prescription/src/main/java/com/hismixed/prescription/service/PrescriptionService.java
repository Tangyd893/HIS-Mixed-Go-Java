package com.hismixed.prescription.service;

import com.hismixed.prescription.entity.Prescription;
import com.hismixed.prescription.entity.PrescriptionItem;
import com.hismixed.prescription.repository.PrescriptionItemRepository;
import com.hismixed.prescription.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionItemRepository prescriptionItemRepository;

    private static final AtomicLong counter = new AtomicLong(0);

    public Page<Prescription> listPrescriptions(Long patientId, Long doctorId, String status, Pageable pageable) {
        return prescriptionRepository.findByConditions(patientId, doctorId, status, pageable);
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("处方不存在"));
    }

    public Prescription getPrescriptionByNo(String prescriptionNo) {
        return prescriptionRepository.findByPrescriptionNo(prescriptionNo)
                .orElseThrow(() -> new RuntimeException("处方不存在"));
    }

    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public List<PrescriptionItem> getPrescriptionItems(Long prescriptionId) {
        return prescriptionItemRepository.findByPrescriptionId(prescriptionId);
    }

    @Transactional
    public Prescription createPrescription(Prescription prescription, List<PrescriptionItem> items) {
        prescription.setPrescriptionNo(generatePrescriptionNo());
        prescription.setStatus("PENDING");
        prescription.setVersion(0);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PrescriptionItem item : items) {
            if (item.getUnitPrice() != null && item.getQuantity() != null) {
                item.setSubtotal(item.getUnitPrice().multiply(item.getQuantity()));
                totalAmount = totalAmount.add(item.getSubtotal());
            }
        }
        prescription.setTotalAmount(totalAmount);
        
        Prescription saved = prescriptionRepository.save(prescription);
        
        for (PrescriptionItem item : items) {
            item.setPrescriptionId(saved.getId());
        }
        prescriptionItemRepository.saveAll(items);
        
        return saved;
    }

    @Transactional
    public Prescription updatePrescription(Long id, Prescription prescriptionDetails, List<PrescriptionItem> items) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setDiagnosisSummary(prescriptionDetails.getDiagnosisSummary());
        prescription.setPrescriptionType(prescriptionDetails.getPrescriptionType());
        
        prescriptionItemRepository.deleteByPrescriptionId(id);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PrescriptionItem item : items) {
            item.setPrescriptionId(id);
            if (item.getUnitPrice() != null && item.getUnitPrice() != null) {
                item.setSubtotal(item.getUnitPrice().multiply(item.getQuantity()));
                totalAmount = totalAmount.add(item.getSubtotal());
            }
        }
        prescription.setTotalAmount(totalAmount);
        prescription.setVersion(prescription.getVersion() + 1);
        
        prescriptionItemRepository.saveAll(items);
        return prescriptionRepository.save(prescription);
    }

    @Transactional
    public Prescription reviewPrescription(Long id, Long reviewerId, String result, String comment) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setReviewerId(reviewerId);
        prescription.setReviewComment(comment);
        prescription.setStatus(result);
        return prescriptionRepository.save(prescription);
    }

    @Transactional
    public Prescription dispensePrescription(Long id) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setStatus("DISPENSED");
        return prescriptionRepository.save(prescription);
    }

    public long countByStatus(String status) {
        return prescriptionRepository.countByStatus(status);
    }

    private String generatePrescriptionNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = counter.incrementAndGet() % 10000;
        return String.format("RX%s%04d", dateStr, seq);
    }
}
