package com.bigcow.com.netty.addcallback;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class AddCallBackDemo {

    public static void main(String[] args) {
        ListeningExecutorService service = MoreExecutors
                .listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture f = service.submit(new Callable<Integer>() {

            public Integer call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 10;
            }
        });
        Futures.addCallback(f, new FutureCallback<Integer>() {

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("计算失败" + throwable);
            }

            @Override
            public void onSuccess(@Nullable Integer integer) {
                try {
                    System.out.println("计算成功" + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        service.shutdown();
    }
}
