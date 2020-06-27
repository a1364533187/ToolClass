package com.bigcow.spring.highconcurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by suzhiwu on 2020/06/27
 */
public class Flusher<T> {

    private final FlushExecutor<T>[] flushExecutors;

    private AtomicInteger index;

    //防止多个线程同时执行， 增加一个随机数间隔
    private static final Random r = new Random();

    private static final int delta = 50;

    private static ScheduledExecutorService TIMER = new ScheduledThreadPoolExecutor(1);

    private static ExecutorService POOL = Executors.newCachedThreadPool();

    public Flusher(String name, int bufferSize, int flushInterval, int queueSize, int threads,
            Processor<T> processor) {
        this.flushExecutors = new FlushExecutor[threads];

        if (threads > 1) {
            index = new AtomicInteger();
        }

        for (int i = 0; i < threads; i++) {
            final FlushExecutor<T> flushThread = new FlushExecutor<>(name + "--->" + i, bufferSize,
                    flushInterval, queueSize, processor);
            flushExecutors[i] = flushThread;
            POOL.submit(flushThread);
            //定时调用timeout() 方法
            TIMER.scheduleAtFixedRate(flushThread::timeOut, r.nextInt(delta), flushInterval,
                    TimeUnit.MILLISECONDS);
        }
    }

    // 对index 取模， 保证多线程都能被add
    public boolean add(T t) {
        int len = flushExecutors.length;
        if (len == 1) {
            return flushExecutors[0].add(t);
        }

        int mod = index.incrementAndGet() % len;
        return flushExecutors[mod].add(t);
    }
}
