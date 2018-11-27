package com.books.notegatewayapi.controller;

import com.books.notebasecore.constants.BaseErrorConstants;
import com.books.notebasecore.util.*;
import com.books.notegatewayapi.service.system.IUserMstService;
import com.books.notegatewayapi.utils.SessionRedisUtil;
import com.books.notesystemservers.entity.UserMstEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class UserMstController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserMstController.class);

    @Autowired
    private IUserMstService userMstService;

    @Autowired
    private SessionRedisUtil sessionRedisUtil;

    @RequestMapping(value = {"/user/findList"}, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpJsonResult<Hashtable<String, Object>> findList(HttpServletRequest request) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        String userName = StringUtil.nullSafeString(request.getParameter("userName"));
        List<UserMstEntity> userMstList = null;
        PagerInfo<?> pagerInfo = null;
        Map<String, Object> params = null;
        try {
            if (!StringUtil.isEmpty(request.getParameter("pageSize"))) {
                pagerInfo = BaseWebUtil.getPageInfoForPC(request);
            }
            params = new HashMap<String, Object>();
            if (!StringUtil.isEmpty(userName)) {
                params.put("userName", userName);
            }
            if (pagerInfo != null) {
                params.put("pageSize", pagerInfo.getPageSize());
                params.put("pageIndex", pagerInfo.getPageIndex());
            }
            ServiceResult<List<UserMstEntity>> userMstResult = userMstService.findList(params);
            pagerInfo = userMstResult.getPager();
            if (userMstResult.getSuccess() && userMstResult.getResult() != null
                    && userMstResult.getResult().size() > 0) {
                userMstList = userMstResult.getResult();
                returnTable.put("userMstList", userMstList);
            }
            returnTable.put("pagerInfo", pagerInfo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[System API Gateway][UserMstController][findList] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Find spuProduceList error.");
        }
        return result;
    }

    @RequestMapping(value = {"/user/findInfo"}, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpJsonResult<Hashtable<String, Object>> findInfo(HttpServletRequest request) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        UserMstEntity userMstEntity = null;
        Map<String, Object> params = null;
        int userId = ConvertUtil.toInt(request.getParameter("userId"), 0);
        try {
            params = new HashMap<String, Object>();
            if (userId > 0) {
                params.put("userId", userId);
            }
            ServiceResult<UserMstEntity> userMstResult = userMstService.findInfo(params);
            if (userMstResult.getSuccess() && userMstResult.getResult() != null) {
                userMstEntity = userMstResult.getResult();
                returnTable.put("userMstEntity", userMstEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[System API Gateway][UserMstController][findInfo] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Find spuProduceInfo error.");
        }
        return result;
    }

    @PostMapping(value = "/user/doInsert")
    public HttpJsonResult<Hashtable<String, Object>> doInsert(
            @RequestBody UserMstEntity userMstEntity) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        try {
            ServiceResult<Integer> userMstResult = userMstService.doInsert(userMstEntity);
            if (userMstResult.getSuccess() && userMstResult.getResult() != null) {
                returnTable.put("id", userMstResult.getResult());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[System API Gateway][UserMstController][doInsert] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Insert spuProduce error.");
        }
        return result;
    }

    @PostMapping(value = "/user/doUpdate")
    public HttpJsonResult<Hashtable<String, Object>> doUpdate(
            @RequestBody UserMstEntity userMstEntity) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        try {
            ServiceResult<Integer> userMstResult = userMstService.doUpdate(userMstEntity);
            if (userMstResult.getSuccess() && userMstResult.getResult() != null) {
                returnTable.put("id", userMstResult.getResult());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[System API Gateway][UserMstController][doUpdate] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Update spuProduce error.");
        }
        return result;
    }

    @PostMapping(value = "/user/doDelete")
    public HttpJsonResult<Hashtable<String, Object>> doDelete(@RequestParam int userId) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        Boolean flag = false;
        try {
            ServiceResult<Boolean> userMstResult = userMstService.doDelete(userId);
            if (userMstResult.getSuccess() && userMstResult.getResult() != null) {
                flag = userMstResult.getResult();
            }
            returnTable.put("deleteFlag", flag);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[System API Gateway][UserMstController][doDelete] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Delete spuProduce error.");
        }
        return result;
    }

    @RequestMapping(value = {"/user/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpJsonResult<Hashtable<String, Object>> login(HttpServletRequest request) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        String userPhone = StringUtil.nullSafeString(request.getParameter("userPhone"));
        String password = StringUtil.nullSafeString(request.getParameter("password"));
        UserMstEntity userMstEntity = null;
        Map<String, Object> params = null;
        try {
            params = new HashMap<String, Object>();
            if (userPhone != null) {
                params.put("userPhone", userPhone);
            }
            ServiceResult<UserMstEntity> userMstResult = userMstService.findInfo(params);
            if (userMstResult.getSuccess() && userMstResult.getResult() != null) {
                userMstEntity = userMstResult.getResult();
                if(password.equals(userMstEntity.getUserPassword())){
                    //登陆成功，存入redis
                    sessionRedisUtil.saveSessionCache(userMstEntity.getUserId());
                    returnTable.put("userMstEntity", userMstEntity);
                }else {
                    returnTable.put("info","密码错误!");
                }
            }else {
                returnTable.put("info","账号不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[System API Gateway][UserMstController][findInfo] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Find spuProduceInfo error.");
        }
        return result;
    }
}
