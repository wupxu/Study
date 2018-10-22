package com.lexue.bp.admin.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 传输通道定义
 * @author bc
 *
 */
public interface BpChannel {
	/**观看视频、分享课程、发帖模块到BP模块的通道**/
	String TO_BP = "to_bp";
	@Input(TO_BP)
	SubscribableChannel toBp();
	
	//正常订单
	String ORDER = "order_notify";//ORDER_NOTIFY
	@Input(ORDER)
	SubscribableChannel ORDER_NOTIFY();
	
	//退单
	String REFUND_ORDER = "review_update_notify";//REVIEW_UPDATE_NOTIFY
	@Input(REFUND_ORDER)
	SubscribableChannel REVIEW_UPDATE_NOTIFY();
}
