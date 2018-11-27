package com.books.notegatewayapi.service.book;

import com.books.notebasecore.util.ServiceResult;
import com.books.notebasecore.util.SystemException;
import com.books.notebookservers.entity.BookMstEntity;
import com.books.notegatewayapi.hystric.book.BookMstServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "note-book-servers",fallback = BookMstServiceHystrix.class)
public interface IBookMstService {

    /**
     * user findList
     *
     * @param params
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = "/book/findList", method = RequestMethod.GET)
    ServiceResult<List<BookMstEntity>> findList(@RequestParam Map<String, Object> params)
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
    @RequestMapping(value = "/book/findInfo", method = RequestMethod.GET)
    ServiceResult<BookMstEntity> findInfo(@RequestParam Map<String, Object> params) throws SystemException;

    /**
     * user doInsert
     *
     * @param bookMstEntity
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/book/doInsert"}, method = {RequestMethod.POST})
    ServiceResult<Integer> doInsert(@RequestBody BookMstEntity bookMstEntity) throws SystemException;

    /**
     * user doUpdate
     *
     * @param bookMstEntity
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/book/doUpdate"}, method = {RequestMethod.POST})
    ServiceResult<Integer> doUpdate(@RequestBody BookMstEntity bookMstEntity) throws SystemException;

    /**
     * user doDelete
     *
     * @param bookId
     * @return
     * @throws SystemException
     * @Description
     * @author chen
     */
    @RequestMapping(value = {"/book/doDelete"}, method = {RequestMethod.POST})
    ServiceResult<Boolean> doDelete(@RequestParam("bookId") int bookId) throws SystemException;
}
