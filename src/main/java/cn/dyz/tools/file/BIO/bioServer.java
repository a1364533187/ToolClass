package cn.dyz.tools.file.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Create by suzhiwu on 2019/11/17
 */
public class bioServer {

    /**
     * BIO 特点 有2次阻塞， 第一次等待连接时阻塞， 第二次等待数据时阻塞
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
                System.out.println("服务器正在等待读数据。。。");
                socket.getInputStream().read(buffer);
                System.out.println();
                System.out.println("服务器已经读到数据。。。");
                System.out.println();
                System.out.println("接收到数据：" + new String(buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
