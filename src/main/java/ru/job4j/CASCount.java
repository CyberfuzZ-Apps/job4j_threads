package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer value;
        do {
            value = count.get();
            if (value == null) {
                value = 0;
                count.set(value);
            }
        } while (!count.compareAndSet(value, value + 1));
    }

    public int get() {
        Integer value = count.get();
        if (value == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return value;
    }

}
