package com.bigcow.com.function;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by suzhiwu on 2019/02/14
 */
public class FunctionDeno {

    public static void main(String[] args) {
        List<String> countries = Arrays.asList("Germany", "Panama", "Australia");
        Iterator<String> iter = countries.iterator();
        ExecutorService service = new ThreadPoolExecutor(3, 3, 1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        while (iter.hasNext()) {
            final String str = iter.next();
            handle(new DpRunnable(str), service);
        }
        service.shutdown();
    }

    static class DpRunnable implements Runnable {

        private String str;

        DpRunnable(String str) {
            this.str = str;
        }

        @Override
        public void run() {
            System.out.println(str);
        }
    }

    private static void handle(Runnable r, ExecutorService service) {
        service.submit(r);
    }

    @FunctionalInterface
    public interface FilterPhoneFuction {

        boolean filter(String phone);

        default String getInfo() {
            return "过滤手机号函数";
        }
    }

}
