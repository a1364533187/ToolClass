package cn.dyz.tools.file.concurrent.threadlearn.interupt;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Create by suzhiwu on 2019/12/31
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("1");
        copyOnWriteArrayList.add("2");
        copyOnWriteArrayList.add("3");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                copyOnWriteArrayList.add("4");
                copyOnWriteArrayList.remove("1");
                System.out.println("t1" + copyOnWriteArrayList);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                copyOnWriteArrayList.remove("2");
                System.out.println("t2" + copyOnWriteArrayList);
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                copyOnWriteArrayList.remove("3");
                System.out.println("t3" + copyOnWriteArrayList);
            }
        });
        t1.start();
        t2.start();
        t3.start();
        System.out.println("main start: " + copyOnWriteArrayList);
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end: " + copyOnWriteArrayList);
    }
}
