package com.bigcow.com.future;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 主要的目的 转换各类future
 * future -> listenFuture
 * future -> CompleteFuture
 * listenFuture -> CompleteFuture
 */
public class FutureAdapterDemo {

    //FutureTask
    @Test
    public void testFutureTask() throws ExecutionException, InterruptedException {
        int available = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = new ThreadPoolExecutor(available, available * 2, 1,
                TimeUnit.MINUTES, new SynchronousQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        FutureTask task = new FutureTask<String>(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "hello world";
            }
        });
        executor.execute(task);
//        Future<?> future = executor.submit(task);
        if (task.isDone()) {
            System.out.println("task is done");
        }
        System.out.println(task.get());
//        System.out.println(future.get());
    }

    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        int available = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = new ThreadPoolExecutor(1, 2, 1,
                TimeUnit.MINUTES, new SynchronousQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                System.out.println("hello world");
                return "hello world";
            }
        };
        System.out.println("--->" + available);
        for (int i = 0; i < 10; i++) {
            executor.submit(callable);
        }
        new CountDownLatch(1).await();
    }
}
