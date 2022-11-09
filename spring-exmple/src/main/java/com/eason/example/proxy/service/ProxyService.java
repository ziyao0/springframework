package com.eason.example.proxy.service;

/**
 * @author Eason
 * @date 2022/11/8
 */
public class ProxyService implements ProxyInterFace {

    @Override
    public void hello() {
        System.out.println("hello world!");
        throw new NullPointerException();
    }

}
