package com.bigcow.spring.highconcurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class FlushExecutor<T> implements Runnable {

    private final String name;

    //
    private final int bufferSize;

    //操作间隔
    private volatile int flushInterval;

    //上一次提交的时间
    private volatile long lastFlushTime;

    //this 指代当前的线程
    private volatile Thread writer;

    //持有数据的阻塞队列
    private final BlockingQueue<T> queue;

    //达成条件后具体执行的方法
    private final Processor<T> processor;

    public FlushExecutor(String name, int bufferSize, int flushInterval, int queueSize,
                         Processor<T> processor) {
        this.name = name;
        this.bufferSize = bufferSize;
        this.flushInterval = flushInterval;
        this.queue = new LinkedBlockingQueue<>(queueSize);
        this.processor = processor;
    }

    //外部提交数据的方法
    public boolean add(T t) {
        boolean result = queue.offer(t);
        return result;
    }

    public void timeOut() {
        if (System.currentTimeMillis() - lastFlushTime >= flushInterval) {
            start();
        }
    }

    public void flushOnDemand() {
        if (queue.size() >= bufferSize) {
            start();
        }
    }

    //解除线程的阻塞
    public void start() {
        LockSupport.unpark(writer);
    }

    //执行提交数据的方法
    public void flush() {
        lastFlushTime = System.currentTimeMillis();
        List<T> temp = new ArrayList<>(bufferSize);
        int size = queue.drainTo(temp, bufferSize);
        if (size > 0) {
            try {
                processor.process(temp);
            } catch (Throwable t) {
                System.out.println("--->" + t);
            }
        }
    }

    //根据数据的尺寸和时间间隔判断是否提交
    private boolean canFlush() {
        return queue.size() > bufferSize
                || System.currentTimeMillis() - lastFlushTime > flushInterval;
    }

    @Override
    public void run() {
        writer = Thread.currentThread();
        writer.setName(name);

        while (!writer.isInterrupted()) {
            while (!canFlush()) {
                LockSupport.park(this);
            }
            flush();
        }
    }
}
