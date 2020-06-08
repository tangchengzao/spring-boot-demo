package com.tangcz.springboot.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:MD5Util
 * Package:com.tangcz.springboot.common.util
 * Description:
 *
 * @date:2020/6/6 14:31
 * @author:tangchengzao
 */
public class MD5Util {

    private static ThreadLocal<MessageDigest> messageDigestHolder = new ThreadLocal<>();
    static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    static {
        try {
            MessageDigest message = MessageDigest.getInstance("MD5");
            messageDigestHolder.set(message);
        } catch (NoSuchAlgorithmException var1) {
            throw new RuntimeException("初始化java.security.MessageDigest失败:" + StackTraceUtil.getStackTrace(var1), var1);
        }
    }

    public static String getMD5Format(String data) {
        try {
            MessageDigest message = (MessageDigest)messageDigestHolder.get();
            if (message == null) {
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }

            message.update(data.getBytes(DEFAULT_CHARSET));
            byte[] b = message.digest();
            String digestHexStr = "";

            for (int i = 0; i < 16; i++) {
                digestHexStr += byteHEX(b[i]);
            }

            return digestHexStr;
        } catch (Exception var5) {
            throw new RuntimeException("MD5格式化时发生异常[" + data + "], " + StackTraceUtil.getStackTrace(var5));
        }
    }

    public static String getMD5Format(byte[] data) {
        try {
            MessageDigest message = (MessageDigest)messageDigestHolder.get();
            if (message == null) {
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }

            message.update(data);
            byte[] b = message.digest();
            String digestHexStr = "";

            for (int i = 0; i < 16; i++) {
                digestHexStr += byteHEX(b[i]);
            }

            return digestHexStr;
        } catch (Exception var5) {
            return null;
        }
    }

    private static String byteHEX(byte ib) {
        char[] ob = new char[]{hexDigits[ib >>> 4 & 15], hexDigits[ib & 15]};
        return new String(ob);
    }

}
