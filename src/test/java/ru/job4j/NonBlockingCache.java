package ru.job4j;

import org.junit.Test;
import ru.job4j.cache.Base;
import ru.job4j.cache.Cache;
import ru.job4j.cache.OptimisticException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NonBlockingCache {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        cache.add(base1);
        assertThat(cache.getMemory().size(), is(1));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        cache.add(base1);
        cache.delete(base1);
        assertThat(cache.getMemory().size(), is(0));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        cache.add(base1);
        cache.update(base1);
        assertThat(cache.getMemory().get(1).getVersion(), is(2));
    }

    @Test(expected = OptimisticException.class)
    public void whenOptimisticException() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(1, 2);
        cache.add(base2);
        cache.update(base1);
    }
}
