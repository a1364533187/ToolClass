package cn.dyz.tools.file.threadlocal;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

/**
 * 看下thread local 内存泄漏问题
 */
public class TnreadLocalDemo {

    public static void main(String[] args) {
        final int count = 100000;
        ThreadLocal<String> local = new ThreadLocal<>();
        for (int i = 0; i < count; i++) {
            final int cur = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    local.set("cur value:" + cur);
                    System.out.println(local.get());
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        System.out.println("main is over");


        FastThreadLocal<String> fastThreadLocal = new FastThreadLocal<>();
        FastThreadLocalThread thread = new FastThreadLocalThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        fastThreadLocal.set("suzhiwu");
    }
}
