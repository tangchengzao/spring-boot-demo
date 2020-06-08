package com.tangcz.springboot.mybatis.spring.processor;

import com.tangcz.springboot.mybatis.mapper.ShardMapperProxyFactory;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:MapperProxyPostProcessor
 * Package:com.tangcz.springboot.mybatis.spring.processor
 * Description:
 *
 * @date:2020/6/6 20:03
 * @author:tangchengzao
 */
@Component
public class MapperProxyPostProcessor implements BeanPostProcessor {

    @Value("mybatis.exclude-annotation-class")
    private String excludeAnnotationClassName;

    private Class<? extends Annotation> excludeAnnotationClass;

    public void setExcludeAnnotationClass() throws ClassNotFoundException {
        this.excludeAnnotationClass = (Class<? extends Annotation>) Class.forName(excludeAnnotationClassName);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final Class<?> beanClass = bean.getClass();

        if (beanClass == DefaultSqlSessionFactory.class) {
            Configuration configuration = ((DefaultSqlSessionFactory) bean).getConfiguration();
            if (configuration != null) {
                MapperRegistry mapperRegistry = configuration.getMapperRegistry();
                try {
                    Field fdKnownMappers = mapperRegistry.getClass().getDeclaredField("knownMappers");
                    fdKnownMappers.setAccessible(true);
                    Map<Class<?>, MapperProxyFactory<?>> knownMappers = (Map<Class<?>, MapperProxyFactory<?>>) fdKnownMappers.get(mapperRegistry);
                    Map<Class<?>, MapperProxyFactory<?>> temp = new HashMap<>();
                    for (Map.Entry<Class<?>, MapperProxyFactory<?>> entry : knownMappers.entrySet()) {
                        MapperProxyFactory<?> val = entry.getValue();

                        boolean ignore = false;
                        if (excludeAnnotationClass != null) {
                            ignore = val.getMapperInterface().isAnnotationPresent(excludeAnnotationClass);
                        }
                        if (!ignore) {
                            val = new ShardMapperProxyFactory<>(val.getMapperInterface());
                            val.getMethodCache().putAll(entry.getValue().getMethodCache());
                        }

                        temp.put(entry.getKey(), val);
                    }
                    for (Map.Entry<Class<?>, MapperProxyFactory<?>> entry : temp.entrySet()) {
                        knownMappers.put(entry.getKey(), entry.getValue());
                    }
                } catch (Exception ignore) {
                }
            }
        }

        return bean;
    }
}
