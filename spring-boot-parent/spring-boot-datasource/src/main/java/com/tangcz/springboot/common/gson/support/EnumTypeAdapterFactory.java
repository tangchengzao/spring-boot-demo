package com.tangcz.springboot.common.gson.support;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.tangcz.springboot.common.data.enums.CodeEnum;
import com.tangcz.springboot.common.util.EnumUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName:EnumTypeAdapterFactory
 * Package:com.tangcz.springboot.common.gson.support
 * Description:
 *
 * @date:2020/6/6 14:19
 * @author:tangchengzao
 */
public class EnumTypeAdapterFactory implements TypeAdapterFactory {

    public static final EnumTypeAdapterFactory      INSTANCE = new EnumTypeAdapterFactory();
    private static final ConcurrentHashMap<Class<?>, TypeAdapter<?>> typeAdapterMap = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final Type t = type.getType();
        if (t instanceof Class) {
            Class<?> cls = (Class<?>) t;
            if (CodeEnum.class.isAssignableFrom(cls)) {
                TypeAdapter<T> typeAdapter = (TypeAdapter<T>) typeAdapterMap.get(cls);
                if (typeAdapter == null) {
                    typeAdapterMap.put(cls, typeAdapter = new TypeAdapter<T>() {
                        @Override
                        public void write(JsonWriter out, T value) throws IOException {
                            if (value == null) {
                                out.nullValue();
                                return;
                            }

                            out.value(((CodeEnum) value).getCode());
                        }

                        @Override
                        public T read(JsonReader in) throws IOException {
                            if (in.peek() == JsonToken.NULL) {
                                in.nextNull();
                                return null;
                            }

                            String val = in.nextString();
                            return (T) EnumUtil.parseIgnoreCase((Class<? extends Enum>) t, val);
                        }
                    });
                }
                return typeAdapter;
            }
        }
        return null;
    }

}
