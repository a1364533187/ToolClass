package com.bigcow.com.future;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.junit.Test;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * 主要的目的 转换各类future
 * future -> listenFuture
 * future -> CompleteFuture
 * listenFuture -> CompleteFuture
 */
public class FutureAdapterDemo {

    //FutureTask
    @Test
    public void testFutureTask() throws ExecutionException, InterruptedException {
        int available = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = new ThreadPoolExecutor(available, available * 2, 1,
                TimeUnit.MINUTES, new SynchronousQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        FutureTask task = new FutureTask<String>(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "hello world";
            }
        });
        executor.execute(task);
        //        Future<?> future = executor.submit(task);
        if (task.isDone()) {
            System.out.println("task is done");
        }
        System.out.println(task.get());
        //        System.out.println(future.get());
    }

    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        int available = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = new ThreadPoolExecutor(available, available * 2, 1,
                TimeUnit.MINUTES, new SynchronousQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                System.out.println("hello world");
                return "hello world";
            }
        };
        System.out.println("--->" + available);
        for (int i = 0; i < 100; i++) {
            executor.submit(callable);
        }
        new CountDownLatch(1).await();
    }

    private Supplier<Integer> supplier = () -> 3;

    @Test
    public void testCompleteFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 3);
        //        completableFuture.complete(5);
        completableFuture.whenComplete((integer, throwable) -> {
            System.out.println(integer);
        });
    }

    @Test
    public void testCompltableFuture1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 100;
        });
        future.join();
        //        future.get();
    }

    @Test
    public void future2CompletableFuture() throws ExecutionException, InterruptedException {
        int available = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = new ThreadPoolExecutor(available, available * 2, 1,
                TimeUnit.MINUTES, new SynchronousQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                System.out.println("hello world");
                return "hello world";
            }
        };

        Future<String> future = executor.submit(callable);
        CompletableFuture<String> completableFuture = makeCompletableFuture(future);
        completableFuture.whenComplete((x, throwable) -> {
            System.out.println("future 转换为complete future成功");
        });

        new CountDownLatch(1).await();
    }

    @Test
    public void testListenableFuture2CompletableFuture()
            throws ExecutionException, InterruptedException {
        //带有回调机制的线程池
        ListeningExecutorService service = MoreExecutors
                .listeningDecorator(Executors.newFixedThreadPool(3));
        ListenableFuture<String> listenableFuture = service.submit(() -> {
            Thread.sleep(1000);
            return "haha";
        });
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {

            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("success");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failed");
            }
        }, MoreExecutors.directExecutor());

        //ListenableFuture 转换为 CompletableFuture
        ListenableFutureAdapter<String> listenableFutureAdapter = new ListenableFutureAdapter(
                listenableFuture);
        CompletableFuture<String> completableFuture = listenableFutureAdapter
                .getCompletableFuture();
        completableFuture.whenComplete((x, failure) -> {
            System.out.println("completable future");
        });

        new CountDownLatch(1).await();
    }

    public static <T> CompletableFuture<T> makeCompletableFuture(Future<T> future) {
        if (future.isDone()) return transformDoneFuture(future);
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (!future.isDone()) awaitFutureIsDoneInForkJoinPool(future);
                return future.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                // Normally, this should never happen inside ForkJoinPool
                Thread.currentThread().interrupt();
                // Add the following statement if the future doesn't have side effects
                // future.cancel(true);
                throw new RuntimeException(e);
            }
        });
    }

    private static <T> CompletableFuture<T> transformDoneFuture(Future<T> future) {
        CompletableFuture<T> cf = new CompletableFuture<>();
        T result;
        try {
            result = future.get();
        } catch (Throwable ex) {
            cf.completeExceptionally(ex);
            return cf;
        }
        cf.complete(result);
        return cf;
    }

    private static void awaitFutureIsDoneInForkJoinPool(Future<?> future)
            throws InterruptedException {
        ForkJoinPool.managedBlock(new ForkJoinPool.ManagedBlocker() {

            @Override
            public boolean block() throws InterruptedException {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }

            @Override
            public boolean isReleasable() {
                return future.isDone();
            }
        });
    }
}

class CompletablePromiseContext {

    private static final ScheduledExecutorService SERVICE = Executors
            .newSingleThreadScheduledExecutor();

    public static void schedule(Runnable r) {
        SERVICE.schedule(r, 1, TimeUnit.MILLISECONDS);
    }
}

//适配器
class CompletablePromise<V> extends CompletableFuture<V> {

    private Future<V> future;

    public CompletablePromise(Future<V> future) {
        this.future = future;
        CompletablePromiseContext.schedule(this::tryToComplete);
    }

    private void tryToComplete() {
        if (future.isDone()) {
            try {
                complete(future.get());
            } catch (InterruptedException e) {
                completeExceptionally(e);
            } catch (ExecutionException e) {
                completeExceptionally(e.getCause());
            }
            return;
        }

        if (future.isCancelled()) {
            cancel(true);
            return;
        }

        CompletablePromiseContext.schedule(this::tryToComplete);
    }

}

//适配器
class ListenableFutureAdapter<T> {

    private final ListenableFuture<T> listenableFuture;
    private final CompletableFuture<T> completableFuture;

    public ListenableFutureAdapter(ListenableFuture<T> listenableFuture) {
        this.listenableFuture = listenableFuture;
        this.completableFuture = new CompletableFuture<T>() {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                boolean cancelled = listenableFuture.cancel(mayInterruptIfRunning);
                super.cancel(cancelled);
                return cancelled;
            }
        };

        Futures.addCallback(this.listenableFuture, new FutureCallback<T>() {

            @Override
            public void onSuccess(T result) {
                completableFuture.complete(result);
            }

            @Override
            public void onFailure(Throwable ex) {
                completableFuture.completeExceptionally(ex);
            }
        }, MoreExecutors.directExecutor());
    }

    public CompletableFuture<T> getCompletableFuture() {
        return completableFuture;
    }

    public static final <T> CompletableFuture<T>
            toCompletable(ListenableFuture<T> listenableFuture) {
        ListenableFutureAdapter<T> listenableFutureAdapter = new ListenableFutureAdapter<>(
                listenableFuture);
        return listenableFutureAdapter.getCompletableFuture();
    }

}
