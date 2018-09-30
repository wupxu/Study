package com.hualife.wxhb.service;



import com.hualife.wxhb.api.rest.message.request.NoteImgDelRequestMessage;
import com.hualife.wxhb.api.rest.message.request.NoteImgRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NoteImgResponseMessage;
/**
 * @author zhangweiwei
 * @description 影像上传service
 * @date 2017-08-04
 */
public interface NoteImgService {
	/**
	 * @param noteImgRequestMessage
	 * @return  影像上传service,返回影像信息
	 */
	public NoteImgResponseMessage ossUpload(NoteImgRequestMessage noteImgRequestMessage);
	/**
	 * 
	 * @param noteImgRequestMessage
	 * @return  删除oss的影像
	 */
	public void  ossDelete(NoteImgDelRequestMessage noteImgDelRequestMessage);
}
