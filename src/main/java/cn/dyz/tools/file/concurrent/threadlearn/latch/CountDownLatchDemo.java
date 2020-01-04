package cn.dyz.tools.file.concurrent.threadlearn.latch;

import java.util.concurrent.CountDownLatch;

/**
 * Create by suzhiwu on 2019/01/03
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t1 run");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t2 run");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        });
        t1.start();
        t2.start();
        //latch.await(); //阻塞当前线程，直到latch count == 0
        System.out.println("main is over");
    }
}
