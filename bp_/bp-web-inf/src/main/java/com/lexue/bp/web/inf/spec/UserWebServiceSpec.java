package com.lexue.bp.web.inf.spec;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.domain.request.ProduceRequest;
import com.lexue.bp.web.inf.request.DataShareRequest;
import com.lexue.bp.web.inf.response.BeanMethodResponse;
import com.lexue.bp.web.inf.response.ScoreDetailResponse;
import com.lexue.bp.web.inf.response.StrategyResponse;
import com.lexue.bp.web.inf.response.StyleResponse;
import com.lexue.bp.web.inf.response.UnclaimedProduceResponse;
import com.lexue.bp.web.inf.response.UserResponse;
import com.lexue.bp.web.inf.response.UserScoreResponse;
import com.lexue.bp.web.inf.response.UserUrlResponse;

@FeignClient(name="bp-web-${platform_type}")
public interface UserWebServiceSpec {
	
	/** 用户领取积分及瓶子样式 */
	@GetMapping(path = "/bp/web/v1/rs")
	public ResponseDto<StyleResponse> receiveScore(@RequestParam("uid") long uid,@RequestParam("moduleId") int moduleId,@RequestParam("produceChannelCode") int produceChannelCode);

	/** 用户积分明细 */
	@GetMapping(path = "/bp/web/v1/sd")
	public ResponseDto<PageReply<ScoreDetailResponse>> scoreDetail(@RequestParam("uid") long uid,
			@RequestParam("moduleId") int moduleId, @RequestParam("pageNumber") int pageNumber,@RequestParam("pageSize") int pageSize);
	
	/** 查询各渠道待领取乐豆数*/
	@GetMapping(path = "/bp/web/v1/gup")
	public ResponseDto<List<UnclaimedProduceResponse>> userUnclaimedProduce(@RequestParam("uid") long uid,@RequestParam("moduleId") int moduleId);

	/** 获取用户乐豆数，公告，头像，攻略url，换好礼url，功能任务说明（分享，发帖等的任务说明）  此方法删除*/
	@GetMapping(path = "/bp/web/v1/gum")
	@Deprecated
	public ResponseDto<UserScoreResponse> getUserCommon(@RequestParam("uid") long uid,@RequestParam("moduleId") int moduleId);
	
	/** 获取用户乐豆数,头像,乐学id ，瓶子样式*/
	@GetMapping(path = "/bp/web/v1/gusi") 
	public ResponseDto<UserResponse> getUserScoreIM(@RequestParam("uid") long uid,@RequestParam("moduleId")int moduleId);
	
	/** 公告攻略url，换好礼url,公告，功能任务说明（分享，发帖等的任务说明） */
	@GetMapping(path = "/bp/web/v1/grur")
	public ResponseDto<UserUrlResponse> getUrlRule(@RequestParam("uid") long uid,@RequestParam("moduleId") int moduleId,@RequestParam("platformType") int platformType);

	/** 添加分享数据 */
	@PostMapping(path = "/bp/web/v1/as")  
	public ResponseDto<Void> addShare(@RequestBody DataShareRequest dataShareRequest,@RequestParam("uid") long uid,@RequestParam("moduleId") int moduleId);
	
	/** 获取乐豆用途信息 */
	@GetMapping(path = "/bp/web/v1/gs")
	public ResponseDto<StrategyResponse> getStrategy(@RequestParam("moduleId") int moduleId);
	
	/** 如何获取乐豆信息 */
	@GetMapping(path = "/bp/web/v1/gbm")
	public ResponseDto<List<BeanMethodResponse>>  getBeanMethod(@RequestParam("moduleId") int moduleId);
	
	/** 给用户直接增加乐豆 */
	@PostMapping(path = "/bp/web/v1/addScore")  
	public ResponseDto<Void> addScore(@RequestBody ProduceRequest produceRequest);
	
	/** 用户待领取的乐豆
	@GetMapping(path = "/bp/web/v1/getUserUnclaimedScore")
	public ResponseDto<Integer> userUnclaimedScore(@RequestParam("uid") long uid); */
	

}