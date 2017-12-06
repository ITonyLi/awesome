package com.github.itonyli.unsafe;

import lombok.Data;
import org.openjdk.jol.info.ClassLayout;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by tony on 2017/11/1.
 */

@Data
public class BooleanClassLayout {

    private boolean v;
    private boolean v0;
    private int v1;
    private int v2;
    private boolean[] bools;

    public static void main(String[] args) throws NoSuchFieldException {
        Unsafe unsafe = UnsafeUtil.getUnsafe();
        BooleanClassLayout obj = new BooleanClassLayout();
        obj.setV0(true);
        obj.setV2(128);
        obj.setBools(new boolean[] {true, false});
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        Class<? extends BooleanClassLayout> clazz = obj.getClass();
        Field f = clazz.getDeclaredField("bools");
        System.out.println(unsafe.objectFieldOffset(f));

        boolean[] bools = new boolean[] {true, false};
        System.out.println(unsafe.arrayIndexScale(boolean[].class));

    }
}
