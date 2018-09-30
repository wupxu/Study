package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.NoteImgDelRequestMessage;
import com.hualife.wxhb.api.rest.message.request.NoteImgRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NoteImgResponseMessage;
import com.hualife.wxhb.service.NoteImgService;

/**
 * @author 张卫卫
 * @description 微信端进行影像上传、删除操作
 * @date 2017-08-08
 */
@RestController
public class NoteImgController {
		
	@Autowired
	private NoteImgService noteImgService;
	/**
	 * 影像上传到oss
	 */
	@RequestMapping("/imageUpload")
	 public ResponseResult<NoteImgResponseMessage> imageUpload(@RequestBody NoteImgRequestMessage noteImgRequestMessage){
		NoteImgResponseMessage noteImgResponseMessage= noteImgService.ossUpload(noteImgRequestMessage);
		return ResponseResultUtil.success(noteImgResponseMessage);
	}
	/**
	 * 影像删除
	 */
	@RequestMapping("/imageDelete")
	ResponseResult<?> deleteImage(@RequestBody NoteImgDelRequestMessage noteImgDelRequestMessage){	
		noteImgService.ossDelete(noteImgDelRequestMessage);
		return ResponseResultUtil.success();	
	}
	
}
