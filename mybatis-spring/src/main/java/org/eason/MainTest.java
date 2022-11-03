package org.eason;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eason.mybatis.MapperScan;
import org.eason.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Eason
 * @date 2022/11/3
 */
@Configuration
@ComponentScan
@MapperScan("org.eason.mapper")
public class MainTest {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainTest.class);
        UserService userService = context.getBean(UserService.class);
        String user = userService.getUser();
        System.out.println("UserService:getUser()="+user);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        final InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");

        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        return sqlSessionFactoryBuilder.build(resourceAsStream);
    }
}
