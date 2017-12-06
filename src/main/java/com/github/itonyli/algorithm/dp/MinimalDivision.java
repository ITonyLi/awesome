package com.github.itonyli.algorithm.dp;

/**
 * Created by tony on 2017/11/10.
 */
public class MinimalDivision {

    /**
     * 给出nums = [1, 6, 11, 5]，返回1
     */
    public static void main(String[] args) {
        int[] arr = {616,202,595,876,388,120,238,296};
        System.out.println(findMin(arr));
    }

    public static int findMin(int[] arr) {
        int len = arr.length;
        int sum = 0;

        for (int i = 0; i < len; i++) {
            sum += arr[i];
        }
        int m = (sum + 1) >> 1;
        int[][] v = new int[len + 1][m + 1];
        int r = 0;
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0) {
                    v[i][j] = 0;
                }
                if (i > 0 && j > arr[i - 1]) {
                    v[i][j] = v[i - 1][j];
                    int temp = v[i - 1][j - arr[i - 1]] + arr[i - 1];
                    v[i][j] = v[i][j] > temp ? v[i][j] : temp;
                    r = r > v[i][j] ? r : v[i][j];
                }
            }
        }
        return (sum - r) > r ? sum - 2 * r : 2 * r - sum;
    }
}
