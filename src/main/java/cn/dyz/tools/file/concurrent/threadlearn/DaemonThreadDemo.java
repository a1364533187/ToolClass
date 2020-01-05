package cn.dyz.tools.file.concurrent.threadlearn;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by suzhiwu on 2019/12/29
 */
public class DaemonThreadDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        //用户线程
////        Thread t1 = new Thread(new Runnable() {
////
////            @Override
////            public void run() {
////                for (;;);
////            }
////        });
////        t1.setDaemon(true);
////        t1.start();
////        try {
////            Thread.sleep(1000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////        System.out.println("main is over");

        ExecutorService service = new ThreadPoolExecutor(1,1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        Future<Object> future = service.submit(new Callable<Object>() {
            @Override
            public Object call() {
                System.out.println("susu");
                return "result";
            }
        });

        System.out.println(future.get());
//        service.shutdown();
    }

}
