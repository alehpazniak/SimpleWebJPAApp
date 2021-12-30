package com.mastery.java.task.controller;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Tag(name = "Employees")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(value = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(description = "Find employee by ID")
    @GetMapping("/{id}")
    public Employee getById(@PathVariable @Min(1) long id) {
        return employeeService.findById(id);
    }

    @Operation(description = "You can search employees by firstName or lastName, by firstName and lastName")
    @GetMapping
    public Page<Employee> findByFirstNameOrLastName(
            @RequestParam(required = false) @Length(min = 3) String firstName,
            @RequestParam(required = false) @Length(min = 2) String lastName,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "lastName") String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return employeeService.findByFirstNameOrLastName(firstName, lastName, paging);
    }

    // todo Example
    @Operation(description = "You can search employees by firstName and lastName part")
    @GetMapping("/filter")
    public Page<Employee> findByFirstNameContainingAndLastNameContaining(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "firstName") String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return employeeService.findByFirstNameContainingAndLastNameContaining(firstName, lastName, paging);
    }

    @Operation(description = "Save employee")
    @PostMapping
    public Employee add(@Valid @RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @Operation(description = "Update employee")
    @PutMapping("/{id}")
    public Employee update(@PathVariable @Min(1) long id,
                           @Valid @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @Operation(description = "Delete employee")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Min(1) long id) {
        employeeService.delete(id);
    }
}
