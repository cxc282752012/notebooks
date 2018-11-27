package com.books.notebasecore.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.LoggerFactory;

/**
 * 日期时间类型的工具类
 * 
 * @Filename: DateUtil.java
 * @Version: 1.0
 * @Author: fenghu
 * @Email: liulei@mightcloud.com
 * 
 */

public class DateUtil {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private static long MS_IN_DAY = 24 * 60 * 60 * 1000;

    /**
     * 获取当前日期时间，包括年、月、日，也包括时、分、秒、毫秒。
     * 
     * @return
     */
    public static Date currentDateTime() {
        return new Date();
    }

    /**
     * 获取当前日期，包括年、月、日（时、分、秒、毫秒均为0）。
     * 
     * @return
     */
    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前时间，包括时、分、秒、毫秒，日期为0001-01-01（无法用{@link Date}表示0000-00-00）。
     * 
     * @return
     */
    public static Date currentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() % MS_IN_DAY);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 用指定的年、月、日创建{@link Date}对象。
     * 
     * @param year
     *            年份
     * @param month
     *            月份，1为1月，2为2月，以此类推（与JDK的{@link Calendar}不同，{@link Calendar}
     *            以0为起始索引）。
     * @param dayOfMonth
     * @return
     * @throws ArgumentException
     *             年月日参数错误时抛出该异常。
     */
    public static Date getDate(int year, int month, int dayOfMonth) {
        if (month <= 0 || month > 12)
            throw new ArgumentException("有效月份值必须从1开始，小于等于12，1表示1月，2表示2月，以此类推");
        if (year < 0)
            throw new ArgumentException("年份必须大于等于0");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, dayOfMonth, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 格式化日期、时间。
     * 
     * @param date
     *            要格式化的日期对象。
     * @param pattern
     *            格式化字符串。
     * @return 格式化后的字符串。
     *         <p>
     *         date为null时，返回空字符串""；pattern为null或者空字符串""时，返回
     *         {@link Date#toString()}。
     *         <p>
     *         格式化过程发生异常，将记录详细异常信息，并返回{@link Date#toString()}。
     * @throws BusinessException
     *             格式化异常时。
     */
    public static String format(Date date, String pattern) {
        if (date == null)
            return "";
        if (StringUtil.isEmpty(pattern))
            return date.toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Throwable e) {
            LOGGER.error("can not format date, date: " + date.toString() + ", pattern:" + pattern + ".", e);
            throw new BusinessException("无法格式化日期时间：" + date.toString() + ", 格式：" + pattern);
        }
    }

    /**
     * @通用格式化日期时间 yyyy-MM-dd HH:mm:ss
     * @param date
     *            格式化日期
     * @return
     */

    public static String formatCommon(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        if (date == null)
            return "";
        if (StringUtil.isEmpty(pattern))
            return date.toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Throwable e) {
            LOGGER.error("can not format date, date: " + date.toString() + ", pattern:" + pattern + ".", e);
            throw new BusinessException("无法格式化日期时间：" + date.toString() + ", 格式：" + pattern);
        }
    }

    /**
     * @通用格式化日期组 yyyy-MM-dd
     * @param date
     *            格式化日期
     * @return
     */

    public static String formatCommonYMD(Date date) {
        String pattern = "yyyy-MM-dd";
        if (date == null)
            return "";
        if (StringUtil.isEmpty(pattern))
            return date.toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Throwable e) {
            LOGGER.error("can not format date, date: " + date.toString() + ", pattern:" + pattern + ".", e);
            throw new BusinessException("无法格式化日期时间：" + date.toString() + ", 格式：" + pattern);
        }
    }

    /**
     * 将字符串表示的日期时间转化为{@link Date}。
     * 
     * @param dateString
     *            日期时间字符串。
     * @param pattern
     *            格式。
     * @return dateString、pattern无效时返回null；转换过程发生异常时返回null，异常信息记录在日志中。
     */
    public static Date parse(String dateString, String pattern) {
        if (StringUtil.isEmpty(dateString, true) || StringUtil.isEmpty(pattern, true))
            return null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(dateString);
        } catch (Exception e) {
            LOGGER.error("can not convert '" + dateString + "' to Date type, format:" + pattern, e);
            return null;
        }
    }

    /**
     * 对日期、时间进行加、减操作。
     * 
     * <pre>
     * DateUtil.add(date, Calendar.YEAR, -1); // date减一年
     * DateUtil.add(date, Calendar.HOUR, -4); // date减4个小时
     * DateUtil.add(date, Calendar.MONTH, 3); // date加3个月
     * </pre>
     * 
     * @param date
     *            日期时间。
     * @param field
     *            执行加减操作的属性，参考{@link Calendar#YEAR}、{@link Calendar#MONTH}、
     *            {@link Calendar#HOUR}等。
     * @param amount
     *            加减数量。
     * @return 执行加减操作后的日期、时间。
     */
    public static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 去除时间（时分秒）部分，保留日期（年月日）部分。
     * 
     * @param date
     *            要操作的日期时间对象。
     * @return 去除时间部分后的日期时间对象。
     */
    public static Date truncateTime(Date date) {
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 去除日期（年月日）部分，保留时间（时分秒）部分。
     * 
     * @param date
     *            要操作的日期时间对象。
     * @return 去除日期部分后的日期时间对象（返回的日期部分为0001-01-01，因为无法用{@link Date}表示0000-00-00）。
     */
    public static Date truncateDate(Date date) {
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, 1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    public static String dateToSn(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String ctime = formatter.format(time);
        return ctime;
    }

    /**
     * 两个时间的差值是否在一个范围内
     * 
     * @param str1
     * @param str2
     * @param second
     * @return
     */
    public static boolean differenceTimeRange(String str1, String str2, long second) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        boolean rs = false;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }

            if (diff / 1000 <= second) {
                System.out.println("Core StringUtil differenceTimeRange: " + (diff / 1000) + " <= " + second);
                rs = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 获取两个时间的差值
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static long differenceTwoTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long diff = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    /**
     * 获取两个时间的差值 (开始时间小于结束时间)
     * 
     * @param start
     * @param end
     * @return
     */
    public static long differenceTime(String start, String end) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long diff = 0;
        try {
            one = df.parse(start);
            two = df.parse(end);
            long time1 = one.getTime();
            long time2 = two.getTime();
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    /**
     * 时间戳转换成日期格式字符串 yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String chageTime(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str_date = dateFormat.format(new Date(date * 1000));
        return str_date;
    }
}