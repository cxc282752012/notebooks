package com.books.notebasecore.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具函数
 * 
 * @Filename: StringUtil.java
 * @Version: 1.0
 * @Author: fenghu
 * @Email: liulei@mightcloud.com
 * 
 */
public final class StringUtil {
    private static final String regValidatorIp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";

    /**
     * 检查字符串是否为空
     * <p>
     * 为null或者长度为0视为空字符串
     * 
     * @param value
     *            要检查的字符串
     * @param trim
     *            是否去掉头尾的特定字符
     * @param trimChars
     *            要去掉的特定字符
     * @return
     */
    public static boolean isEmpty(String value, boolean trim, char... trimChars) {
        if (trim)
            return value == null || trim(value, trimChars).length() <= 0;
        return value == null || value.length() <= 0;
    }

    /**
     * 检查字符串是否为空
     * <p>
     * 为null或者长度为0视为空字符串
     * 
     * @param value
     *            要检查的字符串
     * @param trim
     *            是否去掉头尾的空格
     * @return
     */
    public static boolean isEmpty(String value, boolean trim) {
        return isEmpty(value, trim, ' ');
    }

    /**
     * 检查字符串是否为空
     * <p>
     * 为null或者长度为0视为空字符串
     * 
     * @param value
     *            要检查的字符串
     * @return
     */
    public static boolean isEmpty(String value) {
        return isEmpty(value, false);
    }

    /**
     * 判断字符串是否为空
     * 
     * @param s
     * @return
     */
    public static boolean isZeroEmpty(String s) {
        int length;

        if ((s == null) || (s.length() == 0) || s.equals("null") || s.equals("0")) {
            return true;
        }
        length = s.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 如果为null，转换为""
     * 
     * @param value
     * @return
     */
    public static String nullSafeString(String value) {
        return value == null ? "" : value;
    }

    /**
     * 如果为null，转换为""
     * 
     * @param value
     * @return
     */
    public static JsonObject nullSafeJSONObject(JsonObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            return jsonObject.getAsJsonObject(key);
        }
        return null;
    }

