package cn.dyz.tools.file.lock;

import org.junit.Test;

public class CASDemo {

    volatile int i = 6000;

    /**
     * 模拟CAS过程
     */
    //    private void orderDecr() {
    //        int expect;
    //        while (!compareAndSwap(expect = getCount(), expect - 1));
    //    }

//    /**
//     * 模拟CAS过程1
//     */
//    private void orderDecr() {
//        for (;;) {
//            int current = getCount();
//            int newCount = current - 1;
//            if (compareAndSwap(current, newCount)) {
//                return;
//            }
//        }
//    }

    /**
     * 模拟CAS 失败
     * @return
     */
    private void orderDecr() {
        int expect = getCount();
        int newCount = expect - 1;
        while (!compareAndSwap(expect, newCount));
    }

    private synchronized boolean compareAndSwap(int expect, int newCount) {
        if (expect == getCount()) {
            i = newCount;
            return true;
        }
        return false;
    }

    private int getCount() {
        return i;
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
        Thread.sleep(5000);
        System.out.println(i);
    }
}
