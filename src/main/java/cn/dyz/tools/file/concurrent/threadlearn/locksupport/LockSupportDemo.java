package cn.dyz.tools.file.concurrent.threadlearn.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("start park...");
                LockSupport.park();
                System.out.println("park end");
            }
        });
        t1.start();
        System.out.println("main start..");
        Thread.sleep(1000);
//        LockSupport.unpark(t1);
        t1.interrupt();
    }
}
