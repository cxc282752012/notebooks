
package com.books.noteadminribbon.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.notebasecore.util.BaseWebUtil;
import com.books.notebasecore.util.StringUtil;
import com.books.notesystemservers.entity.UserMstEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebUtil extends BaseWebUtil {

    public static String domainName;

    @Value("${domain.path}")
    public void setDomainName(String path) {
        WebUtil.domainName = path;
    }

    /**
     * 从cookie中获取customer数据
     * 
     * @Description
     * @author chen
     * @param request
     * @return
     */
    public static int getUserId(HttpServletRequest request) {
        int userId = 0;
        String userIdStr = readCookie(request, "userId");
        if (!StringUtil.isEmpty(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }
        return userId;
    }

    /**
     * 判断用户是否登录
     * 
     * @Description
     * @author wangyuhai
     * @param request
     * @return
     */
    public static boolean isCustomerLogin(HttpServletRequest request) {
        boolean isUserLogin = false;
        String userId = readCookie(request, "userId");
        if (!StringUtil.isEmpty(userId)) {
            isUserLogin = true;
        }
        return isUserLogin;
    }

    /**
     * 用户登录成功后保存cookie
     * 
     * @Description
     * @author wangyuhai
     * @param response
     * @param userMstEntity
     */
    public static void saveCookie(HttpServletResponse response, UserMstEntity userMstEntity) {
        if (userMstEntity != null) {
            writeCookie(response, "userId", String.valueOf(userMstEntity.getUserId()));
            writeCookie(response, "userName", StringUtil.nullSafeString(userMstEntity.getUserName()));
            writeCookie(response, "userPhone", userMstEntity.getUserPhone());
            writeCookie(response, "userPassword", userMstEntity.getUserPassword());
        }
    }

    /**
     * 用户退出登录后删除所有cookie
     * 
     * @Description
     * @author wangyuhai
     * @param response
     */
    public static void deleteAllCookies(HttpServletResponse response) {
        deleteCookie(response, "userId");
        deleteCookie(response, "userName");
        deleteCookie(response, "userPhone");
        deleteCookie(response, "userPassword");
    }

    public static void writeCookie(HttpServletResponse response, String name, String value) {
        writeCookie(response, domainName, name, value, 60 * 60 * 24 * 30);
    }

    public static void deleteCookie(HttpServletResponse response, String name) {
        deleteCookie(response, domainName, name);
    }
}
