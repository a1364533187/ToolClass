package cn.dyz.tools.file.concurrent.threadlearn.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Create by suzhiwu on 2020/1/3
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> abq = new ArrayBlockingQueue<Integer>(1);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    abq.put(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    abq.put(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(abq);
            }
        });
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();

    }
}
