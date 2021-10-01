package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < sums.length; i++) {
            int localRowSum = 0;
            int localColSum = 0;
            for (int j = 0; j < sums.length; j++) {
                localRowSum += matrix[i][j];
                localColSum += matrix[j][i];
            }
            sums[i] = new Sums(localRowSum, localColSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        List<CompletableFuture<Sums>> futureList = new ArrayList<>(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            futureList.add(getTask(matrix, i));
        }
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = futureList.get(i).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int number) {
        return CompletableFuture.supplyAsync(() -> {
            int localRowSum = 0;
            int localColSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                localRowSum += matrix[number][i];
                localColSum += matrix[i][number];
            }
            return new Sums(localRowSum, localColSum);
        });
    }

    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

    }
}
