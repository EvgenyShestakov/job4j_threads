package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T elem;

    public ParallelSearch(T[] array, int from, int to, T elem) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.elem = elem;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> firstSearch = new ParallelSearch<>(array, from, mid, elem);
        ParallelSearch<T> secondSearch = new ParallelSearch<>(array, mid + 1, to, elem);
        firstSearch.fork();
        secondSearch.fork();
        Integer first = firstSearch.join();
        Integer second = secondSearch.join();
        return Math.max(first, second);
    }

    private Integer linearSearch() {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(elem)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public Integer search() {
        Integer result;
        if (array.length <= 10) {
            result = linearSearch();
        } else {
            ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
            result = forkJoinPool.invoke(this);
        }
        return result;
    }
}
