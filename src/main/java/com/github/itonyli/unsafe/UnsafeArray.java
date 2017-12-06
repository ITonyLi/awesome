package com.github.itonyli.unsafe;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import sun.misc.Unsafe;

import java.util.stream.IntStream;

/**
 * Created by tony on 2017/10/31.
 */
public class UnsafeArray {

    @Data
    @AllArgsConstructor
    static class User {
        private String name;
        private int age;
    }


    public static void main(String[] args) {
        Unsafe unsafe = UnsafeUtil.getUnsafe();
        /**
         # Running 64-bit HotSpot VM.
         # Using compressed oop with 3-bit shift.
         # Using compressed klass with 3-bit shift.
         # WARNING | Compressed references base/shifts are guessed by the experiment!
         # WARNING | Therefore, computed addresses are just guesses, and ARE NOT RELIABLE.
         # WARNING | Make sure to attach Serviceability Agent to get the reliable addresses.
         # Objects are 8 bytes aligned.
         # Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
         # Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
         */
        System.out.println(VM.current().details());

        User[] users = new User[6];
        IntStream.range(0, users.length).forEach(i ->
                users[i] = new User(String.format("zhongmingmao_%s", i), i));

        /**
         [Lcom.github.itonyli.unsafe.UnsafeArray$User; object internals:
         OFFSET  SIZE                                         TYPE DESCRIPTION                               VALUE
         0     4                                              (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         4     4                                              (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         8     4                                              (object header)                           15 f0 00 f8 (00010101 11110000 00000000 11111000) (-134156267)
         12     4                                              (object header)                           06 00 00 00 (00000110 00000000 00000000 00000000) (6)
         16    24   com.github.itonyli.unsafe.UnsafeArray$User UnsafeArray$User;.<elements>              N/A
         Instance size: 40 bytes
         Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
         */
        System.out.println(ClassLayout.parseInstance(users).toPrintable());

        int baseOffset = unsafe.arrayBaseOffset(User[].class);
        System.out.println(baseOffset);
        int indexScale = unsafe.arrayIndexScale(User[].class);
        System.out.println(indexScale);

        // users[1]
        Object object = unsafe.getObject(users, baseOffset + indexScale + 0L);
        System.out.println(object);
    }
}

