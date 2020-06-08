package com.tangcz.springboot.common.gson.support;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.tangcz.springboot.common.data.enums.CodeEnum;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ClassName:BooleanAdapterFactory
 * Package:com.tangcz.springboot.common.gson.support
 * Description:
 *
 * @date:2020/6/6 14:24
 * @author:tangchengzao
 */
public class BooleanAdapterFactory implements TypeAdapterFactory {

    public static final BooleanAdapterFactory                    INSTANCE       = new BooleanAdapterFactory();
    private static final ConcurrentMap<Class<?>, TypeAdapter<?>> typeAdapterMap = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
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

                            out.value(String.valueOf(value));
                        }

                        @Override
                        public T read(JsonReader in) throws IOException {
                            if (in.peek() == JsonToken.NULL) {
                                in.nextNull();
                                return null;
                            }

                            if (in.peek() == JsonToken.NUMBER) {
                                int num = in.nextInt();
                                return (T) (num == 1 ? Boolean.TRUE : Boolean.FALSE);
                            }

                            return (T) (in.nextBoolean() ? Boolean.TRUE : Boolean.FALSE);
                        }
                    });
                }
                return typeAdapter;
            }
        }
        return null;
    }

}
