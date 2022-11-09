package com.eason.example.proxy.cglib;

import com.eason.example.proxy.service.ProxyService;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * @author Eason
 * @date 2022/11/8
 */
public class Test {


    public static void main(String[] args) {


        ProxyService target = new ProxyService();

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(ProxyService.class);

        enhancer.setCallbacks(new Callback[]{new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                // target
                // o Proxy objects
                System.out.println("before");
//                Object result = methodProxy.invoke(target, objects);
//                Object result = method.invoke(target, objects);
                Object result = methodProxy.invokeSuper(o, objects);
                System.out.println("after");
                return result;
            }
        }, NoOp.INSTANCE});

        // FILTER
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                if (method.getName().equals("hello")) {
                    return 0;
                }
                return 1;
            }
        });
        ProxyService proxyService = (ProxyService) enhancer.create();
        proxyService.hello();
    }
}
