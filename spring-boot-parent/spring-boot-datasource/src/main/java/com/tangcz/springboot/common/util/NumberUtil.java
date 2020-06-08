package com.tangcz.springboot.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentMap;

/**
 * ClassName:NumberUtil
 * Package:com.tangcz.springboot.common.util
 * Description:
 *
 * @date:2020/6/6 14:22
 * @author:tangchengzao
 */
public class NumberUtil {

    private static final ConcurrentMap<Integer, DecimalFormat> LEN_FORMAT_MAP = ConcurrentMapFactory.getLRUConcurrentMap(1000);

    public static String toString(Number num, int maxDecimalNum, boolean useSeparator) {
        if (num == null) {
            return null;
        }
        if (num.intValue() < num.doubleValue()) {
            double d = num.doubleValue() - num.intValue();
            int k = (int) Math.round(d * Math.pow(10, maxDecimalNum));
            while (k >= 10) {
                int t = k % 10;
                if (t > 0) {
                    break;
                }
                k /= 10;
                maxDecimalNum--;
            }
            DecimalFormat decimalFormat = new DecimalFormat(new String(new char[maxDecimalNum]).replace('\0', '0'));
            if (useSeparator) {
                DecimalFormat df = new DecimalFormat(",###");
                return df.format(num.intValue()) + "." + decimalFormat.format(k);
            } else {
                return num.intValue() + "." + decimalFormat.format(k);
            }
        }
        if (useSeparator) {
            DecimalFormat df = new DecimalFormat(",###");
            return df.format(num.intValue());
        } else {
            return String.valueOf(num.intValue());
        }
    }

    public static void main(String[] args) {
        int n = 2;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        System.out.println(decimalFormat.format(1));
        System.out.println(new String(new char[n]).replace('\0', '0'));
        System.out.println(toString(new BigDecimal(100000.01), 2, false));
        System.out.println(toString(new BigDecimal(10000), 2, false));
    }

    public static int or(int v1, int v2) {
        return v1 | v2;
    }

    public static long or(long v1, long v2) {
        return v1 | v2;
    }

    public static int not(int v1, int v2) {
        return v1 & ~v2;
    }

    public static long not(long v1, long v2) {
        return v1 & ~v2;
    }

}
