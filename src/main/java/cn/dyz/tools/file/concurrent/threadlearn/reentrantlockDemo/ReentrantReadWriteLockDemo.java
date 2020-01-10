package cn.dyz.tools.file.concurrent.threadlearn.reentrantlockDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Create by suzhiwu on 2019/02/03
 */
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        List<String> strs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strs.add("" + i);
        }
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                readLock.lock();
                for (int i = 0; i < strs.size(); i++) {
                    System.out.println("t1: " + strs.get(i));
                }
                readLock.unlock();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                readLock.lock();
                for (int i = 0; i < strs.size(); i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t2: " + strs.get(i));
                }
                readLock.unlock();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                readLock.lock();
                for (int i = 0; i < strs.size(); i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t3: " + strs.get(i));
                }
                readLock.unlock();
            }
        });

        Thread t4Write = new Thread(new Runnable() {
            @Override
            public void run() {
                writeLock.lock();
                for (int i = 0; i < 10; i++) {
                    strs.add("write");
                    System.out.println("t4");
                }
                writeLock.unlock();
            }
        });

        t1.start();
        t4Write.start();
        t2.start();
        t3.start();
        t3.join();
        t2.join();
        t1.join();
        t4Write.join();
        //可以看到读锁可以被多个线程同时获取

    }
}
