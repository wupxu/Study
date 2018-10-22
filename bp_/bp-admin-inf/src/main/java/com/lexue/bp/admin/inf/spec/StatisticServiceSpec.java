package com.lexue.bp.admin.inf.spec;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lexue.bp.admin.inf.domain.Statistic;
import com.lexue.bp.admin.inf.domain.StatisticFinalBehavior;
import com.lexue.bp.admin.inf.domain.UserMessageStatistic;
import com.lexue.bp.admin.inf.domain.req.StatisticRequest;
import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.ResponseDto;

@FeignClient(name="bp-admin-${platform_type}")
public interface StatisticServiceSpec {
	
	/**发放统计*/
	@PostMapping(path = "/bp/admin/v1/statistic/queryGrantScore")
	public ResponseDto<PageReply<Statistic>> queryGrantScore(@RequestBody StatisticRequest statisticRequest);
	
	/**待发放统计*/
	@PostMapping(path = "/bp/admin/v1/statistic/queryUnclaimedScore")
	public ResponseDto<PageReply<Statistic>> queryUnclaimedScore(@RequestBody StatisticRequest statisticRequest);
	
	/**已失效*/
	@PostMapping(path = "/bp/admin/v1/statistic/queryUsedScore")
	public ResponseDto<PageReply<Statistic>> queryUsedScore(@RequestBody StatisticRequest statisticRequest);

	/**按用户统计,记录每个用户的乐豆发放和使用记录(已发放数量，待发放数量，已使用数量)*/
	@PostMapping(path = "/bp/admin/v1/statistic/queryScoreDetail")
	public ResponseDto<PageReply<UserMessageStatistic>> queryScoreDetail(@RequestBody StatisticRequest statisticRequest);
	
	/**最后一次行为时间*/
	@PostMapping(path = "/bp/admin/v1/statistic/queryFinalBehavior")
	public ResponseDto<PageReply<StatisticFinalBehavior>> queryFinalBehavior(@RequestBody StatisticRequest statisticRequest);
}
