package com.github.itonyli.thread;

/**
 * Created by tony on 2017/10/25.
 */
public class ObjectWait {

    public static void main(String[] args) {
        Hello hello = new Hello();
        new Thread(() -> {
            try {
                hello.hello();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                hello.hello();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}

class Hello {
    public synchronized void hello() throws InterruptedException {
        notifyAll();
        System.out.println("start " + Thread.currentThread().getName());
        wait(5000);
        System.out.println("end " + Thread.currentThread().getName());
    }
}
