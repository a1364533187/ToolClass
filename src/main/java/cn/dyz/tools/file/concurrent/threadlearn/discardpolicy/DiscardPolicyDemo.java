package cn.dyz.tools.file.concurrent.threadlearn.discardpolicy;

import static java.lang.Thread.sleep;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by suzhiwu on 2019/12/28
 */
public class DiscardPolicyDemo {

    private final static ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L,
            TimeUnit.SECONDS, new NonRejectedArrayBlockingQueue(1, true));

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1");
            }
        });
        Future<?> future1 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2");
            }
        });
        Future<?> future2 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t3");
            }
        });
        executorService.shutdown();
    }
}

class NonRejectedArrayBlockingQueue<E> extends ArrayBlockingQueue<E> {

    public NonRejectedArrayBlockingQueue(int capacity) {
        super(capacity);
    }

    public NonRejectedArrayBlockingQueue(int capacity, boolean fair) {
        super(capacity, fair);
    }

    public NonRejectedArrayBlockingQueue(int capacity, boolean fair, Collection<? extends E> c) {
        super(capacity, fair, c);
    }

    @Override
    public boolean offer(E e) {
        // turn offer() and add() into a blocking calls (unless interrupted)
        try {
            put(e);
            return true;
        } catch(InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }
}

