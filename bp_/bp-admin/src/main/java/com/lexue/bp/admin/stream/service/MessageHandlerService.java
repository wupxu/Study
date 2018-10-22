/**
 * 
 */
package com.lexue.bp.admin.stream.service;

import com.lexue.bp.admin.stream.domain.DataOrderRequest;
import com.lexue.bp.admin.stream.domain.DataPostHotRequest;
import com.lexue.bp.admin.stream.domain.DataPostRequest;
import com.lexue.bp.admin.stream.domain.DataRefundRequest;
import com.lexue.bp.admin.stream.domain.DataWatchRequest;

/**
 * @author bc
 */
public interface MessageHandlerService {
	void handleOrderMessage(DataOrderRequest dor);
	void handleRefundMessage(DataRefundRequest drr);
	void handlePostMessage(DataPostRequest dpr);
	void handlePostHotMessage(DataPostHotRequest dphr);
//	void handleShareMessage(DataShareRequest dpr); 改为单独处理
	void handleWatchMessage(DataWatchRequest dpr);
}
