package com.bigcow.com.thread;

import java.util.Objects;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * create by suzhiwu on 2019/02/18
 */
public class TestCompleteService {
    /**
     * complete service 让提交的runnable 或者 callback有序完成
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletionService service = new ExecutorCompletionService(Executors.newFixedThreadPool(2));
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                service.submit(() -> {
                    Thread.sleep(2000);
                    return 2;
                });
            } else {
                service.submit(() -> {
                    return 1;
                });
            }
        }
        while (true) {
            Future take = service.take();
            System.out.println(take.get());
        }
    }
}
