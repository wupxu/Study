package com.lexue.bp.common.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lexue.bp.common.entity.RuleEntity;

import java.util.List;

public interface RuleRepository extends JpaRepository<RuleEntity, Integer> {

	List<RuleEntity> findByModuleId(int moduleId, Sort sort);

	RuleEntity findByRuleIdAndModuleId(int ruleId, int moduleId);

	List<RuleEntity> findByRuleId(int ruleId);

	RuleEntity findByRuleIdAndModuleIdAndStatus(int ruleId, int moduleId, byte status);

	List<RuleEntity> findByModuleIdAndStatus(int moduleId, byte status, Sort sort);

}
