package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.EmployeeRepository;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.exeption.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee findById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id: " + id + " wasn't found"));
    }

    public List<Employee> findAll(String firstName, String lastName) {
        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return employeeRepository.findAll(Example.of(employee));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employeeRequest) {
        Employee employee = employeeRepository.findById(employeeRequest.getEmployeeId())
                .orElseThrow(EmployeeNotFoundException::new);
        employee.setEmployeeId(employeeRequest.getEmployeeId());
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setDepartmentId(employeeRequest.getDepartmentId());
        employee.setJobTitle(employeeRequest.getJobTitle());
        employee.setGender(employeeRequest.getGender());
        employee.setDateOfBirth(employeeRequest.getDateOfBirth());
        employeeRepository.save(employee);
        log.info("Employee with id - {} has been updated", employeeRequest.getEmployeeId());
        return employee;
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
        log.info("Employee with id - {} has been deleted", id);
    }
}
