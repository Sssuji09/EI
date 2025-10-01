import java.util.Scanner;

class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("Database connection created.");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void executeQuery(String sql) {
        System.out.println("Executing: " + sql);
    }
}

public class SingletonDB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        System.out.println("Are both objects the same? " + (db1 == db2));

        String choice = "yes";
        while (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter SQL query: ");
            String sql = scanner.nextLine();
            db1.executeQuery(sql);

            System.out.print("Do you want to run another query? (yes/no): ");
            choice = scanner.nextLine();
        }

        System.out.println("Program ended.");
        scanner.close();
    }
}
