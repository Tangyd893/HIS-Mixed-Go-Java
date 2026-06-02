package com.hismixed.billing.repository;

import com.hismixed.billing.entity.BillItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    @Query("SELECT b FROM BillItem b WHERE (:patientId IS NULL OR b.patientId = :patientId) AND (:status IS NULL OR b.status = :status)")
    Page<BillItem> findByConditions(@Param("patientId") Long patientId, @Param("status") String status, Pageable pageable);

    List<BillItem> findByPatientIdAndStatusOrderByCreatedAtDesc(Long patientId, String status);

    List<BillItem> findByPaymentId(Long paymentId);

    @Query("SELECT SUM(b.amount) FROM BillItem b WHERE b.patientId = :patientId AND b.status = 'UNPAID'")
    java.math.BigDecimal sumUnpaidAmountByPatient(@Param("patientId") Long patientId);
}
