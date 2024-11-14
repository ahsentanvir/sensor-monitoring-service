package com.ahsen.sensormonitoring.monitor;

public abstract class AbstractParentMonitor {
    public abstract void monitorAndRaisedAlarmIfThresholdReached(String reading);
}
