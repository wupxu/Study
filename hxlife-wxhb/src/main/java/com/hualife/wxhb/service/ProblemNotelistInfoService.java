package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.ProblemInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.ProblemInfoResponseMessage;

/** 
 * @author 吴培旭 
 * @description 问题件列表初始化
 * @time 创建时间：2017年8月8日   
 */
public interface ProblemNotelistInfoService {

	/** 
	 * @author 吴培旭 
	 * @description 问题件列表初始化
	 * @time 创建时间：2017年8月8日   
	 */
	ProblemInfoResponseMessage problemNotelistInfo(ProblemInfoRequestMessage problemInfoReqMess);

}
