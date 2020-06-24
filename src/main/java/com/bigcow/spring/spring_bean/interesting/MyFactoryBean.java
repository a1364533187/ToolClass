package com.bigcow.spring.spring_bean.interesting;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

import org.springframework.beans.factory.FactoryBean;

import com.bigcow.spring.Su;

//@Component
public class MyFactoryBean implements FactoryBean {

    //这里传入的是写死的类
    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(MyFactoryBean.class.getClassLoader(),
                new Class[] { HelloService.class }, new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        if (method.getAnnotation(Su.class) != null) {
                            if (method.getName().equals("sayHi")) {
                                return method.getAnnotation(Su.class).value()[0];
                            } else if (method.getName().equals("sayName")) {
                                return new ArrayList<String>() {

                                    {
                                        add("A");
                                        add("B");
                                        add("C");
                                        add(method.getAnnotation(Su.class).value()[0]);
                                    }
                                };
                            } else {
                                return null;
                            }

                        } else {
                            return null;
                        }
                    }
                });
    }

    @Override
    public Class<?> getObjectType() {
        return HelloService.class;
    }
}
