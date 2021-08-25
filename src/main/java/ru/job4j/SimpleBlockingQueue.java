package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private boolean stop = true;

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() > 5) {
                wait();
            }
            queue.offer(value);
            notify();
        }
    }

    public T poll() throws InterruptedException {
        T t;
        synchronized (this) {
            while (queue.size() == 0 && stop) {
                wait();
            }
            t = queue.poll();
            notify();
        }
        return t;
    }

    public void stop() {
        synchronized (this) {
            stop = false;
            notify();
        }
    }
}
