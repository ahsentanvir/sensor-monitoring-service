package com.ahsen.sensormonitoring.warehouse.sensors;

import com.ahsen.sensormonitoring.util.MonitoringUtils;
import com.ahsen.sensormonitoring.warehouse.AbstractParentSensor;
import com.ahsen.sensormonitoring.warehouse.SensorListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TemperatureSensorRunnable extends AbstractParentSensor {

    @Value("${temperatureSensor.udpPort:3344}")
    private Integer temperatureSensorUdpPort;

    @Autowired
    private SensorListener listener;

    @Override
    public void run() {
        this.publishToBroker(this.temperatureSensorUdpPort, MonitoringUtils.temperatureQueueName);
    }

    @Override
    public void publishToBroker(Integer port, String queueName) {
        listener.listenToPortAndPublish(port, queueName);
    }
}
