package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        assertTrue(cache.add(base));
    }

    @Test
    public void whenNotAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        assertFalse(cache.add(base));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("First");
        cache.add(base);
        base.setName("Second");
        assertTrue(cache.update(base));
    }

    @Test
    public void whenNotUpdate() {
        Cache cache = new Cache();
        assertFalse(cache.update(new Base(-1, 0)));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        assertTrue(cache.delete(base));
    }

    @Test
    public void whenNotDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        assertFalse(cache.delete(new Base(-1, 0)));
    }

    @Test (expected = OptimisticException.class)
    public void whenWrongVersionThenException() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        base = new Base(1, -1);
        cache.update(base);
    }
}