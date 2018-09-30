package com.hualife.wxhb.service;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.api.rest.message.request.FinaLetterSaveRequestMessage;

/**
 * @descrption : 客户提交财务函函信息
 * @author : linyongtao
 * @time : 2017-08-04 17:37:00
 */
@Repository
public interface FinaLetterSaveService {
	/**
	 * @descrption : 客户提交财务函函信息
	 * @author : linyongtao
	 * @date:20170-08-04
	 **/
	public void finaLetterSave(FinaLetterSaveRequestMessage finaLetterSaveReqMess);
}
