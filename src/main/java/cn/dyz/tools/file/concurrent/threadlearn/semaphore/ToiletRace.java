package cn.dyz.tools.file.concurrent.threadlearn.semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Create by suzhiwu on 2019/02/09
 */
public class ToiletRace {

    public static void main(String[] args) {
        final int peopleNum = 30;
        ExecutorService service = Executors.newFixedThreadPool(peopleNum);  //要上toilet 的人有30个
        Semaphore pits = new Semaphore(10); // 10个坑位
        for (int i = 0; i < peopleNum; i++) {
            final int cur = i;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        pits.acquire();
                        System.out.println(cur + " use toilet start...");
                        Random rd = new Random(10);
                        Thread.sleep(rd.nextInt(1000));
                        System.out.println(cur + " use toilet finish");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pits.release();
                }
            });
        }

        service.shutdown();
    }

}
