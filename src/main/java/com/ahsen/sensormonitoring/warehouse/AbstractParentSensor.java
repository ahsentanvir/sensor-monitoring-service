package com.ahsen.sensormonitoring.warehouse;

public abstract class AbstractParentSensor implements Runnable {
    public abstract void publishToBroker(Integer port, String queueName);
}
