package com.smartoffice.manager;

import com.smartoffice.model.Room;
import com.smartoffice.devices.*;
import com.smartoffice.auth.UserAuth;
import com.smartoffice.observer.NotificationService;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

public class OfficeManager {
    private static OfficeManager instance;
    private Map<Integer, Room> rooms = new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private List<NotificationService> notifiers = new ArrayList<>();
    private Map<Integer, String> bookingOwners = new HashMap<>();

    private OfficeManager() {}

    public static OfficeManager getInstance() {
        if (instance == null) instance = new OfficeManager();
        return instance;
    }

    
    public void registerNotifier(NotificationService n) {
        notifiers.add(n);
    }

    public void configureRoomCount(int count) {
        if (!UserAuth.getRole().equals("ADMIN")) {
            System.out.println("Access denied. Only ADMIN can configure rooms.");
            return;
        }
        if (count <= 0) {
            System.out.println("Invalid room number. Please enter a valid room number.");
            return;
        }
        rooms.clear();
        for (int i = 1; i <= count; i++) {
            Room r = new Room(i, 5); 
            r.attach(new Light());
            r.attach(new AirConditioner());
            rooms.put(i, r);
        }
        System.out.print("Office configured with " + count + " meeting rooms: ");
        for (int i = 1; i <= count; i++) {
            System.out.print("Room " + i);
            if (i < count) System.out.print(", ");
        }
        System.out.println(".");
    }

    public void configureRoomCapacity(int roomId, int capacity) {
        if (!UserAuth.getRole().equals("ADMIN")) {
            System.out.println("Access denied. Only ADMIN can configure room capacity.");
            return;
        }
        Room r = rooms.get(roomId);
        if (r == null) {
            System.out.println("Room " + roomId + " does not exist.");
            return;
        }
        if (capacity <= 0) {
            System.out.println("Invalid capacity. Please enter a valid positive number.");
            return;
        }
        r.setCapacity(capacity);
        System.out.println("Room " + roomId + " maximum capacity set to " + capacity + ".");
    }

    // ----- Booking -----
    public void blockRoom(int roomId, String start, int duration) {
    Room r = rooms.get(roomId);
    if (r == null) {
        System.out.println("Invalid room number. Please enter a valid room number.");
        return;
    }

    LocalTime startTime;
    try {
        startTime = LocalTime.parse(start);
    } catch (Exception e) {
        System.out.println("Invalid time format. Please use HH:MM.");
        return;
    }

    LocalTime endTime = startTime.plusMinutes(duration);

    if (r.isBooked()) {
        LocalTime existingStart = r.getBookingStart();
        LocalTime existingEnd = r.getBookingEnd();

        boolean isOverlapping = !endTime.isBefore(existingStart) && !startTime.isAfter(existingEnd);

        if (isOverlapping) {
            System.out.println("Room " + roomId + " is already booked between "
                    + existingStart + " and " + existingEnd + ". Overlapping booking not allowed.");
            return;
        }
    }
    r.book(startTime, duration);
    bookingOwners.put(roomId, UserAuth.getCurrentUser());
    System.out.println("Room " + roomId + " booked from " + start + " for " + duration + " minutes.");

    scheduler.schedule(() -> {
        if (r.isBooked() && !r.isOccupied()) {
            r.cancelBooking();
            r.setOccupied(false);
            String user = bookingOwners.get(roomId);
            for (NotificationService n : notifiers) {
                n.send("Your booking for Room " + roomId + " was auto-released.", user);
            }
            bookingOwners.remove(roomId);
            System.out.println("Room " + roomId + " is now unoccupied. Booking released. AC and lights off.");
        }
    }, 5, TimeUnit.MINUTES);
}


    public void cancelRoom(int roomId) {
        Room r = rooms.get(roomId);
        if (r == null) {
            System.out.println("Invalid room number. Please enter a valid room number.");
            return;
        }
        if (!r.isBooked()) {
            System.out.println("Room " + roomId + " is not booked. Cannot cancel booking.");
            return;
        }
        r.cancelBooking();
        bookingOwners.remove(roomId);
        System.out.println("Booking for Room " + roomId + " cancelled successfully.");
    }
    public void addOccupant(int roomId, int count) {
        Room r = rooms.get(roomId);
        if (r == null) {
            System.out.println("Room " + roomId + " does not exist.");
            return;
        }
        if (count < 0) {
            System.out.println("Invalid occupant count.");
            return;
        }
        r.addOccupants(count);
        if (count >= 2) {
            r.setOccupied(true);
            
        } else if (count == 0) {
            r.setOccupied(false);
            
        } else {
            System.out.println("Room " + roomId + " occupancy insufficient to mark as occupied.");
        }
    }

    
    public void getRoomStatistics(int roomId) {
        Room r = rooms.get(roomId);
        if (r == null) {
            System.out.println("Room " + roomId + " not found.");
            return;
        }
        System.out.println("Room " + roomId + " Stats: " +
                "Total Bookings=" + r.getTotalBookings() +
                ", Cancelled=" + r.getTimesCancelled() +
                ", Total Occupants=" + r.getTotalOccupants());
    }

    public void getAllStatistics() {
        for (int id : rooms.keySet()) {
            getRoomStatistics(id);
        }
    }
}
