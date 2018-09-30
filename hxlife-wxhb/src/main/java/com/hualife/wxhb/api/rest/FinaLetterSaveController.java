package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.FinaLetterSaveRequestMessage;
import com.hualife.wxhb.service.FinaLetterSaveService;

/**
 * @description : 客户提交财务函函信息
 * @author : linyongtao
 * @date : 2017-08-04
 */
@RestController
public class FinaLetterSaveController {
	@Autowired
	private FinaLetterSaveService finaLetterSaveService;

	@RequestMapping("/finaOccuSave")
	public ResponseResult<?> finaOccuSave(@RequestBody FinaLetterSaveRequestMessage finaLetterSaveRequestMessage) {
		finaLetterSaveService.finaLetterSave(finaLetterSaveRequestMessage);
		return ResponseResultUtil.success();
	}
}
