package com.hualife.wxhb.integration.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.ProblemContents;
import com.hualife.wxhb.api.rest.message.request.FinaOccuInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.FinaOccuInfoResponseMessage;
import com.hualife.wxhb.domain.dto.FinaItem;
import com.hualife.wxhb.domain.entity.TNoteFinaNoteItem;
import com.hualife.wxhb.domain.entity.TNoteFinaOccuNote;
/** 
 * @author 吴培旭 
 * @description
 * @time 创建时间：2017年8月7日   
 */
@Repository
public interface FinaDao {

	/**
	 * 财务函初始化 --查询客户层中的noteId
	 * wupeixu
	 * @param finaNoteId
	 */
	String selectFinaOccuInfoNoteId(String finaNoteId);
    /**
     * 财务函初始化 --查询客户 姓名 ，代理人姓名，代理人电话，代理人编号
     * wupeixu
     * @param map
     */
	FinaOccuInfoResponseMessage selectFinaOccuInfoRespMes(Map<Object, Object> map);
    /**
     * 财务函初始化 --查询核保员特别说明，被保人证件类型，被保人证件号码，被保人电话
     * wupeixu
     * @param finaOccuInfoReqMessage
     */
	FinaOccuInfoResponseMessage selectFinaOccuInfodesc(FinaOccuInfoRequestMessage finaOccuInfoReqMessage);
    /**
     * 财务函初始化--查询具体财务问卷
     * wupeixu
     * @param finaNoteId
     */
	List<FinaItem> selectFinaQuestionnaire(String finaNoteId);
    /**
     * 财务函初始化--查询影像集合
     * wupeixu
     * @param finaMap
     * @return
     */
	List<ImageInfo> selectImages(Map<Object, Object> finaMap);	
	/**
	 * @descrption : 财务函提交的方法 --客户提交
	 * @author : linyongtao
	 * @date : 2017-08-24 10:36:00
	 * **/
	public void  finaLetterSubmitClient (Map<String , Object>map);
	/**
	 * @descrption : 财务函提交的方法 --代理人提交
	 * @author : linyongtao
	 * @date : 2017-08-24 10:36:00
	 * **/
	public void  finaLetterSubmitAgent (Map<String , Object>map);
	/**
	 * 函件note_fina_occu数据插入
	 */
	public void insertNoteFinaOccuNote(TNoteFinaOccuNote tNoteFinaOccuNote);
	
	/**
	 * 函件t_note_fina_note_item 数据插入
	 * add by zhanglong
	 */
	public void insertNoteFinaItem(List<TNoteFinaNoteItem> insertTNoteFinaNoteItemList);
	
	/**
	 * @descrption :根据note_item_id查询财务函id
	 * @author :linyongtao
	 * @date : 2017-08-24
	 * **/
	public String  selectFinaNoteId(String noteItemId);
	/**
	 * @descrption :保存财务问卷问题内容
	 * @author :linyongtao
	 * @date : 2017-08-24
	 * **/
	public void finaReportSave(List<ProblemContents> problemContents);
	/**
	 * @descrption :财务问卷提交后需要改变问卷回答状态(Y-已经完成，N-未完成)
	 * @author :linyongtao
	 * @date : 2017-08-24
	 * **/
	public void updateNoteItemStatus(Map<String , Object>map);
	/**
	 * @description : 查询高额报告书下客户提交的影像
	 * @author : linyongtao
	 * @daye : 2017.08.31
	 * **/
	public List<ImageInfo> getAgentReportImageInfo(Map<String , Object>map);	
	/**
	 * @description : 查询财务函问卷下客户提交的影像
	 * @author : linyongtao
	 * @daye : 2017.08.31
	 * **/
	public List<ImageInfo> getFinaReportImageInfo(Map<String , Object>map);
	
	/**
	 * 客户是否已完成高额业务员报告书
	 * @author : linyongtao
	 * **/
	public String  getIsFinishAgentReport(Map<String , Object>map);
	
	/**
	 * 客户是否已完成财务函问卷
	 * @author : linyongtao
	 * **/
	public String getIsFinishFinaReport(Map<String , Object>map);
	
	/**
	 * 查询客户上传的财务问卷
	 * @author : linyongtao
	 */
	public Long getFinaReportContents(String noteItemId);
	
	/**
	 * 删除客户提交的财务问卷内容
	 * @author : linyongtao
	 */
	public void deleteFinaReportContents(String noteItemId);
	
	/**
	 * 更改财务问卷的完成状态为未完成
	 * @author : linyongtao
	 */
	public void updateFinaReportStatus(Map<String , Object> updateStatusMap);
	
}