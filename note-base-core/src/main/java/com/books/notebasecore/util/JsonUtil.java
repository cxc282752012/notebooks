package com.books.notebasecore.util;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Json转换工具
 * 
 * @Filename: JsonUtil.java
 * @Version: 1.0
 * @Author: fenghu
 * @Email: liulei@mightcloud.com
 * 
 */
public final class JsonUtil {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 将JSON字符串反序列化为Java对象。
     * 
     * @param json
     *            JSON字符串
     * @return
     *         <li>json字符串为空时返回null；
     *         <li>json字符串为无效JSON格式时，会记录日志，返回null；
     */
    public static final <T> T fromJson(String json) {
        if (StringUtil.isEmpty(json))
            return null;
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter());
        Gson gson = builder.create();
        try {
            Type type = new TypeToken<T>() {
            }.getType();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            LOGGER.warn("Invalidate json format:" + json, e);
            return null;
        }
    }

    /**
     * JSON字符串转换成对象
     * 
     * @param jsonString
     *            需要转换的字符串
     * @param type
     *            需要转换的对象类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<T> type) {
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter());
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("Invalidate json format:" + json, e);
            return null;
        }
    }

    /**
     * JSON字符串转换成对象
     * 
     * @param jsonString
     *            需要转换的字符串
     * @param type
     *            需要转换的对象类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(JsonElement json, Class<T> type) {
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter());
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("Invalidate json format:" + json, e);
            return null;
        }
    }

    /**
     * JSON字符串转换成对象
     * 
     * @param jsonString
     *            需要转换的字符串
     * @param type
     *            需要转换的对象类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(JsonObject json, Class<T> type) {
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter());
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            LOGGER.warn("Invalidate json format:" + json, e);
            return null;
        }
    }

    public static <T> List<T> jsonToList(String json, Class<T> type) {
        List<T> list = null;
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter());
        Gson gson = builder.create();
        try {
            list = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());// 对于不是类的情况，用这个参数给出
        } catch (Exception e) {
            LOGGER.warn("Invalidate json format:" + json, e);
            return null;
        }
        return list;

    }

    public static <T> List<T> jsonToList(JsonArray jsonArray, Class<T> type) {
        List<T> list = null;
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter());
        Gson gson = builder.create();
        try {
            list = (List<T>) gson.fromJson(jsonArray, new TypeToken<List<T>>() {
            }.getType());// 对于不是类的情况，用这个参数给出
        } catch (Exception e) {
            LOGGER.warn("Invalidate json format:" + jsonArray.toString(), e);
            return null;
        }
        return list;

    }

    /**
     * 将Java对象序列化成JSON字符串。
     * 
     * @param obj
     * @return
     */
    public static final String toJson(Object obj) {
        if (obj == null)
            return null;
        try {
            GsonBuilder gb = new GsonBuilder();
            gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
            return gb.create().toJson(obj);
        } catch (Exception e) {
            LOGGER.warn("Can not serialize object to json", e);
            return null;
        }
    }

    /**
     * Google Gson
     * 
     * @param jsonInString
     * @return
     */
    public final static boolean isJSONValid(String jsonInString) {
        try {
            Gson gson = new Gson();
            gson.fromJson(jsonInString, Object.class);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }
}