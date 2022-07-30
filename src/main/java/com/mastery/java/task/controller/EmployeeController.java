package com.mastery.java.task.controller;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Api(tags = "Employee API")
@Slf4j
@Tag(name = "Employees")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(value = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(description = "Find employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/{id}")
    public Employee getById(@PathVariable @Min(value = 1, message = "Id must be at least 1") long id) {
        log.info("IN: method [getById] - employee with id: {}", id);
        Employee employee = employeeService.findById(id);
        log.info("OUT: method [getById] - has been found {}", employee);
        return employee;
    }

    @Operation(description = "You can search employees by firstName part and lastName part or get all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping
    public Page<Employee> findByFirstNameContainsAndLastNameContains(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "firstName") String sortBy) {
        log.info("IN: method [findByFirstNameContainsAndLastNameContains] - find employee(s) with firstName: '{}', & lastName: '{}'",
                firstName, lastName);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        Page<Employee> employees =
                employeeService.findByFirstNameContainsAndLastNameContains(firstName, lastName, paging);
        log.info("OUT: method [findByFirstNameContainsAndLastNameContains] - find employee(s) in number: '{}'", employees.getTotalElements());
        return employees;
    }

    @Operation(description = "Save employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping
    public Employee add(@Valid @RequestBody Employee employee) {
        log.info("IN: method [add] - save new: {}", employee);
        Employee saveEmployee = employeeService.save(employee);
        log.info("OUT: method [save] - {} has been saved", saveEmployee);
        return saveEmployee;
    }

    @Operation(description = "Update employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/{id}")
    public Employee update(@PathVariable @Min(value = 1, message = "Id must be at least 1") long id,
                           @Valid @RequestBody Employee employee) {
        log.info("IN: method [update] - with id: '{}', & {}", id, employee);
        Employee updateEmployee = employeeService.update(id, employee);
        log.info("OUT: method [update] - the data has been changed to {}", updateEmployee);
        return updateEmployee;
    }

    @Operation(description = "Delete employee", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Min(value = 1, message = "Id must be at least 1") long id) {
        log.info("IN: method [delete] - employee with id: '{}'", id);
        employeeService.delete(id);
        log.info("OUT: method [delete] - employee has been deleted with id: '{}'", id);
    }
}
