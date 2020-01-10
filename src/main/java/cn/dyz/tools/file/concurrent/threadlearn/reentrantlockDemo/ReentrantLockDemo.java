package cn.dyz.tools.file.concurrent.threadlearn.reentrantlockDemo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by suzhiwu on 2019/02/03
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        // ReentrantLock   lock + condition
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        try {
            System.out.println("hahah");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("main is over");

    }


}
