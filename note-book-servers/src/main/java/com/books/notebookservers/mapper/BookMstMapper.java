package com.books.notebookservers.mapper;

import com.books.notebookservers.entity.BookMstEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookMstMapper {

    List<BookMstEntity> findList(Map<String, Object> params);

    BookMstEntity findInfo(Map<String, Object> params);

    Integer doInsert(BookMstEntity bookMstEntity);

    Integer doUpdate(BookMstEntity bookMstEntity);

    Integer doDelete(int bookId);
}