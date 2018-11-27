package com.books.notegatewayapi.controller;

import com.books.notebasecore.constants.BaseErrorConstants;
import com.books.notebasecore.util.*;
import com.books.notebookservers.entity.BookMstEntity;
import com.books.notegatewayapi.service.book.IBookMstService;
import com.books.notegatewayapi.service.system.IUserMstService;
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
@RequestMapping("/book")
public class BookMstController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BookMstController.class);

    @Autowired
    private IBookMstService bookMstService;

    @RequestMapping(value = {"/book/findList"}, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpJsonResult<Hashtable<String, Object>> findList(HttpServletRequest request) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        List<BookMstEntity> bookMstList = null;
        PagerInfo<?> pagerInfo = null;
        Map<String, Object> params = null;
        try {
            if (!StringUtil.isEmpty(request.getParameter("pageSize"))) {
                pagerInfo = BaseWebUtil.getPageInfoForPC(request);
            }
            params = new HashMap<String, Object>();
            if (pagerInfo != null) {
                params.put("pageSize", pagerInfo.getPageSize());
                params.put("pageIndex", pagerInfo.getPageIndex());
            }
            ServiceResult<List<BookMstEntity>> bookMstResult = bookMstService.findList(params);
            pagerInfo = bookMstResult.getPager();
            if (bookMstResult.getSuccess() && bookMstResult.getResult() != null
                    && bookMstResult.getResult().size() > 0) {
                bookMstList = bookMstResult.getResult();
                returnTable.put("bookMstList", bookMstList);
            }
            returnTable.put("pagerInfo", pagerInfo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[Note Gateway API][BookMstController][findList] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Find bookMstList error.");
        }
        return result;
    }

    @RequestMapping(value = {"/book/findInfo"}, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpJsonResult<Hashtable<String, Object>> findInfo(HttpServletRequest request) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        BookMstEntity bookMstEntity = null;
        Map<String, Object> params = null;
        int userId = ConvertUtil.toInt(request.getParameter("userId"), 0);
        try {
            params = new HashMap<String, Object>();
            if (userId > 0) {
                params.put("userId", userId);
            }
            ServiceResult<BookMstEntity> bookMstResult = bookMstService.findInfo(params);
            if (bookMstResult.getSuccess() && bookMstResult.getResult() != null) {
                bookMstEntity = bookMstResult.getResult();
                returnTable.put("bookMstEntity", bookMstEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[Note Gateway API][BookMstController][findInfo] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Find bookMstEntity error.");
        }
        return result;
    }

    @PostMapping(value = "/book/doInsert")
    public HttpJsonResult<Hashtable<String, Object>> doInsert(
            @RequestBody BookMstEntity bookMstEntity) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        try {
            ServiceResult<Integer> bookMstResult = bookMstService.doInsert(bookMstEntity);
            if (bookMstResult.getSuccess() && bookMstResult.getResult() != null) {
                returnTable.put("id", bookMstResult.getResult());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[Note Gateway API][BookMstController][doInsert] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Insert bookMstEntity error.");
        }
        return result;
    }

    @PostMapping(value = "/book/doUpdate")
    public HttpJsonResult<Hashtable<String, Object>> doUpdate(
            @RequestBody BookMstEntity bookMstEntity) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        try {
            ServiceResult<Integer> bookMstResult = bookMstService.doUpdate(bookMstEntity);
            if (bookMstResult.getSuccess() && bookMstResult.getResult() != null) {
                returnTable.put("id", bookMstResult.getResult());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[Note Gateway API][BookMstController][doUpdate] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Update bookMstEntity error.");
        }
        return result;
    }

    @PostMapping(value = "/book/doDelete")
    public HttpJsonResult<Hashtable<String, Object>> doDelete(@RequestParam int bookId) {
        Hashtable<String, Object> returnTable = new Hashtable<String, Object>();
        HttpJsonResult<Hashtable<String, Object>> result = new HttpJsonResult<Hashtable<String, Object>>(returnTable);
        Boolean flag = false;
        try {
            ServiceResult<Boolean> bookMstResult = bookMstService.doDelete(bookId);
            if (bookMstResult.getSuccess() && bookMstResult.getResult() != null) {
                flag = bookMstResult.getResult();
            }
            returnTable.put("deleteFlag", flag);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("[Note Gateway API][BookMstController][doDelete] failed!", e);
            result.setError(BaseErrorConstants.ERROR_CODE_API_CONTROLLER, "Delete bookMstEntity error.");
        }
        return result;
    }
}
