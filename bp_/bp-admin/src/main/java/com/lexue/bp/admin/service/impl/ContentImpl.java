package com.lexue.bp.admin.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.lexue.bp.admin.inf.domain.res.NoticeResponse;
import com.lexue.bp.admin.inf.domain.res.StrategyResponse;
import com.lexue.bp.common.enums.ContentEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lexue.bp.admin.inf.domain.req.NoticeRequest;
import com.lexue.bp.admin.inf.domain.req.StrategyRequest;
import com.lexue.bp.admin.inf.domain.res.ProduceResponse;
import com.lexue.bp.admin.inf.domain.res.UserManagementResponse;
import com.lexue.bp.admin.service.ContentService;
import com.lexue.bp.admin.util.PageableUtil;
import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.request.ProduceQueryRequest;
import com.lexue.bp.common.domain.request.UserManagementQueryRequest;
import com.lexue.bp.common.entity.NoticeEntity;
import com.lexue.bp.common.entity.ProduceEntity;
import com.lexue.bp.common.entity.StrategyEntity;
import com.lexue.bp.common.entity.UserEntity;
import com.lexue.bp.common.repository.NoticeRepository;
import com.lexue.bp.common.repository.ProduceRepository;
import com.lexue.bp.common.repository.StrategyRepository;
import com.lexue.bp.common.repository.UserRepository;
import com.lexue.bp.common.util.ProduceSpecs;
import com.lexue.bp.common.util.UserManagementSpecs;
import com.lexue.member.domain.UserDetail;
import com.lexue.member.domain.UserInfo;
import com.lexue.member.domain.UserResult;
import com.lexue.member.service.UserBaseServiceSpec;


@Service
public class ContentImpl implements ContentService {
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private StrategyRepository strategyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserBaseServiceSpec userBaseServiceSpec;
    @Autowired
    private ProduceRepository produceRepository;

    @Override
    @Transactional
    public NoticeEntity saveNotice(NoticeRequest noticeRequest) {
        NoticeEntity noticeBack = getNotice(noticeRequest.getModuleId());
        if (noticeBack == null) {
            noticeBack = new NoticeEntity();
            noticeBack.setId(ContentEnums.NOTICE.getCode());
        }
        noticeBack.setModuleId(noticeRequest.getModuleId());
        noticeBack.setContent(noticeRequest.getContent());
        noticeBack.setOperatorId(noticeRequest.getOperatorId());
        noticeBack.setOperatorName(noticeRequest.getOperatorName());
        noticeBack.setStatus(noticeRequest.getStatus());
        noticeBack.setUpdateTime(Calendar.getInstance().getTimeInMillis());


        return noticeRepository.save(noticeBack);

    }

    @Override
    public NoticeEntity getNotice(int moduleId) {
        NoticeEntity noticeEntity = noticeRepository.findByModuleId(moduleId);
        return noticeEntity;
    }

    @Override
    public PageReply<ProduceResponse> findProduceDetail(ProduceQueryRequest produceQueryRequest) {
        Pageable pageable = PageableUtil.generatePageable(produceQueryRequest.getPageAsk());
        Page<ProduceEntity> pageData = produceRepository.findAll(ProduceSpecs.queryProduceDetail(produceQueryRequest),pageable);
        List<ProduceResponse> produceResponseList = new ArrayList<>();
        for (ProduceEntity produceEntity : pageData.getContent()) {
            ProduceResponse produceResponse = new ProduceResponse();
            produceResponse.setEffectiveDate(produceEntity.getEffectiveDate());
            produceResponse.setProduceChannel(produceEntity.getProduceChannel());
            produceResponse.setProduceScore(produceEntity.getProduceScore());
            produceResponse.setUid(produceEntity.getUid());
            produceResponse.setId(produceEntity.getId());
            produceResponseList.add(produceResponse);
        }
        PageReply<ProduceResponse> result = new PageReply<>();
        result.setDatas(produceResponseList);
        result.setNumber(pageData.getNumber());
        result.setSize(pageData.getSize());
        result.setTotalElements(pageData.getTotalElements());
        result.setTotalPages(pageData.getTotalPages());
        return result;
    }



