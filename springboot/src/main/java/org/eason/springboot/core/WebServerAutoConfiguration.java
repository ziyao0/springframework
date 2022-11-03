package org.eason.springboot.core;

import org.apache.catalina.startup.Tomcat;
import org.eason.springboot.annotaion.ConditionalOnClass;
import org.eason.springboot.annotaion.ConditionalOnMissingBean;
import org.eason.springboot.web.TomcatWebServer;
import org.eason.springboot.web.WebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Eason
 * @date 2022/11/3
 */
@Configuration
public class WebServerAutoConfiguration implements AutoConfiguration {


    @Bean
    @ConditionalOnClass(Tomcat.class)
    @ConditionalOnMissingBean(WebServer.class)
    public WebServer tomcatWebServer() {
        return new TomcatWebServer();
    }
}
