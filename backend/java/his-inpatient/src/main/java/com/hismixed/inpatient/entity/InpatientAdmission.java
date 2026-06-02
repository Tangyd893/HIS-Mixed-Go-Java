package com.hismixed.inpatient.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inpatient_admissions")
public class InpatientAdmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admission_no", length = 50, unique = true)
    private String admissionNo;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "admission_dept_id")
    private Long admissionDeptId;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "ward_name", length = 100)
    private String wardName;

    @Column(name = "bed_no", length = 20)
    private String bedNo;

    @Column(name = "admission_diagnosis", length = 500)
    private String admissionDiagnosis;

    @Column(name = "discharge_diagnosis", length = 500)
    private String dischargeDiagnosis;

    @Column(name = "admission_type", length = 30)
    private String admissionType;

    @Column(name = "condition_level", length = 20)
    private String conditionLevel;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "admitted_at")
    private LocalDateTime admittedAt;

    @Column(name = "discharged_at")
    private LocalDateTime dischargedAt;

    @Column(name = "discharge_type", length = 30)
    private String dischargeType;

    @Column(name = "discharge_summary", columnDefinition = "TEXT")
    private String dischargeSummary;

    @Column(name = "attending_doctor_id")
    private Long attendingDoctorId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (admittedAt == null) {
            admittedAt = LocalDateTime.now();
        }
        if (status == null) {
            status = "ADMITTED";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAdmissionNo() { return admissionNo; }
    public void setAdmissionNo(String admissionNo) { this.admissionNo = admissionNo; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getAdmissionDeptId() { return admissionDeptId; }
    public void setAdmissionDeptId(Long admissionDeptId) { this.admissionDeptId = admissionDeptId; }
    public Long getWardId() { return wardId; }
    public void setWardId(Long wardId) { this.wardId = wardId; }
    public String getWardName() { return wardName; }
    public void setWardName(String wardName) { this.wardName = wardName; }
    public String getBedNo() { return bedNo; }
    public void setBedNo(String bedNo) { this.bedNo = bedNo; }
    public String getAdmissionDiagnosis() { return admissionDiagnosis; }
    public void setAdmissionDiagnosis(String admissionDiagnosis) { this.admissionDiagnosis = admissionDiagnosis; }
    public String getDischargeDiagnosis() { return dischargeDiagnosis; }
    public void setDischargeDiagnosis(String dischargeDiagnosis) { this.dischargeDiagnosis = dischargeDiagnosis; }
    public String getAdmissionType() { return admissionType; }
    public void setAdmissionType(String admissionType) { this.admissionType = admissionType; }
    public String getConditionLevel() { return conditionLevel; }
    public void setConditionLevel(String conditionLevel) { this.conditionLevel = conditionLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getAdmittedAt() { return admittedAt; }
    public void setAdmittedAt(LocalDateTime admittedAt) { this.admittedAt = admittedAt; }
    public LocalDateTime getDischargedAt() { return dischargedAt; }
    public void setDischargedAt(LocalDateTime dischargedAt) { this.dischargedAt = dischargedAt; }
    public String getDischargeType() { return dischargeType; }
    public void setDischargeType(String dischargeType) { this.dischargeType = dischargeType; }
    public String getDischargeSummary() { return dischargeSummary; }
    public void setDischargeSummary(String dischargeSummary) { this.dischargeSummary = dischargeSummary; }
    public Long getAttendingDoctorId() { return attendingDoctorId; }
    public void setAttendingDoctorId(Long attendingDoctorId) { this.attendingDoctorId = attendingDoctorId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
