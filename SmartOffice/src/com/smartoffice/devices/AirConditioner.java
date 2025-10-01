package com.smartoffice.devices;

public class AirConditioner implements Device {

    public void update(boolean occupied) {
        if (occupied) {
            turnOn();
        } else {
            turnOff();
        }
    }

    public void turnOn() {
        System.out.println("Air Conditioner is ON");
    }

    public void turnOff() {
        System.out.println("Air Conditioner is OFF");
    }
}
