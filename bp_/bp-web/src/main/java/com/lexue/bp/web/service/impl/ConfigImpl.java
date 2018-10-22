package com.lexue.bp.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lexue.bp.common.entity.NoticeEntity;
import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.entity.StrategyEntity;
import com.lexue.bp.common.enums.NoticeStatusEnums;
import com.lexue.bp.common.repository.NoticeRepository;
import com.lexue.bp.common.repository.StrategyRepository;
import com.lexue.bp.web.service.ConfigService;
import com.lexue.bp.web.service.WebRuleService;

@Service
public class ConfigImpl implements ConfigService {
	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private StrategyRepository strategyRepository;
	@Autowired
	private WebRuleService ruleService;
	
	@Override
	public NoticeEntity findConfigureContent(int moduleId) {
		NoticeEntity noticeEntity = noticeRepository.findByStatusAndModuleId(NoticeStatusEnums.OPEN.getCode(),moduleId);
		return noticeEntity;
	}
	
	@Override
	public StrategyEntity findStrategy(int moduleId) {
		StrategyEntity strategyEntity = strategyRepository.findByModuleId(moduleId);
		return strategyEntity;
	}

	@Override
	public List<RuleEntity> findBeanMethod(int moduleId) {
		List<RuleEntity> allRule = ruleService.getRuleByModuleId(moduleId);
		return allRule;
	}

}
