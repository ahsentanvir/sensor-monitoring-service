package com.ahsen.sensormonitoring.broker;

import com.ahsen.sensormonitoring.util.MonitoringUtils;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    //Create the temperature sensor Queue
    @Bean
    public Queue temperatureSensorQueue() {
        return new Queue(MonitoringUtils.temperatureQueueName, true, false, false);
    }

    //Create the humidity sensor Queue
    @Bean
    public Queue humiditySensorQueue() {
        return new Queue(MonitoringUtils.humidityQueueName, true, false, false);
    }

}
