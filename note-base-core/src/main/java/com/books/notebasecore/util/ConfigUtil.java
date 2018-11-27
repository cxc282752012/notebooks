package com.books.notebasecore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.LoggerFactory;

public class ConfigUtil {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

    private static Map<Object, Object> map = null;

    private ConfigUtil() {

    }

    public synchronized static void init() {
        // 根据系统环境读取配置
        String fileName = "/properties/config_pro.properties";
        if (BaseWebUtil.isWindows()) {
            fileName = "/properties/config_dev.properties";
        }
        InputStream in = null;

        try {
            map = new HashMap<Object, Object>();
            in = ConfigUtil.class.getResourceAsStream(fileName);
            Properties props = new Properties();
            props.load(in);
            Set<Object> keys = props.keySet();
            for (Object o : keys) {
                Object t = props.get(o);
                map.put(o, t);
            }
            in.close();

        } catch (IOException e) {
            LOGGER.error("[search-properties]:" + fileName + "读取失败！");
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                LOGGER.error("[search-properties]:" + fileName + "关闭失败！");
            }
        }
    }

    public static String getProperty(String key) {
        if (map == null)
            init();
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("没有转换前的配置信息：" + (String) map.get(key));
            }
            return (String) map.get(key);
        } catch (Exception e) {
            LOGGER.error("[search-properties]:读取" + key + "属性异常" + e);
            return null;
        }
    }

    /**
     * 从配置文件中获取所有的特殊转义字符
     */
    private static Map<String, String> getStringRegex() {
        String str = ConfigUtil.getProperty("stringRegex");
        Map<String, String> map = new HashMap<String, String>();
        if (null == str || str.trim().length() <= 0) {
            return null;
        }
        for (String s : str.split(",")) {
            if (null == s || s.trim().length() <= 0) {
                continue;
            }
            String[] mark = s.split("-");
            if (mark.length > 0) {
                map.put(mark[0].toString(), mark[1].toString());
            }
        }
        return map;
    }

    /**
     * 过滤字符串中需要转义的html标记
     * 
     * @param str
     * @return
     */
    public static String regexString(String str) {
        if (null == str || str.trim().length() <= 0)
            return null;
        Map<String, String> map = getStringRegex();
        if (null != map) {
            for (String regex : map.keySet()) {
                String replacement = map.get(regex);
                str = str.replaceAll(regex, replacement);
            }
        }
        return str;
    }
}