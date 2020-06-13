package cn.dyz.tools.file.spi;

/**
 * Create by suzhiwu on 2020/06/13
 */
public class SpiTest {

    public static void main(String[] args)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("cn.dyz.tools.file.spi.Child");//Class.forName(xxx.xx.xx)的作用是要求JVM查找并加载指定的类，也就是说JVM会执行该类的静态代码段。
//        Child c1 = Child.getChild();
//        c1.eat();
//        Child c2 = Child.getChild();
//        System.out.println(c1 == c2);
    }

}
