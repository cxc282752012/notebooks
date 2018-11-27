package com.books.noteadminribbon.controller.user;

import com.books.noteadminribbon.controller.BaseController;
import com.books.noteadminribbon.service.system.UserMstAPI;
import com.books.noteadminribbon.utils.WebUtil;
import com.books.notebasecore.util.HttpJsonResult;
import com.books.notebasecore.util.StringUtil;
import com.books.notesystemservers.entity.UserMstEntity;
import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Controller
public class UserController extends BaseController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMstAPI userMstAPI;

    @RequestMapping(value = "/user/registerDetail.html",method = { RequestMethod.GET, RequestMethod.POST })
    public String registerDetail(HttpServletRequest request, Model model) {
        return "/user/regist";
    }

    @RequestMapping(value = "/user/login.html",method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,Model model) {
        String userPhone = StringUtil.nullSafeString(request.getParameter("userPhone"));
        String password = StringUtil.nullSafeString(request.getParameter("password"));
        Map<String, Object> result = Maps.newHashMap();
        Map<String,Object> params = null;
        UserMstEntity userMstEntity = null;
        try {
            params = new HashMap<String, Object>();
            if (userPhone != null){
                params.put("userPhone",userPhone);
            }
            if (password != null){
                params.put("password",password);
            }
            HttpJsonResult<Hashtable<String,Object>> userResult = userMstAPI.login(params);
            userMstEntity = userResult.getDataEntity("userMstEntity",UserMstEntity.class);
            if(userResult.getSuccess() && userMstEntity != null){
                result.put("userMstEntity",userMstEntity);
                //登陆成功，存入cookie
                WebUtil.saveCookie(response,userMstEntity);
            }else {
                result.put("info",userResult.getData().get("info"));
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("[Note Admin Ribbon][UserController][login] failed!", e);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @Override
    public String list(HttpServletRequest request, Model model) {
        return null;
    }

    @Override
    public ResponseEntity<?> findList(HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> findInfo(HttpServletRequest request) {
        return null;
    }

    @Override
    public String detail(HttpServletRequest request, Model model) {
        return null;
    }
}
