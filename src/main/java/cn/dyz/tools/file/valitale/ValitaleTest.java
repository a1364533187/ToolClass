package cn.dyz.tools.file.valitale;

/**
 * Create by suzhiwu on 2019/7/14
 */
public class ValitaleTest {
    public static void main(String[] args) {
        final MyThread myThread = new MyThread();
        new Thread(new Runnable() {
            public void run() {
                myThread.run();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    myThread.shutdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class MyThread {
    private volatile boolean quit = false;
    public void run() {
        while (!quit) {

        }
        System.out.println("quit: Done" );
    }

    public void shutdown() throws InterruptedException {
        Thread.sleep(10);
        quit = true;
    }
}
