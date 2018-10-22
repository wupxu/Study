package com.lexue.bp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lexue.bp.common.service.UserMongoService;
import com.lexue.bp.web.BpWebApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={BpWebApplication.class})
public class TestUserMongoService {
	
	@Autowired
	private UserMongoService userMongoService;

	@Test
	public void addUnclaimedScore() {
		userMongoService.addUnclaimedScore(1400184, 8193, 1, 12);
	}
	
	@Test
	public void clearUnclaimedScore() {
		userMongoService.clearUnclaimedScore(1400184, 8193, 4);//再次运行没有影响
	}

}
