package com.ahsen.sensormonitoring.monitor.monitors;

import com.ahsen.sensormonitoring.dto.SensorType;
import com.ahsen.sensormonitoring.monitor.AbstractParentMonitor;
import com.ahsen.sensormonitoring.util.MonitoringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HumidityMonitor extends AbstractParentMonitor {

    @Value("${humidityThreshold:50}")
    private Integer humidityThreshold;

    @Override
    public void monitorAndRaisedAlarmIfThresholdReached(String reading) {
        MonitoringUtils.monitorAndRaisedAlarmIfThresholdReached(SensorType.HUMIDITY, reading, humidityThreshold);
    }
}
