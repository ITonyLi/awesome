package com.github.itonyli.algorithm.sort;

/**
 * @author tony
 * @date 2017-12-12 08:04:29
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] a ={49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51};
        sort(a);
        for (int anA : a) {
            System.out.print(anA + ",");
        }
    }


    private static void sort(int[] arrs) {
        buildMaxHeap(arrs);
        for (int i = arrs.length -1; i >= 1; i--) {
            int temp = arrs[0];
            arrs[0] = arrs[i];
            arrs[i] = temp;
            maxHeap(arrs, i, 0);
        }
    }


    private static void buildMaxHeap(int[] arrs) {
        if (arrs == null || arrs.length <= 1) {
            return;
        }

        int helf = arrs.length / 2;
        for (int i = helf; i >=0 ; i--) {
            maxHeap(arrs, arrs.length, i);
        }
    }


    private static void maxHeap(int[] arrs, int heapSize, int index) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heapSize && arrs[left] > arrs[largest]) {
            largest = left;
        }
        if (right < heapSize && arrs[right] > arrs[largest]) {
            largest = right;
        }
        if (index != largest) {
            int temp = arrs[index];
            arrs[index] = arrs[largest];
            arrs[largest] = temp;
            maxHeap(arrs, heapSize, largest);
        }
    }
}
