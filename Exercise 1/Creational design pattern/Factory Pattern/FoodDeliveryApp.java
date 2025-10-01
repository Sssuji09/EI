import java.util.Scanner;

interface FoodOrder {
    void prepare();
    void deliver();
}

class PizzaOrder implements FoodOrder {
    public void prepare() {
        System.out.println("Preparing Pizza...");
    }
    public void deliver() {
        System.out.println("Delivering Pizza to your address.");
    }
}

class SushiOrder implements FoodOrder {
    public void prepare() {
        System.out.println("Preparing Sushi...");
    }
    public void deliver() {
        System.out.println("Delivering Sushi to your address.");
    }
}

class BurgerOrder implements FoodOrder {
    public void prepare() {
        System.out.println("Preparing Burger...");
    }
    public void deliver() {
        System.out.println("Delivering Burger to your address.");
    }
}

class OrderFactory {
    public static FoodOrder createOrder(String type) {
        switch (type.toLowerCase()) {
            case "pizza": return new PizzaOrder();
            case "sushi": return new SushiOrder();
            case "burger": return new BurgerOrder();
            default: throw new IllegalArgumentException("Invalid order type: " + type);
        }
    }
}

public class FoodDeliveryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Food Delivery App!");
        System.out.print("Enter the food you want to order (Pizza / Sushi / Burger): ");
        String foodType = scanner.nextLine();

        try {
            FoodOrder order = OrderFactory.createOrder(foodType);
            order.prepare();
            order.deliver();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
