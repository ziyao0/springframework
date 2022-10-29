package org.kissing.spring.framework;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.kissing.spring.core.BeanPostProcessor;
import org.kissing.spring.core.InitializingBean;

import java.beans.Introspector;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangziyao
 * @date 2022/7/20 15:33
 */
public class AnnotationConfigApplicationContext implements ApplicationContext {


    private Class<?> configClass;

    private static final Map<String, Object> singletonBeans = new ConcurrentHashMap<>(256);

    private static final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private static final List<BeanPostProcessor> beanPostProcessorList = new LinkedList<>();


    public AnnotationConfigApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        //扫描
        scan(configClass);
        register();
    }

    private void scan(Class<?> configClass) {
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            final ComponentScan componentScanAnnotation = configClass.getAnnotation(ComponentScan.class);
            String scanPath = componentScanAnnotation.value();
            scanPath = scanPath.replace(".", "/");
            final ClassLoader classLoader = AnnotationConfigApplicationContext.class.getClassLoader();
            final URL resource = classLoader.getResource(scanPath);
            Collection<File> files = FileUtils.listFiles(FileUtils.getFile(resource.getFile()), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
            for (File file : files) {
                String absolutePath = file.getAbsolutePath();
                absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                final String replace = absolutePath.replace("/", ".");
                try {
                    final Class<?> beanClass = classLoader.loadClass(replace);
                    if (beanClass.isAnnotationPresent(Component.class)) {

                        if (BeanPostProcessor.class.isAssignableFrom(beanClass)) {
                            BeanPostProcessor beanPostProcessor = (BeanPostProcessor) beanClass.newInstance();
                            beanPostProcessorList.add(beanPostProcessor);
                        }

                        final Component componentAnnotation = beanClass.getAnnotation(Component.class);
                        String beanName = componentAnnotation.name();
                        if ("".equals(beanName)) {
                            beanName = Introspector.decapitalize(beanClass.getSimpleName());
                        }
                        final BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setBeanClass(beanClass);
                        //设置是否懒加载
                        if (beanClass.isAnnotationPresent(Lazy.class)) {
                            beanDefinition.setLazy(true);
                        }
                        // 设置scope
                        if (beanClass.isAnnotationPresent(Scope.class)) {
                            Scope scopeAnnotation = beanClass.getAnnotation(Scope.class);
                            beanDefinition.setScope(scopeAnnotation.value());
                        } else {
                            beanDefinition.setScope(ScopeUtils.SCOPE_SINGLETON);
                        }
                        beanDefinitionMap.put(beanName, beanDefinition);
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public Object getBean(String beanName) {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new NullPointerException("没有找到bean，beanName:" + beanName);
        }
        final BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition.getScope().equals(ScopeUtils.SCOPE_SINGLETON)) {
            Object singletonBean = singletonBeans.get(beanName);
            if (singletonBean == null) {
                singletonBean = createBean(beanName, beanDefinition);
                if (!(singletonBean instanceof BeanPostProcessor)) {
                    singletonBeans.put(beanName, singletonBean);
                }
            }
            return singletonBean;
        } else {
            return createBean(beanName, beanDefinition);
        }
    }

    @Override
    public void register() {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            final String beanName = entry.getKey();
            final BeanDefinition beanDefinition = entry.getValue();
            if (beanDefinition.getScope().equals(ScopeUtils.SCOPE_SINGLETON)) {
                final Object singletonBean = createBean(beanName, beanDefinition);
                if (!(singletonBean instanceof BeanPostProcessor)) {
                    singletonBeans.put(beanName, singletonBean);
                }
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        final Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            final Object bean = beanClass.newInstance();

            // 执行初始化
            if (bean instanceof InitializingBean) {
                ((InitializingBean) bean).afterPropertiesSet();
            }
            return bean;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
