package com.github.itonyli.thread;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BoundedBuffer {

    private static List<Integer> inList = Lists.newCopyOnWriteArrayList();
    private static List<Integer> outList = Lists.newCopyOnWriteArrayList();

    private Integer[] pool = new Integer[10];
    private volatile int pointer;
    private volatile int size;

    public synchronized void put(Integer item) throws InterruptedException {
        // ? 为什么要用 while，if 不行吗？ 方向参考5种线程的状态的定义
        while (size >= 10) {
            wait();
        }
        pool[pointer] = item;
        if (++pointer > 9) {
            pointer = 0;
        }
        size++;
        notifyAll();
    }

    public synchronized Integer get() throws InterruptedException {
        // ? 为什么要用 while，if 不行吗？方向参考5种线程的状态的定义
        while (size <= 0) {
            wait();
        }
        if (--pointer < 0) {
            pointer = 9;
        }
        size--;
        notifyAll();
        return pool[pointer];
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer boundedBuffer = new BoundedBuffer();
        for (int i = 0; i < 9; i++) {
            new Thread(new Producer(boundedBuffer, i * 1000, inList)).start();
        }

        for (int i = 0; i < 1; i++) {
            new Thread(new Consumer(boundedBuffer, outList)).start();
        }
        TimeUnit.SECONDS.sleep(2);
        inList.sort(Integer::compare);
        outList.sort(Integer::compare);
        System.out.println(inList.equals(outList));
        inList.removeAll(outList);
        System.out.println(inList.size());
    }
}

class Producer implements Runnable {
    private BoundedBuffer boundedBuffer;
    private int base;
    private List<Integer> inList;

    public Producer(BoundedBuffer boundedBuffer, int base, List<Integer> inList) {
        this.boundedBuffer = boundedBuffer;
        this.base = base;
        this.inList = inList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                boundedBuffer.put(base + i);
                inList.add((base + i));
                System.out.println(Thread.currentThread().getName() + " ++++++In: " + (base + i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private BoundedBuffer boundedBuffer;
    private List<Integer> outList;

    public Consumer(BoundedBuffer boundedBuffer, List<Integer> outList) {
        this.boundedBuffer = boundedBuffer;
        this.outList = outList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer get =  boundedBuffer.get();
                System.out.println(Thread.currentThread().getName() + " ---Out: " + get);
                outList.add(get);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
