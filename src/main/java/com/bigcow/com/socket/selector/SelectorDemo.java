package com.bigcow.com.socket.selector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

public class SelectorDemo {

    @Test
    public void tcpClient() throws IOException, InterruptedException {
        Socket client = new Socket();
        //client 连接到 server
        client.connect(new InetSocketAddress("127.0.0.1", 7777));
        byte[] bytes = new byte[1024];
        while (true) {
            //拿到outputStream 准备写入数据
            OutputStream out = client.getOutputStream();
            out.write("hello".getBytes());
            out.write("world".getBytes());
            Thread.sleep(1000);

            InputStream intput = client.getInputStream();
            int readSize = intput.read(bytes);
            System.out.println(new String(bytes, 0, readSize));
        }

        //        // 关闭 client
        //        client.close();
    }

    @Test
    public void tcpClient1() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.socket().connect(new InetSocketAddress(7777));
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        while (true) {
            socketChannel.write(ByteBuffer.wrap("hello to server".getBytes()));
            Thread.sleep(1000);
            readBuffer.clear();
            int readSize = socketChannel.read(readBuffer);
            System.out.println(new String(readBuffer.array(), 0, readSize));
        }

        //        // 关闭 client
        //        client.close();
    }

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SelectableChannel selChannel = ServerSocketChannel.open();
        selChannel.configureBlocking(false);
        ((ServerSocketChannel) selChannel).socket().bind(new InetSocketAddress(7777));
        //接收client 的连接
        SelectionKey sk = selChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach("hahaha");

        ByteBuffer readBuf = ByteBuffer.allocate(1024);
        //        ByteBuffer writeBuf = ByteBuffer.allocate(1024);

        while (true) {
            int keys = selector.select();
            if (keys == 0) {
                continue;
            }
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()) {
                SelectionKey key = selectionKeys.next();
                System.out.println("---" + key.attachment());
                if (key.isAcceptable()) {
                    System.out.println("server acceptable");
                    SocketChannel socketChannel = ((ServerSocketChannel) selChannel).accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey acceptKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    acceptKey.attach("--> accept key");
                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    readBuf.clear();
                    int readSize = sc.read(readBuf);
                    readBuf.flip();
                    System.out.println("recieve: " + new String(readBuf.array(), 0, readSize)
                            + "--->" + new Date() + "--->" + key.attachment());
                    key.attach("<-->ha");
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    String str = "send to client " + new Date();
                    //heap buffer
                    ByteBuffer writeBuf = ByteBuffer.wrap(str.getBytes());
                    sc.write(writeBuf);
                    key.interestOps(SelectionKey.OP_READ);
                }
                selectionKeys.remove();
            }

        }

    }
}
