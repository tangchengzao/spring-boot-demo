package com.tangcz.springboot.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:StringUtil
 * Package:com.tangcz.springboot.common.util
 * Description:
 *
 * @date:2020/6/6 14:32
 * @author:tangchengzao
 */
public class StringUtil {

    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static final String LOG_SEPARATOR = "|";

    public static final String HYPHEN = "-";
    public static final String NEW_LINE = "\n";
    private static final String SHARP = "#";

    public static final String ARG_SEPARATOR = ",";
    public static final long[] LONG_EMPTY_ARRAY = new long[0];
    public static final int[] INT_EMPTY_ARRAY = new int[0];

    private static Pattern P_CHINESE = Pattern.compile("[\u4e00-\u9fa5]");

    private static final ThreadLocal<StringBuilder> TL_BUILDER = new ThreadLocal<>();

    public static StringBuilder getReusedBuilder() {
        StringBuilder builder = TL_BUILDER.get();
        if (builder == null) {
            TL_BUILDER.set(builder = new StringBuilder());
        } else {
            builder.setLength(0);
        }
        return builder;
    }

    public static String getFirstCharWithAlphabetOrPinyin(String name) {
        if (name == null || name.length() == 0) {
            return SHARP;
        }
        char c = name.charAt(0);
        String key = getChar(c);
        if (SHARP.equals(key)) {
            String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(c);
            if (CollectionUtil.isNotEmpty(pinyinArr)) {
                c = pinyinArr[0].charAt(0);
                key = getChar(c);
            }
        }

        return key;
    }

    private static String getChar(char c) {
        String key;
        if (c >= 'a' && c <= 'z') {
            key = String.valueOf(c).toUpperCase();
        } else if (c >= 'A' && c <= 'Z') {
            key = String.valueOf(c);
        } else {
            key = SHARP;
        }
        return key;
    }

    public static boolean hasNull(String... arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        for (String str : arr) {
            if (str == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNotNull(String... arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        for (String str : arr) {
            if (str != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNullOrBlank(String... arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        for (String str : arr) {
            if (StringUtils.isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNotNullOrBlank(String... arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        for (String str : arr) {
            if (StringUtils.isNotBlank(str)) {
                return true;
            }
        }
        return false;
    }

    public static String firstCharUpCase(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }

        String firstChar = String.valueOf(value.charAt(0)).toUpperCase();
        if (value.length() == 1) {
            return firstChar;
        } else {
            return firstChar + value.substring(1);
        }
    }

    public static String fixStringLen(String val, int maxLen) {
        if (val == null || maxLen <= 0) {
            return val;
        }
        if (val.length() > maxLen) {
            return val.substring(0, maxLen);
        }
        return val;
    }

    @SafeVarargs
    public static <T> String buildArgs(T... args) {
        return formatArg(ARG_SEPARATOR, args);
    }

    public static String buildArgsFromIterable(Iterable<?> iter) {
        return formatArg(ARG_SEPARATOR, iter);
    }

    @SafeVarargs
    public static <T> String formatArg(String separator, T... args) {
        if (args == null || args.length == 0) {
            return StringUtils.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            if (builder.length() > 0) {
                builder.append(separator);
            }
            if (arg == null) {
                builder.append(StringUtils.EMPTY);
            } else {
                builder.append(arg);
            }
        }
        return builder.toString();
    }

    @SafeVarargs
    public static <T> String formatArgIgnoreNull(String separator, T... args) {
        if (args == null || args.length == 0) {
            return StringUtils.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            if (arg != null) {
                if (builder.length() > 0) {
                    builder.append(separator);
                }
                builder.append(arg);
            }
        }
        return builder.toString();
    }

    public static String formatArg(String separator, Iterable<?> iter) {
        if (iter == null || !iter.iterator().hasNext()) {
            return StringUtils.EMPTY;
        }
        Iterator<?> it = iter.iterator();
        StringBuilder builder = new StringBuilder();
        while (it.hasNext()) {
            Object arg = it.next();
            if (builder.length() > 0) {
                builder.append(separator);
            }
            if (arg == null) {
                builder.append(StringUtils.EMPTY);
            } else {
                builder.append(arg);
            }
        }
        return builder.toString();
    }

    public static String formatArgIgnoreNull(String separator, Iterable<?> iter) {
        if (iter == null || !iter.iterator().hasNext()) {
            return StringUtils.EMPTY;
        }
        Iterator<?> it = iter.iterator();
        StringBuilder builder = new StringBuilder();
        while (it.hasNext()) {
            Object arg = it.next();
            if (arg != null) {
                if (builder.length() > 0) {
                    builder.append(separator);
                }
                builder.append(arg);
            }
        }
        return builder.toString();
    }

    @SafeVarargs
    public static <T> String buildStatLog(T... args) {
        return formatArg(LOG_SEPARATOR, args);
    }

    public static String buildStatLog(Iterable<?> iter) {
        return formatArg(LOG_SEPARATOR, iter);
    }

    public static byte[] toBytes(String val) {
        if (StringUtils.isEmpty(val)) {
            return null;
        }
        return val.getBytes(UTF_8);
    }

    public static String bytesToString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, UTF_8);
    }

    public static String emptyIfNull(String val) {
        if (val == null) {
            return "";
        }
        return val;
    }

    public static String toString(Object value) {
        return toString(value, null);
    }

    public static String toString(Object value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return String.valueOf(value);
    }

    public static String urlEncode(String text, String charset) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        try {
            return URLEncoder.encode(text, charset);
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }

    public static String urlDecode(String text, String charset) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        try {
            return URLDecoder.decode(text, charset);
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }

    public static String removeUnicodeControlChars(String str) {
        if (str != null && str.length() != 0) {
            int ctrlCharNum = 0;
            char[] cArr = str.toCharArray();

            for (int copy = 0; copy < cArr.length; ++copy) {
                char i = cArr[copy];
                if (Character.isISOControl(i)) {
                    ++ctrlCharNum;
                    cArr[copy] = 1;
                }
            }

            if (ctrlCharNum > 0) {
                char[] var7 = new char[cArr.length - ctrlCharNum];
                int var8 = 0;

                for (int j = 0; var8 < cArr.length ; ++var8) {
                    char c = cArr[var8];
                    if (c != 1) {
                        var7[j++] = c;
                    }
                }

                str = new String(var7);
            }

            return str.replace("■", "");
        } else {
            return str;
        }
    }

    public static boolean isChineseStr(String str) {
        char[] c = str.toCharArray();
        for (char value : c) {
            Matcher matcher = P_CHINESE.matcher(String.valueOf(value));
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    public static String unescape(String txt) {
        try {
            return StringEscapeUtils.unescapeHtml4(txt);
        } catch (Exception e) {
            return txt;
        }
    }

}
