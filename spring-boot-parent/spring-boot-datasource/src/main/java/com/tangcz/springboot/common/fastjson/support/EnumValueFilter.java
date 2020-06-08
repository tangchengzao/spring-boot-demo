package com.tangcz.springboot.common.fastjson.support;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.tangcz.springboot.common.data.enums.CodeEnum;

/**
 * ClassName:EnumValueFilter
 * Package:com.tangcz.springboot.common.fastjson.support
 * Description:
 *
 * @date:2020/6/6 14:27
 * @author:tangchengzao
 */
public class EnumValueFilter implements ValueFilter {

    public static final EnumValueFilter INSTANCE = new EnumValueFilter();

    @Override
    public Object process(Object object, String name, Object value) {
        if (value instanceof CodeEnum) {
            return ((CodeEnum) value).getCode();
        }
        return value;
    }

}
