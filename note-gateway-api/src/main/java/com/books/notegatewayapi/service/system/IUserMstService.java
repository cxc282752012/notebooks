package com.books.notegatewayapi.service.system;

import com.books.notebasecore.util.ServiceResult;
import com.books.notebasecore.util.SystemException;
import com.books.notegatewayapi.hystric.system.UserMstServiceHystrix;
import com.books.notesystemservers.entity.UserMstEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "note-system-servers", fallback = UserMstServiceHystrix.class)
public interface IUserMstService {

    /**
     * user findList
     *
     * @param params
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = "/user/findList", method = RequestMethod.GET)
    ServiceResult<List<UserMstEntity>> findList(@RequestParam Map<String, Object> params)
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
    @RequestMapping(value = "/user/findInfo", method = RequestMethod.GET)
    ServiceResult<UserMstEntity> findInfo(@RequestParam Map<String, Object> params) throws SystemException;

    /**
     * user doInsert
     *
     * @param userMstEntity
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/user/doInsert"}, method = {RequestMethod.POST})
    ServiceResult<Integer> doInsert(@RequestBody UserMstEntity userMstEntity) throws SystemException;

    /**
     * user doUpdate
     *
     * @param userMstEntity
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/user/doUpdate"}, method = {RequestMethod.POST})
    ServiceResult<Integer> doUpdate(@RequestBody UserMstEntity userMstEntity) throws SystemException;

    /**
     * user doDelete
     *
     * @param userId
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/user/doDelete"}, method = {RequestMethod.POST})
    ServiceResult<Boolean> doDelete(@RequestParam("userId") int userId) throws SystemException;
}
