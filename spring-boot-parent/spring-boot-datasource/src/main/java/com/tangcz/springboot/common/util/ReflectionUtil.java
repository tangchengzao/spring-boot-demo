package com.tangcz.springboot.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

/**
 * ClassName:ReflectUtil
 * Package:com.tangcz.springboot.common.util
 * Description:
 *
 * @date:2020/6/6 14:38
 * @author:tangchengzao
 */
public class ReflectionUtil extends ReflectionUtils {

    private static final Set<Class<?>> SIMPLE_TYPES = CollectionUtil
            .asHashSet(
                    String.class, Byte.class, byte.class, Short.class, short.class,
                    Integer.class, int.class, Long.class, long.class, Double.class, double.class,
                    Float.class, float.class, Character.class, char.class,
                    Date.class, BigInteger.class, BigDecimal.class
            );

    public static boolean isSimpleType(Class<?> cls) {
        return SIMPLE_TYPES.contains(cls);
    }

    public static Class<?> getGenericType(Object obj, int index) {
        return getGenericTypeFromClass(obj.getClass(), index, null);
    }

    public static Class<?> getGenericType(Object obj, int index, Class<?> clsTop) {
        return getGenericTypeFromClass(obj.getClass(), index, clsTop);
    }

    public static Class<?> getGenericTypeFromClass(Class<?> cls, int index, Class<?> clsTop) {
        TypeVariable<?>[] typeParams = cls.getTypeParameters();
        if (CollectionUtil.isNotEmpty(typeParams)) {
            TypeVariable<?> typeParam = typeParams[index];
            Type[] boundTypes = typeParam.getBounds();
            if (boundTypes.length > 0 && boundTypes[0] instanceof Class) {
                return (Class<?>) boundTypes[0];
            }
        }

        Type genType = cls.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            if (clsTop == null) {
                return Object.class;
            }
            return getGenericTypeFromClass(cls.getSuperclass(), index, clsTop);
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index out of bounds");
        }

        if (params[index] instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) params[index]).getRawType();
        } else if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    public static Object getValue(Object target, String fieldName) throws SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByName(target, fieldName);
        return field.get(target);
    }

    public static void setValue(Object target, String fieldName, Object value) throws IllegalAccessException {
        Field field = getFieldByName(target, fieldName);
        field.set(target, value);
    }

    private static Field getFieldByName(Object target, String fieldName) {
        if (target == null) {
            throw new NullPointerException("target can not be null.");
        } else if (StringUtils.isBlank(fieldName)) {
            throw new IllegalArgumentException("fieldName can not be null or empty.");
        }

        Class<?> clazz = target.getClass();
        Field field = findField(clazz, fieldName);
        assert field != null;
        field.setAccessible(true);

        return field;
    }

    public static Object getStaticValue(Class<?> clazz, String fieldName) throws IllegalAccessException {
        Field field = getStaticField(clazz, fieldName);
        return field.get(null);
    }

    public static void setStaticValue(Class<?> clazz, String fieldName, Object value) throws IllegalAccessException {
        Field field = getStaticField(clazz, fieldName);
        field.set(null, value);
    }

    private static Field getStaticField(Class<?> clazz, String fieldName) {
        if (clazz == null) {
            throw new NullPointerException("clazz can not be null.");
        } else if (StringUtils.isBlank(fieldName)) {
            throw new IllegalArgumentException("fieldName can not be null or empty.");
        }

        Field field = findField(clazz, fieldName);
        assert field != null;
        field.setAccessible(true);

        return field;
    }

}
