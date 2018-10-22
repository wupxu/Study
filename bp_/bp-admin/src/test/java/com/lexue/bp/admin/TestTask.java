package com.lexue.bp.admin;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lexue.bp.admin.service.TaskService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={BPAdminApplication.class})
public class TestTask {
	@Autowired
	private TaskService taskService;
	@Test
	public void TestValidTask() {
		Date date = DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH);
		taskService.invalid(date.getTime());
	}
	
	@Test
	public void TestWatchTask() {
		taskService.watch("2018-10-15");
	}
	
}
