package com.mastery.java.task.jms;

import com.mastery.java.task.dto.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

@Slf4j
@RestController
@RequestMapping("/produce")
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @PostMapping("/message")
    public void sendMessage(@RequestBody Employee employee) {
        log.info("IN: [sendMessage] - {}", employee);
        jmsTemplate.convertAndSend(queue, employee);
        log.info("OUT: [sendMessage] - {}", employee);
    }
}
