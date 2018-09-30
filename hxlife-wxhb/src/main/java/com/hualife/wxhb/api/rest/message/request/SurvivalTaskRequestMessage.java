package com.hualife.wxhb.api.rest.message.request;
/**
 * @author zhangweiwei
 * @description 契调任务初始化的请求报文
 * @date 2017-08-04
 */
public class SurvivalTaskRequestMessage {
	/**
	 * 契调函id
	 */
	private String survival_note_id;//

	public String getSurvival_note_id() {
		return survival_note_id;
	}

	public void setSurvival_note_id(String survival_note_id) {
		this.survival_note_id = survival_note_id;
	}

}
