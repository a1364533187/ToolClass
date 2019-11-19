package cn.dyz.tools.file.interrupt;

/**
 * Create by suzhiwu on 2019/11/19
 */
public class Interrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread running...");
                try {
                    /*
                     * 如果线程阻塞，将不会去检查中断信号量stop变量，所 以thread.interrupt()
                     * 会使阻塞线程从阻塞的地方抛出异常，让阻塞线程从阻塞状态逃离出来，并
                     * 进行异常块进行 相应的处理
                     */
                    Thread.sleep(2998);// 线程阻塞，如果线程收到中断操作信号将抛出异常
                    for (int i = 0; i < 1000000; i++) {
                        if (i % 10000 == 0) {
                            System.out.println(i);
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted...");
                    /*
                     * 如果线程在调用 Object.wait()方法，或者该类的 join() 、sleep()方法
                     * 过程中受阻，则其中断状态将被清除
                     */
                    System.out.println(Thread.currentThread().isInterrupted());// false

                    //中不中断由自己决定，如果需要真真中断线程，则需要重新设置中断位，如果
                    //不需要，则不用调用
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Thread exiting under request...");
        });

        t.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        t.interrupt();// 等中断信号量设置后再调用
        Thread.sleep(3000);
        System.out.println("Stopping application...");
        System.out.println("最后t的中断状态：" + t.isInterrupted());
    }
}
