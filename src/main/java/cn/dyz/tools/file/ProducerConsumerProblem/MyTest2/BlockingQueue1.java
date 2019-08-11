package cn.dyz.tools.file.ProducerConsumerProblem.MyTest2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueue1 {

    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<Integer>(2);
        Thread p1 = new Thread(new Producer(blockingQueue));
        p1.start();

        Thread c1 = new Thread(new Consumer(blockingQueue));
        c1.start();
    }

}

class Producer implements Runnable {

    private BlockingQueue blockingQueue;

    Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                blockingQueue.put(i); //阻塞式
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("producer add " + i);
        }
    }
}

class Consumer implements Runnable {

    private BlockingQueue blockingQueue;

    Consumer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                System.out.println("consumer take: " + blockingQueue.take()); //阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
