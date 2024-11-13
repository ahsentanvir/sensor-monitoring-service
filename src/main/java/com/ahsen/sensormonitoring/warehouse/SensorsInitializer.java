package com.ahsen.sensormonitoring.warehouse;

import com.ahsen.sensormonitoring.warehouse.sensors.HumiditySensorRunnable;
import com.ahsen.sensormonitoring.warehouse.sensors.TemperatureSensorRunnable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SensorsInitializer {

    @Autowired
    private TemperatureSensorRunnable temperatureSensorRunnable;

    @Autowired
    private HumiditySensorRunnable humiditySensorRunnable;

    @PostConstruct
    public void startUdpListener() {
        Thread tempReaderThread = new Thread(temperatureSensorRunnable);
        Thread humidityReaderThread = new Thread(humiditySensorRunnable);
        tempReaderThread.start();
        humidityReaderThread.start();
    }
}
