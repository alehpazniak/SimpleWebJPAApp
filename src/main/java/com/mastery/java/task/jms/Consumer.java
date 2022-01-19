package com.mastery.java.task.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper;
    private final EmployeeService employeeService;

    @JmsListener(destination = "mastery.java.task-queue")
    public void consumeMessage(String message) {
        Employee employee = null;
        try {
            employee = objectMapper.readValue(message, Employee.class);
        } catch (JsonProcessingException e) {
            log.error("Cannot parse json object");
        }
        log.info("Message: {} received from activemq queue", message);
        Employee savedEmployee = employeeService.save(employee);
        log.info("Employee: {} has been saved", savedEmployee);
    }
}
