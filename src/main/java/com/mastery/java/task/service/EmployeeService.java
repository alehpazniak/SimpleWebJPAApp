package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.EmployeeRepository;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.exeption.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findById(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id: " + id + " wasn't found"));
        return employee;
    }

    public List<Employee> findAll(String firstName, String lastName) {
        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return employeeRepository.findAll(Example.of(employee));
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
        log.info("Employee with id - {} was deleted", id);
    }

    public Employee update(Employee updateEmployee) {
        Employee employee = employeeRepository.findById(updateEmployee.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee wasn't found"));
        employee.setEmployeeId(updateEmployee.getEmployeeId());
        employee.setFirstName(updateEmployee.getFirstName());
        employee.setLastName(updateEmployee.getLastName());
        employee.setDepartmentId(updateEmployee.getDepartmentId());
        employee.setJobTitle(updateEmployee.getJobTitle());
        employee.setGender(Gender.valueOf(updateEmployee.getGender().toString().toUpperCase()));
        employee.setDateOfBirth(updateEmployee.getDateOfBirth());
        employeeRepository.save(employee);
        log.info("Employee with id - {} has been changed", updateEmployee.getEmployeeId());
        return employee;
    }
}
