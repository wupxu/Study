/**
 * 
 */
package com.lexue.bp.common.util;

/**
 * @author bc
 *
 */
public interface Constants {
	//报文类型
	static final String REPORT_TYPE = "REPORT";
	static final String REPORT_TYPE_ORDER = "ORDER";//订单数据
	static final String REPORT_TYPE_REFUND = "REFUND";//订单退款
	static final String REPORT_TYPE_POST = "POST";//帖子
	static final String REPORT_TYPE_POSTHOT = "POSTHOT";//热帖
	static final String REPORT_TYPE_SHARE = "SHARE";//分享
	static final String REPORT_TYPE_WATCH = "WATCH";//观看视频
	
	static final String REPORT_TYPE_SIGN = "SIGN";//签到，不用从队列中获取数据
	
}
