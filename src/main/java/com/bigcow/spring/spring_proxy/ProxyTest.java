package com.bigcow.spring.spring_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * Create by suzhiwu on 2020/06/20
 */
public class ProxyTest {
    //代理分为jvm proxy 的静态代理(代理接口)， 以及cglib 的动态代理(代理类)

    //首先测试静态代理, 只能代理接口(实现类的接口)
    @Test
    public void testJvmProxy() {

        AnimalAction animalAction = new AnimalAction();
        PeopleAction peopleAction = (PeopleAction) Proxy.newProxyInstance(
                ProxyTest.class.getClassLoader(), new Class[] { PeopleAction.class },
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        System.out.println("susu");
                        //可以invok 一个接口的实现类
                        method.invoke(animalAction, args);
                        System.out.println("zhizhi");
                        return null;
                    }
                });
        System.out.println("---->" + PeopleAction.class.toString());
        System.out.println("---->" + peopleAction.getClass().getName());
        System.out.println(this.getClass().toString());
        peopleAction.eat();
    }

    @Test
    public void testJvmProxy1() {
        AnimalAction animalAction = new AnimalAction(); //接口的实现类
        PeopleAction peopleAction = (PeopleAction) Proxy.newProxyInstance(
                ProxyTest.class.getClassLoader(), new Class[] { PeopleAction.class },
                new WorkHandler(animalAction));
        System.out.println("---->" + peopleAction.getClass().getName() + " isProxyClass: "
                + Proxy.isProxyClass(peopleAction.getClass())); //代理类
        peopleAction.eat();

        //animal eat, 这里不是代理类， 就没有代理的效果
        System.out.println("------>");
        System.out.println(animalAction.getClass().toString() + " isProxyClass: "
                + Proxy.isProxyClass(animalAction.getClass()));
        animalAction.eat();
    }

    //传入接口的实现类， 完成实现类的代理
    public class WorkHandler implements InvocationHandler {

        //代理类中的真实对象
        private Object obj;

        public WorkHandler() {
            // TODO Auto-generated constructor stub
        }

        //构造函数，给我们的真实对象赋值
        public WorkHandler(Object obj) {
            this.obj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //在真实的对象执行之前我们可以添加自己的操作
            System.out.println("before invoke。。。");
            Object invoke = method.invoke(obj, args);
            //在真实的对象执行之后我们可以添加自己的操作
            System.out.println("after invoke。。。");
            return invoke;
        }
    }

    //cglib 动态代理， 代理类
    @Test
    public void testCglibProxy() {
        TeacherAction teacherAction = new TeacherAction();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TeacherAction.class);
        enhancer.setCallback(new CglibWorkHandler(teacherAction));
        TeacherAction actionProxy = (TeacherAction) enhancer.create();

        System.out.println("----> " + actionProxy.getClass().toString() + " isProxyClass: ");
        actionProxy.eat();
    }

    @Test
    public void testCglibProxy1() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TeacherAction.class);
        enhancer.setCallback(new CglibWorkInterceptor());
        TeacherAction actionProxy = (TeacherAction) enhancer.create();

        System.out.println("----> " + actionProxy.getClass().toString() + " isProxyClass: ");
        actionProxy.eat();
    }

    public class CglibWorkHandler implements org.springframework.cglib.proxy.InvocationHandler {

        private Object target;

        public CglibWorkHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] args)
                throws InvocationTargetException, IllegalAccessException {
            System.out.println("cglib before invoke");
            Object invoke = method.invoke(target, args);
            System.out.println("cglib after invoke");
            return invoke;
        }
    }

    public class CglibWorkInterceptor implements MethodInterceptor {

//        private Object target;
//
//        public CglibWorkInterceptor(Object target) {
//            this.target = target;
//        }

        @Override
        public Object intercept(Object object, Method method, Object[] args,
                MethodProxy methodProxy) throws Throwable {
            System.out.println("before invoke");
            Object invoke = methodProxy.invokeSuper(object, args);
            System.out.println("after invoke");
            return invoke;
        }
    }
}
