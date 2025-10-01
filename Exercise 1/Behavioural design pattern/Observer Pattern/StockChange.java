import java.util.*;

interface Observer {
    void update(String stockName, double updatedPrice);
}

interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

class Stock implements Subject {
    private String stockName;
    private double stockPrice;
    private List<Observer> investorList = new ArrayList<>();

    public Stock(String stockName, double stockPrice) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
    }

    public void setPrice(double newPrice) {
        this.stockPrice = newPrice;
        notifyObservers();
    }

    public void registerObserver(Observer observer) {
        investorList.add(observer);
    }

    public void removeObserver(Observer observer) {
        investorList.remove(observer);
    }

    public void notifyObservers() {
        for (Observer investor : investorList) {
            investor.update(stockName, stockPrice);
        }
    }
}

class Investor implements Observer {
    private String investorName;

    public Investor(String investorName) {
        this.investorName = investorName;
    }

    public void update(String stockName, double updatedPrice) {
        System.out.println("Hello " + investorName + ", the stock " + stockName + " is now priced at $" + updatedPrice);
    }
}

public class StockChange {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter stock name: ");
        String stockName = scanner.nextLine();

        System.out.print("Enter initial stock price: ");
        double initialPrice = scanner.nextDouble();
        scanner.nextLine();

        Stock stock = new Stock(stockName, initialPrice);

        System.out.print("Enter number of investors to register: ");
        int investorCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= investorCount; i++) {
            System.out.print("Enter name of investor " + i + ": ");
            String investorName = scanner.nextLine();
            stock.registerObserver(new Investor(investorName));
        }

        String continueChoice;
        do {
            System.out.print("Enter new stock price: ");
            double updatedPrice = scanner.nextDouble();
            scanner.nextLine();

            stock.setPrice(updatedPrice);

            System.out.print("Do you want to update the price again? (yes/no): ");
            continueChoice = scanner.nextLine();
        } while (continueChoice.equalsIgnoreCase("yes"));

        System.out.println("Program ended. Thank you!");
        scanner.close();
    }
}
