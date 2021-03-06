package ru.job4j.forkjoin;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ParallelIndexSearchTest {

    @Test
    public void whenSearchString() {
        String[] array = new String[1_000];
        String obj = "str 700";
        for (int i = 0; i < array.length; i++) {
            array[i] = "str " + i;
        }
        ParallelIndexSearch<String> p = new ParallelIndexSearch<>(array, obj, 0, array.length - 1);
        assertThat(p.searchIndex(), is(700));
    }

    @Test
    public void whenSearchInteger() {
        Integer[] array = new Integer[1_000];
        Integer obj = 700;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ParallelIndexSearch<Integer> p = new ParallelIndexSearch<>(array, obj, 0, array.length - 1);
        assertThat(p.searchIndex(), is(700));
    }

    @Test
    public void whenSearchInteger2() {
        Integer[] array = new Integer[1_000];
        Integer obj = 999;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ParallelIndexSearch<Integer> p = new ParallelIndexSearch<>(array, obj, 0, array.length - 1);
        assertThat(p.searchIndex(), is(999));
    }

    @Test
    public void whenSearchInteger3() {
        Integer[] array = new Integer[1_000];
        Integer obj = 1;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ParallelIndexSearch<Integer> p = new ParallelIndexSearch<>(array, obj, 0, array.length - 1);
        assertThat(p.searchIndex(), is(1));
    }

    @Test
    public void whenIntegerNotFound() {
        Integer[] array = new Integer[1_000];
        Integer obj = 1_000_000;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ParallelIndexSearch<Integer> p = new ParallelIndexSearch<>(array, obj, 0, array.length - 1);
        assertThat(p.searchIndex(), is(-1));
    }

}