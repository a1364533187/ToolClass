package com.bigcow.com.singleton;

import org.junit.Assert;
import org.junit.Test;

public class EnumSingletonTest {

    @Test
    public void testSingleton() {
        EnumSingleton enumSingleton = EnumSingleton.getInstance();
        EnumSingleton enumSingleton1 = EnumSingleton.getInstance();
        Assert.assertEquals(enumSingleton, enumSingleton1);
    }
}