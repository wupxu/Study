package com.hualife.wxhb.integration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.UndwrtNoteProduct;
import com.hualife.wxhb.api.rest.message.response.UndwrtAgentNoteInfoResponseMessage;
import com.hualife.wxhb.domain.entity.TNoteImage;
import com.hualife.wxhb.domain.entity.TNoteUndwrtNote;
import com.hualife.wxhb.domain.entity.TNoteUndwrtProductResult;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyUndwetInfoInfo;

@Repository
public interface UndwrtDao {
	/**
	 * 核保函为延期、拒保时，点击删除按钮，更新核保函的函件状态    add by zhangweiwei
	 * @param map
	 */
	public void updateUndwrtEndDeal(Map<String, Object> map);
	/**
	 * 核保函为延期、拒保时，点击删除按钮，更新函件主表的函件状态  add by zhangweiwei
	 * @param map
	 */
	public void updateMainStatus(Map<String, Object> map);
	/**
	 * 获取核保函的个数     add by zhangweiwei
	 */
	public TNoteUndwrtNote countUndwrtNoteByNoteId(String noteUndwrtId);
	/**
	 * @param map
	 * @return 核保涵提交 --add by wangt
	 */
	public void updateUndwrtStatus(Map<Object, Object> map);
	/**
	 * @param map
	 * @return 获取函件ID --add by wangt
	 */
	public String getNoteIDByNoteUndwrtId(Map<Object, Object> map);
	/**
	 * @param map
	 * @return 获取银行卡号 --add by wangt
	 */
	public String getAccount_no(Map<Object, Object> map);
	/**
	 * @param map
	 * @return 查询核保函基本信息--add by wangt
	 */
	public UndwrtAgentNoteInfoResponseMessage getUndwrtNote(Map<Object, Object> map);	
	/**
	 * @param map
	 * @return 查询影像信息--add by wangt
	 */
	public List<ImageInfo> getImages(Map<Object, Object> map);
	/**
	 * @param map
	 * @return 查询险种信息--add by wangt
	 */
	public List<UndwrtNoteProduct> getProducts(Map<Object, Object> map);
	/**
	 * 核保函回销状态
	 * @param map
	 */
	public void updateUndwrtNoteStatus(HashMap<String, Object> map);
	/**
	 * t_note_undwrt_note 表插入
	 * add by zhanglong
	 */
	public void insertNoteUndwrt(TNoteUndwrtNote tNoteUndwrtNote);
	/**
	 * t_note_undwrt_product_result 表插入
	 * add by zhanglong
	 */
	public void insertNoteUndwrtProduct(List<TNoteUndwrtProductResult> insertTNoteUndwrtProductResultList);

	/**
	 * 查询核保任务序号和客户回复信息
	 * wupeixu
	 * @param pushMap
	 * @return
	 */
	public PushMessageBodyUndwetInfoInfo getUndwetInfoInfo(Map<Object, Object> pushMap);
	/**
	 * 批处理推送函件信息处理核心返回报文的接口 查询核保函对应note_id
	 * wupeixu
	 * @param uMap
	 * @return
	 */
	public String getUndwetForNoteId(Map<String, String> uMap);
	/**
	 * 批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态
	 * wupeixu
	 * @param undlist 
	 */
	public void updatePushInfoStatus(List<Map<String, String>> undlist);
	/**
	 * 获取核保函的被保人异地签名
	 * @param i
	 * @return 
	 */
	public TNoteImage getRemoteSignature(Map<String, Object> imageMap);
	/**
	 * 批处理推送函件信息处理核心返回报文的接口 成功则更改主表状态为已结束
	 * wupeixu
	 * @param cundlist
	 */
	public void uMainNoteStatus(List<Map<String, String>> cundlist);
}

