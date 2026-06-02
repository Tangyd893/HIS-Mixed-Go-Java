package com.hismixed.healthrecord.repository;

import com.hismixed.healthrecord.entity.HealthTimeline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HealthTimelineRepository extends JpaRepository<HealthTimeline, Long> {

    List<HealthTimeline> findByPatientIdOrderByEventDateDesc(Long patientId);

    @Query("SELECT t FROM HealthTimeline t WHERE t.patientId = :patientId " +
           "AND (:startDate IS NULL OR t.eventDate >= :startDate) " +
           "AND (:endDate IS NULL OR t.eventDate <= :endDate) " +
           "ORDER BY t.eventDate DESC")
    Page<HealthTimeline> findByPatientIdAndDateRange(
            @Param("patientId") Long patientId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT COUNT(t) FROM HealthTimeline t WHERE t.patientId = :patientId AND t.eventType = :eventType")
    long countByPatientIdAndEventType(@Param("patientId") Long patientId, @Param("eventType") String eventType);
}
