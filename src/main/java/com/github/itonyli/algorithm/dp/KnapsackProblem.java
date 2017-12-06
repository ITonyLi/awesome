package com.github.itonyli.algorithm.dp;

public class KnapsackProblem {

    /**
     * weight 5， 4， 3
     * value  20，10，12
     * <p>
     * p(i, j) = max{p(i-1, j), p(i-1, j - weight[i-1]) + value[i-1]}
     */
    public static void main(String[] args) {
        int[] weight = {5, 4, 3, 1, 2};
        int[] value = {20, 10, 12, 3, 2};
        int volume = 10;

        int len = weight.length;
        int[][] values = new int[len + 1][volume + 1];

        int result = 0;
        if (weight != null && weight.length > 0) {
            for (int i = 0; i <= len; i++) {
                for (int j = 0; j <= volume; j++) {
                    values[i][j] = i == 0 ? 0 : values[i - 1][j];
                    if (i > 0 && j > weight[i - 1]) {
                        values[i][j] = Math.max(values[i][j], values[i - 1][j - weight[i - 1]] + value[i - 1]);
                    }
                    result = Math.max(result, values[i][j]);
                }
            }
        }
        System.out.println(result);
    }
}
