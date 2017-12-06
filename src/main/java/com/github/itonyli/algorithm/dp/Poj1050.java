package com.github.itonyli.algorithm.dp;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by tony on 2017/11/12.
 */
public class Poj1050 {
    static final int N = 101;

    static int n;
    static int max;
    static int[][] data = new int[N][N];
    static int[] values;

    static int getMaxN(int[] arrs, int n) {
        int max = 0;
        int temp = 0;
        for (int i = 1; i <= n; i++) {
            temp = temp > 0 ? temp + arrs[i] : arrs[i];
            max = Math.max(temp, max);
        }
        return max;
    }


    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        n = cin.nextInt();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                data[i][j] = cin.nextInt();
            }
        }

        for (int i = 1; i <= n; i++) {
            values = new int[N];

            for (int j = i; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    values[k] += data[j][k];
                }
                max = Math.max(max, getMaxN(values, n));
            }
        }
        System.out.println(max);
    }
}
