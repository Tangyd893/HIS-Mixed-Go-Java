package com.hismixed.user.service;

import com.hismixed.user.entity.Department;
import com.hismixed.user.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> listAllDepartments() {
        return departmentRepository.findAllSorted();
    }

    public List<Department> getDepartmentTree() {
        List<Department> all = departmentRepository.findAllSorted();
        Map<Long, List<Department>> parentMap = all.stream()
                .filter(d -> d.getParentId() != null)
                .collect(Collectors.groupingBy(Department::getParentId));

        return all.stream()
                .filter(d -> d.getParentId() == null || d.getParentId() == 0)
                .peek(d -> setChildren(d, parentMap))
                .collect(Collectors.toList());
    }

    private void setChildren(Department parent, Map<Long, List<Department>> parentMap) {
        List<Department> children = parentMap.getOrDefault(parent.getId(), new ArrayList<>());
        children.forEach(child -> setChildren(child, parentMap));
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    @Transactional
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Transactional
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("科室不存在"));

        department.setName(departmentDetails.getName());
        department.setCode(departmentDetails.getCode());
        department.setParentId(departmentDetails.getParentId());
        department.setSort(departmentDetails.getSort());
        department.setDescription(departmentDetails.getDescription());

        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
