package com.tangcz.springboot.common.util;

import com.tangcz.springboot.common.data.enums.CodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * ClassName:EnumUtil
 * Package:com.tangcz.springboot.common.util
 * Description:
 *
 * @date:2020/6/6 14:21
 * @author:tangchengzao
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EnumUtil {

    public static <TEnum extends Enum> TEnum parseIgnoreCase(Class<TEnum> enumType, String value) {
        return parseIgnoreCase(enumType, value, null);
    }

    public static <TEnum extends Enum> TEnum parseIgnoreCase(Class<TEnum> enumType, String value, TEnum defaultEnum) {
        return parse(enumType, value, defaultEnum, true);
    }

    public static <TEnum extends Enum> TEnum parse(Class<TEnum> enumType, String value) {
        return parseIgnoreCase(enumType, value, null);
    }

    public static <TEnum extends Enum> TEnum parse(Class<TEnum> enumType, Long value) {
        return parseIgnoreCase(enumType, value.toString(), null);
    }

    public static <TEnum extends Enum> TEnum parse(Class<TEnum> enumType, String value, TEnum defaultEnum) {
        return parse(enumType, value, defaultEnum, false);
    }

    private static <TEnum extends Enum> TEnum parse(Class<TEnum> enumType, String value, TEnum defaultEnum, boolean ignoreCase) {
        if (StringUtils.isBlank(value)) {
            return defaultEnum;
        }
        try {
            if (!ignoreCase) {
                return (TEnum) Enum.valueOf(enumType, value);
            } else {
                TEnum[] constants = enumType.getEnumConstants();
                for (TEnum ce : constants) {
                    if (StringUtils.equalsIgnoreCase(ce.name(), value)) {
                        return ce;
                    }
                }
            }
        } catch (Exception ignore) {
        }

        if (CodeEnum.class.isAssignableFrom(enumType)) {
            try {
                Integer intVal = Integer.parseInt(value);
                return parse(enumType, intVal, defaultEnum);
            } catch (Exception ignore) {
            }
        }

        Method parseMethod = ReflectionUtils.findMethod(enumType, "parse", String.class);
        if (parseMethod != null && Modifier.isStatic(parseMethod.getModifiers())) {
            parseMethod.setAccessible(true);
            try {
                return (TEnum) parseMethod.invoke(null, value);
            } catch (Exception ignore) {
            }
        }
        return defaultEnum;
    }

    public static <TEnum extends Enum> TEnum parse(Class<TEnum> enumType, int code) {
        return parse(enumType, code, null);
    }

    private static <TEnum extends Enum> TEnum parse(Class<TEnum> enumType, int code, TEnum defaultEnum) {
        TEnum[] constants = enumType.getEnumConstants();
        for (TEnum ce : constants) {
            if (((CodeEnum) ce).getCode() == code) {
                return ce;
            }
        }
        return defaultEnum;
    }

    public static <TEnum extends CodeEnum> boolean isMatch(int code, TEnum... enumValues) {
        if (enumValues == null) {
            return false;
        }
        for (TEnum enumValue : enumValues) {
            if (enumValue.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    public static <TEnum extends CodeEnum> boolean isMatchByBit(long code, TEnum... enumValues) {
        if (enumValues == null) {
            return false;
        }
        for (TEnum enumValue : enumValues) {
            if ((enumValue.getCode() & code) == enumValue.getCode()) {
                return true;
            }
        }
        return false;
    }

    public static <TEnum extends CodeEnum> int or(int value, TEnum codeEnum) {
        return NumberUtil.or(value, codeEnum.getCode());
    }

    public static <TEnum extends CodeEnum> long or(long value, TEnum codeEnum) {
        return NumberUtil.or(value, codeEnum.getCode());
    }

    public static <TEnum extends CodeEnum> int not(int value, TEnum codeEnum) {
        return NumberUtil.not(value, codeEnum.getCode());
    }

    public static <TEnum extends CodeEnum> long not(long value, TEnum codeEnum) {
        return NumberUtil.not(value, codeEnum.getCode());
    }

}
