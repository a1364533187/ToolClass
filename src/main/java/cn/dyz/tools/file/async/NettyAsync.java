package cn.dyz.tools.file.async;

import java.util.concurrent.Callable;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Create by suzhiwu on 2019/02/02
 */
public class NettyAsync {

    public static void main(String[] args) throws InterruptedException {
        //使用netty的executor
        final DefaultEventExecutor executor = new DefaultEventExecutor();
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello";
            }
        });

        future.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                System.out.println(future.get() + " world");
            }
        });
        System.out.println("main is over");

        Thread.currentThread().join();

    }
}
