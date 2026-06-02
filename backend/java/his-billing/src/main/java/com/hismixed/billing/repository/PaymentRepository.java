package com.hismixed.billing.repository;

import com.hismixed.billing.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentNo(String paymentNo);

    @Query("SELECT p FROM Payment p WHERE (:patientId IS NULL OR p.patientId = :patientId) AND (:status IS NULL OR p.status = :status)")
    Page<Payment> findByConditions(@Param("patientId") Long patientId, @Param("status") String status, Pageable pageable);

    List<Payment> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    @Query("SELECT SUM(p.totalAmount) FROM Payment p WHERE p.status = 'PAID' AND p.paidAt BETWEEN :startTime AND :endTime")
    java.math.BigDecimal sumAmountByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status")
    long countByStatus(@Param("status") String status);
}
