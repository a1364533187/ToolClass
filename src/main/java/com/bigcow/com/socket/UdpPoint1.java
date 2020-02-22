package com.bigcow.com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Create by suzhiwu on 2019/02/19
 */
public class UdpPoint1 {

    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    receive(6666);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        send(7777);
    }

    //接收消息方法
    protected static void receive(int port) throws IOException {
        System.out.println("UDOSocket1 Receiver Start...");

        //1.创建udp的socket服务,并声明端口号
        DatagramSocket ds = new DatagramSocket(port);
        //无限循环，一直处于接收状态
        while (true) {
            //2.创建接收数据的数据包
            byte[] bytes = new byte[1024];
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length);

            //3.将数据接收到数据包中
            ds.receive(dp);

            //4.解析数据
            String content = new String(dp.getData(), 0, dp.getLength());
            System.out.println("UDPSocket1 Receive:" + content);
        }
    }

    protected static void send(int port) throws IOException {
        //1.创建socket服务
        DatagramSocket ds = new DatagramSocket();

        //将键盘输入的信息转换成输入流再放入到缓冲区
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = br.readLine()) != null) {
            //2.封装数据
            byte[] bytes = line.getBytes();
            //地址
            InetAddress address = InetAddress.getByName("127.0.0.1");
            //参数：数据、长度、地址、端口
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);

            //3.发送数据包
            ds.send(dp);
        }

        //4.关闭socket服务
        ds.close();
    }
}
