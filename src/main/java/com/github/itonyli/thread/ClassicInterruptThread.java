package com.github.itonyli.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author tony
 *
 */
public class ClassicInterruptThread extends Thread {

    private volatile boolean flag = true;


    @Override
    public void run() {
        while (flag) {
            System.out.println("Thread running...");
            try {
                // 处于WAITING状态的线程，interrupt将抛出InterruptedException
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted..." + e.getMessage());
            }
        }
        System.out.println("Thread exiting under request...");
    }

    public static void main(String[] args) throws InterruptedException {
        ClassicInterruptThread cThread = new ClassicInterruptThread();
        System.out.println("Starting thread...");
        cThread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        cThread.flag = false;
        cThread.interrupt();
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }
}
