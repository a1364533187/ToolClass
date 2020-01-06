package cn.dyz.tools.file.concurrent.threadlearn.reentrantlockDemo;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by suzhiwu on 2019/02/03
 */
public class ProAndCons {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition notEmpty = lock.newCondition(); // 队列满的话，是full
        Condition notFull = lock.newCondition(); // 队列空的话， 是empty
        LinkedBlockingQueue<Integer> lbq = new LinkedBlockingQueue();
        final int queueSize = 10;

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    lock.lock();
                    try {
                        while (queueSize == lbq.size()) {
                            notFull.await();
                        }
                        lbq.add(i);
                        notEmpty.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }

            }
        });
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    lock.lock();
                    try {
                        while (0 == lbq.size()) {
                            notEmpty.await();
                        }
                        System.out.println("condumer: " + lbq.take());
                        notFull.signalAll();
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
    }
}

