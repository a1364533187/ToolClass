package com.bigcow.com.socket.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ReactorDemo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub  
        try {
            TCPReactor reactor = new TCPReactor(1333);
            reactor.run();
        } catch (IOException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
    }
}

// Reactor線程

class TCPReactor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;

    public TCPReactor(int port) throws IOException {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress(port);
        ssc.socket().bind(addr); // 在ServerSocketChannel綁定監聽端口  
        ssc.configureBlocking(false); // 設置ServerSocketChannel為非阻塞  
        SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT); // ServerSocketChannel向selector註冊一個OP_ACCEPT事件，然後返回該通道的key  
        sk.attach(new Acceptor(selector, ssc)); // 給定key一個附加的Acceptor對象
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) { // 在線程被中斷前持續運行  
            System.out.println(
                    "Waiting for new event on port: " + ssc.socket().getLocalPort() + "...");
            try {
                if (selector.select() == 0) // 若沒有事件就緒則不往下執行  
                    continue;
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys(); // 取得所有已就緒事件的key集合
            Iterator<SelectionKey> it = selectedKeys.iterator();
            while (it.hasNext()) {
                dispatch((SelectionKey) (it.next())); // 根據事件的key進行調度  
                it.remove();
            }
        }
    }

    /*
     * name: dispatch(SelectionKey key)
     * description: 調度方法，根據事件綁定的對象開新線程
     */
    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) (key.attachment()); // 根據事件之key綁定的對象開新線程  
        if (r != null) r.run();
    }

}

class Acceptor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;

    public Acceptor(Selector selector, ServerSocketChannel ssc) {
        this.ssc = ssc;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            SocketChannel sc = ssc.accept(); // 接受client連線請求
            System.out.println(sc.socket().getRemoteSocketAddress().toString() + " is connected.");

            if (sc != null) {
                sc.configureBlocking(false); // 設置為非阻塞
                SelectionKey sk = sc.register(selector, SelectionKey.OP_READ); // SocketChannel向selector註冊一個OP_READ事件，然後返回該通道的key
                selector.wakeup(); // 使一個阻塞住的selector操作立即返回
                sk.attach(new TCPHandler(sk, sc)); // 給定key一個附加的TCPHandler對象
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class TCPHandler implements Runnable {

    private final SelectionKey sk;
    private final SocketChannel sc;

    int state;

    public TCPHandler(SelectionKey sk, SocketChannel sc) {
        this.sk = sk;
        this.sc = sc;
        state = 0; // 初始狀態設定為READING  
    }

    @Override
    public void run() {
        try {
            if (state == 0) read(); // 讀取網絡數據  
            else send(); // 發送網絡數據  

        } catch (IOException e) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
        }
    }

    private void closeChannel() {
        try {
            sk.cancel();
            sc.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private synchronized void read() throws IOException {
        // non-blocking下不可用Readers，因為Readers不支援non-blocking  
        byte[] arr = new byte[1024];
        ByteBuffer buf = ByteBuffer.wrap(arr);

        int numBytes = sc.read(buf); // 讀取字符串  
        if (numBytes == -1) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
            return;
        }
        String str = new String(arr, 0, numBytes); // 將讀取到的byte內容轉為字符串型態  
        if ((str != null) && !str.equals(" ")) {
            process(str); // 邏輯處理  
            System.out.println(sc.socket().getRemoteSocketAddress().toString() + " > " + str);
            state = 1; // 改變狀態  
            sk.interestOps(SelectionKey.OP_WRITE); // 通過key改變通道註冊的事件  
            sk.selector().wakeup(); // 使一個阻塞住的selector操作立即返回  
        }
    }

    private void send() throws IOException {
        // get message from message queue  

        String str = "Your message has sent to " + sc.socket().getLocalSocketAddress().toString()
                + "\r\n";
        ByteBuffer buf = ByteBuffer.wrap(str.getBytes()); // wrap自動把buf的position設為0，所以不需要再flip()  

        while (buf.hasRemaining()) {
            sc.write(buf); // 回傳給client回應字符串，發送buf的position位置 到limit位置為止之間的內容  
        }

        state = 0; // 改變狀態  
        sk.interestOps(SelectionKey.OP_READ); // 通過key改變通道註冊的事件  
        sk.selector().wakeup(); // 使一個阻塞住的selector操作立即返回  
    }

    void process(String str) {
        // do process(decode, logically process, encode)..  
        // ..  
    }
}