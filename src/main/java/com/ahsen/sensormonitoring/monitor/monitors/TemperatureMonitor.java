package com.ahsen.sensormonitoring.monitor.monitors;

import com.ahsen.sensormonitoring.dto.SensorType;
import com.ahsen.sensormonitoring.monitor.AbstractParentMonitor;
import com.ahsen.sensormonitoring.util.MonitoringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TemperatureMonitor extends AbstractParentMonitor {

    @Value("${temperatureThreshold:35}")
    private Integer temperatureThreshold;

    @Override
    public void monitorAndRaisedAlarmIfThresholdReached(String reading) {
        MonitoringUtils.monitorAndRaisedAlarmIfThresholdReached(SensorType.TEMPERATURE, reading, temperatureThreshold);
    }
}
