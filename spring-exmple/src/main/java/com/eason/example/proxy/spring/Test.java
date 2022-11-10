package com.eason.example.proxy.spring;

import com.eason.example.proxy.service.ProxyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Eason
 * @date 2022/11/9
 */
public class Test {

    public static void main(String[] args) {

//        ProxyFactory factory = new ProxyFactory();
//        ProxyService target = new ProxyService();
//
//        factory.setTarget(target);
//        factory.addAdvice(new MethodBeforeAdvice() {
//            @Override
//            public void before(Method method, Object[] args, Object target) throws Throwable {
//                System.out.println("before");
//            }
//        });
//        factory.addAdvice(new AfterReturningAdvice() {
//            @Override
//            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
//                System.out.println("after");
//            }
//        });
//
//        factory.addAdvice(new TestThrowsAdvice());
//
//        factory.addAdvice(new MethodInterceptor() {
//            @Override
//            public Object invoke(MethodInvocation invocation) throws Throwable {
//                try {
//                    System.out.println("invocation 之前");
//                    Object proceed = invocation.proceed();
//                    System.out.println("invocation 之后");
//                    return proceed;
//                } catch (Exception e) {
//                    System.out.println("invocation 发生异常");
//                    throw e;
//                }
//            }
//        });


//        factory.addAdvisor(new PointcutAdvisor() {
//            @Override
//            public Pointcut getPointcut() {
//                return new StaticMethodMatcherPointcut() {
//                    @Override
//                    public boolean matches(Method method, Class<?> targetClass) {
//                        return Objects.equals(method.getName(), "hello");
//                    }
//                };
//            }
//
//            @Override
//            public Advice getAdvice() {
//                return (MethodInterceptor) invocation -> {
//                    try {
//                        System.out.println("invocation 之前");
//                        Object proceed = invocation.proceed();
//                        System.out.println("invocation 之后");
//                        return proceed;
//                    } catch (Exception e) {
//                        System.out.println("invocation 发生异常");
//                        throw e;
//                    }
//                };
//            }
//
//            @Override
//            public boolean isPerInstance() {
//                return false;
//            }
//        });
//
//        ProxyService proxy = (ProxyService) factory.getProxy();
//        proxy.hello();
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        ProxyService proxyService = (ProxyService) applicationContext.getBean("proxyService");
        proxyService.hello();
    }
//
//
//    public static class TestThrowsAdvice implements ThrowsAdvice {
//        public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
//            System.out.println("触发异常");
//        }
//    }
}
