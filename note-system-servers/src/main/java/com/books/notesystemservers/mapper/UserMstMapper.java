package com.books.notesystemservers.mapper;


import com.books.notesystemservers.entity.UserMstEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMstMapper {

    List<UserMstEntity> findList(Map<String, Object> params);

    UserMstEntity findInfo(Map<String, Object> params);

    Integer doInsert(UserMstEntity userMstEntity);

    Integer doUpdate(UserMstEntity userMstEntity);

    Integer doDelete(int userId);
}