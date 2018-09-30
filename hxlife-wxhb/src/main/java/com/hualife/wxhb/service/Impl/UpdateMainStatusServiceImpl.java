package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.pojo.NoteTypeAndStatus;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.service.UpdateMainStatusService;

/**  
* @Description: 修改客户层函件主表状态 Impl
* @author zhanglong 
* @date 2017年8月31日 上午10:55:03  
*/
@Service
public class UpdateMainStatusServiceImpl implements UpdateMainStatusService{
	private final Logger logger = LoggerFactory.getLogger(UpdateMainStatusServiceImpl.class);

	@Autowired
	private ClientDao clientDao;
	
	
	@Override
	public void updateMainStatusService(String client_note_id,String noteStatus) {
		long startTime = 0;
		long endTime =0;
		
		//查询该客户的note_id下其他函件的状态，如果体检函，健康函，契调函等全部已处理，则将t_mian_note表中的函件状态调整为已处理
		List<NoteTypeAndStatus>  noteStatusList = new ArrayList<NoteTypeAndStatus>();	
		startTime =System.currentTimeMillis();
		noteStatusList = clientDao.selectOtherClientNoteStatus(client_note_id);
		endTime =System.currentTimeMillis();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),client_note_id, "查询该财务函对应的客户层其他函件的类型及状态"+(endTime-startTime)+"ms");
		boolean flag = false;
		//校验状态是否为已完成
		if(Constant.note_status_DOWN.equals(noteStatus)){
			flag = checkDownStatus(noteStatusList);
		}else if(Constant.note_status_FINISHED.equals(noteStatus)){
			flag = checkFinishedStatus(noteStatusList);
		}else{
			throw new BusinessException("传入参数错误");
		}
			
		//改变主表函件状态
		Map<Object , Object> updateMap = new HashMap<>();
		if(flag){
			updateMap.put("clientNoteId", client_note_id);
			updateMap.put("noteStatus", noteStatus);
			startTime=System.currentTimeMillis();
			clientDao.updateMainSatus(updateMap);	
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),client_note_id, "更改主表状态为已处理耗时:"+(endTime-startTime)+"ms");
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),client_note_id, "该函件note_id下其他函件均已处理,故更改主表状态--已处理");
		}
	}
	/**
	 * 判断状态是否为已处理
	 * @param noteTypeAndStatusList
	 * @return
	 */
	private boolean checkDownStatus(List<NoteTypeAndStatus>  noteTypeAndStatusList){
		for (NoteTypeAndStatus noteStatus : noteTypeAndStatusList) {
			if(Constant.client_type_HEALTH.equals(noteStatus.getNote_type())){
				if(Constant.physical_or_health_note_status_DOWN.equals(noteStatus.getNote_status())||Constant.physical_or_health_note_status_LINEDOWN.equals(noteStatus.getNote_status())){
					continue;
				}else{
					return false;
				}
			}else if(Constant.client_type_PHYSICAL.equals(noteStatus.getNote_type())){
				if(Constant.physical_or_health_note_status_DOWN.equals(noteStatus.getNote_status()) ||Constant.physical_or_health_note_status_LINEDOWN.equals(noteStatus.getNote_status())){
					continue;
				}else{
					return false;
				}
			}else if(Constant.client_type_FINA.equals(noteStatus.getNote_type())){
				if(Constant.fina_note_status_DOWN.equals(noteStatus.getNote_status())||Constant.fina_note_status_LINEDOWN.equals(noteStatus.getNote_status())  ){
					continue;
				}else{
					return false;
				}			
			}else if(Constant.client_type_SURVIVAL.equals(noteStatus.getNote_type())){
				if(Constant.survival_note_status_DOWN.equals(noteStatus.getNote_status()) ){
					continue;
				}else{
					return false;
				}
			}		
		}
		return true;
	}
	
	/**
	 * 判断状态是否为已回销
	 * @param noteTypeAndStatusList
	 * @return
	 */
	private boolean checkFinishedStatus(List<NoteTypeAndStatus>  noteTypeAndStatusList){
		for (NoteTypeAndStatus noteStatus : noteTypeAndStatusList) {
			if(Constant.client_type_HEALTH.equals(noteStatus.getNote_type())){
				if(Constant.physical_or_health_note_status_WRITEOFF.equals(noteStatus.getNote_status())){
					continue;
				}else{
					return false;
				}
			}else if(Constant.client_type_PHYSICAL.equals(noteStatus.getNote_type())){
				if(Constant.physical_or_health_note_status_WRITEOFF.equals(noteStatus.getNote_status())){
					continue;
				}else{
					return false;
				}
			}else if(Constant.client_type_FINA.equals(noteStatus.getNote_type())){
				if(Constant.fina_note_status_WRITEOFF.equals(noteStatus.getNote_status())){
					continue;
				}else{
					return false;
				}			
			}else if(Constant.client_type_SURVIVAL.equals(noteStatus.getNote_type())){
				if(Constant.survival_note_status_WRITEOFF.equals(noteStatus.getNote_status())){
					continue;
				}else{
					return false;
				}
			}		
		}
		return true;
	}

}