    /**
     * 如果为null，转换为""
     * 
     * @param value
     * @return
     */
    public static String nullSafeJSONString(JsonObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            return jsonObject.getAsJsonObject(key).getAsString();
        }
        return "";
    }

    /**
     * 如果为null，转换为""
     * 
     * @param value
     * @return
     */
    public static int nullSafeJSONInteger(JsonObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            return ConvertUtil.toInt(jsonObject.getAsJsonObject(key).getAsInt(), 0);
        }
        return 0;
    }

    /**
     * 如果为null，转换为""
     * 
     * @param value
     * @return
     */
    public static int nullSafeJSONInteger(JsonObject jsonObject, String key, Integer defaultValue) {
        if (jsonObject.has(key)) {
            return ConvertUtil.toInt(jsonObject.get(key), defaultValue);
        }
        return defaultValue;
    }

    /**
     * 如果null 转换0
     * 
     * @author Administrator
     * @param value
     * @return
     */

    public static Integer nullSafeInteger(String value) {
        int val = 0;
        if (value == null) {
            return val;
        }
        val = Integer.parseInt(value);

        return val;
    }

    /**
     * 确保存入数据库的string值不会引起数据库报错。
     * <p>
     * 1. 数据库不允许为null，value为nul时返回""；<br />
     * 2. 超过最大长度时截断字符串。
     * 
     * @param value
     *            要存入数据库的字符串值。
     * @param nullable
     *            是否允许为null。
     * @param maxLength
     *            最大长度。
     * @return
     */
    public static String dbSafeString(String value, boolean nullable, int maxLength) {
        if (value == null) {
            if (nullable)
                return null;
            return nullSafeString(value);
        }
        if (value.length() > maxLength)
            return value.substring(0, maxLength);
        return value;
    }

    /**
     * 去掉头尾空格字符
     * 
     * @param value
     *            待处理的字符串
     * @return
     */
    public static String trim(String value) {
        return trim(3, value, ' ');
    }

    /**
     * 去除字符串头尾的特定字符
     * 
     * @param value
     *            待处理的字符串
     * @param chars
     *            需要去掉的特定字符
     * @return
     */
    public static String trim(String value, char... chars) {
        return trim(3, value, chars);
    }

    /**
     * 去除字符串头部的特定字符
     * 
     * @param value
     *            待处理的字符串
     * @param chars
     *            需要去掉的特定字符
     * @return
     */
    public static String trimStart(String value, char... chars) {
        return trim(1, value, chars);
    }

    /**
     * 去除字符串尾部的特定字符
     * 
     * @param value
     *            待处理的字符串
     * @param chars
     *            需要去掉的特定字符
     * @return
     */
    public static String trimEnd(String value, char... chars) {
        return trim(2, value, chars);
    }

    /**
     * 去掉字符串头尾特定字符
     * 
     * @param mode
     *            <li>1: 去掉头部特定字符；
     *            <li>2: 去掉尾部特定字符；
     *            <li>3: 去掉头尾特定字符；
     * @param value
     *            待处理的字符串
     * @param chars
     *            需要去掉的特定字符
     * @return
     */
    private static String trim(int mode, String value, char... chars) {
        if (value == null || value.length() <= 0)
            return value;

        int startIndex = 0, endIndex = value.length(), index = 0;
        if (mode == 1 || mode == 3) {
            // trim头部
            while (index < endIndex) {
                if (contains(chars, value.charAt(index++))) {
                    startIndex++;
                    continue;
                }
                break;
            }
        }

        if (startIndex >= endIndex)
            return "";

        if (mode == 2 || mode == 3) {
            // trim尾部
            index = endIndex - 1;
            while (index >= 0) {
                if (contains(chars, value.charAt(index--))) {
                    endIndex--;
                    continue;
                }
                break;
            }
        }

        if (startIndex >= endIndex)
            return "";
        if (startIndex == 0 && endIndex == value.length() - 1)
            return value;

        return value.substring(startIndex, endIndex);
    }

    private static boolean contains(char[] chars, char chr) {
        if (chars == null || chars.length <= 0)
            return false;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == chr)
                return true;
        }
        return false;
    }

    /**
     * 判断是否是有效的IP地址。
     * 
     * @param value
     * @return
     */
    public static boolean isIp(String value) {
        if (isEmpty(value))
            return false;
        return value.matches(regValidatorIp);
    }

    /**
     * 分隔list
     * 
     * @param list
     * @param string
     * @return
     */
    public static String listToString(List<?> list, String string) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(string);
            }
        }
        return sb.toString();
    }

    public static String listToString(String[] arr, String string) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String str : arr) {
            if (i == arr.length - 1) {
                sb.append(str);
            } else {
                sb.append(str);
                sb.append(string);
            }
            i++;
        }
        return sb.toString();
    }

    public static String getOrderSn() {
        int mm = (int) (Math.random() * 1000);
        java.text.DecimalFormat df = new java.text.DecimalFormat("000");
        String value = df.format(mm);
        String orderSn = DateUtil.dateToSn(new Date()) + value;
        return orderSn;
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     * 
     * @param length
     *            int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int randomIntNum(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * URL urlEncode
     * 
     * @param strIn
     * @return
     */
    public static String urlEncode(String strIn) {
        if (isEmpty(strIn)) {
            return "";
        }
        String strOut = null;
        try {
            strOut = java.net.URLEncoder.encode(strIn, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strOut;
    }

    public static void main(String[] args) {
        String str = "http://m.kakashixi.com/passport/returnWxLogin";
        System.out.println(urlEncode(str));
    }

    /**
     * URL urlDecode
     * 
     * @param strIn
     * @return
     */
    public static String urlDecode(String strIn) {
        String strOut = null;
        try {
            strOut = java.net.URLDecoder.decode(strIn, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strOut;
    }

    public final static String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 验证邮箱
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {

            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    // public final static String PHONE_PATTERN =
    // "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9]))\\d{8}$";

    public final static String PHONE_PATTERN = "^\\d{10}$";

    /**
     * 判断是否为手机号码
     * 
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        if (StringUtil.isEmpty(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile(PHONE_PATTERN);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String filterEmoji(String source) {
        if (StringUtils.isNotBlank(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
        } else {
            return source;
        }
    }

    /**
     * 
     * @param length
     *            长度
     * @param type
     *            类型
     * @return
     */
    public static String randomStr(int length, int type) {

        if (length <= 0) {
            length = 10;
        }
        String s = "";
        switch (type) {
        case 1:
            s = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
            break;
        case 2:
            s = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
            break;
        case 3:
            s = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
            break;
        case 4:
            s = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
            break;
        default:
            s = "1,2,3,4,5,6,7,8,9,0";
            break;
        }
        String rs = "";
        String[] sa = s.split(",");
        Random rd = new Random();
        for (int i = 0; i < length; i++) {
            rs += sa[rd.nextInt(sa.length)];
        }
        return rs;
    }

    public static boolean getDistanceRange(Date one, Date two, long second) {
        boolean rs = false;
        try {
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }

            if (diff / 1000 <= second) {
                System.out.println("StringUtils.getDistanceRange: " + (diff / 1000) + " <= " + second);
                rs = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    public static String getOnlySn() {
        int mm = (int) (Math.random() * 1000);
        java.text.DecimalFormat df = new java.text.DecimalFormat("000");
        String value = df.format(mm);
        String orderSn = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS") + value;
        return orderSn;
    }
}