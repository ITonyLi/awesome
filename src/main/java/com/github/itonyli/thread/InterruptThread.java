package com.github.itonyli.thread;

public class InterruptThread extends Thread {

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Someone interrupted me.");
            } else {
                System.out.println("Thread is Going...");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InterruptThread thread = new InterruptThread();
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();
    }
}
