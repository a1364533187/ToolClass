package cn.dyz.tools.file.executorpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by suzhiwu on 2019/7/27
 */
public class ExecutorServiceTest {
    private static AtomicInteger count = new AtomicInteger(1);
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(9));
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Thread(new MyRunnerTask(), "thread: " + i));
        }
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
}

