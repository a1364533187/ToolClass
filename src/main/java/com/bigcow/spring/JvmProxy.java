package com.bigcow.spring;

import java.util.Arrays;

import org.springframework.cglib.proxy.Proxy;

/**
 * Create by suzhiwu on 2020/06/13
 */
public class JvmProxy {

    /**
     * 自己实现mybatis解析Mapper, 使用@Su来代替@Select
     * @param args
     */
    public static void main(String[] args) {
        Class[] interfaces = new Class[] { OneServiceMapper.class };
        OneServiceMapper mapper = (OneServiceMapper) Proxy.newProxyInstance(
                JvmProxy.class.getClassLoader(), interfaces, (o, method, objects) -> {
                    // @com.bigcow.spring.Su(value=[select * from `one_service_admin_user` where id=#{id}])
                    Arrays.asList(method.getAnnotations()).forEach(x -> System.out.println(x));
                    Su annocation = method.getAnnotation(Su.class);
                    if (annocation != null) {
                        //拿到了Select的sql  // select * from `one_service_admin_user` where id=#{id}
                        System.out.println(Arrays.stream(annocation.value()).findFirst().get());
                    }
                    //selectById
                    System.out.println(method.getName()); //获取methodName 是selectById

                    return (int) objects[0] + 6;
                });

        // 9
        System.out.println(mapper.selectById(3));
    }
}
