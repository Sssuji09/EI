package com.smartoffice.observer;

public class EmailNotification implements NotificationService {
    
    public void send(String message, String user) {
        System.out.println("[EMAIL to " + user + "]: " + message);
    }
}
