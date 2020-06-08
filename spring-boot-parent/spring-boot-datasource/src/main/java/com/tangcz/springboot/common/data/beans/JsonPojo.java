package com.tangcz.springboot.common.data.beans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tangcz.springboot.common.fastjson.support.EnumValueFilter;
import com.tangcz.springboot.common.gson.support.BooleanAdapterFactory;
import com.tangcz.springboot.common.gson.support.EnumTypeAdapterFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:JsonPojo
 * Package:com.tangcz.springboot.common.data.beans
 * Description:
 *
 * @date:2020/6/6 14:17
 * @author:tangchengzao
 */
public class JsonPojo {

    private static final Logger LOGGER          = LoggerFactory.getLogger(JsonPojo.class);
    private static final ThreadLocal<Boolean> TL_ENDLESS_MARK = new ThreadLocal<>();
    private static final Gson GSON;

    static {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapterFactory(EnumTypeAdapterFactory.INSTANCE);
        gb.registerTypeAdapterFactory(BooleanAdapterFactory.INSTANCE);
        GSON = gb.create();
    }

    public String toJsonString() {
        return toJson(this);
    }

    @Override
    public String toString() {
        return toJson(this);
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        if (TL_ENDLESS_MARK.get() != null) {
            LOGGER.error("JsonFormatError endless found " + obj.getClass().getName());
            return null;
        }
        try {
            return JSON.toJSONString(obj, EnumValueFilter.INSTANCE, SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            LOGGER.error("JsonFormatError " + obj.getClass().getName(), e);
            TL_ENDLESS_MARK.set(true);
            return obj.toString();
        } finally {
            TL_ENDLESS_MARK.remove();
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return GSON.fromJson(json, clazz);
    }

}
