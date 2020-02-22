package com.bigcow.com.socket;

import java.io.IOException;

/**
 * Create by suzhiwu on 2019/02/19
 */
public class UdpPoint2 {

    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    UdpPoint1.receive(7777);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        UdpPoint1.send(6666);
    }

}
