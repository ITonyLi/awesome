package com.github.itonyli;

import com.google.common.collect.Maps;

import java.util.HashMap;

public class Test {

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {

        /**
         * 1111 1011 1011 1011 1011 1011 1011
         *
         *                     1111 1011 1011
         *
         */
        int a = Integer.valueOf( "1111101110111011101110111011", 2);
        System.out.println(a);
        System.out.println(a ^ (a >>> 16));
        System.out.println(tableSizeFor(16));

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("A", 1);

        System.out.println(1 << 30);
        System.out.println(Integer.MAX_VALUE);
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }



}
