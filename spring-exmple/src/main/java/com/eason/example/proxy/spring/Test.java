package com.eason.example.proxy.spring;

import com.eason.example.proxy.service.ProxyService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author Eason
 * @date 2022/11/9
 */
public class Test {

    public static void main(String[] args) {

        ProxyFactory factory = new ProxyFactory();
        ProxyService target = new ProxyService();

        factory.setTarget(target);
        factory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("before");
            }
        });
        factory.addAdvice(new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println("after");
            }
        });

        factory.addAdvice(new TestThrowsAdvice());

        factory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                try {
                    System.out.println("invocation 之前");
                    Object proceed = invocation.proceed();
                    System.out.println("invocation 之后");
                    return proceed;
                } catch (Exception e) {
                    System.out.println("invocation 发生异常");
                    throw e;
                }
            }
        });


        ProxyService proxy = (ProxyService) factory.getProxy();
        proxy.hello();
    }


    public static class TestThrowsAdvice implements ThrowsAdvice {
        public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
            System.out.println("触发异常");
        }
    }
}
