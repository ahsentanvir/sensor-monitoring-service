package com.ahsen.sensormonitoring.monitor;

import com.ahsen.sensormonitoring.dto.SensorReading;
import com.ahsen.sensormonitoring.dto.SensorType;
import com.ahsen.sensormonitoring.util.MonitoringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"producerService"})
public class CentralMonitoringService {

    @Value("${temperatureThreshold:35}")
    private Integer temperatureThreshold;

    @Value("${humidityThreshold:50}")
    private Integer humidityThreshold;

    @RabbitListener(queues = "temperature.readings")
    public void consumeTempReading(String message) {
        System.out.println("Received temperature reading(string): " + message);
        try {
            SensorReading sensorReading = MonitoringUtils.parseReading(SensorType.TEMPERATURE, message);
            System.out.println("Temperature SensorReading: " + sensorReading.toString());
            if (sensorReading.getValue() > temperatureThreshold) {
                System.out.println("WARNING ---> TEMPERATURE THRESHOLD EXCEEDED!!!!!!! ITS VALUE IS " + sensorReading.getValue() + "*C for sensorId: " + sensorReading.getSensorId());
            }
        } catch (Exception e) {
            System.err.println("Exception while parsing temperature reading - " + e);
        }
    }

    @RabbitListener(queues = "humidity.readings")
    public void consumeHumidityReading(String message) {
        System.out.println("Received humidity reading(string): " + message);
        try {
            SensorReading sensorReading = MonitoringUtils.parseReading(SensorType.HUMIDITY, message);
            System.out.println("Humidity SensorReading: " + sensorReading.toString());
            if (sensorReading.getValue() > humidityThreshold) {
                System.out.println("WARNING ---> HUMIDITY THRESHOLD EXCEEDED!!!!!!!!!! ITS VALUE IS " + sensorReading.getValue() + "% for sensorId: " + sensorReading.getSensorId());
            }
        } catch (Exception e) {
            System.err.println("Exception while parsing humidity reading - " + e);
        }
    }

}
