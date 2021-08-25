package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            monitor.notifyAll();
            count++;
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(100_000_000);
        Thread thread0 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            while (!Thread.currentThread().isInterrupted()) {
                countBarrier.count();
            }
            System.out.println(Thread.currentThread().getName() + " finished");
        }
        );
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
                countBarrier.await();
                for (int index = 0; index < 10; index++) {
                    System.out.println(Thread.currentThread().getName() + " work");
                }
            System.out.println(Thread.currentThread().getName() + " finished");
        }
        );
        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            countBarrier.await();
            for (int index = 0; index < 10; index++) {
                System.out.println(Thread.currentThread().getName() + " work");
            }
            System.out.println(Thread.currentThread().getName() + " finished");
        }
        );
        thread0.start();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        thread0.interrupt();
    }
}
