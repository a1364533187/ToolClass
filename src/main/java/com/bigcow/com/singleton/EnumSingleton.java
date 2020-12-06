package com.bigcow.com.singleton;

public class EnumSingleton {

    //静态内部类单例
    private static class LazyHolder {

        private static final EnumSingleton INSTANCE = new EnumSingleton();
    }

    private EnumSingleton() {
    }

    public static EnumSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }
}
