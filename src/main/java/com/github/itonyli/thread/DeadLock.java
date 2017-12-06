package com.github.itonyli.thread;

import java.util.concurrent.TimeUnit;

public class DeadLock {

    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        new Thread(() -> {
            synchronized (lock1) {
                System.out.println("if lock1 enter");
                try {
                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock2) {
                    System.out.println("if lock2 enter");
                    System.out.println();
                    System.out.println("if lock2 exit");
                }
                System.out.println("if lock1 exit");
            }
        }).start();

        new Thread(() -> {
            synchronized (lock2) {
                System.out.println("else lock2 enter");
                try {
                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock1) {
                    System.out.println("else lock1 enter");
                    System.out.println();
                    System.out.println("else lock1 exit");
                }
                System.out.println("else lock2 exit");
            }
        }).start();
    }
}
