package cn.dyz.tools.file.concurrent.unsafe;

import java.lang.reflect.Constructor;

import sun.misc.Unsafe;

/**
 * Create by suzhiwu on 2019/12/31
 */
public class UnsafeTest {

    public static void main(String[] args) {
//        try {
//            Constructor<Unsafe> con = (Constructor<Unsafe>) Class.forName("sun.misc.Unsafe").getDeclaredConstructor();
//            con.setAccessible(true);
//            User user = new User();
//            Unsafe UNSAFE = con.newInstance(null);
//            Field filed = user.getClass().getDeclaredField("age");
//            long s1=System.currentTimeMillis();
//            for(int i=0;i<1000000;i++){
//                user.setAge(i);
//            }
//            System.out.println(System.currentTimeMillis()-s1);
//            long ageOffset = UNSAFE.objectFieldOffset(filed);
//            long s2=System.currentTimeMillis();
//            for(int i=0;i<1000000;i++){
//                UNSAFE.putInt(user, ageOffset, i);
//            }
//            System.out.println(System.currentTimeMillis()-s2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            Constructor<Unsafe> con = (Constructor<Unsafe>) Class.forName("sun.misc.Unsafe").getDeclaredConstructor();
            con.setAccessible(true);
            Unsafe UNSAFE = con.newInstance(null);
            User a = new User();
            a.setAge(10);
            long valueOffset = UNSAFE.objectFieldOffset(User.class.getDeclaredField("age"));
            /**
             * a 对象
             * valueOffset 对象的内存偏移值, 字段
             * i 期望内存中存在的值
             * i1 如果期望中的内存的值和real 的值一致，则更新当前的内存的值为 i1
             */
            UNSAFE.compareAndSwapInt(a, valueOffset, 10, 18);
            UNSAFE.putInt(a, valueOffset, 19);
            System.out.println(a.getAge());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class User {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}

