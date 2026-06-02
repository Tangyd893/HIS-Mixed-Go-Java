package com.hismixed.user.repository;

import com.hismixed.user.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUserId(Long userId);

    Optional<Employee> findByEmployeeNo(String employeeNo);

    @Query("SELECT e FROM Employee e WHERE e.status = 1 AND (:departmentId IS NULL OR e.departmentId = :departmentId) AND (:name IS NULL OR e.name LIKE %:name%)")
    Page<Employee> findByConditions(@Param("departmentId") Long departmentId, @Param("name") String name, Pageable pageable);

    List<Employee> findByDepartmentIdAndStatus(Long departmentId, Integer status);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.status = 1")
    long countActive();
}
