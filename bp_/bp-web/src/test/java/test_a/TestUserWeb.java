package test_a;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.web.BpWebApplication;
import com.lexue.bp.web.inf.spec.UserWebServiceSpec;
import com.lexue.bp.web.service.UserProduceService;
import com.lexue.bp.web.service.WebRuleService;
import com.lexue.bp.web.v1.controller.UserWebController;



@RunWith(SpringRunner.class)
@SpringBootTest(classes={BpWebApplication.class})
public class TestUserWeb {
	
	@Autowired
	private UserProduceService userProduceService;
	@Autowired
	private UserWebServiceSpec userWebServiceSpec;
	@Autowired
	private UserWebController userWebController;
	@Autowired
	private WebRuleService webRuleService;

	
	@Test
	public void test2() {
		userWebController.userUnclaimedProduce(18700012, 4097);

	}

}