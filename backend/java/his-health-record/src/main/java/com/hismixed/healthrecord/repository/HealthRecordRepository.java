package com.hismixed.healthrecord.repository;

import com.hismixed.healthrecord.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {

    Optional<HealthRecord> findByPatientId(Long patientId);

    boolean existsByPatientId(Long patientId);
}
