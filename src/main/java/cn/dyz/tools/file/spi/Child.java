package cn.dyz.tools.file.spi;

public class Child {

    private static Child child;
    static {
        child = new Child();
    }

    public void eat() {
        System.out.println("hahaha");
    }

    public static synchronized Child getChild() {
        System.out.println("get child");
        return child;
    }
}
