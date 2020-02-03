package cn.dyz.tools.file.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * create by suzhiwu on 2019/02/02
 */
public class GuavaAsync {

    public static void main(String[] args) throws InterruptedException {
        //guava 对java 异步增强回调

        //java 线程池
        ExecutorService jpool = Executors.newScheduledThreadPool(2);
        //包装成guava 线程池
        ListeningExecutorService gpool = MoreExecutors.listeningDecorator(jpool);
        //提交任务 返回listenfuture
        ListenableFuture<String> listenableFuture = gpool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        });

        //绑定回调实例
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println(result + " world");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failed");
            }
        }, gpool);

        Thread.currentThread().join();


    }
}
