package ru.job4j.pools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<List<Integer>> {
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
    protected List<Integer> compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> firstSearch = new ParallelSearch<>(array, from, mid, elem);
        ParallelSearch<T> secondSearch = new ParallelSearch<>(array, mid + 1, to, elem);
        firstSearch.fork();
        secondSearch.fork();
        List<Integer> first = firstSearch.join();
        List<Integer> second = secondSearch.join();
        first.addAll(second);
        return first;
    }

    private List<Integer> linearSearch() {
        List<Integer> list = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            if (array[i].equals(elem)) {
                list.add(i);
            }
        }
        return list;
    }

    public List<Integer> search() {
        List<Integer> result;
        if (array.length <= 10) {
            result = linearSearch();
        } else {
            ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
            result = forkJoinPool.invoke(this);
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Integer[] integers = list.toArray(new Integer[0]);
        integers[0] = 7;
        ParallelSearch<Integer> parallelSearch = new ParallelSearch<>(integers, 0, 29, 7);
        List<Integer> result = parallelSearch.search();
        System.out.println(result);
    }
}
