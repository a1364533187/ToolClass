package cn.dyz.tools.file.concurrent.threadlearn.kindof;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by suzhiwu on 2019/02/09
 */
public class OneCondition {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // ReentrantLock + 一把锁 + 一个锁条件
        ReentrantLock lock = new ReentrantLock();
        Condition lockCond = lock.newCondition();
        final Queue<Integer> que = new LinkedList<>();
        final int queLen = 10;

        Thread producer  = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    lock.lock();
                    try {
                        while (que.size() == queLen) {
                            lockCond.await();
                        }
                        ((LinkedList<Integer>) que).push(i);
                        lockCond.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });

        Thread consumer  = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    lock.lock();
                    try {
                        while (que.size() == 0) {
                            lockCond.await();
                        }
                        System.out.println("cosnumer: " + ((LinkedList<Integer>) que).poll());
                        lockCond.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });

        producer.start();
        consumer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long cost = System.currentTimeMillis() - start;
        System.out.println("duration: " + cost);
    }
}
