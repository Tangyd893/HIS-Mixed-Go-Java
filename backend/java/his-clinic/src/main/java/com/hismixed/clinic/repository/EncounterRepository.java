package com.hismixed.clinic.repository;

import com.hismixed.clinic.entity.Encounter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {

    @Query("SELECT e FROM Encounter e WHERE (:patientId IS NULL OR e.patientId = :patientId) AND (:doctorId IS NULL OR e.doctorId = :doctorId) AND (:status IS NULL OR e.status = :status)")
    Page<Encounter> findByConditions(@Param("patientId") Long patientId, @Param("doctorId") Long doctorId, @Param("status") String status, Pageable pageable);

    List<Encounter> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    List<Encounter> findByDoctorIdAndStatusOrderByCreatedAtDesc(Long doctorId, String status);

    @Query("SELECT COUNT(e) FROM Encounter e WHERE e.status = :status")
    long countByStatus(@Param("status") String status);
}
