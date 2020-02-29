package com.bigcow.com.thread;

public class MyThreadTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(new OrderTask(1));
        Thread t2 = new Thread(new OrderTask(-1));
        t1.start();
        t2.start();
    }

    static class Order {
        public static int moneyAmount = 100; //定义一个共享的资源,需要是引用类型的object
    }

    static class OrderTask implements Runnable {
        private int pay;

        OrderTask(int pay) {
            this.pay = pay;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                synchronized (Order.class) { //加锁之后，无论如何都是100了， 锁最好最小的粒度
                    Order.moneyAmount += pay;
                }
            }
            System.out.println(Order.moneyAmount); //最后的 moneyAmount == 100
        }
    }
}

