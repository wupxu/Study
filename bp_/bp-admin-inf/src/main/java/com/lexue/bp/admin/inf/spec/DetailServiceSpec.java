package com.lexue.bp.admin.inf.spec;


import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lexue.bp.admin.inf.domain.res.DataChangeScoreResponse;
import com.lexue.bp.admin.inf.domain.res.ProduceResponse;
import com.lexue.bp.admin.inf.domain.res.UserManagementResponse;
import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.domain.request.ProduceQueryRequest;
import com.lexue.bp.common.domain.request.ProduceUpdateRequest;
import com.lexue.bp.common.domain.request.ScoreChangeRequest;
import com.lexue.bp.common.domain.request.UserManagementQueryRequest;


@FeignClient(name="bp-admin-${platform_type}")
public interface DetailServiceSpec {
	
	/**
	 * 获取乐豆发放明细记录
	 * @return
	 */
	@PostMapping(value="/bp/admin/v1/detail/produce")
	ResponseDto<PageReply<ProduceResponse>> getProduceDetail(@RequestBody ProduceQueryRequest pqr);
	
	/**
	 * 获取乐豆用户管理信息
	 * @return
	 */
	@PostMapping(value="/bp/admin/v1/detail/userManagement")
	ResponseDto<PageReply<UserManagementResponse>> getUserManagement(@RequestBody UserManagementQueryRequest umqr);

	/**
	 * 获取用户累计积分
	 * @param uid
	 * @return
	 */
	@GetMapping(value="/bp/admin/v1/detail/userTotalRemain")
	ResponseDto<Long> getUserTotalRemain(@RequestParam("uid") long uid,@RequestParam(value = "moduleId",defaultValue = "8193")int moduleId);
	
	/**
	 * 后台变更积分
	 * @param produceUpdateRequest
	 * @return
	 */
	@PostMapping(value="/bp/admin/v1/detail/updateScore")
	ResponseDto<Void> updateScore(@RequestBody ProduceUpdateRequest produceUpdateRequest);
	
	/**
	 * 获取用户乐豆详情
	 * @param scoreDetailRequest
	 * @return
	 */
	@GetMapping(value="/bp/admin/v1/detail/getScoreDetail")
	ResponseDto<List<DataChangeScoreResponse>> getScoreDetail(@RequestParam("uid") long uid,@RequestParam(value = "moduleId",defaultValue = "8193")int moduleId);
	
	/**
	 * 乐豆修改记录查询
	 * @param scoreChangeRequest
	 * @return
	 */
	@PostMapping(value="/bp/admin/v1/detail/getChangeScoreDetail")
	ResponseDto<PageReply<DataChangeScoreResponse>> getChangeScoreDetail(@RequestBody ScoreChangeRequest scoreChangeRequest);


	
}
