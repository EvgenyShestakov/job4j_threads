package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= limit) {
                wait();
            }
            queue.offer(value);
            notify();
        }
    }

    public T poll() throws InterruptedException {
        T t;
        synchronized (this) {
            while (isEmpty()) {
                wait();
            }
            t = queue.poll();
            notify();
        }
        return t;
    }

    public boolean isEmpty() {
        boolean empty = false;
        if (queue.size() == 0) {
            empty = true;
        }
        return empty;
    }
}
