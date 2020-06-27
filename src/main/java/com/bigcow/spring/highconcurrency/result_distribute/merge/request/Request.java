package com.bigcow.spring.highconcurrency.result_distribute.merge.request;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 请求包装类
 */
public class Request<T> {

    //请求参数： 电影id。
    private String movieCode;

    // 多线程的future接收返回值。
    //每一个请求对象中都有一个future接收请求。
    private CompletableFuture<Map<String, T>> future;

    public Request() {
    }

    public Request(String movieCode) {
        this.movieCode = movieCode;
    }

    public String getMovieCode() {
        return movieCode;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }

    public CompletableFuture<Map<String, T>> getFuture() {
        return future;
    }

    public void setFuture(CompletableFuture<Map<String, T>> future) {
        this.future = future;
    }
}
