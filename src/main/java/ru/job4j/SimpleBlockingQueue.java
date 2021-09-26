package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized int getSize() {
        return queue.size();
    }

    public void offer(T value) throws InterruptedException {
        while (getSize() == capacity) {
            this.wait();
        }
        synchronized (this) {
            this.notifyAll();
            queue.add(value);
        }
    }

    public T poll() throws InterruptedException {
        while (getSize() == 0) {
            this.wait();
        }
        synchronized (this) {
            this.notifyAll();
            return queue.poll();
        }
    }
}
