package com.hualife.wxhb.service;


public interface PushNotePrintService {
	/** 
	 * @author 吴培旭 
	 * @description 函件下发方式为自己打印时向中间表里推送数据的接口
	 * @time 创建时间：2017年8月29日   
	 */
	public void addTaskPushnotePrint(String nodeId,String noteType);
	/** 
	 * @author 吴培旭 
	 * @description 函件下发方式为机构打印时组织核心所需输入报文
	 * @time 创建时间：2017年8月29日   
	 */
	public void  setChooseTypeRestClientBody(String nodeId);
}
