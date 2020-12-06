package com.bigcow.com.singleton;

public class EnumSingleton {

    private static class LazyHolder {

        private static final EnumSingleton INSTANCE = new EnumSingleton();
    }

    private EnumSingleton() {
    }

    public static EnumSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }
}
