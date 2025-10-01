import java.util.*;

interface PaymentStrategy {
    void pay(int amount);
}

class UPIPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using UPI.");
    }
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}

class DebitCardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Debit Card.");
    }
}

class PaymentContext {
    private PaymentStrategy strategy;
    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    public void payBill(int amount) {
        if (strategy == null) {
            System.out.println("No payment method selected.");
        } else {
            strategy.pay(amount);
        }
    }
}

public class PaymentStrategy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentContext context = new PaymentContext();

        String choice = "yes";
        do {
            System.out.println("\nChoose Payment Method:");
            System.out.println("1. UPI");
            System.out.println("2. Credit Card");
            System.out.println("3. Debit Card");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();

            System.out.print("Enter amount to pay: ");
            int amount = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1: context.setStrategy(new UPIPayment()); break;
                case 2: context.setStrategy(new CreditCardPayment()); break;
                case 3: context.setStrategy(new DebitCardPayment()); break;
                default: 
                    System.out.println("Invalid choice.");
                    continue;
            }

            context.payBill(amount);

            System.out.print("Do you want to pay another bill? (yes/no): ");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("yes"));

        System.out.println("Payment system closed.");
        scanner.close();
    }
}
