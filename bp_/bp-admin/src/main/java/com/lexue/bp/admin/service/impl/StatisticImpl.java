package com.lexue.bp.admin.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lexue.bp.admin.inf.domain.Statistic;
import com.lexue.bp.admin.inf.domain.StatisticFinalBehavior;
import com.lexue.bp.admin.inf.domain.UserMessageStatistic;
import com.lexue.bp.admin.inf.domain.req.StatisticRequest;
import com.lexue.bp.admin.service.StatisticService;
import com.lexue.bp.admin.util.PageableUtil;
import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.entity.ProduceEntity;
import com.lexue.bp.common.enums.ProduceChannelEnums;
import com.lexue.bp.common.enums.ProduceStatusEnums;
import com.lexue.bp.common.repository.ProduceRepository;

@Service
public class StatisticImpl implements StatisticService {

    @Autowired
    private ProduceRepository produceRepository;

    @Override
    public PageReply<Statistic> queryGrantScore(StatisticRequest statisticRequest) {
        Pageable pageable = PageableUtil.generatePageable(statisticRequest.getPageAsk());
        List<ProduceEntity> produceEntityList = null;
        long count = 0;
        if (statisticRequest.getProduceChannel() == 0) {    //无渠道类型
            produceEntityList = produceRepository.findAllGrantScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(),
                    (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
            count = produceRepository.countAllGrantScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getModuleId());
        } else {
            produceEntityList = produceRepository.findAllGrantScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getProduceChannel(),
                    (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
            count = produceRepository.countAllGrantScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getProduceChannel(), statisticRequest.getModuleId());
        }

        PageReply<Statistic> result = new PageReply<>();

        List<Statistic> statisticList = new ArrayList<>();
        for (ProduceEntity produceEntity : produceEntityList) {
            Statistic statistic = new Statistic();
			statistic.setType(produceEntity.getProduceChannel());
            statistic.setDate(produceEntity.getEffectiveDate());
            statistic.setCountScore(produceEntity.getProduceScore());
            statisticList.add(statistic);
        }
        result.setDatas(statisticList);
        result.setTotalElements(count);
        return result;
    }

    @Override
    public PageReply<Statistic> queryUnclaimedScore(StatisticRequest statisticRequest) {
        Pageable pageable = PageableUtil.generatePageable(statisticRequest.getPageAsk());
        List<ProduceEntity> produceEntityList = null;
        long count = 0;
        if (statisticRequest.getProduceChannel() == 0) {
            produceEntityList = produceRepository.findAllUnclaimedScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(),
                    (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
            count = produceRepository.countAllUnclaimedScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getModuleId());
        } else {
            produceEntityList = produceRepository.findAllUnclaimedScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getProduceChannel(),
                    (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
            count = produceRepository.countAllUnclaimedScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getProduceChannel(), statisticRequest.getModuleId());
        }


        PageReply<Statistic> result = new PageReply<>();
        List<Statistic> statisticList = new ArrayList<>();
        for (ProduceEntity produceEntity : produceEntityList) {
            Statistic statistic = new Statistic();
            statistic.setType(produceEntity.getProduceChannel());         
            statistic.setDate(produceEntity.getCTime());
            statistic.setCountScore(produceEntity.getProduceScore());
            statisticList.add(statistic);
        }
        result.setDatas(statisticList);
        result.setTotalElements(count);
        return result;
    }


    @Override
    public PageReply<Statistic> queryUsedScore(StatisticRequest statisticRequest) {
        // 分页查询
        Pageable pageable = PageableUtil.generatePageable(statisticRequest.getPageAsk());
        List<ProduceEntity> produceEntityList = null;
        long count = 0;
        if (statisticRequest.getProduceChannel() == 0) {
            produceEntityList = produceRepository.findAllInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(),
                    (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
            count = produceRepository.countAllInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getModuleId());
        } else {
            produceEntityList = produceRepository.findAllInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getProduceChannel(),
                    (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
            count = produceRepository.countAllInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getProduceChannel(), statisticRequest.getModuleId());
        }
        PageReply<Statistic> result = new PageReply<>();
        List<Statistic> statisticList = new ArrayList<>();
        for (ProduceEntity produceEntity : produceEntityList) {
            Statistic statistic = new Statistic();
            statistic.setType(produceEntity.getProduceChannel());
            statistic.setDate(produceEntity.getInvalidDate());
            statistic.setCountScore(produceEntity.getProduceScore());
            statisticList.add(statistic);
        }
        result.setDatas(statisticList);
        result.setTotalElements(count);
        return result;
    }


