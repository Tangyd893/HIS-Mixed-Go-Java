package com.hismixed.clinic.repository;

import com.hismixed.clinic.entity.Referral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {

    @Query("SELECT r FROM Referral r WHERE (:patientId IS NULL OR r.patientId = :patientId) AND (:status IS NULL OR r.status = :status)")
    Page<Referral> findByConditions(@Param("patientId") Long patientId, @Param("status") String status, Pageable pageable);

    List<Referral> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    List<Referral> findByToDoctorIdAndStatusOrderByCreatedAtDesc(Long toDoctorId, String status);

    @Query("SELECT COUNT(r) FROM Referral r WHERE r.status = :status")
    long countByStatus(@Param("status") String status);
}
