package cn.dyz.tools.file.completefuture;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

import org.junit.Test;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * Create by suzhiwu on 2020/
 */
public class CompleteFutureTest {

    @Test
    public void testCompleteFuture() {
        long l = System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行耗时操作...");
            timeConsumingOperation();
            return 100;
        });
        completableFuture.whenComplete((result, e) -> {
            System.out.println("结果：" + result);
        });
        System.out.println("主线程运算耗时:" + (System.currentTimeMillis() - l) + "ms");

    }

    //多层CompleteFuture
    @Test
    public void testCompleteFuture2() throws InterruptedException {
        long l = System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("在回调中执行耗时操作11...");
            timeConsumingOperation();
            return 100;
        });
        completableFuture = completableFuture.thenCompose(i -> {
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("在回调的回调中执行耗时操作22...");
                timeConsumingOperation();
                return i + 100;
            });
        });//<1>
        completableFuture.whenComplete((result, e) -> {
            System.out.println("计算结果:" + result);
        });
        System.out.println("主线程运算耗时:" + (System.currentTimeMillis() - l) + "ms");
        new CountDownLatch(1).await();
    }

    static void timeConsumingOperation() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListenFuture() throws ExecutionException, InterruptedException {
        final int a = 2;
        ListeningExecutorService executorService = MoreExecutors.newDirectExecutorService();
        ListenableFuture<Integer> future1 = executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                System.out.println("haha1");
                return 1;
            }
        });

        ListenableFuture<Integer> future2 = executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                System.out.println("haha2");
                return 2;
            }
        });

        ListenableFuture<Integer> future3 = executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                System.out.println("haha3");
                return 3;
            }
        });

        Futures.transform(future1, resp -> {
            if (a == 2) {
                try {
                    int val = future2.get();
                    System.out.println("---->" + val);
                    return val;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return resp;
        }, MoreExecutors.directExecutor());
        System.out.println(future1.get());

    }

    @Test
    public void testListenFuture2() throws InterruptedException, ExecutionException {
        ListeningExecutorService executorService = MoreExecutors.newDirectExecutorService();
        // 原Future
        ListenableFuture<String> future1 = executorService.submit(() -> {
            System.out.println("--->");
            return "hello, future";
        });
        // 同步转换
        ListenableFuture<Integer> future2 = Futures.transform(future1, x -> x.length(),
                executorService);
        // 异步转换
        ListenableFuture<Integer> future3 = Futures.transformAsync(future1,
                input -> Futures.immediateFuture(inputTrans(input)), executorService);

        try {
            System.out.println(future3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Futures.addCallback(future1, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                if (result.length() == 13) {
                    return;
                } else {
                    try {
                        future2.get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException("--->");
                    } catch (ExecutionException e) {
                        throw new RuntimeException("---?");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, MoreExecutors.directExecutor());

        new CountDownLatch(1).await();
    }

    private int inputTrans(String input) {
        if (input.length() > 10) {
            return 3;
        } else {
            return input.length();
        }
    }
}
