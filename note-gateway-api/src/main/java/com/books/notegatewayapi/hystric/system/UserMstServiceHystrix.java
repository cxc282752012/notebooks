package com.books.notegatewayapi.hystric.system;

import com.books.notebasecore.util.ServiceResult;
import com.books.notebasecore.util.SystemException;
import com.books.notegatewayapi.service.system.IUserMstService;
import com.books.notesystemservers.entity.UserMstEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component
public class UserMstServiceHystrix implements IUserMstService {

    @Override
    public ServiceResult<List<UserMstEntity>> findList(@RequestParam Map<String, Object> params)
            throws SystemException {
        throw new SystemException(" UserMst findList connection error");
    }

    @Override
    public ServiceResult<UserMstEntity> findInfo(@RequestParam Map<String, Object> params)
            throws SystemException {
        throw new SystemException(" UserMst findInfo connection error");
    }

    @Override
    public ServiceResult<Integer> doInsert(@RequestBody UserMstEntity userMstEntity)
            throws SystemException {
        throw new SystemException(" UserMst doInsert connection error");
    }

    @Override
    public ServiceResult<Integer> doUpdate(@RequestBody UserMstEntity userMstEntity)
            throws SystemException {
        throw new SystemException(" UserMst doUpdate connection error");
    }

    @Override
    public ServiceResult<Boolean> doDelete(@RequestParam("userId") int userId)
            throws SystemException {
        throw new SystemException(" UserMst doDelete connection error");
    }

}
