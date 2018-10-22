package com.lexue.bp.admin.inf.domain.res;

import lombok.Data;

/**
 * 获得公告接收类
 * @author wpx
 *
 */
@Data
public class NoticeResponse {

	/**
	 * 1/2 开启/关闭 状态
	 */
	private int status;

	/** 公告名称
	private String noticeName; */

	/** 公告内容 */
	private String content;

	/** 操作员姓名 */
	private String operatorName;
	
	/** 操作员编号 */
	private long operatorId;
}
