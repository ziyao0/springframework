package com.eason.example;

import com.eason.example.config.AppConfig;
import com.eason.example.service.UserService;
import org.eason.spring.framework.AnnotationConfigApplicationContext;
import org.eason.spring.framework.ApplicationContext;

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
