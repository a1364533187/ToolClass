package cn.dyz.tools.file.concurrent.threadlearn.sync;

import java.util.concurrent.CountDownLatch;

/**
 * Create by suzhiwu on 2019/02/09
 */
public class SyncTest {

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        MySync mySync = new MySync();
        CountDownLatch latch  = new CountDownLatch(2);
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 10000; j++) {
                    mySync.lock();
                    count++;
                    mySync.unlock();
                }
                latch.countDown();
            }
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r1);
        t1.start();
        t2.start();
        latch.await();
        System.out.println(count);

    }
}
