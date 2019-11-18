package cn.dyz.tools.file.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioConcurrencyServer {

    /**
     * BIO 特点 有2次阻塞， 第一次等待连接时阻塞， 第二次等待数据时阻塞
     * 那么如何处理并发呢： 未每一个到来的请求，分配一个线程处理
     * 问题： 大量的线程十分消耗资源
     * @param args
     */
    public static void main(String[] args) {
        byte[] buffer = new byte[1024]; //1M
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("服务器启动并监听8080端口");

            while (true) {
                System.out.println();
                System.out.println("服务器正在等待连接...");
                Socket socket = serverSocket.accept();
                System.out.println("服务器已经接收到连接请求。。。");
                System.out.println();
                new Thread(() -> {
                    System.out.println("服务器正在等待读数据。。。");
                    try {
                        socket.getInputStream().read(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println();
                    System.out.println("服务器已经读到数据。。。");
                    System.out.println();
                    System.out.println("接收到数据：" + new String(buffer));
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
