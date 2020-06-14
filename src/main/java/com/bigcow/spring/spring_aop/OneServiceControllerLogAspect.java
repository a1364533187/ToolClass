package com.bigcow.spring.spring_aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Create by suzhiwu on 2020/05/16
 */
@Aspect
@Component
public class OneServiceControllerLogAspect {

    /**
     * 定义切面，切所有的spring_aop
     */
    @Pointcut("execution(* com.bigcow.spring.spring_aop..*.*(..))")
    public void log() {
    }

    /**
     * 前置通知:记录(请求url,访问者ip,调用方法,参数)
     * @param joinPoint
     */
    @Before("log()")
    public int doBefore(JoinPoint joinPoint) {
        String classMethod =
                joinPoint.getSignature().getDeclaringTypeName() + "-"
                        + joinPoint.getSignature().getName();
        System.out.println("before:" + classMethod);
        return 5;
    }

    /**
     * 后置通知
     * 记录访问方法（类#方法）和该方法的调用时间
     */
    @After("log()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("after: " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 写AccessLog，目前是写到kafka
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("log()")
    public int writeAccessLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around start " + Arrays.toString(joinPoint.getArgs()));
        Object value = joinPoint.proceed();
        System.out.println("around end.--->" + value);
        return (int)joinPoint.getArgs()[0] + (int)joinPoint.getArgs()[1] + 2;
    }

}
