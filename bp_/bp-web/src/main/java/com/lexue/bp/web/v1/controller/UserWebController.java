package com.lexue.bp.web.v1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.domain.request.ProduceRequest;
import com.lexue.bp.common.entity.NoticeEntity;
import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.entity.StrategyEntity;
import com.lexue.bp.common.entity.mongo.UserMongoEntity;
import com.lexue.bp.common.enums.OtherEnums;
import com.lexue.bp.common.enums.StyleEnums;
import com.lexue.bp.common.util.ResponseUtil;
import com.lexue.bp.web.inf.request.DataShareRequest;
import com.lexue.bp.web.inf.response.BeanMethodResponse;
import com.lexue.bp.web.inf.response.ScoreDetailResponse;
import com.lexue.bp.web.inf.response.StrategyResponse;
import com.lexue.bp.web.inf.response.StyleResponse;
import com.lexue.bp.web.inf.response.TaskResponse;
import com.lexue.bp.web.inf.response.UnclaimedProduceResponse;
import com.lexue.bp.web.inf.response.UserResponse;
import com.lexue.bp.web.inf.response.UserScoreResponse;
import com.lexue.bp.web.inf.response.UserUrlResponse;
import com.lexue.bp.web.inf.spec.UserWebServiceSpec;
import com.lexue.bp.web.service.ConfigService;
import com.lexue.bp.web.service.UserProduceService;
import com.lexue.bp.web.service.WebRuleService;
import com.lexue.member.domain.UserDetail;
import com.lexue.member.domain.UserResult;
import com.lexue.member.service.UserBaseServiceSpec;


/**
 * web
 * 
 * @author wpx
 *
 */
@RestController("userWebControllerV1")
public class UserWebController implements UserWebServiceSpec {

	@Autowired
	private UserProduceService userProduceScoreService;
	@Autowired
	private WebRuleService ruleService;
	@Autowired
	private ConfigService configureService;
	@Autowired
	private UserBaseServiceSpec userBaseServiceSpec;

	@Override
	public ResponseDto<StyleResponse> receiveScore(@RequestParam("uid") long uid,
			@RequestParam("moduleId") int moduleId, @RequestParam("produceChannelCode") int produceChannelCode) {
		StyleResponse styleResponse = new StyleResponse();
		UserMongoEntity totalScore = userProduceScoreService.receiveScoreByChannel(uid, moduleId, produceChannelCode);
		int styleid = convertStyle(totalScore.getTotalProduce());
		styleResponse.setStyle(StyleEnums.parser(styleid).getName());
		styleResponse.setStyleId(styleid);
		styleResponse.setProduceScore(totalScore.getTotalProduce());
		styleResponse.setTotalConsume(totalScore.getTotalConsume());
		styleResponse.setTotalRemain(totalScore.getTotalRemain());
		return ResponseUtil.generateSuccessResponse(styleResponse);
	}

	@Override
	public ResponseDto<PageReply<ScoreDetailResponse>> scoreDetail(@RequestParam("uid") long uid,
			@RequestParam("moduleId") int moduleId, @RequestParam("pageNumber") int pageNumber,
			@RequestParam("pageSize") int pageSize) {
		PageReply<ScoreDetailResponse> sdresult = userProduceScoreService.userScoreDetail(uid, moduleId, pageNumber, pageSize);
		return ResponseUtil.generateSuccessResponse(sdresult);
	}

	@Override
	public ResponseDto<List<UnclaimedProduceResponse>> userUnclaimedProduce(@RequestParam("uid") long uid,
			@RequestParam("moduleId") int moduleId) {
		
		List<UnclaimedProduceResponse> unclaimedProduceResponseList = userProduceScoreService.getUnclaimedScore(uid, moduleId);
		return ResponseUtil.generateSuccessResponse(unclaimedProduceResponseList);
	}

