package cn.dyz.tools.file.ProducerConsumerProblem.ReentrantLockUse;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by suzhiwu on 2019/8/11
 */
public class proAndCon {

    public static void main(String[] args) {
        final Buffer buffer = new Buffer();
        ExecutorService pro = Executors.newFixedThreadPool(1);
        pro.submit(new Runnable() {

            public void run() {
                Thread.currentThread().setName("producer");
                for (int i = 0; i < 20; i++) {
                    buffer.put();
                }
            }
        });
        pro.shutdown();
        ExecutorService con = Executors.newFixedThreadPool(1);
        con.submit(new Runnable() {

            public void run() {
                Thread.currentThread().setName("consumer");
                while (true) {
                    buffer.take();
                }
            }
        });
    }

    static class Buffer {

        private final static ReentrantLock lock = new ReentrantLock();
        private final static Condition full = lock.newCondition();
        private final static Condition empty = lock.newCondition();
        private final static int MAX_QUEUE = 10;
        private static AtomicInteger count = new AtomicInteger(0);
        private static LinkedList<Integer> queue = new LinkedList<Integer>();

        public void take() {
            lock.lock();
            while (queue.isEmpty()) {
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread() + " consumer value is: " + queue.peek());
            queue.removeFirst();
            full.signalAll();
            lock.unlock();
        }

        public void put() {
            lock.lock();
            while (queue.size() > MAX_QUEUE) {
                try {
                    full.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            queue.addLast(count.get());
            System.out.println(Thread.currentThread() + " produce value is: " + count.get());
            count.getAndIncrement();
            empty.signalAll();
            lock.unlock();
        }

    }
}
