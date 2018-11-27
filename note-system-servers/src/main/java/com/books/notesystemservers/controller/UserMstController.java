package com.books.notesystemservers.controller;


import com.books.notebasecore.constants.BaseErrorConstants;
import com.books.notebasecore.util.*;
import com.books.notesystemservers.entity.UserMstEntity;
import com.books.notesystemservers.service.UserMstService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserMstController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserMstController.class);

    @Autowired
    private UserMstService userMstService;

    /**
     * find list
     * @param request
     * @return
     */
    @GetMapping(value = "/user/findList")
    public ServiceResult<List<UserMstEntity>> findList(HttpServletRequest request) {
        ServiceResult<List<UserMstEntity>> result = new ServiceResult<List<UserMstEntity>>();
        PagerInfo<?> pagerInfo = null;
        Map<String, Object> params = null;
        String userName = StringUtil.nullSafeString(request.getParameter("userName"));
        String userPhone = StringUtil.nullSafeString(request.getParameter("userPhone"));
        try {
            if (!StringUtil.isEmpty(request.getParameter("pageSize"))) {
                pagerInfo = BaseWebUtil.getPageInfoForPC(request);
            }
            params = new HashMap<String, Object>();
            if(!StringUtil.isEmpty(userName)){
                params.put("userName",userName);
            }
            if(!StringUtil.isEmpty(userPhone)){
                params.put("userPhone",userPhone);
            }
            result = userMstService.findList(params, pagerInfo);
        } catch (Exception e) {
            LOGGER.error("[UserMstController][findList]：query findList occur exception", e);
            result.setError(BaseErrorConstants.ERROR_CODE_SERVICE_CONTROLLER, "Find inbound order list error.");
        }

        return result;
    }

    /**
     * find info
     * @param request
     * @return
     */
    @GetMapping(value = "/user/findInfo")
    public ServiceResult<UserMstEntity> findInfo(HttpServletRequest request) {
        ServiceResult<UserMstEntity> result = new ServiceResult<UserMstEntity>();
        Map<String, Object> params = null;
        Integer userId = ConvertUtil.toInt(request.getParameter("userId"), 0);
        String userPhone = StringUtil.nullSafeString(request.getParameter("userPhone"));
        try {
            params = new HashMap<String, Object>();
            if(userId > 0){
                params.put("userId",userId);
            }
            if(!StringUtil.isEmpty(userPhone)){
                params.put("userPhone",userPhone);
            }
            result = userMstService.findInfo(params);
        } catch (Exception e) {
            LOGGER.error("[UserMstController][findInfo]：query findInfo occur exception", e);
            result.setError(BaseErrorConstants.ERROR_CODE_SERVICE_CONTROLLER, "Find inbound order error.");
        }
        return result;
    }

    /**
     * do insert
     * @param userMstEntity
     * @return
     */
    @PostMapping(value = "/user/doInsert")
    public ServiceResult<Integer> doInsert(@RequestBody UserMstEntity userMstEntity) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result = userMstService.doInsert(userMstEntity);
        } catch (Exception e) {
            LOGGER.error("[UserMstController][doInsert]：Insert occur exception", e);
            result.setError(BaseErrorConstants.ERROR_CODE_SERVICE_CONTROLLER, "Insert error.");
        }
        return result;
    }

    /**
     * do update
     * @param userMstEntity
     * @return
     */
    @PostMapping(value = "/user/doUpdate")
    public ServiceResult<Integer> doUpdate(@RequestBody UserMstEntity userMstEntity) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result = userMstService.doUpdate(userMstEntity);
        } catch (Exception e) {
            LOGGER.error("[UserMstController][doUpdate]：Update occur exception", e);
            result.setError(BaseErrorConstants.ERROR_CODE_SERVICE_CONTROLLER, "Update error.");
        }
        return result;
    }

    /**
     * do delete
     * @param userId
     * @return
     */
    @PostMapping(value = "/user/doDelete")
    public ServiceResult<Boolean> doDelete(@RequestParam int userId) {
        ServiceResult<Boolean> result = userMstService.doDelete(userId);
        return result;
    }

}