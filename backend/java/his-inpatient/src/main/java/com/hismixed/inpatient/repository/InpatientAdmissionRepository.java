package com.hismixed.inpatient.repository;

import com.hismixed.inpatient.entity.InpatientAdmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InpatientAdmissionRepository extends JpaRepository<InpatientAdmission, Long> {

    Optional<InpatientAdmission> findByAdmissionNo(String admissionNo);

    List<InpatientAdmission> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    List<InpatientAdmission> findByStatus(String status);

    @Query("SELECT a FROM InpatientAdmission a WHERE " +
           "(:patientId IS NULL OR a.patientId = :patientId) AND " +
           "(:status IS NULL OR a.status = :status)")
    Page<InpatientAdmission> findByConditions(
            @Param("patientId") Long patientId,
            @Param("status") String status,
            Pageable pageable);

    @Query("SELECT COUNT(a) FROM InpatientAdmission a WHERE a.status = :status")
    long countByStatus(@Param("status") String status);

    @Query("SELECT COUNT(a) FROM InpatientAdmission a WHERE a.status = 'ADMITTED'")
    long countCurrentInpatients();
}
