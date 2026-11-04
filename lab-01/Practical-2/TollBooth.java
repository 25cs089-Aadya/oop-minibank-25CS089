import java.util.*;

class TollBooth {

    record Vehicle(String number, String type) {}

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int totalToll = 0;
        int bikeCount = 0;
        int carCount = 0;
        int truckCount = 0;

        while (true) {

            System.out.print("Enter vehicle number (or done): ");
            String number = sc.next();

            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike/car/truck): ");
            String type = sc.next().toLowerCase();

            Vehicle v = new Vehicle(number, type);

            int toll = switch (v.type()) {
                case "bike" -> {
                    bikeCount++;
                    yield 20;
                }

                case "car" -> {
                    carCount++;
                    yield 50;
                }

                case "truck" -> {
                    truckCount++;
                    yield 150;
                }

                default -> {
                    System.out.println("Invalid vehicle type!");
                    yield 0;
                }
            };

            totalToll += toll;

            System.out.println("Toll: " + toll);
        }

        System.out.println("Total toll: " + totalToll);

        if (bikeCount >= carCount && bikeCount >= truckCount) {
            System.out.println("Most frequent: bike");
        }
        else if (carCount >= bikeCount && carCount >= truckCount) {
            System.out.println("Most frequent: car");
        }
        else {
            System.out.println("Most frequent: truck");
        }

        sc.close();
    }
}