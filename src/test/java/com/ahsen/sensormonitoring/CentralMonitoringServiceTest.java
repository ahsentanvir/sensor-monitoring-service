package com.ahsen.sensormonitoring;

import com.ahsen.sensormonitoring.broker.RabbitMQConfig;
import com.ahsen.sensormonitoring.monitor.CentralMonitoringService;
import com.ahsen.sensormonitoring.util.MonitoringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.ActiveProfiles;

/*
This class is used to test the central monitoring service
PREREQUISITE: IT REQUIRES AN ACTUAL RABBITMQ ON LOCALHOST PORT 5672 WITH valid guest/guest credentials !!!
 */
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(OutputCaptureExtension.class)
public class CentralMonitoringServiceTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private CentralMonitoringService centralMonitoringService;

    @Test
    public void testTemperatureReadingIsMonitored(CapturedOutput output) throws InterruptedException {
        // Send a reading to the temperature queue
        String tempReading = "sensor_id=t1; value=20";
        rabbitTemplate.convertAndSend(MonitoringUtils.temperatureQueueName, tempReading);

        Thread.sleep(2000L);

        // Assert that central monitoring service receives the temp reading
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.getOut().contains("Temperature SensorReading:"));
    }

    @Test
    public void testHumidityReadingIsMonitored(CapturedOutput output) throws InterruptedException {
        // Send a reading to the humidity queue
        String humidityReading = "sensor_id=h1; value=29";
        rabbitTemplate.convertAndSend(MonitoringUtils.humidityQueueName, humidityReading);

        Thread.sleep(2000L);

        // Assert that central monitoring service receives the humidity reading
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.getOut().contains("Humidity SensorReading:"));
    }

    @Test
    public void testTemperatureAlertRaised(CapturedOutput output) throws InterruptedException {
        // Send a reading to the temperature queue which is more than threshold
        String tempReading = "sensor_id=t1; value=75";
        rabbitTemplate.convertAndSend(MonitoringUtils.temperatureQueueName, tempReading);

        Thread.sleep(2000L);

        // Assert that central monitoring service raises an alert in the logs
        // when the temperature is more than threshold
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.getOut().contains("TEMPERATURE THRESHOLD EXCEEDED"));
    }

    @Test
    public void testHumidityAlertRaised(CapturedOutput output) throws InterruptedException {
        // Send a reading to the temperature queue which is more than threshold
        String humidityReading = "sensor_id=h1; value=99";
        rabbitTemplate.convertAndSend(MonitoringUtils.humidityQueueName, humidityReading);

        Thread.sleep(2000L);

        // Assert that central monitoring service raises an alert in the logs
        // when the humidity is more than threshold
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.getOut().contains("HUMIDITY THRESHOLD EXCEEDED"));
    }
}
