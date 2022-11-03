package org.eason.springboot.core;

import org.eason.springboot.annotaion.ConditionalOnClass;
import org.eason.springboot.annotaion.ConditionalOnMissingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author Eason
 * @date 2022/11/3
 */
public class EasonConditional implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ClassLoader classLoader = context.getClassLoader();
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        if (metadata.isAnnotated(ConditionalOnClass.class.getName())) {
            Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnClass.class.getName());
            if (CollectionUtils.isEmpty(attributes)) {
                return true;
            }
            if (ObjectUtils.isEmpty(classLoader)) {
                return false;
            }
            final Class<?>[] value = (Class<?>[]) attributes.get("value");
            for (Class<?> clazz : value) {
                try {
                    classLoader.loadClass(clazz.getName());
                } catch (ClassNotFoundException e) {
                    return false;
                }
            }
            return true;
        }

        if (metadata.isAnnotated(ConditionalOnMissingBean.class.getName())) {
            final Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ConditionalOnMissingBean.class.getName());
            if (CollectionUtils.isEmpty(annotationAttributes)) {
                return true;
            }
            final Class<?>[] value = (Class<?>[]) annotationAttributes.get("value");
            for (Class<?> clazz : value) {
                Object bean = null;
                if (beanFactory != null) {
                    bean = beanFactory.getBean(clazz);
                }
                if (!ObjectUtils.isEmpty(bean)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
