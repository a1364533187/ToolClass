package cn.dyz.tools.file.concurrent.threadlearn.kindof;

import java.util.LinkedList;
import java.util.Queue;

public class SynchronizedObjectDemo {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        /*
         * synchronized + 共享object 实现， 共享变量生产-消费者模式
         * 这种实现方式有一个问题： 2个线程共用一把锁+一个锁变量，锁会是瓶颈
         */
        //共享资源 lock + queue, 这里lock queue是共享资源， lock是共享的锁变量,
        final Object lock = new Object();
        final Queue<Integer> que = new LinkedList<>();
        final int queLen = 10;

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    synchronized (lock) {
                        while (que.size() == queLen) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        ((LinkedList<Integer>) que).push(i);
                        lock.notifyAll();
                    }
                }
            }
        });
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    synchronized (lock) {
                        while (que.size() == 0) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("consumer: " + ((LinkedList<Integer>) que).poll());
                        lock.notifyAll();
                    }
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        long cost = System.currentTimeMillis() - start;
        System.out.println("duration: " + cost);
    }
}
