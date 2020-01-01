package cn.dyz.tools.file.concurrent.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Create by suzhiwu on 2019/12/31
 */
public class UnsafeTest {
    //static final Unsafe unsafe = Unsafe.getUnsafe();// 采用的是AppClassLoader, 而不是采用Bootstrap
    static Unsafe unsafe;
    static long stateOffset;
    private volatile long state = 10;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            System.out.println("local message: " + e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UnsafeTest unsafeTest = new UnsafeTest();
        Boolean success = unsafe.compareAndSwapInt(unsafeTest, stateOffset, 0, 2);
        System.out.println("success: " + success);
        System.out.println(stateOffset);
    }

}
