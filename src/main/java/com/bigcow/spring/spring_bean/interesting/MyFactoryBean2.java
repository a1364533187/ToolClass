package com.bigcow.spring.spring_bean.interesting;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

import com.bigcow.spring.Su;

//无须注解，通过MyBeanDefinitionRegistrar 引入
public class MyFactoryBean2 implements FactoryBean {

    private Class interfaceMapper;

    public MyFactoryBean2(Class clazz) {
        this.interfaceMapper = clazz;
    }

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(MyFactoryBean2.class.getClassLoader(),
                new Class[] { interfaceMapper }, new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        if (method.getAnnotation(Su.class) != null) {
                            System.out.println("--->" + method.getAnnotation(Su.class).value()[0]);
                            return method.getAnnotation(Su.class).value()[0] + "---"
                                    + method.getAnnotatedReturnType();
                        } else {
                            return null;
                        }
                    }
                });
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceMapper;
    }
}
