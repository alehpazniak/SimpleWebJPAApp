package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeRepository;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exeption.EmployeeServiceNotFoundException;
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
                .orElseThrow(() -> new EmployeeServiceNotFoundException("Employee with id: " + id + " wasn't found"));
    }

    public Page<Employee> findByFirstNameContainsAndLastNameContains(String firstName, String lastName, Pageable pageable) {
        return employeeRepository.findByFirstNameIsContainingAndLastNameIsContaining(firstName, lastName, pageable);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(long id, Employee employeeRequest) {
        if (id != employeeRequest.getEmployeeId()) {
            throw new IllegalArgumentException();
        }
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeServiceNotFoundException(
                        "Employee with id: " + id + " wasn't found. Updating is not possible"));
        employee.setEmployeeId(id);
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setDepartmentId(employeeRequest.getDepartmentId());
        employee.setJobTitle(employeeRequest.getJobTitle());
        employee.setGender(employeeRequest.getGender());
        employee.setDateOfBirth(employeeRequest.getDateOfBirth());
        employeeRepository.save(employee);
        return employee;
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
        log.info("OUT: [delete] - Employee with id - {} - has been deleted", id);
    }
}
