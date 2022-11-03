package org.eason.mybatis;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Eason
 * @date 2022/11/3
 */
public class MapperDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public MapperDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        return beanDefinitionHolders.stream().peek(bh -> {
            BeanDefinition beanDefinition = bh.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClassName(MapperFactoryBean.class.getName());
        }).collect(Collectors.toSet());
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // 根据元数据信息判断是否为接口，如果是接口则返回true
        return beanDefinition.getMetadata().isInterface();
    }
}
