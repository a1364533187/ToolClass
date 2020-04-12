package com.bigcow.com.IteratorConsumer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.Test;

import com.google.common.base.Preconditions;

/**
 * Create by suzhiwu on 2020/04/06
 */
public class IteratorConsumerDemo {

    @Test
    public void genIterator() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        Iterator<Integer> iter = list.iterator();
        long start = System.currentTimeMillis();
        comsumerIterator(iter, o -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if ((Integer) o == 100) {
                throw new RuntimeException("--->e, o: " + o);
            }
            System.out.println(o);
        }, 30, 3);

        long cost = System.currentTimeMillis() - start;
        System.out.println("--->cost ms: " + cost);
    }

    @Test
    public void testConsumer() {
        Consumer<Integer> consumer = o -> {
            System.out.println("hello: " + o);
            throw new RuntimeException("--->e");
        };
        consumer.accept(3);

        System.out.println("hello2");
    }

    private void comsumerIterator(Iterator<Integer> iter, Consumer consumer, Integer available,
            int retry) throws InterruptedException {
        if (available == null) {
            available = Runtime.getRuntime().availableProcessors();
        }
        Preconditions.checkArgument(available > 0, "available must be greater than 0");
        NonRejectedLinkedBlockingQueue nonRejectedLinkedBlockingQueue = new NonRejectedLinkedBlockingQueue(
                100, false);
        ExecutorService executor = new ThreadPoolExecutor(available, available, 1L,
                TimeUnit.MINUTES, nonRejectedLinkedBlockingQueue);
//        ExecutorService executor = new MyThreadExecutor(available, available, 1L,
//                TimeUnit.MINUTES, nonRejectedLinkedBlockingQueue);

        while (iter.hasNext()) {
            final Integer next = iter.next();
            CompletableFuture.runAsync(() -> {consumer.accept(next);}, executor).exceptionally(throwable -> {
                System.out.println("--->susu");
                return null;
            });
        }

        awaitTerminationAfterShutdown(executor);

    }

    public static void awaitTerminationAfterShutdown(ExecutorService executorService) {
        if (executorService == null || executorService.isShutdown()) {
            return;
        }
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

class MyThreadExecutor extends ThreadPoolExecutor {
    public MyThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        throw new RuntimeException("--->" + t);
    }
}

class NonRejectedLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

    public NonRejectedLinkedBlockingQueue(int capacity, boolean fair) {
        super(capacity);
    }

    @Override
    public boolean offer(E e) {
        // turn offer() and add() into a blocking calls (unless interrupted)
        try {
            put(e);
            return true;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
