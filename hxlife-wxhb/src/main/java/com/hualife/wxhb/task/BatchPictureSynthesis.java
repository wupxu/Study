package com.hualife.wxhb.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hualife.wxhb.service.BatchPictureSynthesisService;
/**
 * @author yangpeixin
 * @description 图片合成
 * @time 创建时间：2017年8月17日
 */
public class BatchPictureSynthesis {
	
	@Autowired
	private BatchPictureSynthesisService batchPictureSynthesisService;
	private static final Logger LOG = LoggerFactory.getLogger(BatchPictureSynthesis.class);
	public void run() throws Exception {
		LOG.info("批处理开始");
		// todo 在service实现类中调用核心接口并处理核心返回报文
		batchPictureSynthesisService.batchPictureSynthesis();
		LOG.info("批处理结束");
}
}