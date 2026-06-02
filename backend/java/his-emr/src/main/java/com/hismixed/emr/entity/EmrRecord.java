package com.hismixed.emr.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emr_records")
public class EmrRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "encounter_id")
    private Long encounterId;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "template_code", length = 50)
    private String templateCode;

    @Column(name = "subjective", columnDefinition = "jsonb")
    private String subjective;

    @Column(name = "objective", columnDefinition = "jsonb")
    private String objective;

    @Column(name = "assessment", columnDefinition = "jsonb")
    private String assessment;

    @Column(name = "plan", columnDefinition = "jsonb")
    private String plan;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "qc_level", nullable = false)
    private Integer qcLevel = 0;

    @Column(name = "fhir_resource", columnDefinition = "jsonb")
    private String fhirResource;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEncounterId() { return encounterId; }
    public void setEncounterId(Long encounterId) { this.encounterId = encounterId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }
    public String getSubjective() { return subjective; }
    public void setSubjective(String subjective) { this.subjective = subjective; }
    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }
    public String getAssessment() { return assessment; }
    public void setAssessment(String assessment) { this.assessment = assessment; }
    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getQcLevel() { return qcLevel; }
    public void setQcLevel(Integer qcLevel) { this.qcLevel = qcLevel; }
    public String getFhirResource() { return fhirResource; }
    public void setFhirResource(String fhirResource) { this.fhirResource = fhirResource; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
