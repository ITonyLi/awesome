package com.github.itonyli.unsafe;

import sun.misc.Unsafe;

/**
 * Created by tony on 2017/10/31.
 */
public class UnsafeMemory {

    public static void main(String[] args) {
        Unsafe unsafe = UnsafeUtil.getUnsafe();
        System.out.println(unsafe.pageSize());

        long address = unsafe.allocateMemory(1024L);
        System.out.println(address);
        unsafe.putLong(address, 1024L);
        System.out.println(unsafe.getAddress(address));
        unsafe.freeMemory(address);
    }
}
