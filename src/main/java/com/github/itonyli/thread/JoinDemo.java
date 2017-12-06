package com.github.itonyli.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by tony on 2017/10/27.
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("t1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(8);
                    System.out.println("t2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
//
//        Runnable runnable = new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(3);
//                    System.out.println(Thread.currentThread().getName() + " Running!");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//
//        new Thread(runnable).start();
//        new Thread(runnable).start();



        System.out.println("HAHA");
    }

}
