package com.github.itonyli;

import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 *
 * dsf 接口
 *
 * 排期和通用交易碰一下
 *
 *
 */
public class Main {

    private static final int[] array = new int[80000];

    static {
        Random random = new Random();
        for(int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(i + 1);
        }
    }

    private static int sort(int[] array) throws InterruptedException {
        for (int i = 0; i < array.length-1; i++){
            for(int j = 0 ;j < array.length - i - 1; j++){
                if(array[j] < array[j + 1]){
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                //Thread.interrupted();
            }
        }
        System.out.println("done!");
        return array[0];
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sort(array);
                    //TimeUnit.SECONDS.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        TimeUnit.SECONDS.sleep(2L);
        t1.interrupt();
        System.out.println(t1.isInterrupted());
    }

}
