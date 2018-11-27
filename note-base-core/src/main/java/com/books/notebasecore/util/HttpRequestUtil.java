package com.books.notebasecore.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.LoggerFactory;

import static com.books.notebasecore.util.JsonUtil.fromJson;

/**
 * Http请求工具类
 * 
 * @Filename: HttpRequestUtil.java
 * @Version: 1.0
 * @Author: baocai
 * @Email: baocai@xianghuoquan.com
 *
 */
public class HttpRequestUtil {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpRequestUtil.class);

    /**
     * 模拟一个GET请求
     * 
     * @param url
     * @return
     */
    public static String doGet(String targetURL, Map<String, String> headerMap) {
        HttpURLConnection conn = null;
        String reStr = "";
        OutputStreamWriter osw = null;
        try {
            URL url1 = new URL(targetURL);
            conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            if (headerMap != null && headerMap.size() > 0) {
                for (String key : headerMap.keySet()) {
                    System.out.println("Key = " + key);
                    conn.setRequestProperty(key, headerMap.get(key));
                }
            }
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // conn.setRequestProperty("Content-Type", "multipart/form-data");
            // conn.setRequestProperty("Connection", "Keep-Alive");
            // conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible;
            // MSIE 7.0; Windows NT; DigExt)");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            // conn.connect();
            InputStream is = conn.getInputStream();
            reStr = convertStreamToString(is).replaceAll("\\n|\\r|\\r\\n", "");
            System.out.println("response is :" + reStr);
        } catch (IOException e) {
            LOGGER.error("[HttpUtil][doGet] have exception!", e);
        } finally {
            if (osw != null)
                try {
                    osw.close();
                } catch (IOException e1) {
                    e1.printStackTrace(System.out);
                }
            if (conn != null)
                conn.disconnect();
        }
        return reStr;
    }

    public static String doPost(String targetURL, String urlParameters) throws JSONException {
        HttpURLConnection connection = null;

        try {
            String targetURL1 = targetURL.concat(urlParameters);
            URL url = new URL(targetURL1);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if
            String line = new String("");
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            System.out.println("response is =" + response);
            rd.close();
            return response.toString();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return "";
    }

    public static String doPostJson(String targetURL, Map<String, String> headerMap, String jsonParameters)
            throws JSONException {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            if (headerMap != null && headerMap.size() > 0) {
                for (String key : headerMap.keySet()) {
                    System.out.println("Key = " + key + " value=" + headerMap.get(key));
                    connection.setRequestProperty(key, headerMap.get(key));
                }
            }
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(jsonParameters);
            wr.close();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if
            String line = new String("");
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            System.out.println("response is =" + response);
            rd.close();
            return response.toString();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return "";
    }

    public static String processUrlConnectionReq(String pBankUrl, String pBankData) throws Exception {
        URL vUrl = null;
        URLConnection vHttpUrlConnection = null;
        DataOutputStream vPrintout = null;
        DataInputStream vInput = null;
        StringBuffer vStringBuffer = null;
        vUrl = new URL(pBankUrl);

        if (vUrl.openConnection() instanceof HttpsURLConnection) {
            vHttpUrlConnection = (HttpsURLConnection) vUrl.openConnection();
        } else if (vUrl.openConnection() instanceof com.sun.net.ssl.HttpsURLConnection) {
            vHttpUrlConnection = (com.sun.net.ssl.HttpsURLConnection) vUrl.openConnection();
        } else {
            vHttpUrlConnection = (URLConnection) vUrl.openConnection();
        }
        vHttpUrlConnection.setDoInput(true);
        vHttpUrlConnection.setDoOutput(true);
        vHttpUrlConnection.setUseCaches(false);
        vHttpUrlConnection.connect();
        vPrintout = new DataOutputStream(vHttpUrlConnection.getOutputStream());
        vPrintout.writeBytes(pBankData);
        vPrintout.flush();
        vPrintout.close();
        try {
            BufferedReader bufferedreader = new BufferedReader(
                    new InputStreamReader(vHttpUrlConnection.getInputStream()));
            vStringBuffer = new StringBuffer();
            String vRespData;
            while ((vRespData = bufferedreader.readLine()) != null)
                if (vRespData.length() != 0)
                    vStringBuffer.append(vRespData.trim());
            bufferedreader.close();
            bufferedreader = null;
        } finally {
            if (vInput != null)
                vInput.close();
            if (vHttpUrlConnection != null)
                vHttpUrlConnection = null;
        }
        System.out.println("response is:" + vStringBuffer.toString());
        return vStringBuffer.toString();
    }

    /**
     * post request
     * 
     * @param <T>
     * 
     * @param url
     * @param map
     * @return
     */
    public static HttpJsonResult<?> doPostEntity(String url, BaseEntity entity) {
        HttpURLConnection conn = null;
        HttpJsonResult<?> jsonResult = null;
        String reStr = "";
        ObjectOutputStream objOut = null;
        try {
            URL url1 = new URL(url);
            conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
            // conn.setRequestProperty("Content-Type",
            // "application/json;charset=utf-8");
            // conn.setRequestProperty("Content-Type", "multipart/form-data");
            // conn.setRequestProperty("Connection", "Keep-Alive");
            // conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible;
            // MSIE 7.0; Windows NT; DigExt)");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            objOut = new ObjectOutputStream(conn.getOutputStream());
            objOut.writeObject(entity);
            objOut.flush();
            objOut.close();
            InputStream is = conn.getInputStream();
            reStr = convertStreamToString(is).replaceAll("\\n|\\r|\\r\\n", "");
        } catch (IOException e) {
            LOGGER.error("[HttpUtil][doPost] have exception!", e);
        } finally {
            if (objOut != null)
                try {
                    objOut.close();
                } catch (IOException e1) {
                    e1.printStackTrace(System.out);
                }
            if (conn != null)
                conn.disconnect();
        }
        if (!StringUtils.isEmpty(reStr)) {
            jsonResult = (HttpJsonResult<?>) fromJson(reStr, HttpJsonResult.class);
        }
        return jsonResult;
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

    private static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    private static String encodeFormData(Map<String, String> paramsMap) {
        StringBuffer sb = new StringBuffer();
        if (paramsMap != null) {
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                if (sb.length() != 0)
                    sb.append("&");
                try {
                    sb.append(entry.getKey());
                    sb.append("=");
                    Object value = entry.getValue();
                    if (value instanceof List) {
                        for (int i = 0; i < ((List) value).size(); i++) {
                            Object temp = ((List) value).get(i);
                            if (i == 0) {
                                sb.append(java.net.URLEncoder.encode(String.valueOf(temp), "utf-8"));
                            } else {
                                sb.append(entry.getKey()).append("=")
                                        .append(java.net.URLEncoder.encode(String.valueOf(temp), "utf-8"));
                            }
                            if (i < ((List) value).size() - 1) {
                                sb.append("&");
                            }
                        }
                    } else {
                        sb.append(java.net.URLEncoder.encode(String.valueOf(entry.getValue()), "utf-8"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
