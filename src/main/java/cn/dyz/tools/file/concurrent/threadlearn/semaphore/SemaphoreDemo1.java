package cn.dyz.tools.file.concurrent.threadlearn.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Create by suzhiwu on 2019/02/03
 */
public class SemaphoreDemo1 {

    public static void main(String[] args) {
//        Semaphore s = new Semaphore(0);
//        System.out.println("s: " + s.availablePermits());
//        try {
//            s.acquire();    //阻塞  当permits <= 0的时候， 已经无法 acquire 了
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("s: " + s.availablePermits());

        Semaphore s = new Semaphore(1);
        s.release();
        System.out.println("s: " + s.availablePermits());  // s -> 2
        s.release();
        System.out.println("s: " + s.availablePermits()); // s -> 3

        Random rd = new Random();
        rd.nextInt(1000);
        System.out.print(rd.nextInt(1000));
//        try {
//            Thread.sleep(rd.nextInt());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
