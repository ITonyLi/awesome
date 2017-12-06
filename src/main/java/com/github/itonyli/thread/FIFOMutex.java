package com.github.itonyli.thread;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;


public class FIFOMutex {

    private final AtomicBoolean LOCKED = new AtomicBoolean(false);
    private final Queue<Thread> WAITERS = new ConcurrentLinkedQueue<>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread currentThread = Thread.currentThread();
        WAITERS.add(currentThread);

        while (!Thread.currentThread().equals(WAITERS.peek()) || !LOCKED.compareAndSet(false, true)) {
            LockSupport.park(this);
            if (Thread.interrupted()) {
                wasInterrupted = true;
            }
        }

        WAITERS.remove();
        if (wasInterrupted) {
            currentThread.interrupt();
        }
    }


    public void unlock() {
        LOCKED.set(false);
        LockSupport.unpark(WAITERS.peek());
    }


    public static void main(String[] args) {
        FIFOMutex mutex = new FIFOMutex();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                mutex.lock();
                int random = new Random().nextInt(4);
                try {
                    TimeUnit.SECONDS.sleep(random);
                    System.out.println(Thread.currentThread().getName() + " -> sleep:" + random);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mutex.unlock();
            }).start();
        }
    }
}
