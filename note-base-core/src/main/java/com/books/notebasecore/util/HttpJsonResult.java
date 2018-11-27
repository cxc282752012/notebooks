package com.books.notebasecore.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * JSON处理封装
 * 
 * @Filename: HttpJsonResult.java
 * @Version: 1.0
 * @Author: fenghu
 * @Email: liulei@mightcloud.com
 * 
 */
public class HttpJsonResult<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8637111820477625638L;

    /**
     * 返回成功失败的标识
     */
    private Boolean success = true;

    private Date systemTime;

    public Date getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Date systemTime) {
        this.systemTime = systemTime;
    }

    /**
     * 返回成功时，数据内容
     */
    private T data;

    /**
     * 返回错误时错误信息内容
     */
    private String message;

    /**
     * 返回错误时的错误编码
     */
    private String errorCode;

    /**
     * 返回数据总数
     */
    private Integer totalCount = 0;

    public HttpJsonResult() {
        this.message = "";
        this.errorCode = "";
    }

    public HttpJsonResult(T data) {
        this.data = data;
        this.message = "";
        this.errorCode = "";
        this.systemTime = new Date();
    }

    public HttpJsonResult(String errorMessage) {
        this.success = false;
        this.message = errorMessage;
    }

    public void setError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.message = errorMessage;
        this.success = false;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setTotalCount(Integer count) {
        this.totalCount = count;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    // /**
    // * Find Pager info from data
    // *
    // * @Description
    // * @author hale.wang@milmila.com
    // * @return
    // */
    // public PagerInfo<?> getDataPagerInfo() {
    // PagerInfo<?> pagerInfo = null;
    // try {
    // JSONObject dataObj = JSONObject.fromObject(this.data);
    // if (dataObj.containsKey("pagerInfo")) {
    // pagerInfo = (PagerInfo<?>)
    // JSONObject.toBean(dataObj.getJSONObject("pagerInfo"), PagerInfo.class);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return pagerInfo;
    // }

    /**
     * Find Pager info from data
     * 
     * @Description
     * @author hale.wang@milmila.com
     * @return
     */
    public PagerInfo<?> getDataPagerInfo() {
        PagerInfo<?> pagerInfo = null;
        try {

            Gson gson = new Gson();
            String json = gson.toJson(this.data);
            JsonParser parser = new JsonParser();
            JsonObject dataObj = parser.parse(json).getAsJsonObject();

            if (dataObj.has("pagerInfo")) {
                pagerInfo = (PagerInfo<?>) JsonUtil.fromJson(dataObj.getAsJsonObject("pagerInfo"), PagerInfo.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagerInfo;
    }

    /**
     * find entity from data
     *
     * @Description
     * @author hale.wang@milmila.com
     * @param key
     * @param type
     * @return
     */
    public <T> T getDataEntity(String key, Class<T> type) {
        Gson gson = new Gson();
        String json = gson.toJson(this.data);
        JsonParser parser = new JsonParser();
        JsonObject dataObj = parser.parse(json).getAsJsonObject();
        JsonObject dataTemp = null;
        try {
            if (dataObj.has(key)) {
                dataTemp = dataObj.getAsJsonObject(key);
                return (T) JsonUtil.fromJson(dataTemp, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * find entity from data
     *
     * @Description
     * @author hale.wang@milmila.com
     * @param key
     * @param type
     * @return
     */
    public static <T> T getDataEntity(Object object, String key, Class<T> type) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        JsonParser parser = new JsonParser();
        JsonObject dataObj = parser.parse(json).getAsJsonObject();
        JsonObject dataTemp = null;
        try {
            if (dataObj.has(key)) {
                dataTemp = dataObj.getAsJsonObject(key);
                return (T) JsonUtil.fromJson(dataTemp, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // /**
    // * find entity from data
    // *
    // * @Description
    // * @author hale.wang@milmila.com
    // * @param key
    // * @param type
    // * @return
    // */
    // public <T> T getDataEntity(String key, Class<T> type) {
    // JSONObject dataObj = JSONObject.fromObject(this.data);
    // JSONObject dataTemp = null;
    // try {
    // if (dataObj.containsKey(key)) {
    // dataTemp = dataObj.getJSONObject(key);
    // String str = dataTemp.toString();
    // String regEx = "[0-9]{13}";
    // Pattern pattern = Pattern.compile(regEx);
    // Matcher matcher = pattern.matcher(str);
    // while (matcher.find()) {
    // String group = matcher.group();
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // String format = sdf.format(new Date(Long.valueOf(group)));
    // str = str.replace(group, "\"" + format + "\"");
    // }
    // String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
    // JSONUtils.getMorpherRegistry().registerMorpher(new
    // DateMorpher(dateFormats));
    // JSONObject obj = JSONObject.fromObject(str);
    // return (T) JSONObject.toBean(obj, type);
    // }
    // // if (dataObj.containsKey(key)) {
    // // return (T) JSONObject.toBean(dataObj.getJSONObject(key), type);
    // // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // /**
    // * find entity list from data
    // *
    // * @Description
    // * @author hale.wang@milmila.com
    // * @param key
    // * @param class1
    // * @return
    // */
    // public <T> List<T> getDataList(String key, Class<T> type) {
    // JSONObject dataObj = JSONObject.fromObject(this.data);
    // JSONArray dataArray = null;
    // try {
    // if (dataObj.containsKey(key)) {
    // dataArray = dataObj.getJSONArray(key);
    // String str = dataArray.toString();
    // String regEx = "[0-9]{13}";
    // Pattern pattern = Pattern.compile(regEx);
    // Matcher matcher = pattern.matcher(str);
    // while (matcher.find()) {
    // String group = matcher.group();
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // String format = sdf.format(new Date(Long.valueOf(group)));
    // str = str.replace(group, "\"" + format + "\"");
    // }
    // String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss" };
    // JSONUtils.getMorpherRegistry().registerMorpher(new
    // DateMorpher(dateFormats));
    // JSONArray jArray = JSONArray.fromObject(str);
    // return (List<T>) JSONArray.toCollection(jArray, type);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    // }
    /**
     * find entity list from data
     * 
     * @Description
     * @author hale.wang@milmila.com
     * @param key
     * @param class1
     * @return
     */
    public <T> List<T> getDataList(String key, Class<T> type) {

        Gson gson = new Gson();
        String json = gson.toJson(this.data);
        JsonParser parser = new JsonParser();
        JsonObject dataObj = parser.parse(json).getAsJsonObject();

        JsonArray dataArray = null;
        List<T> list = new ArrayList<T>();
        try {
            if (dataObj.has(key)) {
                dataArray = dataObj.getAsJsonArray(key);
                for (JsonElement elem : dataArray) {
                    list.add(JsonUtil.fromJson(elem, type));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> T convertStreamToEntity(HttpServletRequest request, Class<T> type) throws ServletException {
        ServletInputStream in = null;
        ObjectInputStream obj = null;
        try {
            in = request.getInputStream();
            obj = new ObjectInputStream(in);
            return (T) obj.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (obj != null) {
                try {
                    obj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static <T> List<T> getDataList(Object object, String key, Class<T> type) {

        Gson gson = new Gson();
        String json = gson.toJson(object);
        JsonParser parser = new JsonParser();
        JsonObject dataObj = parser.parse(json).getAsJsonObject();

        JsonArray dataArray = null;
        List<T> list = new ArrayList<T>();
        try {
            if (dataObj.has(key)) {
                dataArray = dataObj.getAsJsonArray(key);
                for (JsonElement elem : dataArray) {
                    list.add(JsonUtil.fromJson(elem, type));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}