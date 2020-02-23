package com.bigcow.com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

/**
 * create by suzhiwu on 2019/02/19
 */
public class TcpDemo {

    @Test
    public void tcpClient() throws IOException {
        Socket client = new Socket();
        //client 连接到 server
        client.connect(new InetSocketAddress("127.0.0.1", 1333));
        //拿到outputStream 准备写入数据
        OutputStream out = client.getOutputStream();
        out.write("hello".getBytes());
        out.write("world".getBytes());

        // 关闭 client
        client.close();
    }

    @Test
    public void tcpServer() throws IOException {
        //
        ServerSocket serverSocket = new ServerSocket(7777);
        // 接受客户端的请求
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        //4.通过输入流获取到客户端传递的数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        socket.close();
        serverSocket.close();
    }

    @Test
    public void tcpClient2() throws IOException {
        Socket client = new Socket();
        //client 连接到 server
        client.connect(new InetSocketAddress("127.0.0.1", 7777));
        //拿到outputStream 准备写入数据
        OutputStream out = client.getOutputStream();
        out.write("hello".getBytes());
        out.write("world".getBytes());
        //告诉client 数据发送完毕
        client.shutdownOutput();

        //再读取从client 发送过来的消息
        InputStream in = client.getInputStream();
        byte[] bytes = new byte[1024];
        int size = in.read(bytes);
        System.out.println(new String(bytes, 0, size));

        client.close();
    }

    @Test
    public void tcpServer2() throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);
        while (true) {
            // 接受客户端的请求
            new Thread(new Handler(serverSocket.accept())).start();
        }
    }

    class Handler implements Runnable {

        private Socket socket;

        Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            InputStream inputStream = null;
            try {
                inputStream = socket.getInputStream();
                //4.通过输入流获取到客户端传递的数据
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                socket.shutdownInput();

                //读入完数据再给客户端发送completed
                OutputStream out = socket.getOutputStream();
                out.write("completed".getBytes());
                socket.shutdownOutput();

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
