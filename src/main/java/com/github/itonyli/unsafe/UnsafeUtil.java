package com.github.itonyli.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by tony on 2017/10/31.
 */
public class UnsafeUtil {

    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new SecurityException("Unsafe");
        }
    }
}
