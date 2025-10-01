package com.smartoffice.devices;

public interface Device {
    void update(boolean occupied);

    void turnOn();
    void turnOff();
}
