public class ProducerConsumer {
    static class Buffer {
        private final int[] items = new int[3];
        private int count = 0;
        private int in = 0;
        private int out = 0;
        private boolean finished = false;

        synchronized void produce(int value) throws InterruptedException {
            while (count == items.length) {
                wait();
            }

            items[in] = value;
            in = (in + 1) % items.length;
            count++;
            System.out.println("Produced: " + value);
            notifyAll();
        }

        synchronized Integer consume() throws InterruptedException {
            while (count == 0 && !finished) {
                wait();
            }

            if (count == 0 && finished) {
                return null;
            }

            int value = items[out];
            out = (out + 1) % items.length;
            count--;
            System.out.println("Consumed: " + value);
            notifyAll();
            return value;
        }

        synchronized void finish() {
            finished = true;
            notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.produce(i);
                }
                buffer.finish();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                buffer.finish();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Integer value = buffer.consume();
                    if (value == null) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Producer-consumer completed");
    }
}