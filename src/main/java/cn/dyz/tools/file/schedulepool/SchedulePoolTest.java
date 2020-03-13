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
        service.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("job1" + "---" + System.currentTimeMillis() / 1000);
            }
        }, 5, 5, TimeUnit.SECONDS);

        service.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("job2" + "---" + System.currentTimeMillis() / 1000);
            }
        }, 3, 3, TimeUnit.SECONDS);

    }
}
