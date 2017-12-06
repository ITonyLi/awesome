package com.github.itonyli.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CalcWithForkJoin {

    private static ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinTask<Integer> task = forkJoinPool.submit(new CalcTask(0, 10));
        int sum1 = task.get();
        System.out.println(sum1);

        int sum2 = 0;
        for (int i = 0; i < 10; i++) {
            sum2 += i;
        }
        System.out.println(sum2);
    }
}

/**
 * 1 2 3 4 5 6 7 8 9 10
 */

class CalcTask extends RecursiveTask<Integer> {
    private static final int MAX_PAGE_SIZE = 5;
    private int start;
    private int end;

    public CalcTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= MAX_PAGE_SIZE) {
            System.out.println("Thread:[" + Thread.currentThread().getName() + "] -> {" + start + "," + end + "}");
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += i;
            }
            return sum;
        } else {
            int next = start + MAX_PAGE_SIZE;
            CalcTask left = new CalcTask(start, next);
            left.fork();
            CalcTask right = new CalcTask(next, end);
            right.fork();
            Integer result = left.join();
            return result + right.join();
        }
    }
}
