package org.eason.springboot;

import org.eason.springboot.web.WebServer;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Map;
import java.util.Optional;

/**
 * @author Eason
 * @date 2022/11/3
 */
public class SpringApplication {

    public static void run(Class<?> configureClass) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(configureClass);
        context.refresh();
        // 启动web容器
        getInstance(context).start(8888);
    }

    private static WebServer getInstance(AnnotationConfigWebApplicationContext context) {
        Map<String, WebServer> webServers = context.getBeansOfType(WebServer.class);
        if (CollectionUtils.isEmpty(webServers)) {
            throw new NullPointerException("No matching web server found of type.");
        }
        if (webServers.size() > 1) {
            throw new IllegalStateException("Multiple matching beans found of type.");
        }
        final Optional<WebServer> webServer = webServers.values().stream().findFirst();
        if (webServer.isPresent()) {
            return webServer.get();
        }
        throw new NullPointerException("No matching web server found of type.");
    }
}
