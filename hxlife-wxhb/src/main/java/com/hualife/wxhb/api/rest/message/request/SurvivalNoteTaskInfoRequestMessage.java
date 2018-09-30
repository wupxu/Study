package com.hualife.wxhb.api.rest.message.request;
/**
 * @description 契调任务初始化请求报文实体类
 * @author wangt
 * @date 2017-08-07
 */
public class SurvivalNoteTaskInfoRequestMessage {
	
	private String survival_no;		//当前契调人工号

	public String getSurvival_no() {
		return survival_no;
	}

	public void setSurvival_no(String survival_no) {
		this.survival_no = survival_no;
	}
		
}
