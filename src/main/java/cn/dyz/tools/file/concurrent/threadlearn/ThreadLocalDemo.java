package cn.dyz.tools.file.concurrent.threadlearn;

/**
 * Create by suzhiwu
 */
public class ThreadLocalDemo {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
//    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    public static void main(String[] args) {
        threadLocal.set("hahha");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                threadLocal.set("wzj");
                System.out.println(Thread.currentThread() + ":" + threadLocal.get());
            }
        });
        t1.start();
        System.out.println(Thread.currentThread() + ":" + threadLocal.get());
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
