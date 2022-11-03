package org.eason.spring.framework;

/**
 * @author zhangziyao
 * @date 2022/7/20 15:31
 */
public interface ApplicationContext {

    Object getBean(String beanName);

    void register();
}
