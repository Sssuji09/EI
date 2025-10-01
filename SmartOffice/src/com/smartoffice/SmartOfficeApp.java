package com.smartoffice;

import com.smartoffice.manager.OfficeManager;
import com.smartoffice.auth.UserAuth;
import com.smartoffice.observer.EmailNotification;
import com.smartoffice.observer.SMSNotification;

import java.util.Scanner;

public class SmartOfficeApp {

    public static void printHelp() {
        System.out.println(" Smart Office Commands (Single-Line Format):");
        System.out.println(" Config room count <number_of_rooms>   (ADMIN only)");
        System.out.println(" Config room max capacity <room_id> <capacity>   (ADMIN only)");
        System.out.println(" Block room <room_id> <HH:MM> <duration_minutes>");
        System.out.println(" Cancel room <room_id>");
        System.out.println(" Add occupant <room_id> <number_of_persons>");
        System.out.println(" Stats room <room_id>");
        System.out.println(" Stats all");
        System.out.println("Type 'help' anytime to see this guide again.\n");
    }

   public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    OfficeManager manager = OfficeManager.getInstance();

    
    manager.registerNotifier(new EmailNotification());
    manager.registerNotifier(new SMSNotification());

    boolean exitApp = false;

    do {
        
        System.out.println("\nSmart Office Login Menu");
        System.out.println("1. Login as Admin");
        System.out.println("2. Login as User");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice == 3) {
            exitApp = true;
            break; 
        }

        String expectedRole = (choice == 1) ? "ADMIN" : "EMPLOYEE";

        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter username: ");
            String user = sc.nextLine();
            System.out.print("Enter password: ");
            String pass = sc.nextLine();
            loggedIn = UserAuth.login(user, pass);

            if (loggedIn && !UserAuth.getRole().equals(expectedRole)) {
                System.out.println("You selected " + expectedRole + " but logged in as " + UserAuth.getRole());
                loggedIn = false;
            }
        }

        System.out.println(" Welcome " + UserAuth.getCurrentUser() + "! Type 'help' for commands, 'logout' to switch user, 'exit' to quit.");

        boolean loggedSession = true;

        while (loggedSession) {
            System.out.print("> ");
            String command = sc.nextLine().trim();
            String[] parts = command.split(" ");

            if (command.equalsIgnoreCase("help")) {
                printHelp();
            } else if (command.equalsIgnoreCase("logout")) {
                System.out.println(" Logged out.");
                loggedSession = false; 
            } else if (command.equalsIgnoreCase("exit")) {
                exitApp = true;
                loggedSession = false;
            } else {
                try {
                    if (parts[0].equalsIgnoreCase("Config")) {
                        if (UserAuth.getRole().equals("ADMIN")) {
                            if (parts[1].equalsIgnoreCase("room") && parts[2].equalsIgnoreCase("count")) {
                                int count = Integer.parseInt(parts[3]);
                                manager.configureRoomCount(count);
                            } else if (parts[1].equalsIgnoreCase("room") && parts[2].equalsIgnoreCase("max") && parts[3].equalsIgnoreCase("capacity")) {
                                int roomId = Integer.parseInt(parts[4]);
                                int capacity = Integer.parseInt(parts[5]);
                                manager.configureRoomCapacity(roomId, capacity);
                            } else {
                                System.out.println("Invalid Config command.");
                            }
                        } else {
                            System.out.println(" Access denied. Only ADMIN can configure rooms.");
                        }
                    } else if (parts[0].equalsIgnoreCase("Block") && parts[1].equalsIgnoreCase("room")) {
                        int roomId = Integer.parseInt(parts[2]);
                        String time = parts[3];
                        int duration = Integer.parseInt(parts[4]);
                        manager.blockRoom(roomId, time, duration);
                    } else if (parts[0].equalsIgnoreCase("Cancel") && parts[1].equalsIgnoreCase("room")) {
                        int roomId = Integer.parseInt(parts[2]);
                        manager.cancelRoom(roomId);
                    } else if (parts[0].equalsIgnoreCase("Add") && parts[1].equalsIgnoreCase("occupant")) {
                        int roomId = Integer.parseInt(parts[2]);
                        int count = Integer.parseInt(parts[3]);
                        manager.addOccupant(roomId, count);
                    } else if (parts[0].equalsIgnoreCase("Stats")) {
                        if (parts[1].equalsIgnoreCase("room")) {
                            int roomId = Integer.parseInt(parts[2]);
                            manager.getRoomStatistics(roomId);
                        } else if (parts[1].equalsIgnoreCase("all")) {
                            manager.getAllStatistics();
                        } else {
                            System.out.println("Invalid Stats command.");
                        }
                    } else {
                        System.out.println("Invalid command. Type 'help'.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    } while (!exitApp);

    System.out.println("Exiting Smart Office. Goodbye!");
}


}
