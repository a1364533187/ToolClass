package com.bigcow.spring.highconcurrency;

import org.junit.Test;

public class ConcurrencyDemo {

    @Test
    public void test1() throws InterruptedException {
        Flusher<String> stringFlusher = new Flusher<>("test", 5, 1000, 30, 1,
                new PrintOutProcessor());
        int index = 1;
        while (true) {
            stringFlusher.add(String.valueOf(index++));
            Thread.sleep(100);
        }
    }
}
