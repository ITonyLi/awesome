package com.github.itonyli.algorithm.dp;

import static java.lang.Math.incrementExact;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class DigitalTriangle {

    /**
     * [2],
     * [3,4],
     * [6,5,7],
     * [4,1,8,3]
     * <p>
     * 给定一个数字三角形，找到从顶部到底部的最小路径和。每一步可以移动到下面一行的相邻数字上。
     *
     * @param args
     */

    public static void main(String[] args) {
        int[][] arrs = {
                {2},
                {4, 3},
                {6, 7, 5},
                {4, 1, 8, 3}
        };

        System.out.println(minimumTotal(arrs));
    }


    public static int minimumTotal(int[][] triangle) {
        if (triangle == null || triangle.length == 0) {
            return 0;
        }

        int len = triangle.length;
        int[][] cost = new int[len][len];
        cost[0][0] = triangle[0][0];

        for (int i = 1; i < len; i++) {
            for (int j = 0; j < triangle[i].length; j++) {
                int lower = max(0, j - 1);
                int uper = j + 1 == triangle[i - 1].length - 1 ? j + 1 : j + 1 > triangle[i - 1].length - 1 ? min(j, triangle[i - 1].length - 1) : j + 1;
                if (uper - lower >= 1) {
                    cost[i][j] = min(min(cost[i - 1][lower], cost[i - 1][uper]), cost[i - 1][lower + uper >> 2]) + triangle[i][j];
                } else {
                    cost[i][j] = min(cost[i - 1][lower], cost[i - 1][uper]) + triangle[i][j];
                }
            }
        }
        int minCost = Integer.MAX_VALUE;
        for (int k = 0; k < triangle[len - 1].length; k++) {
            minCost = min(minCost, cost[len - 1][k]);
        }

        return minCost;
    }
}
