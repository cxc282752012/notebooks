package com.books.notebookservers.controller;


import com.books.notebasecore.constants.BaseErrorConstants;
import com.books.notebasecore.util.*;
import com.books.notebookservers.entity.BookMstEntity;
import com.books.notebookservers.service.BookMstService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookMstController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BookMstController.class);

    @Autowired
    private BookMstService bookMstService;

    /**
     * find list
     * @param request
     * @return
     */
    @GetMapping(value = "/book/findList")
    public ServiceResult<List<BookMstEntity>> findList(HttpServletRequest request) {
        ServiceResult<List<BookMstEntity>> result = new ServiceResult<List<BookMstEntity>>();
        PagerInfo<?> pagerInfo = null;
        Map<String, Object> params = null;
        Integer userId = ConvertUtil.toInt(request.getParameter("userId"),0);
        try {
            if (!StringUtil.isEmpty(request.getParameter("pageSize"))) {
                pagerInfo = BaseWebUtil.getPageInfoForPC(request);
            }
            params = new HashMap<String, Object>();
            if(userId > 0){
                params.put("userId",userId);
            }
            result = bookMstService.findList(params, pagerInfo);
        } catch (Exception e) {
            LOGGER.error("[BookMstController][findList]：query findList occur exception", e);
            result.setError(BaseErrorConstants.ERROR_CODE_SERVICE_CONTROLLER, "Find inbound order list error.");
        }

        return result;
    }

    /**
     * find info
     * @param request
     * @return
     */
    @GetMapping(value = "/book/findInfo")
    public ServiceResult<BookMstEntity> findInfo(HttpServletRequest request) {
        ServiceResult<BookMstEntity> result = new ServiceResult<BookMstEntity>();
        Map<String, Object> params = null;
        Integer bookId = ConvertUtil.toInt(request.getParameter("bookId"), 0);
        try {
            params = new HashMap<String, Object>();
            if(bookId > 0){
                params.put("bookId",bookId);
            }
            result = bookMstService.findInfo(params);
        } catch (Exception e) {
            LOGGER.error("[BookMstController][findInfo]：query findInfo occur exception", e);
            result.setError(BaseErrorConstants.ERROR_CODE_SERVICE_CONTROLLER, "Find inbound order error.");
        }
        return result;
    }

    /**
     * do insert
     * @param bookMstEntity
     * @return
     */
    @PostMapping(value = "/book/doInsert")
    public ServiceResult<Integer> doInsert(@RequestBody BookMstEntity bookMstEntity) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result = bookMstService.doInsert(bookMstEntity);
        } catch (Exception e) {
            LOGGER.error("[BookMstController][doInsert]：Insert occur exception", e);
            result.setError(BaseErrorConstants.ERROR_CODE_SERVICE_CONTROLLER, "Insert error.");
        }
        return result;
    }

    /**
     * do update
     * @param bookMstEntity
     * @return
     */
    @PostMapping(value = "/book/doUpdate")
    public ServiceResult<Integer> doUpdate(@RequestBody BookMstEntity bookMstEntity) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result = bookMstService.doUpdate(bookMstEntity);
        } catch (Exception e) {
            LOGGER.error("[BookMstController][doUpdate]：Update occur exception", e);
            result.setError(BaseErrorConstants.ERROR_CODE_SERVICE_CONTROLLER, "Update error.");
        }
        return result;
    }

    /**
     * do delete
     * @param bookId
     * @return
     */
    @PostMapping(value = "/book/doDelete")
    public ServiceResult<Boolean> doDelete(@RequestParam int bookId) {
        ServiceResult<Boolean> result = bookMstService.doDelete(bookId);
        return result;
    }

}