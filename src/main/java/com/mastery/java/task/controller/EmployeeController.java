package com.mastery.java.task.controller;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Tag(name = "Employees")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public Employee getById(@PathVariable long id) {
        return employeeService.findById(id);
    }

    @Operation(description = "You can find employees by firstName or lastName, by firstName and lastName or get all")
    @GetMapping
    public List<Employee> getAll(String firstName, String lastName) {
        return employeeService.findAll(firstName, lastName);
    }

    @PostMapping
    public Employee add(@Valid @RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping
    public Employee update(@RequestBody Employee employee) {
        return employeeService.update(employee);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }
}
