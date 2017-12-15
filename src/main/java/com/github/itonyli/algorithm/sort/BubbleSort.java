package com.github.itonyli.algorithm.sort;

/**
 * @author tony
 * @date 2017-12-11 21:50:22
 */
public class BubbleSort {


    public static void main(String[] args) {
        int[] a ={49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51};
        sort(a);
        for (int anA : a) {
            System.out.print(anA + ",");
        }
    }
    
    private static int[] sort(int[] arrs) {
        for (int i = arrs.length -1; i > 0; --i) {
            for (int j = 0; j < i; j++) {
                if (arrs[j] > arrs[i]) {
                    int temp = arrs[j];
                    arrs[j] = arrs[i];
                    arrs[i] = temp;
                }
            }
        }
        return arrs;
    }
}
