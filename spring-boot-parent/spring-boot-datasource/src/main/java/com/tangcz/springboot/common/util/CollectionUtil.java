package com.tangcz.springboot.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tangcz.springboot.common.data.support.Tuple2;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName:CollectionUtil
 * Package:com.tangcz.springboot.common.util
 * Description:
 *
 * @date:2020/6/6 14:33
 * @author:tangchengzao
 */
public class CollectionUtil {

    public static final long[] LONG_EMPTY_ARRAY = new long[0];
    public static final int[]  INT_EMPTY_ARRAY  = new int[0];

    public static enum KeyFeature {
        UPPER_CASE,
        LOWER_CASE,
        TRIM,
        ;
    }

    @SuppressWarnings("unchecked")
    public static <TKey> Set<TKey> getDistinctFieldValueList(Collection<?> col, String keyField, KeyFeature... keyFeatures) {
        if (isEmpty(col)) {
            return Sets.newLinkedHashSet();
        }
        return new LinkedHashSet<>(getFieldValueList(col, keyField, keyFeatures));
    }

    @SuppressWarnings("unchecked")
    public static <TKey> Set<TKey> getDistinctFieldValueListIgnoreNullKey(Collection<?> col, String keyField, KeyFeature... keyFeatures) {
        if (isEmpty(col)) {
            return Sets.newLinkedHashSet();
        }
        return new LinkedHashSet<>(getFieldValueListIgnoreNullKey(col, keyField, keyFeatures));
    }

    @SuppressWarnings("unchecked")
    public static <TKey> List<TKey> getFieldValueList(Collection<?> col, String keyField, KeyFeature... keyFeatures) {
        if (isEmpty(col)) {
            return Lists.newArrayList();
        }

        List<TKey> list = new ArrayList<>();
        for (Object item : col) {
            if (item != null) {
                TKey key = getKeyValue(keyField, item, keyFeatures);
                list.add(key);
            }
        }
        return list;
    }

    public static <TKey> List<TKey> getFieldValueListIgnoreNullKey(Collection<?> col, String keyField, KeyFeature... keyFeatures) {
        return getFieldValueListIgnoreNullKey(col, null, keyField, keyFeatures);
    }

    public static <TKey, TItem> List<TKey> getFieldValueListIgnoreNullKey(Collection<TItem> col, Filter<TItem> filter, String keyField, KeyFeature... keyFeatures) {
        if (isEmpty(col)) {
            return Lists.newArrayList();
        }

        List<TKey> list = new ArrayList<>();
        for (TItem item : col) {
            if (item != null && (filter == null || filter.matches(item))) {
                TKey key = getKeyValue(keyField, item, keyFeatures);
                if (key != null) {
                    list.add(key);
                }
            }
        }

        return list;
    }

    public static <TKey, TItem> Map<TKey, TItem> toMapIgnoreNullKey(Collection<TItem> col, String keyField, KeyFeature... keyFeatures) {
        if (isEmpty(col)) {
            return Maps.newLinkedHashMap();
        }
        Map<TKey, TItem> map = new LinkedHashMap<>();
        for (TItem item : col) {
            if (item != null) {
                TKey key = getKeyValue(keyField, item, keyFeatures);
                if (key != null) {
                    map.put(key, item);
                }
            }
        }
        return map;
    }

