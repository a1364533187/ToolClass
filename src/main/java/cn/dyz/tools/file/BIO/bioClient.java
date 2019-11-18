package cn.dyz.tools.file.BIO;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Create by suzhiwu on 2019/11/17
 */
public class bioClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            Scanner rc = new Scanner(System.in);
            String message  = rc.next();
            socket.getOutputStream().write(message.getBytes());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
