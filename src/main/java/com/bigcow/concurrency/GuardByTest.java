package com.bigcow.concurrency;

import org.apache.http.annotation.GuardedBy;
import org.junit.Test;

public class GuardByTest {

    //线程安全50001
    @Test
    public void test1() throws InterruptedException {
        A a = new A(1);
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    a.setNext();
                }
            }, String.valueOf(i)).start();
        }

        // 当所有累加线程都结束
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(a.getNext());
    }

    //48514
    @Test
    public void test2() throws InterruptedException {
        B b = new B(1);
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    b.setNext();
                }
            }, String.valueOf(i)).start();
        }

        // 当所有累加线程都结束
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(b.getNext());
    }
}

class A {

    public A(int value) {
        this.value = value;
    }

    @GuardedBy("this")
    private int value;

    public int getNext() {
        return value;
    }

    public synchronized int setNext() {
        value = value + 1;
        return value;
    }

}

class B {

    public B(int value) {
        this.value = value;
    }

    @GuardedBy("B::this") //只是提示使用要注意线程安全， 并不保证线程安全
    private int value;

    public int getNext() {
        return value;
    }

    public int setNext() {
        value = value + 1;
        return value;
    }

}