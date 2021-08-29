package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer current;
        int increment;
        do {
            current = count.get();
            increment = current + 1;
        } while (!count.compareAndSet(current, increment));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASCount cas = new CASCount();
        Thread thread1 = new Thread(() -> {
            for (int x = 0; x < 10000; x++) {
                cas.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int x = 0; x < 10000; x++) {
                cas.increment();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(cas.get());
    }
}

