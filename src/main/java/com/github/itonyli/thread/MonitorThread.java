package com.github.itonyli.thread;


/**
 * public class com.github.itonyli.thread.MonitorThread extends java.lang.Thread {
 *  public com.github.itonyli.thread.MonitorThread(java.lang.String);
 *      Code:
 *          0: aload_0
 *          1: aload_1
 *          2: invokespecial #1                  // Method java/lang/Thread."<init>":(Ljava/lang/String;)V
 *          5: return
 * <p>
 *  public void run();
 *      Code:
 *          0: aload_0
 *          1: dup
 *          2: astore_1
 *          3: monitorenter
 *          4: aload_0
 *          5: invokevirtual #2                  // Method java/lang/Object.notify:()V
 *          8: aload_1
 *          9: monitorexit
 *          10: goto          18
 *          13: astore_2
 *          14: aload_1
 *          15: monitorexit
 *          16: aload_2
 *          17: athrow
 *          18: return
 *  Exception table:
 *      from    to  target type
 *      4    10    13   any
 *      13    16    13   any
 * <p>
 *  public static void main(java.lang.String[]) throws java.lang.InterruptedException;
 *      Code:
 *          0: new           #3                  // class com/github/itonyli/thread/MonitorThread
 *          3: dup
 *          4: ldc           #4                  // String monitorThread
 *          6: invokespecial #5                  // Method "<init>":(Ljava/lang/String;)V
 *          9: astore_1
 *          10: aload_1
 *          11: dup
 *          12: astore_2
 *          13: monitorenter                      // 获取monitor监视器进去同步代码块
 *          14: aload_1
 *          15: invokevirtual #6                  // Method start:()V
 *          18: aload_1
 *          19: invokevirtual #7                  // Method java/lang/Object.wait:()V
 *          22: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *          25: invokestatic  #9                  // Method java/lang/Thread.currentThread:()Ljava/lang/Thread;
 *          28: invokevirtual #10                 // Method java/lang/Thread.getName:()Ljava/lang/String;
 *          31: invokevirtual #11                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *          34: aload_2
 *          35: monitorexit                       // 程序正常执行结束，释放monitor监视器
 *          36: goto          44
 *          39: astore_3
 *          40: aload_2
 *          41: monitorexit                       // 程序异常结束，先释放monitor监视器
 *          42: aload_3
 *          43: athrow
 *          44: return
 *  Exception table:
 *      from    to  target type
 *      14    36    39   any
 *      39    42    39   any
 *
 *
 *  目的：观察wait后是否会释放monitor监视器，在反编译角度上是不会的（反编译还在会保持代码的逻辑，所以monitor）
 *  {@link Object#wait()} key*: The thread releases ownership of this monitor
 * }
 */
public class MonitorThread extends Thread {


    public MonitorThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName()+" wakup others");
            notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MonitorThread ta = new MonitorThread("monitorThread");
        synchronized (ta) {
            System.out.println(Thread.currentThread().getName()+" start ta");
            ta.start();
            System.out.println(Thread.currentThread().getName()+" block");
            ta.wait();
            System.out.println(Thread.currentThread().getName()+" continue");
        }
    }
}
