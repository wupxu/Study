import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lexue.bp.admin.BPAdminApplication;
import com.lexue.bp.admin.service.TaskService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BPAdminApplication.class)
public class Test {

	@Autowired
	private TaskService taskService;
	@org.junit.Test
	public  void test() {
//		taskService.watch("2018-10-18");
//		taskService.order("2018-10-14");
		taskService.post("2018-10-18");
		taskService.postHot("2018-10-20");
	}
	

}