    public static <TKey, TItem> Map<TKey, TItem> toMap(Collection<TItem> col, String keyField, KeyFeature... keyFeatures) {
        if (isEmpty(col)) {
            return Maps.newLinkedHashMap();
        }
        Map<TKey, TItem> map = new LinkedHashMap<>();
        for (TItem item : col) {
            if (item != null) {
                TKey key = getKeyValue(keyField, item, keyFeatures);
                map.put(key, item);
            }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private static <TKey, TItem> TKey getKeyValue(String keyField, TItem item, KeyFeature... keyFeatures) {
        if (keyField.contains("+")) {
            String [] keyArr = keyField.split("\\+");
            if (keyArr.length > 1) {
                StringBuilder builder = new StringBuilder();
                for (String k : keyArr) {
                    if (StringUtils.isBlank(k)) {
                        continue;
                    }
                    try {
                        Object key = ReflectionUtil.getValue(item, StringUtils.trim(k));
                        if (builder.length() > 0) {
                            builder.append("+");
                        }
                        builder.append(processKey(key, keyFeatures));
                    } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                        throw new RuntimeException("字段" + keyField + "获取失败", e);
                    }
                }
                return (TKey) builder.toString();
            }
        }
        TKey key;
        try {
            key = (TKey) ReflectionUtil.getValue(item, StringUtils.trim(keyField));
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("字段" + keyField + "获取失败", e);
        }
        return (TKey) processKey(key, keyFeatures);
    }

    public static <TKey extends Number, TItem> TKey sum(Collection<TItem> col, String keyField) {
        return sum(col, null, keyField);
    }

    @SuppressWarnings("unchecked")
    public static <TKey extends Number, TItem> TKey sum(Collection<TItem> col, Filter<TItem> filter, String keyField) {
        List<TKey> valueList = getFieldValueListIgnoreNullKey(col, filter, keyField);
        if (isEmpty(valueList)) {
            return (TKey) ((Number) 0);
        }
        TKey firstItem = valueList.get(0);
        Number num = firstItem;
        BigDecimal total;
        if (num instanceof BigDecimal) {
            total = (BigDecimal) num;
        } else {
            total = new BigDecimal(num.doubleValue());
        }
        for (int i = 1; i < valueList.size(); i++) {
            TKey tKey = valueList.get(i);
            if (tKey == null) {
                continue;
            }
            BigDecimal val;
            if (tKey instanceof BigDecimal) {
                total = total.add((BigDecimal) tKey);
            } else {
                total = total.add(new BigDecimal(tKey.doubleValue()));
            }
        }

        return (TKey) castType(total, firstItem.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <TKey extends Number, TItem> TKey avg(Collection<TItem> col, String keyField, int scale, int roundMode) {
        return avg(col, null, keyField, scale, roundMode);
    }

    @SuppressWarnings("unchecked")
    public static <TKey extends Number, TItem> TKey avg(Collection<TItem> col, Filter<TItem> filter, String keyField, int scale, int roundMode) {
        List<TKey> valueList = getFieldValueListIgnoreNullKey(col, filter, keyField);
        if (isEmpty(valueList)) {
            return (TKey) ((Number) 0);
        }
        TKey firstItem = valueList.get(0);
        Number num = firstItem;
        BigDecimal total = new BigDecimal(num.doubleValue());
        for (int i = 1; i < valueList.size(); i++) {
            TKey tKey = valueList.get(i);
            total = total.add(new BigDecimal(tKey.doubleValue()));
        }
        total = total.divide(new BigDecimal(col.size()), scale, roundMode);

        return (TKey) castType(total, firstItem.getClass());
    }

    @SuppressWarnings("unchecked")
    private static <TKey> TKey castType(BigDecimal total, Class<TKey> cls) {
        if (BigDecimal.class == cls) {
            return (TKey) total;
        } else if (Double.class == cls || double.class == cls) {
            return (TKey) (Number) total.doubleValue();
        } else if (Float.class == cls || float.class == cls) {
            return (TKey) (Number) total.floatValue();
        } else if (Long.class == cls || long.class == cls) {
            return (TKey) (Number) total.longValue();
        } else if (Integer.class == cls || int.class == cls) {
            return (TKey) (Number) total.intValue();
        } else if (Short.class == cls || short.class == cls) {
            return (TKey) (Number) total.shortValue();
        } else if (Byte.class == cls || byte.class == cls) {
            return (TKey) (Number) total.byteValue();
        }
        throw new RuntimeException("type " + cls + " not support");
    }

    private static Object processKey(Object key, KeyFeature... keyFeatures) {
        if (keyFeatures != null && keyFeatures.length > 0) {
            String strKey = String.valueOf(key);
            for (KeyFeature keyFeature : keyFeatures) {
                switch (keyFeature) {
                    case UPPER_CASE:
                        strKey = StringUtils.upperCase(strKey);
                        break;
                    case LOWER_CASE:
                        strKey = StringUtils.lowerCase(strKey);
                        break;
                    case TRIM:
                        strKey = StringUtils.trim(strKey);
                        break;
                    default:
                        break;
                }
            }
            return strKey;
        }
        return key;
    }

    public static <TKey, TItem> Map<TKey, List<TItem>> toListMap(Collection<TItem> col, String keyField, KeyFeature... keyFeatures) {
        if (isEmpty(col)) {
            return Maps.newLinkedHashMap();
        }
        Map<TKey, List<TItem>> map = new LinkedHashMap<>();
        for (TItem item : col) {
            TKey key = getKeyValue(keyField, item, keyFeatures);
            List<TItem> l = map.computeIfAbsent(key, k -> new ArrayList<>());
            l.add(item);
        }
        return map;
    }

    public static <TKey, TItem, TValue> Map<TKey, List<TValue>> toListMap(Collection<TItem> col, String keyField, KeyFeature[] keyFeatures,
                                                                          String valueField, KeyFeature[] valueFeatures) {
        if (isEmpty(col)) {
            return Maps.newLinkedHashMap();
        }
        Map<TKey, List<TValue>> map = new LinkedHashMap<>();
        for (TItem item : col) {
            TKey key = getKeyValue(keyField, item, keyFeatures);
            if (key == null) {
                continue;
            }
            TValue value = getKeyValue(valueField, item, keyFeatures);
            if (value == null) {
                continue;
            }

            List<TValue> l = map.computeIfAbsent(key, k -> new ArrayList<>());
            l.add(value);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static Map<?, ?> toNestedMap(Collection<?> col, String... keys) {
        Tuple2<String, KeyFeature[]>[] tupleKeys = (Tuple2<String, KeyFeature[]>[]) Array.newInstance(Tuple2.class, keys.length);
        for (int i = 0; i < keys.length; i++) {
            tupleKeys[i] = new Tuple2<>(keys[i]);
        }
        return toNestedMap(col, tupleKeys);
    }

    @SuppressWarnings("unchecked")
    public static Map<?, ?> toNestedMap(Collection<?> col, Tuple2<String, KeyFeature[]>... keys) {
        if (isEmpty(col)) {
            return Maps.newLinkedHashMap();
        }
        Map<Object, Object> map = new LinkedHashMap<>();
        for (Object item : col) {
            if (item == null) {
                continue;
            }
            Map<Object, Object> tempMap = map;
            for (int i = 0; i < keys.length; i++) {
                Tuple2<String, KeyFeature[]> tuple = keys[i];
                Object key = getKeyValue(tuple.getV1(), item, tuple.getV2());

                if (i < keys.length - 1) {
                    tempMap = (Map<Object, Object>) tempMap.get(key);
                    if (tempMap == null) {
                        tempMap = new LinkedHashMap<>();
                        map.put(key, tempMap);
                    }
                } else {
                    List<Object> l = (List<Object>) tempMap.computeIfAbsent(key, k -> new ArrayList<>());
                    l.add(item);
                }
            }
        }
        return map;
    }

    public static <T> List<T> asArrayListFromIterable(Iterable<T> iter) {
        if (iter == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        Iterator<T> it = iter.iterator();
        while (it.hasNext()) {
            T e = it.next();
            if (e != null) {
                list.add(e);
            }
        }
        return list;
    }

    @SafeVarargs
    public static <T> List<T> asArrayList(T... elements) {
        if (elements == null) {
            return null;
        }
        List<T> list = new ArrayList<>(elements.length);
        for (T e : elements) {
            if (e != null) {
                list.add(e);
            }
        }
        return list;
    }

    public static <T> Set<T> asHashSetFromIterable(Iterable<T> iter) {
        if (iter == null) {
            return null;
        }
        Set<T> set = new HashSet<>();
        for (T e : iter) {
            if (e != null) {
                set.add(e);
            }
        }
        return set;
    }

    @SafeVarargs
    public static <T> Set<T> asHashSet(T... elements) {
        if (elements == null) {
            return null;
        }
        Set<T> set = new HashSet<>(elements.length);
        for (T e : elements) {
            if (e != null) {
                set.add(e);
            }
        }
        return set;
    }

    @SafeVarargs
    public static <T> Set<T> asLinkedHashSet(T... elements) {
        if (elements == null) {
            return null;
        }
        Set<T> set = new LinkedHashSet<>(elements.length);
        for (T e : elements) {
            if (e != null) {
                set.add(e);
            }
        }
        return set;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] mergeArray(T[]... arrays) {
        int len = 0;
        Class<?> componentType = null;
        for (T[] arr : arrays) {
            if (arr != null) {
                len += arr.length;
                componentType = arr.getClass().getComponentType();
            }
        }

        T[] retArr = (T[]) Array.newInstance(componentType, len);
        int idx = 0;
        for (T[] arr : arrays) {
            if (arr != null) {
                for (int i = 0; i < arr.length; i++) {
                    retArr[idx++] = arr[i];
                }
            }
        }

        return retArr;
    }

    public static long[] toLongArray(Collection<Long> col) {
        if (col == null) {
            return null;
        } else if (col.size() == 0) {
            return LONG_EMPTY_ARRAY;
        }
        // 过滤null
        col.removeIf(Objects::isNull);
        long[] arr = new long[col.size()];
        int idx = 0;
        for (long l : col) {
            arr[idx++] = l;
        }
        return arr;
    }

    public static List<Long> toLongList(long... elements) {
        if (elements == null) {
            return null;
        }
        List<Long> list = new ArrayList<>(elements.length);
        for (long e : elements) {
            list.add(e);
        }
        return list;
    }

    public static Set<Long> toLongSet(long... elements) {
        if (elements == null) {
            return null;
        }
        Set<Long> set = new HashSet<>(elements.length);
        for (long e : elements) {
            set.add(e);
        }
        return set;
    }

    public static int[] toIntegerArray(Collection<Integer> col) {
        if (col == null) {
            return null;
        } else if (col.size() == 0) {
            return INT_EMPTY_ARRAY;
        }

        col.removeIf(Objects::isNull);
        int[] arr = new int[col.size()];
        int idx = 0;
        for (int l : col) {
            arr[idx++] = l;
        }
        return arr;
    }

    public static List<Integer> toIntegerList(int... elements) {
        if (elements == null) {
            return null;
        }
        List<Integer> list = new ArrayList<>(elements.length);
        for (int e : elements) {
            list.add(e);
        }
        return list;
    }

    public static List<Long> splitAsLong(String text, String separator) {
        if (StringUtils.isBlank(separator)) {
            return Lists.newArrayList();
        }
        if (StringUtils.isNotBlank(text)) {
            String[] arr = text.split(separator);
            List<Long> list = new ArrayList<>(arr.length);
            for (String v : arr) {
                list.add(Long.parseLong(StringUtils.trim(v)));
            }
            return list;
        }
        return Lists.newArrayList();
    }

    public static Set<Long> splitAsLongSet(String text, String separator) {
        if (StringUtils.isBlank(separator)) {
            return Sets.newLinkedHashSet();
        }
        if (StringUtils.isNotBlank(text)) {
            String[] arr = text.split(separator);
            Set<Long> set = new LinkedHashSet<>(arr.length);
            for (String v : arr) {
                set.add(Long.parseLong(StringUtils.trim(v)));
            }
            return set;
        }
        return Sets.newLinkedHashSet();
    }

    public static List<String> splitAndTrim(String text, String separator) {
        if (StringUtils.isBlank(separator)) {
            return Lists.newArrayList();
        }
        if (StringUtils.isNotBlank(text)) {
            String arr[] = text.split(separator);
            List<String> list = new ArrayList<>(arr.length);
            for (String v : arr) {
                list.add(StringUtils.trim(v));
            }
            return list;
        }
        return Lists.newArrayList();
    }

    public static List<String> toStringListIgnoreNull(Iterable<?> iterable) {
        if (iterable == null) {
            return null;
        }

        Iterator<?> iterator = iterable.iterator();
        List<String> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (object != null) {
                list.add(object.toString());
            }
        }
        return list;
    }

    public static <T> boolean isEmpty(Collection<T> col) {
        return col == null || col.size() == 0;
    }

    public static <T> boolean isNotEmpty(Collection<T> col) {
        return !isEmpty(col);
    }

    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] arr) {
        return !isEmpty(arr);
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    public static <T> boolean containsAny(Collection<T> col1, Collection<T> col2) {
        if (isEmpty(col1)) {
            return false;
        } else if (isEmpty(col2)) {
            return true;
        }
        for (T item : col2) {
            if (col1.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public static <K, V> Map<K, V> subMap(Map<K, V> map, Collection<K> keys) {
        if (isEmpty(map) || isEmpty(keys)) {
            return new LinkedHashMap<>();
        }
        Map<K, V> subMap = new LinkedHashMap<>();
        for (K key : keys) {
            V v = map.get(key);
            if (v != null) {
                subMap.put(key, v);
            }
        }
        return subMap;
    }

    public static <K, V> List<V> getMapValues(Map<K, V> map, Collection<K> keys) {
        if (isEmpty(map) || isEmpty(keys)) {
            return new ArrayList<>();
        }
        List<V> list = new ArrayList<>();
        for (K key : keys) {
            V v = map.get(key);
            if (v != null) {
                list.add(v);
            }
        }
        return list;
    }

    public static <T> List<T> subListL(List<T> list, int count) {
        return subListL(list, 0, count);
    }

    public static <T> List<T> subListL(List<T> list, int fromIdx, int count) {
        if (list.size() <= fromIdx) {
            return Lists.newArrayList();
        }
        count = Math.min(list.size() - fromIdx, count);
        List<T> l = new ArrayList<>(count);
        for (int i = fromIdx; i < list.size() && count-- > 0; i++) {
            l.add(list.get(i));
        }
        return l;
    }

    public static void removeNullValues(Collection<?> col) {
        if (isEmpty(col)) {
            return;
        }

        col.removeIf(Objects::isNull);
    }

    @FunctionalInterface
    public interface Filter<T> {
        boolean matches(T data);
    }

}
