package com.books.noteadminribbon.service.system;

import com.books.notebasecore.util.HttpJsonResult;
import com.books.notebasecore.util.ServiceResult;
import com.books.notebasecore.util.SystemException;
import com.books.notesystemservers.entity.UserMstEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@FeignClient(value = "note-gateway-api")
public interface UserMstAPI {

    /**
     * user findList
     *
     * @param params
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = "/system/user/findList", method = RequestMethod.GET)
    HttpJsonResult<Hashtable<String, Object>> findList(@RequestParam Map<String, Object> params)
            throws SystemException;

    /**
     * user findInfo
     *
     * @param params
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = "/system/user/findInfo", method = RequestMethod.GET)
    HttpJsonResult<Hashtable<String, Object>> findInfo(@RequestParam Map<String, Object> params) throws SystemException;

    /**
     * user doInsert
     *
     * @param userMstEntity
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/system/user/doInsert"}, method = {RequestMethod.POST})
    HttpJsonResult<Hashtable<String, Object>> doInsert(@RequestBody UserMstEntity userMstEntity) throws SystemException;

    /**
     * user doUpdate
     *
     * @param userMstEntity
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/system/user/doUpdate"}, method = {RequestMethod.POST})
    HttpJsonResult<Hashtable<String, Object>> doUpdate(@RequestBody UserMstEntity userMstEntity) throws SystemException;

    /**
     * user doDelete
     *
     * @param userId
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/system/user/doDelete"}, method = {RequestMethod.POST})
    HttpJsonResult<Hashtable<String, Object>> doDelete(@RequestParam("userId") int userId) throws SystemException;

    /**
     * login
     * @param params
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/system/user/login", method = RequestMethod.GET)
    HttpJsonResult<Hashtable<String, Object>> login(@RequestParam Map<String, Object> params) throws SystemException;
}
