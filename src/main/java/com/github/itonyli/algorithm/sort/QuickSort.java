package com.github.itonyli.algorithm.sort;

/**
 * @author tony
 * @date 2017-12-11 22:02:32
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] a ={49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51};
        sort(a, 0, a.length - 1);
        for (int anA : a) {
            System.out.print(anA + ",");
        }
    }


    private static void sort(int[] arrs, int low, int high) {
        if (low < high) {
            int middle = getMiddle(arrs, low, high);
            sort(arrs, low, middle - 1);
            sort(arrs, middle + 1, high);
        }
    }


    private static int getMiddle(int[] arrs, int low, int high) {
        int temp = arrs[low];
        while (low < high) {
            while (low < high && arrs[high] >= temp) {
                high--;
            }
            arrs[low] = arrs[high];
            while (low < high && arrs[low] <= temp) {
                low++;
            }
            arrs[high] = arrs[low];
        }
        arrs[low] = temp;
        return low;
    }
}
