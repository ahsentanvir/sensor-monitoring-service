package com.ahsen.sensormonitoring.util;

import com.ahsen.sensormonitoring.dto.SensorReading;
import com.ahsen.sensormonitoring.dto.SensorType;

public class MonitoringUtils {

    public static String temperatureQueueName = "temperature.readings";

    public static String humidityQueueName = "humidity.readings";

    public static SensorReading parseReading(SensorType sensorType, String reading) {
        //Sample reading is : 	sensor_id=h1;	value=40;
        String sensorId = "";
        Integer value = 0;
        String[] keyValuePairs = reading.split(";");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String valueStr = keyValue[1].trim();

                // Assign sensorId and value based on the key
                if (key.equals("sensor_id")) {
                    sensorId = valueStr;
                } else if (key.equals("value")) {
                    value = Integer.parseInt(valueStr);
                }
            }
        }

        return SensorReading.builder().sensorId(sensorId).value(value).sensorType(sensorType).build();
    }

}
