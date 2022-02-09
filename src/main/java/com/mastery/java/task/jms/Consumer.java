package com.mastery.java.task.jms;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

    private final EmployeeService employeeService;

    @JmsListener(destination = "mastery.java.task-queue", containerFactory = "factory")
    public void consumeMessage(Employee employee) {
        log.info("IN: [consumeMessage] - {}", employee);
        Employee savedEmployee = employeeService.save(employee);
        log.info("OUT: [consumeMessage] - {}", savedEmployee);
    }
}
