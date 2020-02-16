package com.bigcow.com.Streams;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by suzhiwu on 2019/02/19
 */
public class StreamDemo {
    //rxjava 主要2点： 观察者模式 + 异步

    @Test
    public void test1() {
        //emit 发射
        Observable<Object> observable = Observable.create(observableEmitter -> {
            observableEmitter.onNext("susu: " + Math.random() * 100);
            observableEmitter.onComplete();
        }).cache();

        observable.subscribe(x -> System.out.println(x));
        observable.subscribe(x -> System.out.println(x));
    }

    @Test
    public void test2() {
        //emit 发射
        Observable<Object> observable = Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> observableEmitter) {
                observableEmitter.onNext("haha");
                observableEmitter.onNext(1 / 0);
                observableEmitter.onNext("papa");
                observableEmitter.onComplete();
            }
        });

        observable.subscribe(new MySubcribe());
    }

    @Test
    public void test3() {
        Observable<Object> observable = Observable.just(1).map(i -> i * 3).map(i -> i * 7);
        observable.subscribe(x -> System.out.println(x));
    }

    @Test
    public void test4() {
        //        Observable<Object> observable = Observable.just(1).map(i -> i * 3);
        //        observable.subscribe(new MySubcribe());

        Observable<Object> observable1 = Observable.fromArray(1, 3, 9).map(i -> i * 3);
        observable1.forEach(x -> System.out.println(x));
    }

    @Test
    public void test5() {
        //        Schedulers.newThread();
        //        Schedulers.io();

        Observable<Object> observable1 = Observable.fromArray(1, 3, 9).map(i -> {
            Thread.sleep(10000);
            return i * 3;
        });
        observable1.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io());
        observable1.subscribe(i -> System.out.println(i));
    }
}

class MySubcribe implements Observer {

    @Override
    public void onSubscribe(Disposable disposable) {
        System.out.println("subcrible: " + disposable);
    }

    @Override
    public void onNext(Object o) {
        System.out.println("next: " + o);
    }

    @Override
    public void onError(Throwable throwable) { //遇到 onError 流会终止
        System.out.println("throw: " + throwable);
    }

    @Override
    public void onComplete() { //遇到complete 流也会终止
        System.out.println("complete");
    }
}