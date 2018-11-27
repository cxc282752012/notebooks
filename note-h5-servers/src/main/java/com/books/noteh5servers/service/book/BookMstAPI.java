package com.books.noteh5servers.service.book;

import com.books.notebasecore.util.HttpJsonResult;
import com.books.notebookservers.entity.BookMstEntity;
import org.omg.CORBA.SystemException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Hashtable;
import java.util.Map;

@FeignClient(value = "note-gateway-api")
public interface BookMstAPI {

    /**
     * user findList
     *
     * @param params
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = "/book/book/findList", method = RequestMethod.GET)
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
    @RequestMapping(value = "/book/book/findInfo", method = RequestMethod.GET)
    HttpJsonResult<Hashtable<String, Object>> findInfo(@RequestParam Map<String, Object> params) throws SystemException;

    /**
     * user doInsert
     *
     * @param bookMstEntity
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/book/book/doInsert"}, method = {RequestMethod.POST})
    HttpJsonResult<Hashtable<String, Object>> doInsert(@RequestBody BookMstEntity bookMstEntity) throws SystemException;

    /**
     * user doUpdate
     *
     * @param bookMstEntity
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/book/book/doUpdate"}, method = {RequestMethod.POST})
    HttpJsonResult<Hashtable<String, Object>> doUpdate(@RequestBody BookMstEntity bookMstEntity) throws SystemException;

    /**
     * user doDelete
     *
     * @param bookId
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/book/book/doDelete"}, method = {RequestMethod.POST})
    HttpJsonResult<Hashtable<String, Object>> doDelete(@RequestParam("bookId") int bookId) throws SystemException;
}
