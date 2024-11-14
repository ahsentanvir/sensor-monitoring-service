package com.ahsen.sensormonitoring.monitor;

import com.ahsen.sensormonitoring.monitor.monitors.HumidityMonitor;
import com.ahsen.sensormonitoring.monitor.monitors.TemperatureMonitor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"producerService"})
public class CentralMonitoringService {

    @Autowired
    private TemperatureMonitor temperatureMonitor;

    @Autowired
    private HumidityMonitor humidityMonitor;

    @RabbitListener(queues = "temperature.readings")
    public void consumeTempReading(String message) {
        temperatureMonitor.monitorAndRaisedAlarmIfThresholdReached(message);
    }

    @RabbitListener(queues = "humidity.readings")
    public void consumeHumidityReading(String message) {
        humidityMonitor.monitorAndRaisedAlarmIfThresholdReached(message);
    }

}
