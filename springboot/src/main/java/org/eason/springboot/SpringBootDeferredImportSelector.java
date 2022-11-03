package org.eason.springboot;

import org.eason.springboot.core.AutoConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Eason
 * @date 2022/11/3
 */
public class SpringBootDeferredImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> beanNames = new ArrayList<>();
        ServiceLoader<AutoConfiguration> configurations = ServiceLoader.load(AutoConfiguration.class);
        for (AutoConfiguration configuration : configurations) {
            beanNames.add(configuration.getClass().getName());
        }
        return StringUtils.toStringArray(beanNames);
    }
}
