package com.mastery.java.task.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

@RestController
@RequestMapping("/produce")
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @PostMapping("/message")
    public Employee sendMessage(@RequestBody Employee employee) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String studentAsJson = mapper.writeValueAsString(employee);

            jmsTemplate.convertAndSend(queue, studentAsJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }
}
