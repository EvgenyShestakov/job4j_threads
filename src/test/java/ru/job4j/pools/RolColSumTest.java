package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import ru.job4j.pools.RolColSum.Sums;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;


public class RolColSumTest {
   @Test
   public void whenSum() {
       int[][] matrix = new int[3][3];
       matrix[0][0] = 1;
       matrix[0][1] = 4;
       matrix[0][2] = 7;
       matrix[1][0] = 2;
       matrix[1][1] = 5;
       matrix[1][2] = 8;
       matrix[2][0] = 3;
       matrix[2][1] = 6;
       matrix[2][2] = 9;
       Sums[] result = RolColSum.sum(matrix);
       Sums[] expected = new Sums[]{new Sums(6, 12),
               new Sums(15, 15), new Sums(24, 18)};
       assertArrayEquals(expected, result);
   }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[3][3];
        matrix[0][0] = 1;
        matrix[0][1] = 4;
        matrix[0][2] = 7;
        matrix[1][0] = 2;
        matrix[1][1] = 5;
        matrix[1][2] = 8;
        matrix[2][0] = 3;
        matrix[2][1] = 6;
        matrix[2][2] = 9;
        CompletableFuture<Sums[]> sum = RolColSum.asyncSum(matrix);
        Sums[] result = sum.get();
        assertThat(result[0].getRowSum(), is(6));
        assertThat(result[0].getColSum(), is(12));
        assertThat(result[1].getRowSum(), is(15));
        assertThat(result[1].getColSum(), is(15));
        assertThat(result[2].getRowSum(), is(24));
        assertThat(result[2].getColSum(), is(18));
    }
}
