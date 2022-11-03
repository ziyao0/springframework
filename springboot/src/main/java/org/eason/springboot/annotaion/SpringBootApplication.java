package org.eason.springboot.annotaion;

import org.eason.springboot.SpringBootDeferredImportSelector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Eason
 * @date 2022/11/3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan
@Import(SpringBootDeferredImportSelector.class)
public @interface SpringBootApplication {
}
