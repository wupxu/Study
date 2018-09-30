package com.hualife.wxhb.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hualife.wxhb.service.ImageService;

/**
 * @deprecation 给代理人推送影像消息--批处理类
 * @author wangt
 * @date  2017-08-20
 */
public class ImageInfoTask {
	/** 日志对象 */
	@Autowired
	ImageService tImageService;
    private static final Logger LOG = LoggerFactory.getLogger(ImageInfoTask.class);
    public void run(){
    	LOG.info("影像上载开始启动");
    	this.tImageService.imageUpLoad();
    	LOG.info("影像上载结束");
    } 
}
