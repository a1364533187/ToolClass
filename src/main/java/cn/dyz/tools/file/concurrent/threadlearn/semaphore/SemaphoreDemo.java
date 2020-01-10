package cn.dyz.tools.file.concurrent.threadlearn.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Create by suzhiwu on 2019/02/07
 */
public class SemaphoreDemo {

    /**
     * 信号量主要用来控制线程的并发个数
     * @param args
     */
    public static void main(String[] args) {
        //利用信号量 实现3个线程交替执行
        Semaphore a1 = new Semaphore(1); //控制A线程运行的指示灯
        Semaphore a2 = new Semaphore(0); //控制B线程运行的指示灯
        Semaphore a3 = new Semaphore(0); //控制C线程运行的指示灯

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        a1.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1");
                    a2.release();
                    System.out.println("t1: " + "a1: " + a1.availablePermits() + " a2: " + a2.availablePermits()
                            + " a3: " + a3.availablePermits());
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        a2.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("2");
                    a3.release();
                    System.out.println("t2: " + "a1: " + a1.availablePermits() + " a2: " + a2.availablePermits()
                            + " a3: " + a3.availablePermits());
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        a3.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("3");
                    a1.release();
                    System.out.println("t3: " + "a1: " + a1.availablePermits() + " a2: " + a2.availablePermits()
                            + " a3: " + a3.availablePermits());
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
