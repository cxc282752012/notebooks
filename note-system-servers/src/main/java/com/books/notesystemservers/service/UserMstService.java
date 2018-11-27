package com.books.notesystemservers.service;


import com.books.notebasecore.util.BusinessException;
import com.books.notebasecore.util.PagerInfo;
import com.books.notebasecore.util.ServiceResult;
import com.books.notesystemservers.entity.UserMstEntity;
import com.books.notesystemservers.mapper.UserMstMapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Service("userMstService")
public class UserMstService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserMstService.class);

    @Autowired
    private UserMstMapper userMstMapper;

    public ServiceResult<List<UserMstEntity>> findList(Map<String,Object> params, PagerInfo<?> pagerInfo){
        Assert.notNull(this.userMstMapper, "Property 'userMstMapper' is required.");
        ServiceResult<List<UserMstEntity>> result = new ServiceResult<List<UserMstEntity>>();
        try {
            if (pagerInfo != null) {
                PageHelper.startPage(pagerInfo.getPageIndex(), pagerInfo.getPageSize());
            }
            result.setResult(this.userMstMapper.findList(params));
        }catch (BusinessException be){
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("Unknown error!");
            LOGGER.error("[UserMstService][findList]：query findList occur exception", e);
        }
        return result;
    }

    public ServiceResult<UserMstEntity> findInfo(Map<String, Object> params) {
        Assert.notNull(this.userMstMapper, "Property 'userMstMapper' is required.");
        ServiceResult<UserMstEntity> result = new ServiceResult<UserMstEntity>();
        try {
            result.setResult(this.userMstMapper.findInfo(params));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Unknown error!");
            LOGGER.error("[UserMstService][findInfo]：query findInfo by param occur exception", e);
        }
        return result;
    }

    public ServiceResult<Integer> doInsert(UserMstEntity userMstEntity) {
        Assert.notNull(this.userMstMapper, "Property 'userMstMapper' is required.");
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        int id = 0;
        try {
            int success = this.userMstMapper.doInsert(userMstEntity);
            if (success > 0) {
                id = userMstEntity.getUserId();
            }
            result.setResult(id);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Unknown error!");
            LOGGER.error("[UserMstService][doInsert]：query doInsert by id occur exception", e);
        }
        return result;
    }

    public ServiceResult<Integer> doUpdate(UserMstEntity userMstEntity) {
        Assert.notNull(this.userMstMapper, "Property 'userMstMapper' is required.");
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        Integer id = 0;
        try {
            Integer success = this.userMstMapper.doUpdate(userMstEntity);
            if (success > 0) {
                id = userMstEntity.getUserId();
            }
            result.setResult(id);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Unknown error!");
            LOGGER.error("[UserMstService][doUpdate]：query doInsert by id occur exception", e);
        }
        return result;
    }

    public ServiceResult<Boolean> doDelete(int userId) {
        Assert.notNull(this.userMstMapper, "Property 'userMstMapper' is required.");
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        boolean flag = false;
        try {
            int id = this.userMstMapper.doDelete(userId);
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
            LOGGER.error("[UserMstService][doDelete]：query doDelete by id occur exception", e);
        }
        return result;
    }
}