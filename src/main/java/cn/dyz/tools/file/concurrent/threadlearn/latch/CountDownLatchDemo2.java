package cn.dyz.tools.file.concurrent.threadlearn.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by suzhiwu on 2019/01/03
 */
public class CountDownLatchDemo2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));
        //只能处理5个任务
        for (int i = 0; i < 10; i++) {
            final int tmp  = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("t" + tmp + " run");
                    latch.countDown();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        latch.await();
        System.out.println("main is over");
    }
}
