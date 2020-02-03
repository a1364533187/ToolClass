package cn.dyz.tools.file.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * create by suzhiwu on 2019/02/02
 */
public class JavaAsync {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = new ThreadPoolExecutor(1,1,1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        CompletableFuture future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("susu");
                System.exit(1);
            }
        }, service);

//        System.out.println(future.get());
        //让当前的线程暂停
        Thread.currentThread().join();
    }
}
