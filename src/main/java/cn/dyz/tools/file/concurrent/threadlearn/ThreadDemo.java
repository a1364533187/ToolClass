package cn.dyz.tools.file.concurrent.threadlearn;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Create by suzhiwu on 2019/12/24
 */
public class ThreadDemo {

    public static void main(String[] args) {
        //extends Thread 类直接启动
        new MyThread().start();
        new MyThread().start();
        // runnable task 启动
        new Thread(new MyThread1()).start();

        //同过包装成future task 异步启动
        FutureTask<String> futureTask = new FutureTask<>(new MyThread2());
        new Thread(futureTask).start();

        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyThread extends Thread {

    @Override
    public void run() {
        Thread.currentThread().setPriority(1);
        System.out.println(Thread.currentThread() + "+ thread");
    }
}

class MyThread1 implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + "+ thread");
    }
}

class MyThread2 implements Callable<String> {

    @Override
    public String call() {
        System.out.println(Thread.currentThread() + "+ thread");
        return "succ";
    }
}
