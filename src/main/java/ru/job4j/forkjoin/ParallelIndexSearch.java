package ru.job4j.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Класс реализует параллельный поиск индекса объекта в массиве.
 *
 * @param <T> - любой тип.
 */
public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T object;
    private final int start;
    private final int end;

    /**
     * Конструктор принимает исходный массив, объект для поиска и
     * диапазон поиска - начальный и конечный индекс массива.
     *
     * @param array - массив, в котором будет осуществляться поиск.
     * @param object - объект, индекс которого надо найти.
     * @param start - стартовый индекс поиска.
     * @param end - конечный индекс поиска.
     */
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
            return indexOf();
        }
        ParallelIndexSearch<T> pLeft = new ParallelIndexSearch<>(array, object, start, mid);
        ParallelIndexSearch<T> pRight = new ParallelIndexSearch<>(array, object, mid, end);
        pLeft.fork();
        pRight.fork();
        int rslL = pLeft.join();
        int rslR = pRight.join();
        return Math.max(rslL, rslR);
    }

    private int indexOf() {
        for (int i = start; i <= end; i++) {
            if (object.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Метод ищет индекс объекта в массиве. Если длина массива больше 10,
     * то поиск осуществляется параллельно (в несколько потоков, зависит от
     * количества физических процессоров). Возвращает индекс объекта в массиве или
     * -1 если объект не найден.
     *
     * @return - индекс объекта в массиве, либо -1 если объект не найден.
     */
    public int searchIndex() {
        if (array.length <= 10) {
            return indexOf();
        }
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(this);
    }

}
