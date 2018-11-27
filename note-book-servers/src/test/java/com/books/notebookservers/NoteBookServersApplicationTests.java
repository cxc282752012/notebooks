package com.books.notebookservers;

import com.books.notebasecore.util.PagerInfo;
import com.books.notebasecore.util.ServiceResult;
import com.books.notebookservers.entity.BookMstEntity;
import com.books.notebookservers.service.BookMstService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteBookServersApplicationTests {

    @Autowired
    private BookMstService bookMstService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void userFindList(){
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            ServiceResult<List<BookMstEntity>> result = bookMstService.findList(params,
                    new PagerInfo<BookMstEntity>(1, 10));
            List<BookMstEntity> bookMstEntities = result.getResult();
            for (int i = 0; i < bookMstEntities.size(); i++) {
                System.out.println("Notes:" + bookMstEntities.get(i).getNotes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
