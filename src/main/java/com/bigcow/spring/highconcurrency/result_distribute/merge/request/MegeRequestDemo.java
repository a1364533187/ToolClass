package com.bigcow.spring.highconcurrency.result_distribute.merge.request;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class MegeRequestDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        QueryMovieService queryMovieService = new QueryMovieService();
        queryMovieService.init(); //初始化调度器

        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    queryMovieService.queryMovie("code1");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    Map<String, Object> res2 = queryMovieService.queryMovie("code2");
                    System.out.println("res2:" + res2);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    Map<String, Object> res3 = queryMovieService.queryMovie("code3");
                    System.out.println("res3--->" + res3);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        new CountDownLatch(1);

    }
}
