package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Sums)) return false;
            Sums sums = (Sums) o;
            return getRowSum() == sums.getRowSum() && getColSum() == sums.getColSum();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getRowSum(), getColSum());
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        int count = 0;
        Sums[] sums = new Sums[matrix.length];
        for (int row = 0, col = 0; row < n; row++, col++) {
            int sumCol = 0;
            int sumRow = 0;
            for (int i = 0; i < n; i++) {
                sumRow += matrix[i][col];
                sumCol += matrix[row][i];
            }
            sums[count++] = new Sums(sumRow, sumCol);
        }
        return sums;
    }

    public static CompletableFuture<Sums[]> asyncSum(int[][] matrix) {
        return CompletableFuture.supplyAsync(() -> {
            int n = matrix.length;
            int count = 0;
            Sums[] sums = new Sums[matrix.length];
            for (int row = 0, col = 0; row < n; row++, col++) {
                int sumCol = 0;
                int sumRow = 0;
                for (int i = 0; i < n; i++) {
                    sumRow += matrix[i][col];
                    sumCol += matrix[row][i];
                }
                sums[count++] = new Sums(sumRow, sumCol);
            }
            return sums;
        });
    }
}
