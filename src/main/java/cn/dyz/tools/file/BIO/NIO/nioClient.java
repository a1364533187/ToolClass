package cn.dyz.tools.file.BIO.NIO;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class nioClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            while (true) {
                Scanner rc = new Scanner(System.in);
                String message  = rc.next();
                if (message.equals("1")) {
                    break;
                }
                socket.getOutputStream().write(message.getBytes());
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
