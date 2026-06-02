package com.hismixed.user.service;

import com.hismixed.user.entity.Employee;
import com.hismixed.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> listEmployees(Long departmentId, String name, Pageable pageable) {
        return employeeRepository.findByConditions(departmentId, name, pageable);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByUserId(Long userId) {
        return employeeRepository.findByUserId(userId);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentIdAndStatus(departmentId, 1);
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("员工不存在"));

        employee.setName(employeeDetails.getName());
        employee.setGender(employeeDetails.getGender());
        employee.setPhone(employeeDetails.getPhone());
        employee.setEmail(employeeDetails.getEmail());
        employee.setTitle(employeeDetails.getTitle());
        employee.setJobType(employeeDetails.getJobType());
        employee.setDepartmentId(employeeDetails.getDepartmentId());
        employee.setSpecialty(employeeDetails.getSpecialty());
        employee.setIntroduction(employeeDetails.getIntroduction());

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateStatus(Long id, Integer status) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("员工不存在"));
        employee.setStatus(status);
        return employeeRepository.save(employee);
    }

    public long countActiveEmployees() {
        return employeeRepository.countActive();
    }
}
