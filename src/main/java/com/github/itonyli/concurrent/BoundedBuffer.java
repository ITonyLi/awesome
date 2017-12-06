package com.github.itonyli.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    private Object[] items = new Object[100];
    private int putptr, takeptr, count;

    public void put(Object item) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[putptr] = item;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }


    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    try {
                        buffer.put(j);
                        System.out.println(Thread.currentThread().getName() + " -> " + j);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Producer-" + i).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " <- " + buffer.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Consumer-" + i).start();
        }
    }
}
