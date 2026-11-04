
public class CounterRace {
    static int counter = 0;
    static final int THREADS = 10;
    static final int INCREMENTS = 100000;

    static void increment() {
        counter++;
    }

    static synchronized void safeIncrement() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];

        counter = 0;

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    increment();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Without synchronization: " + counter);
        System.out.println("Expected result: " + (THREADS * INCREMENTS));

        counter = 0;

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    safeIncrement();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("With synchronization: " + counter);
        System.out.println("Expected result: " + (THREADS * INCREMENTS));
    }
}