package com.bigcow.com.scheduledemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.hbase.DaemonThreadFactory;
import org.junit.Test;

/**
 * Create by suzhiwu on 2020/05/26
 */
public class ScheduleDemo {

    private ScheduledExecutorService scheduleThreadPool;


    //Any exception escaping your run method halts all further work, without notice.
    @Test
    public void testScheduleAtFixedRate() throws InterruptedException {
        scheduleThreadPool = Executors.newScheduledThreadPool(1,
                new DaemonThreadFactory("simple-dsl-version-updater"));
        AtomicInteger counter = new AtomicInteger(1);
        ScheduledFuture<?> future =  scheduleThreadPool.scheduleAtFixedRate(() -> {
            System.out.println("sususu--->");
            if (counter.get() % 5 == 0) throw new RuntimeException("e");
            counter.getAndIncrement();
        }, 1, 1, TimeUnit.SECONDS);


        //可以采用future来get异常
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

    //Any exception escaping your run method halts all further work, without notice.
    @Test
    public void testSchedule() throws InterruptedException {
        scheduleThreadPool = Executors.newScheduledThreadPool(1,
                new DaemonThreadFactory("simple-dsl-version-updater"));
        AtomicInteger counter = new AtomicInteger(1);
        scheduleThreadPool.scheduleWithFixedDelay(() -> {
            System.out.println("sususu--->");
            if (counter.get() % 5 == 0) throw new RuntimeException("e");
            counter.getAndIncrement();
        }, 1, 1, TimeUnit.SECONDS);

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

}
