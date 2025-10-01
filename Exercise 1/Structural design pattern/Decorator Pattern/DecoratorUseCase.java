import java.util.Scanner;

interface Coffee {
    String getDescription();
    double cost();
}

class SimpleCoffee implements Coffee {
    public String getDescription() {
        return "Simple Coffee";
    }
    public double cost() {
        return 50;
    }
}

abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }
    public double cost() {
        return coffee.cost() + 10;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    public String getDescription() {
        return coffee.getDescription() + ", Sugar";
    }
    public double cost() {
        return coffee.cost() + 5;
    }
}

public class DecoratorUseCase {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Coffee order = new SimpleCoffee();
        System.out.println("Base Order: " + order.getDescription() + " -> Rs." + order.cost());

        boolean ordering = true;
        while (ordering) {
            System.out.println("\nChoose an option to add:");
            System.out.println("1. Add Milk (+10)");
            System.out.println("2. Add Sugar (+5)");
            System.out.println("3. Finish Order");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    order = new MilkDecorator(order);
                    System.out.println("Added Milk!");
                    break;
                case 2:
                    order = new SugarDecorator(order);
                    System.out.println("Added Sugar!");
                    break;
                case 3:
                    ordering = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        System.out.println("\nFinal Order: " + order.getDescription() + " -> Rs." + order.cost());
        sc.close();
    }
}