    @Override
    public PageReply<UserMessageStatistic> queryScoreDetail(StatisticRequest statisticRequest) {

        List<ProduceEntity> produceEntityList = null;
        long count = 0;
        // 分页
        Pageable pageable = PageableUtil.generatePageable(statisticRequest.getPageAsk());
        if (statisticRequest.getStatus() == ProduceStatusEnums.UNUSED.getCode()) { //已发放
            if (statisticRequest.getUid() == 0) {
                produceEntityList = produceRepository.findAllByUserGrantScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(),
                        (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
                count = produceRepository.countAllByUserGrantScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getModuleId());
            } else {
                produceEntityList = produceRepository.findAllByUserGrantScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getUid(),
                        (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
                count = produceRepository.countAllByUserGrantScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getUid(), statisticRequest.getModuleId());
            }
        } else if (statisticRequest.getStatus() == ProduceStatusEnums.UNCLAIMED.getCode()) {//待领取
            if (statisticRequest.getUid() == 0) {
                produceEntityList = produceRepository.findAllByUserInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(),
                        (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
                count = produceRepository.countAllByUserInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getModuleId());
            } else {
                produceEntityList = produceRepository.findAllByUserUnclaimedScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getUid(),
                        (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
                count = produceRepository.countAllByUserUnclaimedScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getUid(), statisticRequest.getModuleId());
            }
        } else if (statisticRequest.getStatus() == ProduceStatusEnums.INVALID.getCode()) {//已失效
            if (statisticRequest.getUid() == 0) {
                produceEntityList = produceRepository.findAllByUserInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(),
                        (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
                count = produceRepository.countAllByUserInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getModuleId());
            } else {
                produceEntityList = produceRepository.findAllByUserInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getUid(),
                        (pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
                count = produceRepository.countAllByUserInvalidScore(statisticRequest.getStartDate(), statisticRequest.getEndDate(), statisticRequest.getUid(), statisticRequest.getModuleId());
            }
        }


        PageReply<UserMessageStatistic> stResult = new PageReply<>();
        List<UserMessageStatistic> returnResult = new ArrayList<UserMessageStatistic>();

        for (ProduceEntity produceEntity : produceEntityList) {
            UserMessageStatistic statistic = new UserMessageStatistic();
            statistic.setUid(produceEntity.getUid());
            if (statisticRequest.getStatus() == ProduceStatusEnums.UNUSED.getCode()) { //已发放
                statistic.setDate(produceEntity.getEffectiveDate());
            } else if (statisticRequest.getStatus() == ProduceStatusEnums.UNCLAIMED.getCode()) {//待领取
                statistic.setDate(produceEntity.getCTime());
            } else if (statisticRequest.getStatus() == ProduceStatusEnums.INVALID.getCode()) {//已失效
                statistic.setDate(produceEntity.getInvalidDate());
            }
            statistic.setType(produceEntity.getProduceChannel());
            statistic.setCountScore(produceEntity.getProduceScore());
            returnResult.add(statistic);
        }
        stResult.setDatas(returnResult);
        stResult.setTotalElements(count);
        return stResult;
    }


    @Override
    public PageReply<StatisticFinalBehavior> queryFinalBehavior(StatisticRequest statisticRequest) {
        //分页
        Pageable pageable = PageableUtil.generatePageable(statisticRequest.getPageAsk());
        List<ProduceEntity> produceEntityList = null;
        long count = 0;
        if (statisticRequest.getUid() > 0) {
            produceEntityList = produceRepository.findFinalBehavior(statisticRequest.getUid(), statisticRequest.getModuleId());
            count = produceEntityList.size();
        } else {
            produceEntityList = produceRepository.findFinalBehavior((pageable.getPageNumber()) * pageable.getPageSize(), pageable.getPageSize(), statisticRequest.getModuleId());
            count = produceRepository.countFinalBehavior(statisticRequest.getModuleId());
        }

        List<StatisticFinalBehavior> finalBehaviorList = new ArrayList<>();
        PageReply<StatisticFinalBehavior> sbPageReply = new PageReply<>();
        for (ProduceEntity produceEntity : produceEntityList) {
            StatisticFinalBehavior finalBehavior = new StatisticFinalBehavior();
			finalBehavior.setProduceChannel(produceEntity.getProduceChannel());
            finalBehavior.setUnclaimedDate(produceEntity.getCTime());
            finalBehavior.setUnUseDate(produceEntity.getEffectiveDate());
            finalBehavior.setUid(produceEntity.getUid());
            finalBehaviorList.add(finalBehavior);

        }
        sbPageReply.setDatas(finalBehaviorList);
        sbPageReply.setTotalElements(count);
        return sbPageReply;
    }


}
