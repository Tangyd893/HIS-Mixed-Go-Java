package com.hismixed.clinic.repository;

import com.hismixed.clinic.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    List<Diagnosis> findByEncounterIdOrderBySequenceAsc(Long encounterId);

    void deleteByEncounterId(Long encounterId);
}
