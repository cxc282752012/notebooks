package com.books.notegatewayapi;

import com.books.notebasecore.util.ServiceResult;
import com.books.notegatewayapi.service.system.IUserMstService;
import com.books.notesystemservers.entity.UserMstEntity;
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
public class NoteGatewayApiApplicationTests {

	@Autowired
	private IUserMstService userMstService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void userFindList(){
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			ServiceResult<List<UserMstEntity>> result = userMstService.findList(params);
			List<UserMstEntity> userMstEntities = result.getResult();
			for (int i = 0; i < userMstEntities.size(); i++) {
				System.out.println("userName:" + userMstEntities.get(i).getUserName());
				System.out.println("userPhone:" + userMstEntities.get(i).getUserPhone());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
