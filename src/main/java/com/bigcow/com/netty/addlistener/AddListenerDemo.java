package com.bigcow.com.netty.addlistener;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

/**
 * Create by suzhiwu on 2020/02/03
 */
public class AddListenerDemo {

    public static void main(String[] args) {
        /**
         * addListener 方法增加回调函数，一般用于不在乎执行结果的地方,成功后通知
         * 等同于callback onSuccess() onError()
         */
        EventExecutorGroup group = new DefaultEventExecutorGroup(4);
        System.out.println("开始:" + new Date());

        Future<Integer> f = group.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("开始耗时计算:" + new Date());
                Thread.sleep(5000);
                System.out.println("结束耗时计算:" + new Date());
//                return 100;
                throw new RuntimeException("error");
            }
        });

        Future<Integer> listen = f.addListener(new FutureListener<Object>() {
            @Override
            public void operationComplete(Future<Object> objectFuture) throws Exception {
                System.out.println("计算结果:" + objectFuture.get());
            }
        });

        System.out.println("计算结束" + new Date());
        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
