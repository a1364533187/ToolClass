package cn.dyz.tools.file.concurrent.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Create by suzhiwu on 2020/11/12
 */
public class LinkedTranferQueueDemo {

    public static void main(String[] args) {
        char[] a1 = "123456789".toCharArray();
        char[] a2 = "abcdefghm".toCharArray();

//        LinkedBlockingQueue<Character> queue = new LinkedBlockingQueue<>();
        //
        LinkedTransferQueue<Character> queue = new LinkedTransferQueue<>();
        new Thread(()-> {
            try {
                for (int i = 0; i < a1.length; i++) {
                    char c = a1[i];
                    System.out.println(queue.take());
                    queue.put(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()-> {
            try {
                for (int i = 0; i < a2.length; i++) {
                    char c = a2[i];
                    queue.put(c);
                    System.out.println("--->" + queue.take());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
