package cn.dyz.tools.file.reference;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

/**
 * create by suzhiwu on 2019/02/09
 */
public class AtomicReferenceDemo {

    @Test
    public void test1() {
        String p1 = "suzhiwu";
        AtomicReference<String> ar = new AtomicReference<>(p1);
        System.out.println(ar.compareAndSet(p1, "yangjie")); // true
        System.out.println(ar.get());
        System.out.println(ar.compareAndSet(p1, "nn")); //false
        System.out.println(ar.get());
        ar.set(p1);
        System.out.println(ar.compareAndSet(p1, "ww")); //false
        System.out.println(ar.get());

    }
}
