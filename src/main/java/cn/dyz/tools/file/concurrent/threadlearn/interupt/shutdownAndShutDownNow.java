package cn.dyz.tools.file.concurrent.threadlearn.interupt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by suzhiwu on 2019/02/02
 */
public class shutdownAndShutDownNow {

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        for(int i = 1; i <= 100 ; i++){
            workQueue.add(new Task(String.valueOf(i)));
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, workQueue);
        executor.execute(new Task("0"));
        executor.shutdown();
//        executor.shutdownNow();
        System.out.println("workQueue size = " + workQueue.size() + " after shutdown");
    }

    static class Task implements Runnable {
        String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("task " + name + " is running");
            System.out.println("task " + name + " is over");
        }
    }
}
