package com.bigcow.spring;

import java.util.Arrays;

import org.springframework.cglib.proxy.Proxy;

/**
 * Create by suzhiwu on 2020/06/13
 */
public class JvmProxy {

    /**
     * 自己实现mybatis解析Mapper
     * @param args
     */
    public static void main(String[] args) {
        Class[] interfaces = new Class[] { OneServiceMapper.class };
        OneServiceMapper mapper = (OneServiceMapper) Proxy.newProxyInstance(
                JvmProxy.class.getClassLoader(), interfaces, (o, method, objects) -> {
                    Arrays.asList(method.getAnnotations()).forEach(x -> System.out.println(x));
                    Select annocation = method.getAnnotation(Select.class);
                    if (annocation != null) {
                        //拿到了Select的sql
                        System.out.println(Arrays.stream(annocation.value()).findFirst().get());
                    }
                    System.out.println(method.getName()); //获取methodName 是selectById

                    return (int) objects[0] + 6;
                });

        System.out.println(mapper.selectById(3));
    }
}
