package com.tangcz.springboot.common.util;

import com.tangcz.springboot.common.cache.ScriptLangEnum;
import org.mvel2.MVEL;

/**
 * ClassName:ScriptEngine
 * Package:com.tangcz.springboot.common.util
 * Description:
 *
 * @date:2020/6/8 1:28
 * @author:tangchengzao
 */
public class ScriptEngine {

    public static <T> String evalToString(ScriptLangEnum scriptLang, String script, Object ctx, Class<T> toType) {
        return String.valueOf(eval(scriptLang, script, ctx, toType));
    }

    @SuppressWarnings("unchecked")
    public static <T> T eval(ScriptLangEnum scriptLang, String script, Object ctx, Class<T> toType) {
        if (scriptLang == null) {
            scriptLang = ScriptLangEnum.MVEL;
        }

        switch (scriptLang) {
            case MVEL:
            default:
                if (toType != null) {
                    return MVEL.eval(script, ctx, toType);
                } else {
                    return (T) MVEL.eval(script, ctx);
                }
        }
    }

}
