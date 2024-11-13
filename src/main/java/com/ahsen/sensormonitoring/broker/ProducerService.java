package com.ahsen.sensormonitoring.broker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String queueName, String data) throws InterruptedException {
        System.out.println("Sending message: " + data + " to queueName: " + queueName);
        rabbitTemplate.convertAndSend(queueName, data);
    }
}