	@Override
	public ResponseDto<UserScoreResponse> getUserCommon(@RequestParam("uid") long uid,
			@RequestParam("moduleId") int moduleId) {
		UserMongoEntity userMongoEntity = userProduceScoreService.findUserInfo(uid, moduleId);

		NoticeEntity noticeEntity = configureService.findConfigureContent(moduleId);// 查公告
		UserResult<UserDetail> userDetail = userBaseServiceSpec.getUserDetail(uid);// 查头像
		List<RuleEntity> allRule = ruleService.getAllRule();
		List<TaskResponse> taskResponseList = new ArrayList<>();
		for (RuleEntity ruleEntity : allRule) {
			TaskResponse taskResponse = new TaskResponse();
			taskResponse.setRuleName(ruleEntity.getRuleName());
			taskResponse.setTaskSpecification(ruleEntity.getTaskSpecification());
			taskResponseList.add(taskResponse);
		}
		UserScoreResponse userScoreResponse = new UserScoreResponse();
		userScoreResponse.setTotalRemain(userMongoEntity.getTotalRemain());
		userScoreResponse.setTotalProduce(userMongoEntity.getTotalProduce());
		userScoreResponse.setTotalConsume(userMongoEntity.getTotalConsume());
		userScoreResponse.setNoticeContent(noticeEntity.getContent());
		userScoreResponse.setTaskResponse(taskResponseList);
		userScoreResponse.setStrategyUrl(OtherEnums.STRATEGY.getMsg());
		userScoreResponse.setCourtesyUrl(OtherEnums.COURTESY.getMsg());// 为null表敬请期待，不为null表该功能一实现
		userScoreResponse.setHeadSculptureUrl(userDetail.getBody().getPortrait());
		return ResponseUtil.generateSuccessResponse(userScoreResponse);
	}

	@Override
	public ResponseDto<UserUrlResponse> getUrlRule(@RequestParam("uid") long uid,
			@RequestParam("moduleId") int moduleId, @RequestParam("platformType") int platformType) {
		NoticeEntity noticeEntity = configureService.findConfigureContent(moduleId);// 查公告
		List<RuleEntity> allRule = ruleService.getRuleByModuleId(moduleId);// 功能说明
		List<TaskResponse> taskResponseList = new ArrayList<>();
		for (RuleEntity ruleEntity : allRule) {
			TaskResponse taskResponse = new TaskResponse();
			taskResponse.setRuleName(ruleEntity.getRuleName());
			taskResponse.setImageUrl(ruleEntity.getImageUrl());
			taskResponse.setTaskSpecification(ruleEntity.getTaskSpecification());
			taskResponseList.add(taskResponse);
		}

		UserUrlResponse userUrlResponse = new UserUrlResponse();
		if (noticeEntity == null) {
			userUrlResponse.setNoticeContent("");
		} else {
			userUrlResponse.setNoticeContent(noticeEntity.getContent());
		}
		userUrlResponse.setTaskResponse(taskResponseList);
		userUrlResponse.setStrategyUrl(OtherEnums.STRATEGY.getMsg());
		if (platformType == 101) {// ios添加
			userUrlResponse.setCourtesyUrl(OtherEnums.IOS.getMsg());
		}
		userUrlResponse.setCourtesyUrl(OtherEnums.COURTESY.getMsg());// 为null表敬请期待，不为null表该功能一实现

		return ResponseUtil.generateSuccessResponse(userUrlResponse);
	}

	@Override
	public ResponseDto<UserResponse> getUserScoreIM(long uid, int moduleId) {
		UserMongoEntity userMongoEntity = userProduceScoreService.findUserInfo(uid, moduleId);
		UserResult<UserDetail> userDetail = userBaseServiceSpec.getUserDetail(uid);// 查头像
		UserResponse userResponse = new UserResponse();
		int styleid = StyleEnums.GLASS.getCode();
		styleid = convertStyle(userMongoEntity.getTotalProduce());
		userResponse.setTotalRemain(userMongoEntity.getTotalRemain());
		userResponse.setTotalProduce(userMongoEntity.getTotalProduce());
		userResponse.setTotalConsume(userMongoEntity.getTotalConsume());
		if (userDetail.getBody() == null) {
			userResponse.setHeadSculptureUrl("");
		} else {
			userResponse.setHeadSculptureUrl(userDetail.getBody().getPortrait());
		}
		userResponse.setStyle(StyleEnums.parser(styleid).getName());
		userResponse.setStyleId(styleid);
		return ResponseUtil.generateSuccessResponse(userResponse);
	}

