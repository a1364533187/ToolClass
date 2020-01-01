package cn.dyz.tools.file.concurrent.threadlearn.interupt;

/**
 * Create by suzhiwu on 2019/12/31
 */
public class InterruptDemo {

//    public static void main(String[] args) throws InterruptedException {
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
////                while (!Thread.currentThread().isInterrupted()) {
////                    System.out.println(Thread.currentThread() + ":" + "hello");
////                }
//            }
//        });
//
//        t1.start();
//        t1.interrupt();
//        System.out.println("main is over");
//    }

    public static void main(String[] args) {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;);
            }
        });

        threadOne.start();
        threadOne.interrupt();
        System.out.println("thread: " + threadOne.isInterrupted());

    }
}
