package ru.job4j;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest {

    @Test
    public void fullTest() throws InterruptedException {
        int capacity = 3;
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(capacity);
        Thread producer = new Thread(() -> {
            for (int i = 0; i < capacity; i++) {
                sbq.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < capacity; i++) {
                sbq.poll();
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
        Thread producer = new Thread(() -> sbq.offer(i));
        producer.start();
        producer.join();
        assertThat(sbq.getSize(), is(1));
    }

    @Test
    public void pollTest() throws InterruptedException {
        int capacity = 3;
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(capacity);
        Integer i = Integer.MIN_VALUE;
        Thread producer = new Thread(() -> sbq.offer(i));
        producer.start();
        producer.join();
        assertThat(sbq.poll(), is(i));
    }
}