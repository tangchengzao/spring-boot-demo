package com.tangcz.springboot.common.context;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:RequestContext
 * Package:com.tangcz.springboot.common.context
 * Description:
 *
 * @date:2020/6/6 13:54
 * @author:tangchengzao
 */
public class RequestContext {

    private static final String                           KEY_DATA_SOURCE_ID = "dataSourceId";
    private static final String                           KEY_ROUTE_ID       = "routeId";
    private static final String                           KEY_TABLE_PREFIX   = "tablePrefix";

    private static final ThreadLocal<Map<String, Object>> TL_CTX             = new ThreadLocal<>();

    public static long getRouteId() {
        Long routeId = getValue(KEY_ROUTE_ID);
        return routeId == null ? 0L : routeId;
    }

    public static void setRouteId(long routeId) {
        setValue(KEY_ROUTE_ID, routeId);
    }

    public static long getDataSourceId() {
        Long dataSourceId = getValue(KEY_DATA_SOURCE_ID);
        return dataSourceId == null ? 0L : dataSourceId;
    }

    public static void setDataSourceId(long dataSourceId) {
        setValue(KEY_DATA_SOURCE_ID, dataSourceId);
    }

    public static String getTablePrefix() {
        return getValue(KEY_TABLE_PREFIX);
    }

    public static void setTablePrefix(String tablePrefix) {
        setValue(KEY_TABLE_PREFIX, tablePrefix);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(String key) {
        Map<String, Object> map = TL_CTX.get();
        if (map == null) {
            return null;
        }
        return (T) map.get(key);
    }

    public static void setValue(String key, Object value) {
        Map<String, Object> map = TL_CTX.get();
        if (map == null) {
            map = new HashMap<>();
            TL_CTX.set(map);
        }
        map.put(key, value);
    }

}
