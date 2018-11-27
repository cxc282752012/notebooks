package com.books.notebasecore.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.books.notebasecore.constants.BaseConstants;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

/**
 * 公共工具类
 * 
 * @Filename: WebUtil.java
 * @Version: 1.0
 * @Author: linyue
 * @Email: linyue@xianghuoquan.com
 * 
 */
public class BaseWebUtil {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BaseWebUtil.class);

    /**
     * 判断当前操作系统是不是window
     * 
     * @return boolean
     */
    public static boolean isWindows() {
        boolean flag = false;
        if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 取得当前时间的UNIX TIME
     * 
     * @return
     */
    public static Integer getUnixTimestamp() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    /**
     * 判断微信浏览器请求
     * 
     * @param request
     * @return
     */
    public static boolean isWeixinRequest(HttpServletRequest request) {
        boolean validation = false;
        String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
        // 是微信浏览器
        if (ua.indexOf("micromessenger") > 0) {
            validation = true;
        }
        return validation;
    }

    /**
     * 判断ajax请求
     * 
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    /**
     * 获取客户端IP地址。
     * 
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        // 如果客户端经过多级反向代理，则X-Forwarded-For的值并不止一个，而是一串IP值，
        // 取X-Forwarded-For中第一个非unknown的有效IP字符串即为用户真实IP
        if (!StringUtil.isEmpty(ip) && ip.contains(",")) {
            String[] tokens = ip.split(",");
            for (String s : tokens) {
                if (StringUtil.isIp(s.trim())) {
                    ip = s.trim();
                    break;
                }
            }
        }
        if (StringUtil.isIp(ip))
            return ip;
        ip = request.getHeader("Proxy-Client-IP");
        if (StringUtil.isIp(ip))
            return ip;
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtil.isIp(ip))
            return ip;
        return request.getRemoteAddr();
    }

    /**
     * 获取内部ip
     * 
     * @return
     */
    public static List<String> getLocalhostIp() {
        List<String> ips = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address)
                        continue;
                    if ("127.0.0.1".equals(ia.getHostAddress()))
                        continue;
                    ips.add(ia.getHostAddress());
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ips;
    }

    // ------------------------------------------------------------------------------------------------------------

    /**
     * 读取cookie值。
     * 
     * @param request
     * @param name
     * @return
     */
    public static String readCookie(HttpServletRequest request, String name) {
        String value = readCookie(request.getCookies(), name);
        if (!StringUtil.isEmpty(value))
            return value;
        return null;
    }

    private static String readCookie(Cookie[] cookies, String name) {
        if (cookies == null || cookies.length <= 0 || StringUtil.isEmpty(name))
            return null;
        for (Cookie c : cookies) {
            if (name.trim().equalsIgnoreCase(c.getName()))
                return c.getValue();
        }
        return null;
    }

    /**
     * 写cookie。
     * 
     * @param response
     * @param name
     *            cookie名称。
     * @param value
     *            cookie值。
     * @param age
     *            cookie有效时间，单位：秒。age&lt;0表示关闭浏览器失效；age=0表示立即失效（立即删除某个cookie）。
     */
    public static void writeCookie(HttpServletResponse response, String domain, String name, String value, int age) {
        if (response == null || StringUtil.isEmpty(name))
            return;
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(age);
        response.addCookie(cookie);
    }

    /**
     * 删除某个cookie。
     * 
     * @param response
     * @param name
     *            cookie名称。
     */
    public static void deleteCookie(HttpServletResponse response, String domain, String name) {
        writeCookie(response, domain, name, null, 0);
    }

    // ------------------------------------------------------------------------------------------------------------

    /**
     * 添加Session
     * 
     * @param request
     * @param key
     * @param val
     */
    public static void writeSession(HttpServletRequest request, String key, Object val) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute(key, val);
        } catch (Exception e) {
            LOGGER.error("[AdminWebUtil][writeSession] exception!", e);
            e.printStackTrace();
        }
    }

    /**
     * 获取Session value
     * 
     * @param request
     * @param key
     * @return
     */
    public static String getSession(HttpServletRequest request, String key) {
        String str = "";
        try {
            HttpSession session = request.getSession();
            Object attr = session.getAttribute(key);
            if (attr != null) {
                str = String.valueOf(attr);
            }
        } catch (Exception e) {
            LOGGER.error("[AdminWebUtil][getSession] exception!", e);
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 删除Session
     * 
     * @param request
     * @param key
     * @param val
     */
    public static void removeSession(HttpServletRequest request, String key) {
        try {
            HttpSession session = request.getSession();
            session.removeAttribute(key);
        } catch (Exception e) {
            LOGGER.error("[AdminWebUtil][removeSession] exception!", e);
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    public static Map<String, Object> getPageInfo(PagerInfo pagerInfo, String url) {
        int pageIndex = pagerInfo.getPageIndex();
        int pageSize = pagerInfo.getPageSize();
        Map<String, Object> map = new Hashtable<String, Object>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        int total = ConvertUtil.toInt(pagerInfo.getRowsCount(), 0);
        int pageTotal = (total - 1) / pageSize + 1;
        List<Integer> pageList = new ArrayList<Integer>();
        int pageStart = 1;
        int pageEnd = pageTotal;

        if (pageIndex - 4 >= pageStart) {
            pageStart = pageIndex - 4;
        }

        if (pageIndex + 4 <= pageEnd) {
            pageEnd = pageIndex + 4;
        }
        // 总页数小于10页
        for (int i = pageStart; i <= pageEnd; i++) {
            pageList.add(i);
        }
        url = url + "pageSize=" + pageSize + "&pageIndex=";
        map.put("pageUrl", url);
        map.put("pageList", pageList);
        map.put("pageStart", pageStart);
        map.put("pageEnd", pageEnd);
        map.put("pageTotal", pageTotal);
        map.put("pageBegin", 1);
        map.put("pagePre", pageIndex - 1);
        map.put("pageNext", pageIndex + 1);

        return map;
    }

    public static PagerInfo getPageInfo(HttpServletRequest request) {
        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), BaseConstants.PAGE_INDEX_DEFAULT);
        int pageSize = ConvertUtil.toInt(request.getParameter("pageSize"), BaseConstants.PAGE_SIZE_DEFAULT);

        PagerInfo pagerInfo = new PagerInfo(pageSize, pageIndex);
        return pagerInfo;
    }

    public static PagerInfo getPageInfoForPC(HttpServletRequest request) {
        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), BaseConstants.PAGE_INDEX_DEFAULT);
        int pageSize = ConvertUtil.toInt(request.getParameter("pageSize"), BaseConstants.PAGE_SIZE_DEFAULT_10);

        PagerInfo pagerInfo = new PagerInfo(pageSize, pageIndex);
        return pagerInfo;
    }

    public static PagerInfo getPageInfoForDatatables(HttpServletRequest request) {
        // int draw = Integer.valueOf(request.getParameter("draw"));
        int pageSize = ConvertUtil.toInt(request.getParameter("length"), BaseConstants.PAGE_SIZE_DEFAULT_10);

        int pageIndex = ConvertUtil.toInt(request.getParameter("start"), pageSize) / pageSize;
        pageIndex += 1;
        PagerInfo pagerInfo = new PagerInfo(pageSize, pageIndex);
        return pagerInfo;
    }

    public static PagerInfo getJSONPageInfo(JsonObject paramJSON) {
        int pageIndex = StringUtil.nullSafeJSONInteger(paramJSON, "pageIndex", BaseConstants.PAGE_INDEX_DEFAULT);
        int pageSize = StringUtil.nullSafeJSONInteger(paramJSON, "pageSize", BaseConstants.PAGE_SIZE_DEFAULT);
        PagerInfo pagerInfo = new PagerInfo(pageSize, pageIndex);
        return pagerInfo;
    }

    public static List<String[]> getRequestParameterForLike(HttpServletRequest request, String hashPara) {
        Map<String, String[]> paraMap = request.getParameterMap();
        List<String[]> list = new ArrayList<String[]>();
        Enumeration<String> paras = request.getParameterNames();
        while (paras.hasMoreElements()) {
            String para = paras.nextElement().toString();
            if (para.contains(hashPara)) {
                if (paraMap.containsKey(para)) {
                    list.add(paraMap.get(para));
                }
            }
        }
        return list;
    }

    public static String getCurrentUrl(HttpServletRequest request, String param, Object value) {

        StringBuffer sbUrl = request.getRequestURL();
        if (StringUtil.isEmpty(param)) {
            return sbUrl.toString();
        }
        String querys = "";

        Enumeration<?> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String key = en.nextElement().toString();
            String val = request.getParameter(key);
            if (!param.equals(key)) {
                if (querys.equals(""))
                    querys += key + "=" + val;
                else
                    querys += "&" + key + "=" + val;
            }
        }
        if (!querys.equals("")) {
            return sbUrl.toString() + "?" + querys + "&" + param + "=" + value;
        } else {
            return sbUrl.toString() + "?" + param + "=" + value;
        }
    }

    public static String getAllRequestUrl(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (!StringUtil.isEmpty(queryString)) {
            requestURL = requestURL + "?" + queryString;
        }
        return requestURL;
    }
}
