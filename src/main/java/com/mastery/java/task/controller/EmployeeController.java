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
    public Employee getById(@PathVariable @Min(1) long id) {
        log.info("IN: [getById] - {}", id);
        Employee employee = employeeService.findById(id);
        log.info("OUT: [getById] - {} ", employee);
        return employee;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(description = "You can search employees by firstName part and lastName part or get all employee")
    @GetMapping
    public Page<Employee> findByFirstNameContainsAndLastNameContains(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "firstName") String sortBy) {
        log.info("IN: method [findByFirstNameContainsAndLastNameContains] - with firstName {}, lastName {}", firstName, lastName);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        Page<Employee> employees =
                employeeService.findByFirstNameContainsAndLastNameContains(firstName, lastName, paging);
        log.info("OUT: method [findByFirstNameContainsAndLastNameContains] - {}", employees.getContent());
        return employees;
    }

    @Operation(description = "Save employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping
    public Employee add(@Valid @RequestBody Employee employee) {
        log.info("IN: [add] - {}", employee);
        Employee saveEmployee = employeeService.save(employee);
        log.info("OUT: [save] - {}", saveEmployee);
        return saveEmployee;
    }

    @Operation(description = "Update employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/{id}")
    public Employee update(@PathVariable @Min(1) long id,
                           @Valid @RequestBody Employee employee) {
        log.info("IN: [update] - {}, {}", id, employee);
        Employee updateEmployee = employeeService.update(id, employee);
        log.info("OUT: [update] - {}", updateEmployee);
        return updateEmployee;
    }

    @Operation(description = "Delete employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Min(1) long id) {
        log.info("IN: [delete] - {}", id);
        employeeService.delete(id);
        log.info("OUT: [delete] - {}", id);
    }
}
