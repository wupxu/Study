package com.lexue.bp.admin.inf.spec;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lexue.bp.admin.inf.domain.req.NoticeRequest;
import com.lexue.bp.admin.inf.domain.req.StrategyRequest;
import com.lexue.bp.admin.inf.domain.res.ContentResponse;
import com.lexue.bp.admin.inf.domain.res.NoticeResponse;
import com.lexue.bp.admin.inf.domain.res.StrategyResponse;
import com.lexue.bp.common.domain.ResponseDto;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="bp-admin-${platform_type}")
public interface ContentServiceSpec {
	
	/**
	 * 获取内容信息
	 * @return
	 */
	@GetMapping(value="/bp/admin/v1/content/all")
	ResponseDto<List<ContentResponse>> getAllContent(@RequestParam(value = "moduleId",defaultValue = "8193") int moduleId);
	
	/**
	 * 保存攻略
	 * @param strategyRequest
	 * @return
	 */
	@PostMapping(value="/bp/admin/v1/content/saveStrategy")
	ResponseDto<Void> saveStrategy(@RequestBody StrategyRequest strategyRequest);	
	
	/**
	 * 保存公告
	 * @param noticeRequest
	 * @return
	 */
	@PostMapping(value="/bp/admin/v1/strategy/saveNotice")
	ResponseDto<Void> saveNotice(@RequestBody NoticeRequest noticeRequest);


	
	/**
	 * 查询公告内容
	 * @return
	 */
	@GetMapping(value="/bp/admin/v1/content/notice")
	ResponseDto<NoticeResponse> findNotice(@RequestParam(value = "moduleId",defaultValue = "8193") int moduleId);
	
	/**
	 * 查询乐豆用途信息
	 * @return
	 */
	@GetMapping(value="/bp/admin/v1/content/strategy")
	ResponseDto<StrategyResponse> findStrategy(@RequestParam(value = "moduleId",defaultValue = "8193") int moduleId);
	
	
	
}