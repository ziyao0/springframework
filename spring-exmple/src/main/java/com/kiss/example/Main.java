package com.kiss.example;

import com.kiss.example.config.AppConfig;
import com.kiss.example.service.UserService;
import org.kissing.spring.framework.AnnotationConfigApplicationContext;
import org.kissing.spring.framework.ApplicationContext;

/**
 * @author zhangziyao
 * @date 2022/7/20 15:38
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) context.getBean("userService");
        userService.test();
    }
}
