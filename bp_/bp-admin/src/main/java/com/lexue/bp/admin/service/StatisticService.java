package com.lexue.bp.admin.service;


import com.lexue.bp.admin.inf.domain.Statistic;
import com.lexue.bp.admin.inf.domain.StatisticFinalBehavior;
import com.lexue.bp.admin.inf.domain.UserMessageStatistic;
import com.lexue.bp.admin.inf.domain.req.StatisticRequest;
import com.lexue.bp.common.domain.PageReply;

/**
 * 统计需求接口(单日统计:用户待发放，已发放，已使用及其发放的数量)
 */
public interface StatisticService {

    /**
     * 发放统计
     */
    PageReply<Statistic> queryGrantScore(StatisticRequest statisticRequest);

    /**
     * 待发放统计
     */
    PageReply<Statistic> queryUnclaimedScore(StatisticRequest statisticRequest);

    /**
     * 已使用统计(购买商品：直播礼品等，状态是已使用的)
     */
    PageReply<Statistic> queryUsedScore(StatisticRequest statisticRequest);


    /**
     * 按用户统计,记录每个用户的乐豆发放和使用记录(已发放数量，待发放数量，已使用数量)
     */
    PageReply<UserMessageStatistic> queryScoreDetail(StatisticRequest statisticRequest);

    /**
     * 用户最后一次行为时间(已发放,待发放,已使用)
     */
    PageReply<StatisticFinalBehavior> queryFinalBehavior(StatisticRequest statisticRequest);


}
