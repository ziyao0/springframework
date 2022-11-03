package org.eason.mybatis;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author Eason
 * @date 2022/11/3
 */
public class MapperImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        if (metadata.isAnnotated(MapperScan.class.getName())) {
            Map<String, Object> attributes = metadata.getAnnotationAttributes(MapperScan.class.getName());
            String value = (String) attributes.get("value");
            MapperDefinitionScanner scanner = new MapperDefinitionScanner(registry);
            scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
            scanner.scan(value);
        }
    }
}
