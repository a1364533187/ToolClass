package com.bigcow.com.synchronizeddemo;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class SyncDemo {

    @Test
    public void testSync1() throws InterruptedException {
        //验证类锁
        AClass a1 = new AClass();
        new Thread(() -> {
            try {
                a1.test1(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                a1.test1(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * Thread-0--->0
         * Thread-0--->1
         * Thread-0--->2
         * Thread-0--->3
         * Thread-0--->4
         * Thread-1--->0
         * Thread-1--->1
         * Thread-1--->2
         * Thread-1--->3
         * Thread-1--->4
         */
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

    @Test
    public void testSync2() throws InterruptedException {
        //验证类锁
        AClass a1 = new AClass();
        new Thread(() -> {
            try {
                a1.test1(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        AClass a2 = new AClass();
        new Thread(() -> {
            try {
                a2.test1(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /*
        * Thread-0--->0
            Thread-0--->1
            Thread-0--->2
            Thread-0--->3
            Thread-0--->4
            Thread-1--->0
            Thread-1--->1
            Thread-1--->2
            Thread-1--->3
            Thread-1--->4
        *
        * */

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

    @Test
    public void testSync3() throws InterruptedException {
        //验证对象锁
        AClass a1 = new AClass();
        new Thread(() -> {
            try {
                a1.test2(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        AClass a2 = new AClass();
        new Thread(() -> {
            try {
                a2.test2(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * Thread-0--->0
         * Thread-1--->0
         * Thread-0--->1
         * Thread-1--->1
         * Thread-1--->2
         * Thread-0--->2
         * Thread-1--->3
         * Thread-0--->3
         * Thread-1--->4
         * Thread-0--->4
         */

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

    @Test
    public void testSync4() throws InterruptedException {
        //验证对象锁、类锁
        AClass a1 = new AClass();
        new Thread(() -> {
            try {
                a1.test1(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                a1.test2(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * Thread-0--->0
         * Thread-1--->0
         * Thread-0--->1
         * Thread-1--->1
         * Thread-1--->2
         * Thread-0--->2
         * Thread-1--->3
         * Thread-0--->3
         * Thread-1--->4
         * Thread-0--->4
         */

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

    @Test
    public void testSync5() throws InterruptedException {
        //验证静态方法， 静态方法是类锁， 非静态方法是对象锁
        AClass a1 = new AClass();
        new Thread(() -> {
            try {
                a1.test3(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                a1.test4(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * Thread-0--->0
         * Thread-1--->0
         * Thread-0--->1
         * Thread-1--->1
         * Thread-1--->2
         * Thread-0--->2
         * Thread-1--->3
         * Thread-0--->3
         * Thread-1--->4
         * Thread-0--->4
         */

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

    @Test
    public void testSync6() throws InterruptedException {
        //验证非静态方法是对象锁
        AClass a1 = new AClass();
        new Thread(() -> {
            try {
                a1.test4(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                a1.test4(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * Thread-0--->0
         * Thread-0--->1
         * Thread-0--->2
         * Thread-0--->3
         * Thread-0--->4
         * Thread-1--->0
         * Thread-1--->1
         * Thread-1--->2
         * Thread-1--->3
         * Thread-1--->4
         */

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

    @Test
    public void testSync7() throws InterruptedException {
        //验证静态方法， 静态方法是类锁， 非静态方法是对象锁
        AClass a1 = new AClass();
        new Thread(() -> {
            try {
                a1.test4(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        AClass a2 = new AClass();
        new Thread(() -> {
            try {
                a2.test4(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * Thread-0--->0
         * Thread-1--->0
         * Thread-0--->1
         * Thread-1--->1
         * Thread-1--->2
         * Thread-0--->2
         * Thread-1--->3
         * Thread-0--->3
         * Thread-1--->4
         * Thread-0--->4
         */

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}

class AClass {
    public void test1(String threadName) throws InterruptedException {
        synchronized (AClass.class) {
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + "--->" + i);
                Thread.sleep(1000);
            }
        }
    }

    public void test2(String threadName) throws InterruptedException {
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + "--->" + i);
                Thread.sleep(1000);
            }
        }
    }

    public synchronized static void test3(String threadName) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.println(threadName + "--->" + i);
            Thread.sleep(1000);
        }
    }

    public synchronized void test4(String threadName) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.println(threadName + "--->" + i);
            Thread.sleep(1000);
        }
    }
}