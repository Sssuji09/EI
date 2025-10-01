package com.smartoffice.model;

import com.smartoffice.devices.Device;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id;
    private int capacity;
    private boolean booked;
    private boolean occupied;
    private LocalTime bookingStart;
    private int bookingDuration;
    private List<Device> devices = new ArrayList<>();

    private int totalBookings = 0;
    private int timesCancelled = 0;
    private int totalOccupants = 0; 

    public Room(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public void attach(Device device) {
        devices.add(device);
    }

    public void book(LocalTime start, int duration) {
        this.booked = true;
        this.bookingStart = start;
        this.bookingDuration = duration;
        totalBookings++;
    }

    public void cancel() {
        this.booked = false;
        this.bookingStart = null;
        this.bookingDuration = 0;
        timesCancelled++;
    }

    public void cancelBooking() {
        cancel();
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isBooked() {
        return booked;
    }

    public void addOccupants(int count) {
        if (count < 0) {
            System.out.println("Invalid occupant count.");
            return;
        }

        if (count == 0) {
            totalOccupants = 0;
            occupied = false;
            for (Device d : devices) d.turnOff();
            System.out.println("Room " + id + " is now unoccupied. AC and lights turned off.");
        } else {
            totalOccupants = count;
            occupied = true;
            for (Device d : devices) d.turnOn();
            System.out.println("Room " + id + " is now occupied by " + count + " persons. AC and lights turned on.");
        }
    }

    public void showStats() {
        System.out.println("Room " + id + " Stats: Total Bookings=" + totalBookings +
                ", Cancelled=" + timesCancelled +
                ", Current Occupants=" + totalOccupants);
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public int getTimesCancelled() {
        return timesCancelled;
    }

    public int getTotalOccupants() {
        return totalOccupants;
    }
    public LocalTime getBookingStart() {
    return bookingStart;
}

public int getBookingDuration() {
    return bookingDuration;
}

public LocalTime getBookingEnd() {
    if (bookingStart != null) {
        return bookingStart.plusMinutes(bookingDuration);
    }
    return null;
}

}
