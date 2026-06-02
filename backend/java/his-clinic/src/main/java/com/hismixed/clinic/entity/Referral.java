package com.hismixed.clinic.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "referrals")
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "encounter_id", nullable = false)
    private Long encounterId;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "from_department_id")
    private Long fromDepartmentId;

    @Column(name = "to_department_id")
    private Long toDepartmentId;

    @Column(name = "from_doctor_id")
    private Long fromDoctorId;

    @Column(name = "to_doctor_id")
    private Long toDoctorId;

    @Column(name = "referral_reason")
    private String referralReason;

    @Column(name = "diagnosis_summary", length = 500)
    private String diagnosisSummary;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING";

    @Column(name = "referred_at")
    private LocalDateTime referredAt;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

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
    public Long getFromDepartmentId() { return fromDepartmentId; }
    public void setFromDepartmentId(Long fromDepartmentId) { this.fromDepartmentId = fromDepartmentId; }
    public Long getToDepartmentId() { return toDepartmentId; }
    public void setToDepartmentId(Long toDepartmentId) { this.toDepartmentId = toDepartmentId; }
    public Long getFromDoctorId() { return fromDoctorId; }
    public void setFromDoctorId(Long fromDoctorId) { this.fromDoctorId = fromDoctorId; }
    public Long getToDoctorId() { return toDoctorId; }
    public void setToDoctorId(Long toDoctorId) { this.toDoctorId = toDoctorId; }
    public String getReferralReason() { return referralReason; }
    public void setReferralReason(String referralReason) { this.referralReason = referralReason; }
    public String getDiagnosisSummary() { return diagnosisSummary; }
    public void setDiagnosisSummary(String diagnosisSummary) { this.diagnosisSummary = diagnosisSummary; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getReferredAt() { return referredAt; }
    public void setReferredAt(LocalDateTime referredAt) { this.referredAt = referredAt; }
    public LocalDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
