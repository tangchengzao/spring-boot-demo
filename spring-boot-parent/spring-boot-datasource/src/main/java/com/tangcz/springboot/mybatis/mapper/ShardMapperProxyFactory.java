package com.tangcz.springboot.mybatis.mapper;

import com.tangcz.springboot.common.context.RequestContext;
import com.tangcz.springboot.common.util.ConcurrentMapFactory;
import com.tangcz.springboot.mybatis.annotation.ShardOnConfigDB;
import com.tangcz.springboot.mybatis.annotation.ShardStrategy;
import com.tangcz.springboot.mybatis.util.DBContextHolder;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentMap;

public class ShardMapperProxyFactory<T> extends MapperProxyFactory<T> {
    public ShardMapperProxyFactory(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @SuppressWarnings("unchecked")
    public T newInstance(SqlSession sqlSession) {
        T instance = super.newInstance(sqlSession);

        return (T) Proxy.newProxyInstance(getMapperInterface().getClassLoader(), new Class[] { getMapperInterface() },
                new ShardInvocationHandler(instance));
    }

    public static class ShardInvocationHandler implements InvocationHandler {
        private static final ConcurrentMap<Class<?>, Boolean> SHARD_ON_CONFIG_CACHE = ConcurrentMapFactory.getLRUConcurrentMap(10000);
        private Object instance;

        public ShardInvocationHandler(Object instance) {
            this.instance = instance;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                // 设置分库DataSource
                if (RequestContext.getDataSourceId() > 0) {
                    if (!isMarkedAsShardOnConfig(method.getDeclaringClass())) {
                        DBContextHolder.setDataSourceId(String.valueOf(RequestContext.getDataSourceId()));
                    }
                }

                // 分表策略
                // TODO:暂不处理

                try {
                    return method.invoke(instance, args);
                } catch (Throwable t) {
                    throw ExceptionUtil.unwrapThrowable(t);
                }
            } finally {
                DBContextHolder.clear();
            }
        }

        public static boolean isMarkedAsShardOnConfig(Class<?> cls) {
            Boolean mark = SHARD_ON_CONFIG_CACHE.get(cls);
            if (mark != null) {
                return mark;
            }
            ShardOnConfigDB shardOnConfigDB = cls.getAnnotation(ShardOnConfigDB.class);
            SHARD_ON_CONFIG_CACHE.put(cls, mark = shardOnConfigDB != null);

            return mark;
        }
    }

}