    @Override
    public PageReply<UserManagementResponse> findUserManagementDetail(UserManagementQueryRequest umqr) {
        Pageable pageable = PageableUtil.generatePageable(umqr.getPageAsk());
        Page<UserEntity> pageData = userRepository.findAll(UserManagementSpecs.queryUserManagementDetail(umqr),
                pageable);
        List<UserManagementResponse> umrList = new ArrayList<>();
        for (UserEntity userEntity : pageData.getContent()) {
            UserManagementResponse umResponse = new UserManagementResponse();
            UserResult<UserInfo> userInfo = userBaseServiceSpec.getUserInfo(userEntity.getUid());//昵称,lid
            if(userInfo.getCode() == 200 && userInfo.getBody() != null){
                umResponse.setNick(userInfo.getBody().getNick());
                umResponse.setLid(userInfo.getBody().getLexueId());
            }
            UserResult<UserDetail> userDetail = userBaseServiceSpec.getUserDetail(userEntity.getUid());//手机号
            if(userDetail.getCode() == 200 && userDetail.getBody() != null){
                umResponse.setMobile(userDetail.getBody().getMobile());
            }
            umResponse.setTotalRemain(userEntity.getTotalRemain());
            umResponse.setUid(userEntity.getUid());
            umrList.add(umResponse);
        }

        PageReply<UserManagementResponse> umrPageReply = new PageReply<>();
        umrPageReply.setDatas(umrList);
        umrPageReply.setNumber(pageData.getNumber());
        umrPageReply.setSize(pageData.getSize());
        umrPageReply.setTotalElements(pageData.getTotalElements());
        umrPageReply.setTotalPages(pageData.getTotalPages());
        return umrPageReply;
    }

    @Override
    public long getUserTotalRemain(long uid,int moduleId) {
        try {
            UserEntity entity = userRepository.findByUidAndModuleId(uid,moduleId);
            if(entity != null){
                return entity.getTotalRemain();
            }else{
                return 0;
            }
        }catch (Exception e){
            return 0;
        }

    }

    @Override
    @Transactional
    public StrategyEntity saveStrategy(StrategyRequest strategyRequest) {
        StrategyEntity saveStrategy = getStrategy(strategyRequest.getModuleId());
        if (saveStrategy == null) {
            saveStrategy = new StrategyEntity();
            saveStrategy.setId(ContentEnums.STRATEGY.getCode());
        }

        saveStrategy.setModuleId(strategyRequest.getModuleId());
        saveStrategy.setBeanDescribe(strategyRequest.getBeanDescribe());
        saveStrategy.setOperatorName(strategyRequest.getOperatorName());
        saveStrategy.setOther(strategyRequest.getOther());
        saveStrategy.setTimeDescribe(strategyRequest.getTimeDescribe());
        saveStrategy.setUpdateTime(Calendar.getInstance().getTimeInMillis());
        saveStrategy.setUseMethod(strategyRequest.getUseMethod());

        return strategyRepository.save(saveStrategy);
    }

    @Override
    public StrategyEntity getStrategy(int moduleId) {
        StrategyEntity strategyEntity = strategyRepository.findByModuleId(moduleId);
        return strategyEntity;
    }

    @Override
    public NoticeResponse findNotice(int moduleId) {
        NoticeEntity notice = noticeRepository.findByModuleId(moduleId);
        if (notice == null) {
            return new NoticeResponse();
        }
        NoticeResponse noticeResponse = new NoticeResponse();
        noticeResponse.setContent(notice.getContent());
//		noticeResponse.setNoticeName(notice.getNoticeName());
        noticeResponse.setOperatorId(notice.getOperatorId());
        noticeResponse.setOperatorName(notice.getOperatorName());
        noticeResponse.setStatus(notice.getStatus());
        return noticeResponse;
    }

    @Override
    public StrategyResponse findStrategy(int moduleId) {
        StrategyEntity strategy = strategyRepository.findByModuleId(moduleId);
        if (strategy == null) {
            return new StrategyResponse();
        }
        StrategyResponse strategyResponse = new StrategyResponse();
        strategyResponse.setBeanDescribe(strategy.getBeanDescribe());
        strategyResponse.setOperatorName(strategy.getOperatorName());
        strategyResponse.setOther(strategy.getOther());
//		strategyResponse.setStrategyName(strategy.getStrategyName());
        strategyResponse.setTimeDescribe(strategy.getTimeDescribe());
        strategyResponse.setUseMethod(strategy.getUseMethod());

        return strategyResponse;
    }


}
