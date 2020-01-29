package cn.dyz.tools.file.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Create by suzhiwu on 2019/02/09
 */
public class WeakReferenceDemo {

    @Test
    public void testWeekReference() {
        WeakReference<Object> wo = new WeakReference<Object>(new HashMap<>());

        if (wo.get() != null) {
            System.out.println(wo.get());
            System.out.println("not null before gc");
        }

        System.gc();

        if (wo.get() != null) {
            System.out.println("not null after gc");
        } else {
            System.out.println("null after gc");
        }
    }

    @Test
    public void testWeekReference1() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        WeakReference<Map<String, String>> wo = new WeakReference<>(map);
        int i = 0;
        while (true) {
            i++;
            if (wo.get() != null) {
                System.out.println("当前的数据 " + i + " " + wo.get());
            } else {
                System.out.println("gc");
                break;
            }
        }
//        System.out.println(wo.get().get("1"));
    }

    @Test
    public void testPhantomReference() {
        ReferenceQueue<Object> q = new ReferenceQueue<>();
        PhantomReference<Object> po = new PhantomReference<>(new HashMap<>(), q);
        //虚引用 get 始终是null
        System.out.println(po.get());
    }


}
