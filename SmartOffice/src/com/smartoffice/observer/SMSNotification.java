package com.smartoffice.observer;

public class SMSNotification implements NotificationService {
    
    public void send(String message, String user) {
        System.out.println("[SMS to " + user + "]: " + message);
    }
}
