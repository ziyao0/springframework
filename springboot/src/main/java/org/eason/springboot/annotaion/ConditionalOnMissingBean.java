package org.eason.springboot.annotaion;

import org.eason.springboot.core.EasonConditional;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author Eason
 * @date 2022/7/15 10:35
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(EasonConditional.class)
public @interface ConditionalOnMissingBean {
    Class<?>[] value() default {};
}
