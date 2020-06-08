package com.tangcz.springboot.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * ClassName:StackTraceUtil
 * Package:com.tangcz.springboot.common.util
 * Description:
 *
 * @date:2020/6/6 14:32
 * @author:tangchengzao
 */
public class StackTraceUtil {

    public static String getStackTrace(Throwable exception) {
        StringWriter sw = null;
        PrintWriter pw = null;

        String var4;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            var4 = sw.toString();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return var4;
    }

    public static Throwable findException(Throwable throwable, Class<?>... exClassArray) {
        Throwable t = throwable;
        int i = 0;

        while (t != null) {
            ++i;
            if (i >= 10) {
                break;
            }

            Class[] var7 = exClassArray;
            int var6 = exClassArray.length;

            for (int var5 = 0; var5 < var6; ++var5) {
                Class<?> exClass = var7[var5];
                if (exClass.isInstance(t)) {
                    return t;
                }

                t = t.getCause();
            }
        }

        return null;
    }

}
