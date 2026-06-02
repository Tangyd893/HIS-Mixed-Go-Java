package com.hismixed.user.repository;

import com.hismixed.user.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByParentIdOrderBySort(Long parentId);

    @Query("SELECT d FROM Department d ORDER BY d.sort ASC")
    List<Department> findAllSorted();

    List<Department> findByNameContaining(String name);
}
