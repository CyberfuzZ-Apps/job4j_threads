package ru.job4j;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CASCountTest {

    @Test
    public void when3IncrementThen3() {
        CASCount count = new CASCount();
        count.increment();
        count.increment();
        count.increment();
        assertThat(3, is(count.get()));
    }

    @Test
    public void when2IncrementThen2() {
        CASCount count = new CASCount();
        count.increment();
        count.increment();
        assertThat(2, is(count.get()));
    }

    @Test
    public void when1IncrementThen1() {
        CASCount count = new CASCount();
        count.increment();
        assertThat(1, is(count.get()));
    }

    @Test
    public void when6MultithreadedIncrementThen6() throws InterruptedException {
        CASCount count = new CASCount();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                count.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                count.increment();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(6, is(count.get()));
    }
}