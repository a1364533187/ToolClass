package cn.dyz.tools.file.ProducerConsumerProblem.MyTest1;

public class queueis1 {

    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread p1 = new Thread(new Producer(10, buffer));
        p1.start();

        Thread c1 = new Thread(new Consumer(10, buffer));
        c1.start();
    }
}

class Buffer {

    private boolean empty = true;
    private int content;

    public synchronized int get() {
        while (empty == true) {
            try {
                wait(); //this 锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        empty = true;
        System.out.println("consumer get: " + content);
        notifyAll();    //当前this锁被占用，需要唤醒这个this锁的等待队列里的其他线程(这里是唤醒producer线程的锁)
        return content;
    }

    public synchronized void put(int content) {
        while (empty == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        empty = false;
        this.content = content;
        System.out.println("producer put: " + content);
        notifyAll();
    }
}

class Producer implements Runnable {

    private Buffer buffer;
    private int n;

    Producer(int n, Buffer buffer) {
        this.n = n;
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            buffer.put(i);
        }
    }
}

class Consumer implements Runnable {

    private Buffer buffer;
    private int n;

    Consumer(int n, Buffer buffer) {
        this.n = n;
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            buffer.get();
        }
    }
}
