package com.github.itonyli.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportThread extends Thread {

    public static Thread main;

    public LockSupportThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" wakup others");
        LockSupport.unpark(main);
    }

    public static void main(String[] args) throws InterruptedException {
        LockSupportThread ls = new LockSupportThread("lst");
        main = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+" start lst");
        ls.start();
        System.out.println(Thread.currentThread().getName()+" block");
        LockSupport.park(main);
        System.out.println(Thread.currentThread().getName()+" continue");
    }
}
