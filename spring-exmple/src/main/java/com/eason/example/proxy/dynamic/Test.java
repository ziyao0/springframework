package com.eason.example.proxy.dynamic;

import com.eason.example.proxy.service.ProxyInterFace;
import com.eason.example.proxy.service.ProxyService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Eason
 * @date 2022/11/8
 */
public class Test {

    public static void main(String[] args) {

        ProxyService target = new ProxyService();


        ProxyInterFace interFace = (ProxyInterFace) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{ProxyInterFace.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before");
                method.invoke(target, args);
                System.out.println("after");
                return null;
            }
        });
        interFace.hello();
    }
}
