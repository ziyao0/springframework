package org.kissing.spring.core;

/**
 * @author zhangziyao
 * @date 2022/7/20 16:08
 */
public interface BeanPostProcessor {


    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
