package com.github.itonyli.unsafe;

import sun.misc.Unsafe;

import java.util.concurrent.TimeUnit;

/**
 * Created by tony on 2017/10/31.
 */
public class UnsafePark {

    private static Thread mainThread;
    public static void main(String[] args) {
        Unsafe unsafe = UnsafeUtil.getUnsafe();
        mainThread = Thread.currentThread();
        System.out.println(String.format("park %s", mainThread.getName())); // park main
        System.out.println(TimeUnit.SECONDS.toNanos(1));
        unsafe.park(false, TimeUnit.SECONDS.toNanos(1));
        new Thread(() -> {
            System.out.println(String.format("%s unpark %s",
                    Thread.currentThread().getName(),
                    mainThread.getName())); // Thread-0 unpark main
            unsafe.unpark(mainThread);
        }).start();
    }
}
