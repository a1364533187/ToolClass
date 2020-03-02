package com.bigcow.com.cacheline;

/**
 * Create by suzhiwu on 2020/02/13
 */
public class FalseSharingTest {

    public static void main(String[] args) throws InterruptedException {
        testPointer(new Pointer());
    }

    private static void testPointer(Pointer pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.x++;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.y++;
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(pointer);
    }

    static class Pointer {

        volatile long x;
        long p1, p2, p3, p4, p5, p6, p7; //解决伪共享
        volatile long y;

        @Override
        public String toString() {
            return "Pointer{" + "x=" + x + ", y=" + y + '}';
        }
    }
}
