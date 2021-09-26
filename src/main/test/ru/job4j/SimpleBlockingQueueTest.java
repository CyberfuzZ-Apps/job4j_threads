package ru.job4j;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest {

    @Test
    public void fullTest() throws InterruptedException {
        int capacity = 3;
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(capacity);
        Thread producer = new Thread(() -> {
            for (int i = 0; i < capacity; i++) {
                try {
                    sbq.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < capacity; i++) {
                try {
                    sbq.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(sbq.getSize(), is(0));
    }

    @Test
    public void offerTest() throws InterruptedException {
        int capacity = 3;
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(capacity);
        Integer i = Integer.MIN_VALUE;
        Thread producer = new Thread(() -> {
            try {
                sbq.offer(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        producer.join();
        assertThat(sbq.getSize(), is(1));
    }

    @Test
    public void pollTest() throws InterruptedException {
        int capacity = 3;
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(capacity);
        Integer i = Integer.MIN_VALUE;
        Thread producer = new Thread(() -> {
            try {
                sbq.offer(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        producer.join();
        assertThat(sbq.poll(), is(i));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(6);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 10).forEach(
                            value -> {
                                try {
                                    queue.offer(value);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    Thread.currentThread().interrupt();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }
}