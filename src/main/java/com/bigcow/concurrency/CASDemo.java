package com.bigcow.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

public class CASDemo {

    @Nullable
    final Concurrency concurrency;

    private static final int MAX_CONCURRENCY = 100000;

    CASDemo() {
        concurrency = new Concurrency();
    }

    @SuppressWarnings("checkstyle:VisibilityModifier")
    static class Concurrency {

        final AtomicInteger atomicValue = new AtomicInteger(0);

        Concurrency() {
        }

        public void incr() {
            atomicValue.updateAndGet(v -> Math.min(v + 1, MAX_CONCURRENCY));
        }

        public void decr() {
            atomicValue.updateAndGet(v -> Math.max(v - 1, 0));
        }

        public int get() {
            return atomicValue.get();
        }

        public void reset() {
            atomicValue.set(0);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CASDemo casDemo = new CASDemo();
//        for (int i = 0; i < 10000; i++) {
//            new Thread(() -> {
//                casDemo.concurrency.decr();
//                casDemo.concurrency.decr();
//            }).start();
//        }

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                casDemo.concurrency.incr();
            }).start();
        }
        Thread.sleep(2000);
        System.out.println(casDemo.concurrency.get());

        new CountDownLatch(1).await();
    }
}
