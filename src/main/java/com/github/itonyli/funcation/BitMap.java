package com.github.itonyli.funcation;

import java.util.concurrent.TimeUnit;

public class BitMap {

    public static void main(String[] args) throws InterruptedException {

        BitMap bm = new BitMap();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    bm.print();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public synchronized void print() throws InterruptedException {
        this.wait(3000);
        System.out.println(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
    }

}
