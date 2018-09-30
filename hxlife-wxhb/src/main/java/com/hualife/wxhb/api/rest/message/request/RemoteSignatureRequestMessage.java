package com.hualife.wxhb.api.rest.message.request;
/**
 * @author zhangweiwei
 * @deprecation 投保人查看被保人的异地签名的请求报文
 * @date 2017-09-11
 */
public class RemoteSignatureRequestMessage {
	/**
	 * 函件id
	 */
	private String note_id;
	/**
	 * 函件类型
	 */
	private String note_type;
	/**
	 * 影像类型
	 */
	private String image_type;
	/**
	 * 次数
	 */
	private Integer count;
	
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public String getNote_type() {
		return note_type;
	}
	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}
	public String getImage_type() {
		return image_type;
	}
	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
