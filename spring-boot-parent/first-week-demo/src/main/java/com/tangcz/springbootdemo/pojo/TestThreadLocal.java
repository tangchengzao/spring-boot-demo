package com.tangcz.springbootdemo.pojo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadLocal {

    public static final ThreadLocal<Integer> TEST_THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (TEST_THREAD_LOCAL.get() == null) {
                        TEST_THREAD_LOCAL.set(1);
                    } else {
                        TEST_THREAD_LOCAL.set(TEST_THREAD_LOCAL.get() + 1);
                    }
                    System.out.println(Thread.currentThread().getName() + "_" + TEST_THREAD_LOCAL.get());
                }
            };
            executorService.execute(runnable);
        }
    }

}
