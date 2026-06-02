package com.hismixed.prescription.repository;

import com.hismixed.prescription.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findByPrescriptionNo(String prescriptionNo);

    @Query("SELECT p FROM Prescription p WHERE (:patientId IS NULL OR p.patientId = :patientId) AND (:doctorId IS NULL OR p.doctorId = :doctorId) AND (:status IS NULL OR p.status = :status)")
    Page<Prescription> findByConditions(@Param("patientId") Long patientId, @Param("doctorId") Long doctorId, @Param("status") String status, Pageable pageable);

    List<Prescription> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    List<Prescription> findByEncounterId(Long encounterId);

    @Query("SELECT COUNT(p) FROM Prescription p WHERE p.status = :status")
    long countByStatus(@Param("status") String status);
}
