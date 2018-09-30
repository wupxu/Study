package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;

import com.hualife.wxhb.service.PictureSynthesisService;

/**
 * @author yangpeixin
 * @description 图片合成
 * @date 2017-08-29
 */
@RestController
public class PictureSynthesisController {

	@Autowired
	private PictureSynthesisService pictureSynthesisService;

	/**
	 * 图片合成
	 * @param
	 * @return
	 */
	@RequestMapping(path = "/pictureSynthesis")

	public ResponseResult<?> pictureSynthesis(
			@RequestParam(name = "FORM") String form) {
		pictureSynthesisService.pictureSynthesis(form);
		return ResponseResultUtil.success();
	}
}
