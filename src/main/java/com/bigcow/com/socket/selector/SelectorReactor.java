package com.bigcow.com.socket.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorReactor {

    public static void main(String[] args) {
        /**
         * 启动Reacor
         */
        new Reactor(8089).run();
    }
}

class Reactor {

    private Selector selector;

    private ServerSocketChannel servChannel;

    private volatile boolean stop;

    public Reactor(int port) {
        try {
            selector = Selector.open();
            servChannel = ServerSocketChannel.open();
            servChannel.configureBlocking(false);
            servChannel.socket().bind(new InetSocketAddress(port), 1024);
            SelectionKey sk = servChannel.register(selector, SelectionKey.OP_ACCEPT);
            stop = false;
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        disptach(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) key.channel().close();
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void disptach(SelectionKey key) {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                new Acceptor(key).run();
            }
            if (key.isReadable()) {
                new ReadHandler(key).run();
            }
            if (key.isWritable()) {
                new WriteHandler(key).run();
            }
        }
    }

}

class Acceptor {

    private SelectionKey selectionKey;

    public Acceptor(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    public void run() {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            Selector selector = selectionKey.selector();
            SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class WriteHandler {

    private SelectionKey selectionKey;

    public WriteHandler(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    public void run() {
        try {
            SocketChannel sc = (SocketChannel) selectionKey.channel();
            //处理写
            doWrite(sc);
            //写完后，将通道注册为读
            Selector selector = selectionKey.selector();
            SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doWrite(SocketChannel sc) {
        System.out.println("处理写。。。");
    }
}

class ReadHandler {

    private SelectionKey selectionKey;

    public ReadHandler(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    public void run() {
        try {
            SocketChannel sc = (SocketChannel) selectionKey.channel();
            //处理写
            doRead(sc);
            //写完后，将通道注册为读
            Selector selector = selectionKey.selector();
            SelectionKey sk = sc.register(selector, SelectionKey.OP_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doRead(SocketChannel sc) {
        System.out.println("处理写。。。");
    }
}
