package cn.dyz.tools.file.lock;

import org.junit.Test;

public class LockDemo {

    int i = 6000;

    //    ReentrantLock lock = new ReentrantLock();
    ZhiwuLock lock = new ZhiwuLock();

    private void orderDecr() {
        lock.lock();
        try {
            i--; //线程不安全
        } finally {
            lock.unlock();
        }

    }

    @Test
    public void test1() throws InterruptedException {
        for (int j = 0; j < 6; j++) {
            new Thread(() -> {
                for (int k = 0; k < 1000; k++) {
                    orderDecr();
                }
            }).start();
        }
        Thread.sleep(3000);
        System.out.println(i);
    }
}
