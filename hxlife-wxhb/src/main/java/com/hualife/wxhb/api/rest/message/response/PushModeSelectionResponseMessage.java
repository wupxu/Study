package com.hualife.wxhb.api.rest.message.response;

/**
 * @author 吴培旭
 * @description 函件下发方式选择为机构打印时的返回报文
 * @time 创建时间：2017年8月21日
 * channel_type:代理人所属渠道
 */
public class PushModeSelectionResponseMessage {

	private String channel_type;// 代理人所属渠道

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
}