	/** 瓶子样式判断 */
	private int convertStyle(int totalScore) {
		int style = 0;
		if (totalScore >= 0 && totalScore <= 100) {
			style = StyleEnums.GLASS.getCode();
		}
		if (totalScore > 100 && totalScore <= 300) {
			style = StyleEnums.CRYSTAL.getCode();
		}
		if (totalScore > 300 && totalScore <= 1000) {
			style = StyleEnums.PURPLE.getCode();
		}
		if (totalScore > 1000 && totalScore <= 5000) {
			style = StyleEnums.RED.getCode();
		}
		if (totalScore > 5000 && totalScore <= 10000) {
			style = StyleEnums.BLUE.getCode();
		}
		if (totalScore > 10000 && totalScore <= 50000) {
			style = StyleEnums.EMERALD.getCode();
		}
		if (totalScore > 50000) {
			style = StyleEnums.COLOURFUL.getCode();
		}
		return style;
	};

	/*
	 * @Override public ResponseDto<CommonResponse> getCommon(long uid) {
	 * NoticeEntity noticeEntity = configureService.findConfigureContent();
	 * UserResult<UserDetail> userDetail =
	 * userBaseServiceSpec.getUserDetail(uid);//查头像 List<RuleEntity> allRule =
	 * ruleService.getAllRule(); List<TaskResponse> taskResponseList = new
	 * ArrayList<>(); for (RuleEntity ruleEntity : allRule) { TaskResponse
	 * taskResponse = new TaskResponse();
	 * taskResponse.setRuleName(ruleEntity.getRuleName());
	 * taskResponse.setTaskSpecification(ruleEntity.getTaskSpecification());
	 * taskResponseList.add(taskResponse); } CommonResponse commonResponse = new
	 * CommonResponse(); commonResponse.setNoticeContent(noticeEntity.getContent());
	 * commonResponse.setTaskResponse(taskResponseList);
	 * commonResponse.setStrategyUrl(OtherEnums.STRATEGY.getMsg());
	 * commonResponse.setCourtesyUrl(OtherEnums.COURTESY.getMsg());//为null表敬请期待，
	 * 不为null表该功能一实现
	 * commonResponse.setHeadSculptureUrl(userDetail.getBody().getPortrait());
	 * 
	 * return ResponseUtil.generateSuccessResponse(commonResponse); }
	 */

	@Override
	public ResponseDto<Void> addShare(@RequestBody DataShareRequest dataShareRequest, long uid, int moduleId) {
		userProduceScoreService.addDataShareEntity(dataShareRequest, uid, moduleId);
		return ResponseUtil.generateSuccessResponse();
	}

	@Override
	public ResponseDto<StrategyResponse> getStrategy(int moduleId) {
		StrategyEntity strategyEntity = configureService.findStrategy(moduleId);
		StrategyResponse strategyResponse = new StrategyResponse();
		if (strategyEntity == null) {
			strategyResponse.setBeanDescribe("");
			strategyResponse.setTimeDescribe("");
			strategyResponse.setOther("");
			strategyResponse.setUseMethod("");
		} else {
			strategyResponse.setBeanDescribe(strategyEntity.getBeanDescribe());
			strategyResponse.setTimeDescribe(strategyEntity.getTimeDescribe());
			strategyResponse.setOther(strategyEntity.getOther());
			strategyResponse.setUseMethod(strategyEntity.getUseMethod());
		}
		return ResponseUtil.generateSuccessResponse(strategyResponse);
	}

	@Override
	public ResponseDto<List<BeanMethodResponse>> getBeanMethod(int moduleId) {
		List<RuleEntity> ruleEntityList = configureService.findBeanMethod(moduleId);
		List<BeanMethodResponse> beanMethodResponseList = new ArrayList<>();
		if (ruleEntityList.size() > 0) {
			for (RuleEntity ruleEntity : ruleEntityList) {
				BeanMethodResponse beanMethodResponse = new BeanMethodResponse();
				beanMethodResponse.setRuleId(ruleEntity.getRuleId());
				beanMethodResponse.setRuleName(ruleEntity.getRuleName());
				beanMethodResponse.setStrategySpecification(ruleEntity.getStrategySpecification());
				beanMethodResponseList.add(beanMethodResponse);
			}
		}
		return ResponseUtil.generateSuccessResponse(beanMethodResponseList);
	}


	@Override
	public ResponseDto<Void> addScore(ProduceRequest produceRequest) {
		userProduceScoreService.addScore(produceRequest);
		return ResponseUtil.generateSuccessResponse();
	}
}

