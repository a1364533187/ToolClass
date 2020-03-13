package cn.dyz.tools.file.schedulepool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * create by suzhiwu on 2020/03/06
 */
public class SchedulePoolTest {

    /**
     * Schedule pool 使用
     * @param args
     */
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
//        service.scheduleWithFixedDelay(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(6000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("job1" + "---" + System.currentTimeMillis() / 1000);
//            }
//        }, 5, 5, TimeUnit.SECONDS);

        //都不会并发的执行
        /**
         * scheduleWithFixedDelay
         *
         * 如果delay设置的是3秒，系统执行要5秒；中间delay3s, 下一个任务也需要等在也就是任务与任务之间的差异是8s；
         *
         * 如果delay设置的是3s，系统执行要2s；那么需要等到3S后再次执行下一次任务。
         */
        service.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("job2" + "---" + System.currentTimeMillis() / 1000);
            }
        }, 3, 3, TimeUnit.SECONDS);


        /**
         * scheduleAtFixedRate
         * period为固定周期时间，按照一定频率来重复执行任务。
         *
         * 如果period设置的是3秒，系统执行要5秒；那么等上一次任务执行完就立即执行，也就是任务与任务之间的差异是5s；
         *
         * 如果period设置的是3s，系统执行要2s；那么需要等到3S后再次执行下一次任务。
         */
        service.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("job2" + "---" + System.currentTimeMillis() / 1000);
            }
        }, 3, 3, TimeUnit.SECONDS);

    }
}
