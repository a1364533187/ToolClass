package com.bigcow.com.socket.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

public class SelectorDemo {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SelectableChannel selChannel = ServerSocketChannel.open();
        selChannel.configureBlocking(false);
        ((ServerSocketChannel) selChannel).socket().bind(new InetSocketAddress(7777));
        //接收client 的连接
        SelectionKey sk = selChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach("hahaha");

        ByteBuffer readBuf = ByteBuffer.allocate(1024);
        ByteBuffer writeBuf = ByteBuffer.allocate(1024);
        writeBuf.put("recieve".getBytes());
        writeBuf.flip();

        while (true) {
            int keys = selector.select();
            if (keys == 0) {
                continue;
            }
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()) {
                SelectionKey key = selectionKeys.next();
                if (key.isAcceptable()) {
                    System.out.println("server acceptable");
                    SocketChannel socketChannel = ((ServerSocketChannel) selChannel).accept();
                    socketChannel.configureBlocking(false);
                    key.attach("haha");
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    readBuf.clear();
                    int readSize = sc.read(readBuf);
                    readBuf.flip();
                    System.out.println("recieve: " + new String(readBuf.array(), 0, readSize)
                            + "--->" + new Date() + "--->" + key.attachment());
                    key.attach("ha");
                    key.interestOps(SelectionKey.OP_READ);
                }
                //                else if (key.isWritable()) {
                //                    SocketChannel sc = (SocketChannel) key.channel();
                //                    writeBuf.rewind();
                //                    sc.write(writeBuf);
                //                    key.interestOps(SelectionKey.OP_READ);
                //
                //                }
                selectionKeys.remove();
            }

        }

    }
}
