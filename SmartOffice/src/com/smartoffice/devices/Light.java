package com.smartoffice.devices;

public class Light implements Device {

    
    public void update(boolean occupied) {
        if (occupied) {
            turnOn();
        } else {
            turnOff();
        }
    }

    
    public void turnOn() {
        System.out.println("Light is ON");
    }

    
    public void turnOff() {
        System.out.println("Light is OFF");
    }
}
