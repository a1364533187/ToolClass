package cn.dyz.tools.file.ProducerConsumerProblem.monitor;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.Monitor;

/**
 * Create by suzhiwu on 2019/8/12
 */
public class MonitorInGuava {

    public static void main(String[] args) {

    }

    class MonitorGuard {
        private final static int MAX = 10;
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final Monitor monitor = new Monitor();
        AtomicInteger count = new AtomicInteger();
        public void put() {
            try {
                monitor.enterWhen(monitor.newGuard(() -> queue.size() < MAX));
                queue.addLast(count.get());
                System.out.println("comsumer: " + count.get());
                count.getAndIncrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                monitor.leave();
            }
        }

        public void take() {
            try {
                monitor.enterWhen(monitor.newGuard(() -> !queue.isEmpty()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                monitor.leave();
            }
        }


    }
}
