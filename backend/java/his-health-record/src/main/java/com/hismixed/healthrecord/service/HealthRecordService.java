package com.hismixed.healthrecord.service;

import com.hismixed.healthrecord.entity.HealthRecord;
import com.hismixed.healthrecord.entity.HealthTimeline;
import com.hismixed.healthrecord.repository.HealthRecordRepository;
import com.hismixed.healthrecord.repository.HealthTimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HealthRecordService {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @Autowired
    private HealthTimelineRepository timelineRepository;

    public HealthRecord getRecordByPatientId(Long patientId) {
        return healthRecordRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("健康档案不存在"));
    }

    @Transactional
    public HealthRecord createOrUpdateRecord(HealthRecord record) {
        HealthRecord existing = healthRecordRepository.findByPatientId(record.getPatientId()).orElse(null);
        if (existing != null) {
            existing.setBloodType(record.getBloodType());
            existing.setAllergies(record.getAllergies());
            existing.setChronicDiseases(record.getChronicDiseases());
            existing.setFamilyHistory(record.getFamilyHistory());
            existing.setSurgicalHistory(record.getSurgicalHistory());
            existing.setHeight(record.getHeight());
            existing.setWeight(record.getWeight());
            existing.setEmergencyContact(record.getEmergencyContact());
            existing.setEmergencyPhone(record.getEmergencyPhone());
            return healthRecordRepository.save(existing);
        }
        return healthRecordRepository.save(record);
    }

    public Map<String, Object> getHealthOverview(Long patientId) {
        HealthRecord record = healthRecordRepository.findByPatientId(patientId).orElse(null);

        Map<String, Object> overview = new HashMap<>();
        overview.put("patient_id", patientId);

        if (record != null) {
            overview.put("blood_type", record.getBloodType());
            overview.put("allergies", record.getAllergies());
            overview.put("chronic_diseases", record.getChronicDiseases() != null ?
                    Arrays.asList(record.getChronicDiseases().split(",")) : List.of());
            overview.put("height", record.getHeight());
            overview.put("weight", record.getWeight());
        }

        long visitCount = timelineRepository.countByPatientIdAndEventType(patientId, "VISIT");
        long admissionCount = timelineRepository.countByPatientIdAndEventType(patientId, "ADMISSION");
        overview.put("total_visits", visitCount);
        overview.put("total_admissions", admissionCount);

        List<HealthTimeline> recentEvents = timelineRepository.findByPatientIdOrderByEventDateDesc(patientId);
        if (!recentEvents.isEmpty()) {
            overview.put("last_visit_date", recentEvents.get(0).getEventDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        return overview;
    }

    public Page<HealthTimeline> getTimeline(Long patientId, String startDate, String endDate, Pageable pageable) {
        LocalDateTime start = startDate != null && !startDate.isEmpty() ?
                LocalDateTime.parse(startDate + "T00:00:00") : null;
        LocalDateTime end = endDate != null && !endDate.isEmpty() ?
                LocalDateTime.parse(endDate + "T23:59:59") : null;

        return timelineRepository.findByPatientIdAndDateRange(patientId, start, end, pageable);
    }

    @Transactional
    public HealthTimeline addTimelineEntry(HealthTimeline entry) {
        return timelineRepository.save(entry);
    }
}
