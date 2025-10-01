package com.smartoffice.auth;

import java.util.HashMap;
import java.util.Map;

public class UserAuth {
    private static Map<String, String> users = new HashMap<>();
    private static Map<String, String> roles = new HashMap<>();
    private static String currentUser;

    static {
        
        users.put("admin", "admin123");
        roles.put("admin", "ADMIN");

        users.put("user", "user123");
        roles.put("user", "EMPLOYEE");
    }

    public static boolean login(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            currentUser = username;
            System.out.println("Login successful as " + username + " [" + roles.get(username) + "]");
            return true;
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    public static String getRole() {
        return roles.get(currentUser);
    }

    public static String getCurrentUser() {
        return currentUser;
    }
}
