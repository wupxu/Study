package com.lexue.bp.admin.service;

import com.lexue.bp.common.entity.DataOrderEntity;
import com.lexue.bp.common.entity.DataPostEntity;
import com.lexue.bp.common.entity.DataPostHotEntity;
import com.lexue.bp.common.entity.DataRefundEntity;
import com.lexue.bp.common.entity.DataShareEntity;
import com.lexue.bp.common.entity.DataWatchEntity;
import com.lexue.bp.common.entity.RuleEntity;

public interface TaskBusinessService {

	void Order(long sDate,long eDate,DataOrderEntity order,RuleEntity ruleEntity);
	
	@Deprecated
	void Refund(DataRefundEntity dataRefundEntity,RuleEntity ruleEntity);
	
	void Refund(DataRefundEntity dataRefundEntity,int days);
	
	void post(DataPostEntity order,RuleEntity ruleEntity);

	void share(DataShareEntity dataShareEntity, RuleEntity ruleEntity);

	void watch(DataWatchEntity dataWatchEntity, RuleEntity ruleEntity);

	void postHot(DataPostHotEntity postHotEntity, RuleEntity ruleEntity);

	void invalid(long startTime);
}
