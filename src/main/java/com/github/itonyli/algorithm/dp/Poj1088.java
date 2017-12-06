package com.github.itonyli.algorithm.dp;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Poj1088 {

    static int[][] meta = new int[100][100];
    static int[][] cnt = new int[100][100];
    static int row, col;

    /**
     * dp[i][j] = max {dp[i-1][j], dp[i+1][j], dp[i][j+1], dp[i][j-1]}
     *
     * @param i
     * @param j
     * @return
     */
    static int dp(int i, int j) {
        int max = 0;
        if (cnt[i][j] > 0) {
            return cnt[i][j];
        }

        if (j - 1 >= 0) {
            if (meta[i][j] > meta[i][j - 1]) {
                int temp = dp(i, j - 1);
                if (max < temp) {
                    max = temp;
                }
            }
        }

        if (j + 1 <= col - 1) {
            if (meta[i][j] > meta[i][j + 1]) {
                int temp = dp(i, j + 1);
                if (max < temp) {
                    max = temp;
                }
            }
        }

        if (i - 1 >= 0) {
            if (meta[i][j] > meta[i - 1][j]) {
                int temp = dp(i - 1, j);
                if (max < temp) {
                    max = temp;
                }
            }
        }

        if (i + 1 <= row - 1) {
            if (meta[i][j] > meta[i + 1][j]) {
                int temp = dp(i + 1, j);
                if (max < temp) {
                    max = temp;
                }
            }
        }

        return cnt[i][j] = max + 1;
    }

    public static void main(String args[]) throws Exception {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        row = cin.nextInt();
        col = cin.nextInt();
        int result = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                meta[i][j] = cin.nextInt();
                // java will init 0
                cnt[i][j] = 0;
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                dp(i, j);
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result = Math.max(result, cnt[i][j]);
            }
        }

        System.out.println(result);
    }


}
