package com.bigcow.spring.highconcurrency.result_distribute;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

/**
 * Create by suzhiwu on 2020/06/27
 */
public class ResultDistribute {

    @Test
    public void testCompleteFuture() throws Exception {
        //
        CompletableFuture<String> future1 = new CompletableFuture<>();
        CompletableFuture<String> future2 = new CompletableFuture<>();
        future1.exceptionally(t -> {
            return null;
        });
        Thread t1 = new Thread(() -> {
            String val = "thread1--";
            try {
                val += future1.get();
                System.out.println(val);
                System.out.println("t1 开始给结果t2");
                future2.complete("t1 ---> t2");
            } catch (Exception e) {
                System.out.println("--->" + e);
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println("t2 开始给结果t1");
            try {
                Thread.sleep(1000);
                future1.complete("t2 -> t1");
                String val = "Thread2--";
                val += future2.get();
                System.out.println(val);
            } catch (Exception e) {
                System.out.println("--->" + e);
            }
        });

        t2.start();

        t1.join();
        t2.join();

    }

    @Test
    public void testThread() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("susu");
                    throw new Error("--->");
                }
            }
        }).start();
    }
}
