package cn.dyz.tools.file.executorpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * Create by suzhiwu on 2019/7/27
 */
public class ExecutorServiceTest {

    private static AtomicInteger count = new AtomicInteger(1);

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(9));
        long start = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            executorService.submit(new Thread(new MyRunnerTask(), "thread: " + i));
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println("---> duration: " + cost);
        executorService.shutdown();
    }

    static class MyRunnerTask implements Runnable {

        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("haha" + count.get());
            count.getAndIncrement();
        }
    }

    @Test
    public void TestThread() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long cost = System.currentTimeMillis() - start;
        System.out.println("duration: -->" + cost);
    }
}
