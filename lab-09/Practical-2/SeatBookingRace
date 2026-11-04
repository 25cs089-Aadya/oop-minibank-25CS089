
public class SeatBookingRace {
    static class Booking {
        int seatsLeft = 5;
        int successfulBookings = 0;
        boolean synchronizedBooking;

        Booking(boolean synchronizedBooking) {
            this.synchronizedBooking = synchronizedBooking;
        }

        void book() {
            if (synchronizedBooking) {
                safeBook();
            } else {
                unsafeBook();
            }
        }

        void unsafeBook() {
            if (seatsLeft > 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                seatsLeft--;
                successfulBookings++;
            }
        }

        synchronized void safeBook() {
            if (seatsLeft > 0) {
                seatsLeft--;
                successfulBookings++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        runTest(false);
        runTest(true);
    }

    static void runTest(boolean synchronizedBooking) throws InterruptedException {
        Booking booking = new Booking(synchronizedBooking);
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(booking::book, "Customer-" + (i + 1));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(synchronizedBooking
                ? "With synchronization"
                : "Without synchronization");
        System.out.println("Successful bookings: " + booking.successfulBookings);
        System.out.println("Seats left: " + booking.seatsLeft);
        System.out.println();
    }
}