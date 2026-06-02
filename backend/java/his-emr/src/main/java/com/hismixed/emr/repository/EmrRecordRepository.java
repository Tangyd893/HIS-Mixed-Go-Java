package com.hismixed.emr.repository;

import com.hismixed.emr.entity.EmrRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmrRecordRepository extends JpaRepository<EmrRecord, Long> {

    @Query("SELECT e FROM EmrRecord e WHERE (:patientId IS NULL OR e.patientId = :patientId) AND (:doctorId IS NULL OR e.doctorId = :doctorId) AND (:status IS NULL OR e.status = :status)")
    Page<EmrRecord> findByConditions(@Param("patientId") Long patientId, @Param("doctorId") Long doctorId, @Param("status") String status, Pageable pageable);

    List<EmrRecord> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    List<EmrRecord> findByEncounterId(Long encounterId);

    @Query("SELECT COUNT(e) FROM EmrRecord e WHERE e.status = :status")
    long countByStatus(@Param("status") String status);
}
