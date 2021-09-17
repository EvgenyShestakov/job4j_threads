package ru.job4j.pools;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ParallelSearchTest {
    @Test
    public void when50Elem() {
        Integer[] mass = new Integer[50];
        for (int i = 0, j = 49; i < mass.length && j >= 0; i++, j--) {
            mass[i] = j;
        }
        ParallelSearch<Integer> parallelSearch = new ParallelSearch<>(mass, 0, 49, 34);
        Integer expected = 15;
        Integer result = parallelSearch.search();
        assertThat(result, is(expected));
    }

    @Test
    public void whenElemNotFound() {
        Integer[] mass = new Integer[30];
        for (int i = 0; i < mass.length ; i++) {
            mass[i] = i;
        }
        ParallelSearch<Integer> parallelSearch = new ParallelSearch<>(mass, 0, 29, 50);
        Integer expected = -1;
        Integer result = parallelSearch.search();
        assertThat(result, is(expected));
    }
}