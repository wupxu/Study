package com.lexue.bp.admin.v1.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lexue.bp.admin.inf.domain.Statistic;
import com.lexue.bp.admin.inf.domain.StatisticFinalBehavior;
import com.lexue.bp.admin.inf.domain.UserMessageStatistic;
import com.lexue.bp.admin.inf.domain.req.StatisticRequest;
import com.lexue.bp.admin.inf.spec.StatisticServiceSpec;
import com.lexue.bp.admin.service.StatisticService;
import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.util.ResponseUtil;
/**
 * 需求统计
 * @author wpx
 *
 */
@RestController("statictisControllerV1")
public class StatisticController implements StatisticServiceSpec {
	
	@Autowired
	private StatisticService statisticService;

	@Override
	public ResponseDto<PageReply<Statistic>> queryGrantScore(@RequestBody StatisticRequest statisticRequest) {
		PageReply<Statistic> grantScore = statisticService.queryGrantScore(statisticRequest);
		return ResponseUtil.generateSuccessResponse(grantScore);
	}
	
	@Override
	public ResponseDto<PageReply<Statistic>> queryUnclaimedScore(@RequestBody StatisticRequest statisticRequest) {
		PageReply<Statistic> unclaimedScore = statisticService.queryUnclaimedScore(statisticRequest);
		return ResponseUtil.generateSuccessResponse(unclaimedScore);
	}
	
	@Override
	public ResponseDto<PageReply<Statistic>> queryUsedScore(@RequestBody StatisticRequest statisticRequest) {
		PageReply<Statistic> usedScore = statisticService.queryUsedScore(statisticRequest);
		return ResponseUtil.generateSuccessResponse(usedScore);
	}


	@Override
	public ResponseDto<PageReply<UserMessageStatistic>> queryScoreDetail(@RequestBody StatisticRequest statisticRequest) {
		PageReply<UserMessageStatistic> scoreDetail = statisticService.queryScoreDetail(statisticRequest);
		return ResponseUtil.generateSuccessResponse(scoreDetail);
	}

	@Override
	public ResponseDto<PageReply<StatisticFinalBehavior>> queryFinalBehavior(@RequestBody StatisticRequest statisticRequest) {
		PageReply<StatisticFinalBehavior> statisticFinalBehaviorList = statisticService.queryFinalBehavior(statisticRequest);
		return ResponseUtil.generateSuccessResponse(statisticFinalBehaviorList);
	}
	
}
