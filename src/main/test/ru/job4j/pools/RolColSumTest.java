package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static ru.job4j.pools.RolColSum.*;

public class RolColSumTest {

    @Test
    public void linearSum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] sums = sum(matrix);
        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[0].getColSum(), is(12));
        assertThat(sums[1].getRowSum(), is(15));
        assertThat(sums[1].getColSum(), is(15));
        assertThat(sums[2].getRowSum(), is(24));
        assertThat(sums[2].getColSum(), is(18));
    }

    @Test
    public void asynchronousSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] sums = asyncSum(matrix);
        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[0].getColSum(), is(12));
        assertThat(sums[1].getRowSum(), is(15));
        assertThat(sums[1].getColSum(), is(15));
        assertThat(sums[2].getRowSum(), is(24));
        assertThat(sums[2].getColSum(), is(18));
    }

}