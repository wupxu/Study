package com.hualife.wxhb.integration.dao;

import java.util.HashMap;

import com.hualife.wxhb.domain.entity.TNoteTrace;
/**
 * @author zhangweiwei
 * @description 保存函件轨迹
 * @date 2017-08-04
 */
public interface TraceDao {
	/**
	 * 保存函件轨迹    addby zhangweiwei
	 * @param tNoteTrace
	 */
	public void saveNoteTrace(TNoteTrace tNoteTrace);
	/**
	 * 获取函件轨迹编号    addby zhangweiwei
	 * @param tNoteTrace
	 */
	public String getTrackSeqByMap(HashMap<String, Object> traceMap);
}
