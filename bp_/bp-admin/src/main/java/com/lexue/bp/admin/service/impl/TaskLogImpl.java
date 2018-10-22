package com.lexue.bp.admin.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lexue.bp.admin.service.TaskLogService;
import com.lexue.bp.common.entity.TaskExecEntity;
import com.lexue.bp.common.exception.BizException;
import com.lexue.bp.common.repository.TaskExecRepository;
import com.lexue.bp.common.util.CommonUtil;


@Service
public class TaskLogImpl implements TaskLogService {
	
	@Autowired
	private TaskExecRepository taskExecRepository;

	@Override
	public TaskExecEntity generateExceptionTaskExecEntity(BizException e, String type) {
		TaskExecEntity result = new TaskExecEntity();
		result.setCTime(Calendar.getInstance().getTime());
		result.setId(CommonUtil.generateId());
		result.setSuccessRecord(0);
		result.setType(type);
		result.setRemark(e.getMessage());
		return result;
	}
	
	@Override
	public TaskExecEntity generateSuccessTaskExecEntity(int totalRecord, List<String> errorIds, String type,Date startTaskDate) {
		TaskExecEntity result = new TaskExecEntity();
		result.setCTime(Calendar.getInstance().getTime());
		result.setId(CommonUtil.generateId());
		result.setStartDate(startTaskDate);
		result.setEndDate(Calendar.getInstance().getTime());
		result.setFailureRecord(errorIds.size());
		result.setIds(StringUtils.join(errorIds.toArray(new String[0]), ","));
		result.setTotalRecord(totalRecord);
		result.setSuccessRecord(totalRecord-result.getFailureRecord());
		result.setType(type);
		result.setRemark("");
		return result;
	}

	@Override
	public TaskExecEntity save(TaskExecEntity tee) {
		return taskExecRepository.save(tee);
	}

	
	

}
