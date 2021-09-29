package ru.job4j.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T object;
    private final int start;
    private final int end;

    public ParallelIndexSearch(T[] array, T object, int start, int end) {
        this.array = array;
        this.object = object;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int mid = (end + start) / 2;
        if (end - start <= 10) {
            return indexOf(array, object, start, end);
        }
        ParallelIndexSearch<T> pLeft = new ParallelIndexSearch<>(array, object, start, mid);
        ParallelIndexSearch<T> pRight = new ParallelIndexSearch<>(array, object, mid, end);
        pLeft.fork();
        pRight.fork();
        int rslL = pLeft.join();
        int rslR = pRight.join();
        return Math.max(rslL, rslR);
    }

    private int indexOf(T[] array, T object, int start, int end) {
        for (int i = start; i < end; i++) {
            if (object.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    public int searchIndex() {
        if (array.length <= 10) {
            return indexOf(array, object, start, end);
        }
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(this);
    }

}
