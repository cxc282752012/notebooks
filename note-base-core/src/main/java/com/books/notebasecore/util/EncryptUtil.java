package com.books.notebasecore.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

/**
 * 加密、解密相关操作
 * 
 * @Filename: EncryptUtil.java
 * @Version: 1.0
 * @Author: fenghu
 * @Email: liulei@mightcloud.com
 * 
 */
@SuppressWarnings("restriction")
public final class EncryptUtil {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * 将字符串MD5
     * 
     * @param plainText
     *            需要MD5的字符串
     * @return MD5字符串值
     */
    public static String md5(String plainText) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("can not computer MD5 for: " + plainText, e);
            return plainText;
        }
        return result;
    }

    /**
     * 将字符串转换成BASE64编码。
     * 
     * @param s
     * @return
     */
    public static String toBASE64(String s) {
        if (s == null)
            return null;
        try {
            return new sun.misc.BASE64Encoder().encode(s.getBytes());
        } catch (Exception e) {
            LOGGER.error("Can not convert string to base64:" + s, e);
            return null;
        }
    }

    /**
     * 将BASE64编码的字符串还原。
     * 
     * @param s
     * @return
     */
    public static String fromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            LOGGER.error("Can not convert base64 to string:" + s, e);
            return null;
        }
    }
}
