package cn.dyz.tools.file.promise;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.riversun.promise.Promise;


/**
 * Create by suzhiwu on 2020/03/28
 */
public class PromiseTest {

    @Test
    public void test1() throws InterruptedException {
        Promise p = Promise.resolve("foo")
                .then(new Promise((action, data) -> {
                    new Thread(() -> {
                        if (data == "foo") {
                            String newData = data + "bar";
                            action.resolve(newData);
                        } else {
                            return;
                        }
                    }).start();
                }))
                .then(new Promise((action, data) -> {
                    System.out.println(data);
                    action.resolve();
                })).start();
        System.out.println("Promise in Java");
        new CountDownLatch(1).await();
    }
}
