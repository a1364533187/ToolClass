package com.bigcow.com.socket.selector.attach;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SelectorReactorAttach {

    public static void main(String[] args) {
        /**
         * 启动Reacor
         */
        new Thread(new Reactor1(7777)).start();
    }
}

class Reactor1 implements Runnable {

    private Selector selector;

    private ServerSocketChannel servChannel;

    private volatile boolean stop;

    public Reactor1(int port) {
        try {
            selector = Selector.open();
            servChannel = ServerSocketChannel.open();
            servChannel.configureBlocking(false);
            servChannel.socket().bind(new InetSocketAddress(port), 1024);
            SelectionKey sk = servChannel.register(selector, SelectionKey.OP_ACCEPT);
            sk.attach(new Acceptor(selector, sk));
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
                int count = selector.select();
                System.out.println(Thread.currentThread().getName() + "监听就绪事件个数:" + count);
                if (count == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                // 遍历就绪事件，然后进行分发
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    Runnable attachment = (Runnable) key.attachment();
                    try {
                        attachment.run();
                        iterator.remove();
                    } catch (Exception e) {
                        System.out.println("客户端意外关闭");
                        iterator.remove();
                        e.printStackTrace();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Acceptor implements Runnable {

    private final Selector selector;

    private final SelectionKey selectionKey;

    public Acceptor(Selector selector, SelectionKey selectionKey) {
        this.selector = selector;
        this.selectionKey = selectionKey;
    }

    @Override
    public void run() {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            // 注册读事件
            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);
            // 分发读写事件
            key.attach(new Dispatcher(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Dispatcher implements Runnable {

    private final SelectionKey selectionKey;

    private int state;

    //reactor 多线程模式
    ExecutorService executorService = new ThreadPoolExecutor(3,3,1,TimeUnit.MINUTES, new LinkedBlockingQueue<>(10), new ThreadPoolExecutor.CallerRunsPolicy());

    public Dispatcher(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
        this.state = 0;
    }

    @Override
    public void run() {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        try {
            if (state == 0) {
                read(socketChannel);
            } else {
                send(socketChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println("客户端" + socketChannel.getRemoteAddress() + "关闭");
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void read(SocketChannel socketChannel) throws Exception {
        // 读取客户端的消息
        ByteBuffer buffer = ByteBuffer.allocate(50);
        int readSize = socketChannel.read(buffer);
        buffer.flip();
        String msg = new String(buffer.array(), 0, readSize);
        System.out.println("接收到客户端的消息：" + msg);
//        // 业务逻辑处理
//        process();
        executorService.submit(() -> {
            try {
                process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 设置状态为1
        state = 1;
        // 注册写事件，写事件一般不注册，因为可能出现服务器一支对写事件就绪
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    private void send(SocketChannel socketChannel) throws IOException {
        ByteBuffer wrap = ByteBuffer.wrap("server receive client msg, thank you".getBytes());
        socketChannel.write(wrap);
        state = 0;
        // 注册写事件
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    private void process() throws InterruptedException {
        // handle business logic,单线程的反应器模式的弊端主要是如果业务逻辑处理事件过长会造成长时间不无法处理select()方法
        // 进而导致客户端阻塞，所以一般将业务逻辑交由线程池来进行异步处理，防止反应器被阻塞
        TimeUnit.SECONDS.sleep(5);
    }
}
