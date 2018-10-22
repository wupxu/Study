package com.lexue.bp.web.service;

import java.util.List;

import com.lexue.bp.common.entity.NoticeEntity;
import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.entity.StrategyEntity;

/**
 * 配置(公告，渠道说明..)
 * @author wpx
 *
 */
public interface ConfigService {

	/**查询公告内容*/
	NoticeEntity findConfigureContent(int moduleId);

	/**查询乐豆用途信息*/
	StrategyEntity findStrategy(int moduleId);

	/**查询(如何获取乐豆)*/
	List<RuleEntity> findBeanMethod(int moduleId);
}
