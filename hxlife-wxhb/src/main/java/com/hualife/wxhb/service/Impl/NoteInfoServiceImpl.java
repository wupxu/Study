package com.hualife.wxhb.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.request.NoteInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NoteInfoResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.service.NoteInfoService;

/**
 * @description:函件初始化
 * @author yangpeixin
 * @date 2017-08-04
 */
@Service
public class NoteInfoServiceImpl implements NoteInfoService {
	private final Logger logger = LoggerFactory.getLogger(NoteInfoServiceImpl.class);
	@Autowired
	private ClientDao clientDao;

	/**
	 * 函件初始化
	 */
	@Override
	public NoteInfoResponseMessage noteClientInfo(NoteInfoRequestMessage noteInfoRequestMessage) {
		// 检测入参
		checkResData(noteInfoRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteInfoRequestMessage.getNote_id(), "开始检查请求报文");
		// 查询数据
		return this.findData(noteInfoRequestMessage);
	}

	/**
	 * 查询信息
	 */
	public NoteInfoResponseMessage findData(NoteInfoRequestMessage noteInfoRequestMessage) {
		long startTime = 0;
		long endTime = 0;
		NoteInfoResponseMessage noteInfoResponseMessage = new NoteInfoResponseMessage();
		// 客户信息查询
		startTime = System.currentTimeMillis();
		noteInfoResponseMessage = clientDao.findNoteInfo(noteInfoRequestMessage.getNote_id());
		if(noteInfoResponseMessage==null){
			throw new BusinessException("客户查询信息有误");
		}
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"noteid:" + noteInfoRequestMessage.getNote_id(),
				"查询数据耗时:" + (endTime - startTime) + "ms");
		noteInfoResponseMessage.setAppid(Constant.appid);
		// 查询所有信息
		List<TNoteClientNote> tNoteClientNoteList = clientDao.getnoteClientInfo(noteInfoRequestMessage.getNote_id());
		for (TNoteClientNote tNoteClientNote : tNoteClientNoteList) {
			if (tNoteClientNote.getNoteType().equals(Constant.client_type_HEALTH)) {			
				noteInfoResponseMessage.getLetters().getHealth().setHealth_note_id(tNoteClientNote.getClientNoteId());
				noteInfoResponseMessage.getLetters().getHealth().setNote_client_status(tNoteClientNote.getNoteClientStatus());
				noteInfoResponseMessage.getLetters().getHealth().setNote_client_status_desc(tNoteClientNote.getNoteClientStatusDesc());
				noteInfoResponseMessage.getLetters().getHealth().setNote_type(tNoteClientNote.getNoteType());
				noteInfoResponseMessage.getLetters().getHealth().setNote_type_dec(Constant.client_type_HEALTH_DESC);
			}
			if (tNoteClientNote.getNoteType().equals(Constant.client_type_PHYSICAL)) {
				noteInfoResponseMessage.getLetters().getPhysical().setNote_client_status(tNoteClientNote.getNoteClientStatus());
				noteInfoResponseMessage.getLetters().getPhysical().setNote_client_status_desc(tNoteClientNote.getNoteClientStatusDesc());
				noteInfoResponseMessage.getLetters().getPhysical().setNote_type(tNoteClientNote.getNoteType());
				noteInfoResponseMessage.getLetters().getPhysical().setNote_type_dec(Constant.client_type_PHYSICAL_DESC);
				noteInfoResponseMessage.getLetters().getPhysical().setPhysical_note_id(tNoteClientNote.getClientNoteId());
				String client_choose_type = clientDao.selectChooseType(tNoteClientNote.getClientNoteId());
				if (client_choose_type != null) {
					if (client_choose_type.equals(Constant.physical_client_choose_type_WITHOUT_INSPECTION)) {
						noteInfoResponseMessage.getLetters().getPhysical().setClient_choose_type(Constant.answer_Y);
					} else {
						noteInfoResponseMessage.getLetters().getPhysical().setClient_choose_type(Constant.answer_N);
					}
				}
				noteInfoResponseMessage.getLetters().getPhysical().setClient_choose_type(Constant.answer_N);
			}
			if (tNoteClientNote.getNoteType().equals(Constant.client_type_SURVIVAL)) {
				noteInfoResponseMessage.getLetters().getSurvival().setNote_client_status(tNoteClientNote.getNoteClientStatus());
				noteInfoResponseMessage.getLetters().getSurvival().setNote_client_status_desc(tNoteClientNote.getNoteClientStatusDesc());
				noteInfoResponseMessage.getLetters().getSurvival().setNote_type(tNoteClientNote.getNoteType());
				noteInfoResponseMessage.getLetters().getSurvival().setNote_type_dec(Constant.client_type_SURVIVAL_DESC);
			}
			if (tNoteClientNote.getNoteType().equals(Constant.client_type_FINA)) {
				noteInfoResponseMessage.getLetters().getFina().setFina_note_id(tNoteClientNote.getClientNoteId());
				noteInfoResponseMessage.getLetters().getFina().setNote_client_status(tNoteClientNote.getNoteClientStatus());
				noteInfoResponseMessage.getLetters().getFina().setNote_client_status_desc(tNoteClientNote.getNoteClientStatusDesc());
				noteInfoResponseMessage.getLetters().getFina().setNote_type(tNoteClientNote.getNoteType());
				noteInfoResponseMessage.getLetters().getFina().setNote_type_dec(Constant.client_type_FINA_DESC);
			}
		}

		return noteInfoResponseMessage;

	}

	/**
	 * 检查请求报文
	 **/
	public void checkResData(NoteInfoRequestMessage noteInfoRequestMessage) {
		Assert.notNull(noteInfoRequestMessage, Constant.error_DESC_BODY_NULL);
		Assert.notEmpty(noteInfoRequestMessage.getNote_id(), "函件id（note_id）不能为空");

	}
}
