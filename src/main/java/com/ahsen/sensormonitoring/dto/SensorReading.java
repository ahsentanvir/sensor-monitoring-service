package com.ahsen.sensormonitoring.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class SensorReading {
    private String warehouseId;
    private String sensorId;
    private Integer value;
    private SensorType sensorType;
}
