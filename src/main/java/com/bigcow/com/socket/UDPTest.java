package com.bigcow.com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import org.junit.Test;

/**
 * Create by suzhiwu on 2019/02/19
 */
public class UDPTest {

    @Test
    public void testUdpClient() throws IOException {
        //定义socket
        DatagramSocket socket = new DatagramSocket();
        byte[] bytes = "hello".getBytes();
        //地址
        InetAddress address = InetAddress.getByName("127.0.0.1");
        //参数：数据、长度、地址、端口
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, 6666);
        //3.发送数据包
        socket.send(dp);

        //关闭socket
        socket.close();
    }

    @Test
    public void testUdpServer() throws IOException {
        //定义socket
        DatagramSocket socket = new DatagramSocket(6666);

        byte[] revData = new byte[100];
        //定义接收的数据报
        DatagramPacket dp = new DatagramPacket(revData, 0, revData.length);
        //接收数据报
        socket.receive(dp);

        System.out.println(new String(dp.getData(), 0, dp.getLength()) + "--->ip: "
                + dp.getSocketAddress() + "----> port" + dp.getPort());
        //关闭socket
        socket.close();
    }

    @Test
    public void testUdpClient2() throws IOException {
        send(7777);
    }

    @Test
    public void testUdpServer2() throws IOException {
        receive(7777);
    }

    void receive(int port) throws IOException {
        //定义socket
        DatagramSocket socket = new DatagramSocket(port);
        //不断接收发来的消息
        while (true) {
            byte[] revData = new byte[100];
            //定义接收的数据报
            DatagramPacket dp = new DatagramPacket(revData, 0, revData.length);
            //接收数据报
            socket.receive(dp);

            System.out.println(new String(dp.getData(), 0, dp.getLength()) + "--->ip: "
                    + dp.getSocketAddress() + "----> port" + dp.getPort());
        }

//        //关闭socket
//        socket.close();
    }

    void send(int port) throws IOException {
        //定义socket
        DatagramSocket socket = new DatagramSocket();
        System.out.println("start----");
        //2.封装数据
        byte[] bytes = "hello".getBytes();
        //地址
        InetAddress address = InetAddress.getByName("127.0.0.1");
        //参数：数据、长度、地址、端口
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);
        //3.发送数据包
        socket.send(dp);

        //关闭socket
        socket.close();
    }
}
