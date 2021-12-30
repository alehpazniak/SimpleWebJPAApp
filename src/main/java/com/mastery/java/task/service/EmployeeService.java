package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeRepository;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exeption.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee findById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id: " + id + " wasn't found"));
    }

    public Page<Employee> findByFirstNameOrLastName(String firstName, String lastName, Pageable pageable) {
        return employeeRepository.findByFirstNameOrLastName(firstName, lastName, pageable);
    }

    public Page<Employee> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName, Pageable pageable) {
        return employeeRepository.findByFirstNameContainingAndLastNameContaining(firstName, lastName, pageable);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(long id, Employee employeeRequest) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        employee.setEmployeeId(id);
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setDepartmentId(employeeRequest.getDepartmentId());
        employee.setJobTitle(employeeRequest.getJobTitle());
        employee.setGender(employeeRequest.getGender());
        employee.setDateOfBirth(employeeRequest.getDateOfBirth());
        employeeRepository.save(employee);
        log.info("Employee with id - {} has been updated", id);
        return employee;
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
        log.info("Employee with id - {} has been deleted", id);
    }
}
