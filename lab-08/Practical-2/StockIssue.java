import java.util.*;

class OutOfStockException extends Exception {
    int shortfall;

    OutOfStockException(int shortfall) {
        this.shortfall = shortfall;
    }

    int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    HashMap<String, Integer> stock = new HashMap<>();

    Warehouse() {
        stock.put("Laptop", 5);
        stock.put("Phone", 10);
        stock.put("Mouse", 20);
    }

    void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than 0");
        }

        int available = stock.getOrDefault(item, 0);

        if (qty > available) {
            throw new OutOfStockException(qty - available);
        }

        stock.put(item, available - qty);
        System.out.println("Issued " + qty + " " + item);
    }
}

public class StockIssue {
    public static void main(String[] args) {

        Warehouse w = new Warehouse();

        String[][] requests = {
            {"Laptop", "3"},
            {"Phone", "15"},
            {"Mouse", "0"},
            {"Laptop", "2"},
            {"Pen", "5"}
        };

        for (String[] request : requests) {
            String item = request[0];
            int qty = Integer.parseInt(request[1]);

            try {
                w.issue(item, qty);

            } catch (OutOfStockException e) {
                System.out.println("Out of stock: " + item);
                System.out.println("Shortfall: " + e.getShortfall());

            } catch (InvalidQuantityException e) {
                System.out.println("Invalid quantity: " + e.getMessage());
            }
        }
    }
}