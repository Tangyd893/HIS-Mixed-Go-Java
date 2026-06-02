package com.hismixed.user.repository;

import com.hismixed.user.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.deletedAt IS NULL AND (:name IS NULL OR p.name LIKE %:name%) AND (:phone IS NULL OR p.phone LIKE %:phone%)")
    Page<Patient> findByConditions(@Param("name") String name, @Param("phone") String phone, Pageable pageable);

    Optional<Patient> findByIdCardAndDeletedAtIsNull(String idCard);

    Optional<Patient> findByPhoneAndDeletedAtIsNull(String phone);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.deletedAt IS NULL")
    long countActive();
}
