package com.books.notegatewayapi.hystric.book;

import com.books.notebasecore.util.ServiceResult;
import com.books.notebasecore.util.SystemException;
import com.books.notebookservers.entity.BookMstEntity;
import com.books.notegatewayapi.service.book.IBookMstService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component
public class BookMstServiceHystrix implements IBookMstService {

    @Override
    public ServiceResult<List<BookMstEntity>> findList(@RequestParam Map<String, Object> params)
            throws SystemException {
        throw new SystemException("BookMst findList connection error");
    }

    @Override
    public ServiceResult<BookMstEntity> findInfo(@RequestParam Map<String, Object> params)
            throws SystemException {
        throw new SystemException("BookMst findInfo connection error");
    }

    @Override
    public ServiceResult<Integer> doInsert(@RequestBody BookMstEntity bookMstEntity)
            throws SystemException {
        throw new SystemException("BookMst doInsert connection error");
    }

    @Override
    public ServiceResult<Integer> doUpdate(@RequestBody BookMstEntity bookMstEntity)
            throws SystemException {
        throw new SystemException("BookMst doUpdate connection error");
    }

    @Override
    public ServiceResult<Boolean> doDelete(@RequestParam("bookId") int bookId)
            throws SystemException {
        throw new SystemException("BookMst doDelete connection error");
    }

}
