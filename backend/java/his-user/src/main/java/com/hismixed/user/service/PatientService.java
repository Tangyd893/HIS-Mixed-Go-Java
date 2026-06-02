package com.hismixed.user.service;

import com.hismixed.user.entity.Patient;
import com.hismixed.user.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Page<Patient> listPatients(String name, String phone, Pageable pageable) {
        return patientRepository.findByConditions(name, phone, pageable);
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Optional<Patient> getPatientByIdCard(String idCard) {
        return patientRepository.findByIdCardAndDeletedAtIsNull(idCard);
    }

    public Optional<Patient> getPatientByPhone(String phone) {
        return patientRepository.findByPhoneAndDeletedAtIsNull(phone);
    }

    @Transactional
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("患者不存在"));

        patient.setName(patientDetails.getName());
        patient.setGender(patientDetails.getGender());
        patient.setBirthDate(patientDetails.getBirthDate());
        patient.setPhone(patientDetails.getPhone());
        patient.setAddress(patientDetails.getAddress());
        patient.setBloodType(patientDetails.getBloodType());
        patient.setAllergicHistory(patientDetails.getAllergicHistory());
        patient.setMaritalStatus(patientDetails.getMaritalStatus());
        patient.setOccupation(patientDetails.getOccupation());
        patient.setEmergencyContact(patientDetails.getEmergencyContact());
        patient.setEmergencyPhone(patientDetails.getEmergencyPhone());

        return patientRepository.save(patient);
    }

    @Transactional
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("患者不存在"));
        patient.setDeletedAt(LocalDateTime.now());
        patientRepository.save(patient);
    }

    public long countActivePatients() {
        return patientRepository.countActive();
    }
}
