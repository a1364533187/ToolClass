package cn.dyz.tools.file.concurrent.threadlearn;

/**
 * Create by suzhiwu on 2019/12/29
 */
public class DaemonThreadDemo {

    public static void main(String[] args) {
        //用户线程
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (;;);
            }
        });
        t1.setDaemon(true);
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main is over");
    }

}
