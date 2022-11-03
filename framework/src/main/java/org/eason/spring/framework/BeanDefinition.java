package org.eason.spring.framework;

/**
 * @author Eason
 * @date 2022/7/20 16:02
 */
public class BeanDefinition {
    private Class<?> beanClass;

    private String scope = ScopeUtils.SCOPE_SINGLETON;

    private boolean lazy;

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }
}
