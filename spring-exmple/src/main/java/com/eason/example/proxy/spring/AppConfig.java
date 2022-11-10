package com.eason.example.proxy.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Eason
 * @date 2022/11/9
 */
@ComponentScan("com.eason.example.proxy.service")
public class AppConfig {

    @Bean
    public MethodInterceptor methodInterceptor() {
        return new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("invocation 之前");
                Object proceed = invocation.proceed();
                System.out.println("invocation 之后");
                return proceed;
            }
        };
    }


//    @Bean
//    private BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
//        BeanNameAutoProxyCreator proxyCreator = new BeanNameAutoProxyCreator();
//
//        proxyCreator.setBeanNames("proxy*");
//
//        proxyCreator.setInterceptorNames("methodInterceptor");
//        return proxyCreator;
//    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(MethodInterceptor methodInterceptor) {
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();

        nameMatchMethodPointcut.addMethodName("hello");

        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
        defaultPointcutAdvisor.setPointcut(nameMatchMethodPointcut);
        defaultPointcutAdvisor.setAdvice(methodInterceptor);
        return defaultPointcutAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }
}

