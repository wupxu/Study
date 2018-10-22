package com.lexue.bp.admin.service;

import java.util.Date;
import java.util.List;

import com.lexue.bp.common.entity.TaskExecEntity;
import com.lexue.bp.common.exception.BizException;

public interface TaskLogService {

	TaskExecEntity generateExceptionTaskExecEntity(BizException e,String type);
	
	TaskExecEntity generateSuccessTaskExecEntity(int totalRecord,List<String> errorIds,String type,Date startTaskDate);
	
	TaskExecEntity save(TaskExecEntity tee);
}
