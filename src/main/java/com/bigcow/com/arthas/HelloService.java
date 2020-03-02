package com.bigcow.com.arthas;

import java.util.concurrent.TimeUnit;

/**
 * Create by suzhiwu on 2020/02/19
 */
public class HelloService {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            hello();
            hello2();
        }
    }

    public static void hello() {
        System.out.println("hello world");
    }

    public static void hello2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("hello world2...");
    }
}
