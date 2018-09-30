package com.hualife.wxhb.service.Impl;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.ImageDao;
import com.hualife.wxhb.service.PictureSynthesisServiceByIdService;
/**
 * @descrption : 批处理中介接口
 * @author : ynagpeixin
 * @time : 2017-08-04 
 */
@Service
public class PictureSynthesisServiceByIdServiceImpl implements PictureSynthesisServiceByIdService {
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private ClientDao  clientDao;
	
	/**
	 * @descrption : 存数据
	 * **/
	@Override
	public void pushPictureSynthesisServicePush(String id) {
		String noteid = clientDao.getNoteIdByclietId(id);
		TNoteMain tNoteMain = new TNoteMain();
    	tNoteMain = imageDao.getMainInfoById(noteid);
    	Date time = new Date();
    	Map<String, Object> imgMap = new HashMap<>();
    	imgMap.put("clientNoteId", id);
    	imgMap.put("pushStatus", Constant.push_status_unsend);
    	imgMap.put("time", DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", time));
    	imgMap.put("manageCom", tNoteMain.getBranchCode());
    	imgMap.put("channelType", tNoteMain.getChannelType());
    	imageDao.addTtaskMerge(imgMap);
    	
	}

}
