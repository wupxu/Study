package com.lexue.bp.common.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnclaimedScore {

	/** 渠道编码  1:订单 2:观看视频 3:分享课程 4:发贴 5:评为热贴 */
	private int channelCode;

	/** 待领积分数 */
	private int score;

}
