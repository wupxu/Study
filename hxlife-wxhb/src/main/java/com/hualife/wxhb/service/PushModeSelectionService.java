package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.PushModeSelectionRequestMessage;
import com.hualife.wxhb.api.rest.message.response.PushModeSelectionResponseMessage;
/**
 * @author 吴培旭
 * @description 函件下发方式选择
 * @time 2017-08-03
 */
public interface PushModeSelectionService {
	/**
	 * @author 吴培旭
	 * @description 函件下发方式选择
	 * @time 2017-08-03
	 */
	public PushModeSelectionResponseMessage pushModeSelection(PushModeSelectionRequestMessage pushModeSelectionMessage);
}
