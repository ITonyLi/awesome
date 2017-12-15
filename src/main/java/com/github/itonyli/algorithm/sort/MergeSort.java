package com.github.itonyli.algorithm.sort;

/**
 * @author tony
 * @date 2017-12-12 10:39:03
 */
public class MergeSort {


    public static void main(String[] args) {
        int[] a ={49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51};
        int[] temp = new int[a.length];
        sort(a, 0, a.length - 1, temp);
        for (int anA : a) {
            System.out.print(anA + ",");
        }
    }


    private static void sort(int[] arrs, int left, int right, int[] temp) {
        if (left >= right) {
            return;
        }

        int middle = (left + right) >> 1;
        sort(arrs, left, middle, temp);
        sort(arrs, middle + 1, right, temp);
        merge(arrs, left, right, middle, temp);
    }


    private static void merge(int[] arrs, int left, int right, int middle, int[] temp) {
        int leftIndex = left, rightIndex = middle + 1, index = left;
        while (leftIndex <= middle && rightIndex <= right) {
            if (arrs[leftIndex] >= arrs[rightIndex]) {
                temp[index++] = arrs[rightIndex++];
            } else {
                temp[index++] = arrs[leftIndex++];
            }
        }
        while (leftIndex <= middle) {
            temp[index++] = arrs[leftIndex++];
        }
        while (rightIndex <= right) {
            temp[index++] = arrs[rightIndex++];
        }
        System.arraycopy(temp, left, arrs, left, right - left + 1);
    }


}
