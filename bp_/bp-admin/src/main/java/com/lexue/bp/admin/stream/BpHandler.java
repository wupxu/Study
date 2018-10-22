/**
 * 
 */
package com.lexue.bp.admin.stream;


import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import com.lexue.bp.admin.service.RuleService;
import com.lexue.bp.admin.stream.domain.DataOrderRequest;
import com.lexue.bp.admin.stream.domain.DataPostHotRequest;
import com.lexue.bp.admin.stream.domain.DataPostRequest;
import com.lexue.bp.admin.stream.domain.DataRefundRequest;
import com.lexue.bp.admin.stream.domain.DataWatchRequest;
import com.lexue.bp.admin.stream.service.MessageHandlerService;
import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.enums.RuleStatusEnums;
import com.lexue.bp.common.util.Constants;
import com.lexue.order.server.api.rest.domain.UserOrder;
import com.lexue.refund.server.common.domain.RefundItem;
import com.lexue.refund.server.common.domain.RefundOrderUpdate;

import lombok.extern.slf4j.Slf4j;

/**
 * 负责处理消息,
 * 并通过在头部增加消息类型(REPORT_TYPE)，分开处理不同的消息。
 * @author bc
 */
@EnableBinding(BpChannel.class)
@EnableAutoConfiguration
@Slf4j
public class BpHandler {
	@Autowired
	private MessageHandlerService messageHandlerService;

	@StreamListener(target = BpChannel.ORDER, condition = "headers['status']=='FINISHED'")
	public void receiveOrder(Message<UserOrder> message ) {
		log.info("Received DataOrderRequest:[{}]",message.getPayload());
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		if (acknowledgment != null) {
			try{
				UserOrder userOrder = message.getPayload();
				DataOrderRequest dor = new DataOrderRequest();
				dor.setAmount(userOrder.getFeeTotal());
				dor.setModuleId(userOrder.getBizType().getValue());
				dor.setOrderId(String.valueOf(userOrder.getOrderId()));
				dor.setPayTime(userOrder.getPayProcess() == null?userOrder.getCreateTime().getTime():userOrder.getPayProcess().getPayTime().getTime());
				dor.setUid(userOrder.getUserId());
				
				messageHandlerService.handleOrderMessage(dor);
				acknowledgment.acknowledge();
			} catch (Throwable e) {
				log.error("接收订单失败",e);
			} finally {
				acknowledgment.acknowledge();
			}
		}
	}
	
	@StreamListener(target = BpChannel.REFUND_ORDER)
	public void receiveRefund(Message<RefundOrderUpdate> message) {
		log.info("Received DataRefundRequest:[{}]",message.getPayload());
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		if (acknowledgment != null) {
			try{
				RefundOrderUpdate refundOrderUpdate = message.getPayload();
				DataRefundRequest drr = new DataRefundRequest();
				List<RefundItem> refundItems = refundOrderUpdate.getRefundItems();
				if (CollectionUtils.isNotEmpty(refundItems)) {
					BigDecimal amount = BigDecimal.ZERO;
					for(RefundItem refundItem:refundItems) {
						amount = amount.add(refundItem.getRefundFee()) ;
					}
					drr.setAmount(amount);
				}
				drr.setOrderId(refundOrderUpdate.getOrderId().toString());
				drr.setRefundId(refundOrderUpdate.getRefundId().toString());
				drr.setRefundTime(refundOrderUpdate.getRefundTime().getTime());
				
				messageHandlerService.handleRefundMessage(drr);
				acknowledgment.acknowledge();
			} catch (Throwable e) {
				log.error("接收订单退款失败",e);
			} finally {
				acknowledgment.acknowledge();
			}
		}
		
	}
	
	@StreamListener(target = BpChannel.TO_BP, condition = "headers['"+Constants.REPORT_TYPE+"']=='"+Constants.REPORT_TYPE_POST+"'")
	public void receivePost(Message<DataPostRequest> message) {
		log.debug("Received DataPostRequest:[{}]",message.getPayload());
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		if (acknowledgment != null) {
			try{
				DataPostRequest dataPostRequest = message.getPayload();
				messageHandlerService.handlePostMessage(dataPostRequest);
				acknowledgment.acknowledge();
			} catch (Throwable e) {
				log.error("接收帖子失败",e);
			} finally {
				acknowledgment.acknowledge();
			}
		}
	}
	
	@StreamListener(target = BpChannel.TO_BP, condition = "headers['"+Constants.REPORT_TYPE+"']=='"+Constants.REPORT_TYPE_POSTHOT+"'")
	public void receivePostHot(Message<DataPostHotRequest> message) {
		log.debug("Received DataPostHotRequest:[{}]",message.getPayload());
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		if (acknowledgment != null) {
			try{
				DataPostHotRequest dataPostHotRequest = message.getPayload();
				messageHandlerService.handlePostHotMessage(dataPostHotRequest);
				acknowledgment.acknowledge();
			} catch (Throwable e) {
				log.error("接收热帖失败",e);
			} finally {
			   acknowledgment.acknowledge();
			}
		}
	}
	//改为单独处理 
	/*@StreamListener(target = BpChannel.TO_BP, condition = "headers['"+Constants.REPORT_TYPE+"']=='"+Constants.REPORT_TYPE_SHARE+"'")
	public void receiveShare(Message<DataShareRequest> message) {
		log.debug("Received DataShareRequest:[{}]",message.getPayload());
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		if (acknowledgment != null) {
			try{
				DataShareRequest dataShareRequest = message.getPayload();
				messageHandlerService.handleShareMessage(dataShareRequest);
				acknowledgment.acknowledge();
			} catch (Throwable e) {
				log.error("接收课程分享失败",e);
			} finally {
				acknowledgment.acknowledge();
			}
		}
	}*/
	
	@StreamListener(target = BpChannel.TO_BP, condition = "headers['"+Constants.REPORT_TYPE+"']=='"+Constants.REPORT_TYPE_WATCH+"'")
	public void receiveWatch(Message<DataWatchRequest> message) {
		log.debug("Received DataWatchRequest:[{}]",message.getPayload());
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		if (acknowledgment != null) {
			try{
				DataWatchRequest dataWatchRequest = message.getPayload();
				messageHandlerService.handleWatchMessage(dataWatchRequest);
				acknowledgment.acknowledge();
			} catch (Throwable e) {
				log.error("接收课程观看失败",e);
			} finally {
				acknowledgment.acknowledge();
			}
		}
	}
	
	
}
