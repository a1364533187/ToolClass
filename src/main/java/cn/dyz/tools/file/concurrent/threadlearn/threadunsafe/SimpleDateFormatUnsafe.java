package cn.dyz.tools.file.concurrent.threadlearn.threadunsafe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Create by suzhiwu on 2020/1/3
 */
public class SimpleDateFormatUnsafe {

    public static void main(String[] args) {
        // 线程安全2种方法： 1、ThreadLocal  2、自己new 对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ThreadLocal<SimpleDateFormat> sdfLocal = new ThreadLocal<SimpleDateFormat>(){
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        };

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(sdfLocal.get().parse("2019-12-23 15:17:23"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(sdfLocal.get().parse("2019-12-24 15:17:23"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
