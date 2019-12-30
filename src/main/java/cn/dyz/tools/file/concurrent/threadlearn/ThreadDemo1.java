package cn.dyz.tools.file.concurrent.threadlearn;

/**
 * Create by suzhiwu on 2019/12/24
 */
public class ThreadDemo1 {

    static Object obj = new Object();
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    try {
                        System.out.println("start");
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("end");
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
