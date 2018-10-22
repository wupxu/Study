package com.lexue.bp.admin.converter;

import java.util.Calendar;

import org.springframework.core.convert.converter.Converter;

import com.lexue.bp.admin.inf.domain.req.RuleRequest;
import com.lexue.bp.common.entity.RuleEntity;

public class RuleRequestToRuleEntity implements Converter<RuleRequest, RuleEntity> {

	@Override
	public RuleEntity convert(RuleRequest source) {
		RuleEntity dest = new RuleEntity();
		dest.setConditionValue(source.getConditionValue());
		dest.setMaxCount(source.getMaxCount());
		dest.setMaxScore(source.getMaxScore());
		dest.setModuleId(source.getModuleId());
		dest.setResultValue(source.getResultValue());
		dest.setRuleId(source.getProduceChannelEnums().getCode());
		dest.setRuleName(source.getProduceChannelEnums().getName());
		dest.setStatus(source.getRuleStatusEnums().getCode());
		dest.setUpdateTime(Calendar.getInstance().getTimeInMillis());
	
		//签到去除
		/*if (!CollectionUtils.isEmpty(source.getRuleSignList())) {
			dest.setRuleSignList(new ArrayList<RuleSignEntity>());
			for (RuleSign ruleSign:source.getRuleSignList()) {
				RuleSignEntity ruleSignEntity = new RuleSignEntity();
				ruleSignEntity.setDays(ruleSign.getDays());
				ruleSignEntity.setRuleEntity(dest);
				ruleSignEntity.setScore(ruleSign.getScore());
				ruleSignEntity.setType(ruleSign.getRuleSignTypeEnums().getCode());
				dest.getRuleSignList().add(ruleSignEntity);
			}
		}*/
		return dest;
	}

}
