package com.mastery.java.task.controller;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.service.EmployeeService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Test
    void shouldAddEmployee() {
        //given
        Long employeeId = 1L;
        Employee employee = createEmployee(employeeId);
        when(employeeService.save(employee)).thenReturn(employee);

        //when
        Employee result = employeeController.add(employee);

        //then
        assertTrue(EqualsBuilder.reflectionEquals(result, employee));
        verify(employeeService, times(1)).save(employee);
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