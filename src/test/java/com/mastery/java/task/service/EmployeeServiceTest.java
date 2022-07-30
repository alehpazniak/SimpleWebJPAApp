package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeRepository;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void shouldFindEmployeeById() {
        //given
        Long employeeId = 1L;
        Employee employee = createEmployee(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        //when
        Employee result = employeeService.findById(employeeId);

        //then
        assertNotNull(result);
        assertEquals(employee, result);
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void shouldSaveEmployee() {
        //given
        Long employeeId = 1L;
        Employee employee = createEmployee(employeeId);
        when(employeeRepository.save(employee)).thenReturn(employee);

        //when
        Employee resultAfterSaving = employeeService.save(employee);

        //then
        assertNotNull(resultAfterSaving);
        assertEquals(employee, resultAfterSaving);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void shouldDeleteEmployee() {
        //given
        Long employeeId = 1L;
        Employee employee = createEmployee(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee)).thenReturn(null);

        //when
        employeeService.delete(employeeId);

        //then
        verify(employeeRepository, times(1)).deleteById(employeeId);

    }

    @Test
    void shouldUpdateEmployee() {
        //given
        Long employeeId = 1L;
        Employee employee = createEmployee(employeeId);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee)).thenReturn(null);

        //when
        Employee update = new Employee();
        update.setEmployeeId(employeeId);
        update.setFirstName("Andrej");
        update.setLastName("Pazniak");
        update.setDepartmentId(1);
        update.setJobTitle("manager");
        update.setGender(Gender.valueOf("MALE"));
        update.setDateOfBirth(LocalDate.parse("1987-08-01"));
        update.setEmail("andrej@email.com");

        employee = employeeService.update(employeeId, update);

        //then
        assertTrue(EqualsBuilder.reflectionEquals(update, employee));
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(employee);
    }

    private Employee createEmployee(Long employeeId) {
        Employee employee = new Employee();

        employee.setEmployeeId(employeeId);
        employee.setFirstName("Aleh");
        employee.setLastName("Pazniak");
        employee.setDepartmentId(1);
        employee.setJobTitle("manager");
        employee.setGender(Gender.valueOf("MALE"));
        employee.setDateOfBirth(LocalDate.parse("1988-07-23"));
        employee.setEmail("aleh@email.com");
        return employee;
    }
}