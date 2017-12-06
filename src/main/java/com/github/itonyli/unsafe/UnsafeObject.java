package com.github.itonyli.unsafe;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.openjdk.jol.info.ClassLayout;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by tony on 2017/10/31.
 */
public class UnsafeObject {

    @AllArgsConstructor
    @ToString(of = {"name", "age", "location"})
    static class User {
        private String name;
        private int age;
        //private double money;
        private static String location = "ZhongShan";
        private static long phone = 13383066603L;
    }


    public static void main(String[] args) throws InstantiationException, NoSuchFieldException {
        Unsafe unsafe = UnsafeUtil.getUnsafe();

        User user = (User) unsafe.allocateInstance(User.class);
        System.out.println(user);

        /**
         * com.github.itonyli.unsafe.UnsafeObject$User object internals:
         OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
         0     4                    (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         4     4                    (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         8     4                    (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
         12     4                int User.age                                  0
         16     4   java.lang.String User.name                                 null
         20     4                    (loss due to the next object alignment)
         Instance size: 24 bytes
         Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */
        System.out.println(ClassLayout.parseInstance(user).toPrintable());

        Class<? extends User> userClass = user.getClass();
        Field name = userClass.getDeclaredField("name");
        Field age = userClass.getDeclaredField("age");
        Field location = userClass.getDeclaredField("location");

        System.out.println(unsafe.objectFieldOffset(name));
        unsafe.putObject(user, unsafe.objectFieldOffset(name), "tony");
        System.out.println(unsafe.objectFieldOffset(age)); // 12
        unsafe.putInt(user, unsafe.objectFieldOffset(age), 99);
        System.out.println(user);

        Object staticFieldBase = unsafe.staticFieldBase(location);
        System.out.println(staticFieldBase);

        long staticFieldOffset = unsafe.staticFieldOffset(location);
        System.out.println(unsafe.getObject(staticFieldBase, staticFieldOffset));
        unsafe.putObject(staticFieldBase, staticFieldOffset, "BeiJing");
        System.out.println(user);

        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }
}
