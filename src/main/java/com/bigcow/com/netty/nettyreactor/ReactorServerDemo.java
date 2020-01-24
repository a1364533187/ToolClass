package com.bigcow.com.netty.nettyreactor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Create by suzhiwu on 2019/01/15
 */
public class ReactorServerDemo {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        Collections.shuffle(list);
//        System.out.println(list);
//        System.out.println("hostname: " + InetAddress.getLocalHost().getHostName());
        BlockingQueue<Integer> list = new LinkedBlockingQueue<>(2);
        list.put(1);
//        list.put(2);
//        list.put(3);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                }
            }
        }).start();
    }
}
