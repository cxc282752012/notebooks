package com.books.notebookservers.service;

import com.books.notebasecore.util.BusinessException;
import com.books.notebasecore.util.PagerInfo;
import com.books.notebasecore.util.ServiceResult;
import com.books.notebookservers.entity.BookMstEntity;
import com.books.notebookservers.mapper.BookMstMapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Service("bookMstService")
public class BookMstService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BookMstService.class);

    @Autowired
    private BookMstMapper bookMstMapper;

    public ServiceResult<List<BookMstEntity>> findList(Map<String,Object> params, PagerInfo<?> pagerInfo){
        Assert.notNull(this.bookMstMapper, "Property 'bookMstMapper' is required.");
        ServiceResult<List<BookMstEntity>> result = new ServiceResult<List<BookMstEntity>>();
        try {
            if (pagerInfo != null) {
                PageHelper.startPage(pagerInfo.getPageIndex(), pagerInfo.getPageSize());
            }
            result.setResult(this.bookMstMapper.findList(params));
        }catch (BusinessException be){
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("Unknown error!");
            LOGGER.error("[BookMstService][findList]：query findList occur exception", e);
        }
        return result;
    }

    public ServiceResult<BookMstEntity> findInfo(Map<String, Object> params) {
        Assert.notNull(this.bookMstMapper, "Property 'bookMstMapper' is required.");
        ServiceResult<BookMstEntity> result = new ServiceResult<BookMstEntity>();
        try {
            result.setResult(this.bookMstMapper.findInfo(params));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Unknown error!");
            LOGGER.error("[BookMstService][findInfo]：query findInfo by param occur exception", e);
        }
        return result;
    }

    public ServiceResult<Integer> doInsert(BookMstEntity bookMstEntity) {
        Assert.notNull(this.bookMstMapper, "Property 'bookMstMapper' is required.");
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        int id = 0;
        try {
            int success = this.bookMstMapper.doInsert(bookMstEntity);
            if (success > 0) {
                id = bookMstEntity.getBookId();
            }
            result.setResult(id);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Unknown error!");
            LOGGER.error("[BookMstService][doInsert]：query doInsert by id occur exception", e);
        }
        return result;
    }

    public ServiceResult<Integer> doUpdate(BookMstEntity bookMstEntity) {
        Assert.notNull(this.bookMstMapper, "Property 'bookMstMapper' is required.");
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        Integer id = 0;
        try {
            Integer success = this.bookMstMapper.doUpdate(bookMstEntity);
            if (success > 0) {
                id = bookMstEntity.getBookId();
            }
            result.setResult(id);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Unknown error!");
            LOGGER.error("[BookMstService][doUpdate]：query doInsert by id occur exception", e);
        }
        return result;
    }

    public ServiceResult<Boolean> doDelete(int userId) {
        Assert.notNull(this.bookMstMapper, "Property 'bookMstMapper' is required.");
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        boolean flag = false;
        try {
            int id = this.bookMstMapper.doDelete(userId);
            if (id > 0) {
                flag = true;
            }
            result.setResult(flag);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Unknown error!");
            LOGGER.error("[BookMstService][doDelete]：query doDelete by id occur exception", e);
        }
        return result;
    }
}