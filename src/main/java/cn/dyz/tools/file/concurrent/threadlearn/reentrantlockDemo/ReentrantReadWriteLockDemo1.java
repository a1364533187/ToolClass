package cn.dyz.tools.file.concurrent.threadlearn.reentrantlockDemo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Create by suzhiwu on 2019/02/03
 */
public class ReentrantReadWriteLockDemo1 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("cpu nums: " + Runtime.getRuntime().availableProcessors());
        SafeArrayList<String> list = new SafeArrayList<>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    list.add("t1:" + i);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    list.add("t2:" + i);
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    list.add("t3:" + i);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        list.remove("t2:1");
        t2.join();
        t3.join();
        System.out.println(list.size());
    }
}

// 用读写锁改造LinkedList
class SafeArrayList<T> {
    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.WriteLock writeLock;
    private ReentrantReadWriteLock.ReadLock readLock;
    private ArrayList<T> arrayList;

    public SafeArrayList() {
        writeLock = lock.writeLock();
        readLock = lock.readLock();
        arrayList = new ArrayList<>();
    }

    public T get(int i) {
        readLock.lock();
        T t = arrayList.get(i);
        readLock.unlock();
        return t;
    }

    public void add(T t) {
        writeLock.lock();
        arrayList.add(t);
        writeLock.unlock();
    }

    public void remove(T t) {
        writeLock.lock();
        arrayList.remove(t);
        writeLock.unlock();
    }

    public int size() {
        readLock.lock();
        int size = arrayList.size();
        readLock.unlock();
        return size;
    }
}