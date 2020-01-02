package cn.dyz.tools.file.concurrent.threadlearn.interupt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * Create by suzhiwu on 2019/12/31
 */
public class ProducerAndConsumer {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(2);
//        //生产者
//      ExecutorService producer = Executors.newFixedThreadPool(3);
        //
        LongAdder longAdder = new LongAdder();
        ExecutorService producer = new ThreadPoolExecutor(1, 5, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(9));
        producer.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        longAdder.increment();
                        blockingQueue.put("" + longAdder.longValue());
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        ExecutorService consumer = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(9));
        for (int i = 0; i < 10; i++) {
            consumer.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
//                            System.out.println("consumer, count: " + ((ThreadPoolExecutor) consumer).getActiveCount() + " queue size:" + blockingQueue.size());
                            String res = blockingQueue.take();
//                            System.out.println("res: " + res);
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        ExecutorService schedule = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "schedule");
            }
        });
        ((ScheduledExecutorService) schedule).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("queue size: " + blockingQueue.size());
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

}
