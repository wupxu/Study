package com.hualife.wxhb.common;

import org.apache.commons.lang3.StringUtils;

import com.hualife.mesiframework.core.exception.BusinessException;

/***
 * @description : 数据类型转换 
 * @author zhanyilong
 * @time : 2017-08-24
 */
public class DataConVersion {
      
	/**
	 * 数据转换
	 * 效果等同于redis缓存，将编码转成具体含义
	 * throw new BusinessException("此类型数据值域中不存储在该值");
	 * **/
	public static String dataConVersion(String key,String value){
		
		switch(key){
			case Constant.transfer_data_SEX:{
				return transferSex(value);
			}
			case Constant.transfer_data_NOTE_TYPE:{
				return transferNoteType(value);
			}
			case Constant.transfer_data_NOTE_STATUS:{
				return transferNoteStatus(value);
			}
			case Constant.transfer_data_FINA_TYPE_NAME:{
				return transferFinaTypeName(value);
			}
			case Constant.transfer_data_HEALTH_TYPE_NAME:{
				return transferHealthTypeName(value);
			}
			case Constant.transfer_data_NOTE_AGENT_STATUS:{
				return transferNoteAgentStatus(value);
			}
			case Constant.transfer_core_data_Note_TYPE_NAME:{
				return transferCoreNoteType(value);
			}
			case Constant.transfer_core_data_ID_TYPE_NAME:{
				return transferCoreIdType(value);
			}
			case Constant.transfer_core_PERIOD_TYPE_NAME:{
				return transferPeriodTypeName(value);
			}
			case Constant.transfer_core_Undwrt_Result:{
				return transferUndwrtResult(value);
			}			
			default: 
			 throw new BusinessException("此类型不存在！");
		}
	}
	
	/**
	 * 性别转换
	 * **/
	public static String transferSex(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_data_SEX_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.sex_MALE)){
				return Constant.sex_MALE_DESC;
			}else if(StringUtils.equals(value, Constant.sex_FEMALE)){
				return Constant.sex_FEMALE_DESC;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_data_SEX_DESC + "值域内！"); 
			}
		}
	}
	
	/**
	 * 函件类型
	 * **/
	public static String transferNoteType(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_data_NOTE_TYPE_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.client_type_HEALTH)){
				return Constant.client_type_HEALTH_DESC;
			}else if(StringUtils.equals(value, Constant.client_type_PHYSICAL)){
				return Constant.client_type_PHYSICAL_DESC;
			}else if(StringUtils.equals(value, Constant.client_type_FINA)){
				return Constant.client_type_FINA_DESC;
			}else if(StringUtils.equals(value, Constant.client_type_SURVIVAL)){
				return Constant.client_type_SURVIVAL_DESC;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_data_NOTE_TYPE_DESC + "值域内！"); 
			}
		}
	}
	/**
	 * 核心函件类型 
	 */
	public static String transferCoreNoteType(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_data_NOTE_TYPE_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.note_from_core_type_FINAOCCU)){
				return Constant.client_type_FINA_DESC;
			}else if(StringUtils.equals(value, Constant.note_from_core_type_PHYSICAL)){
				return Constant.client_type_PHYSICAL_DESC;
			}else if(StringUtils.equals(value, Constant.note_from_core_type_HEALTH)){
				return Constant.client_type_HEALTH_DESC;
			}else if(StringUtils.equals(value, Constant.note_from_core_type_SURVIVAL)){
				return Constant.client_type_SURVIVAL_DESC;
			}else if(StringUtils.equals(value, Constant.note_from_core_type_PROBLEM)){
				return Constant.note_type_PROBLEM_DESC;
			}else if(StringUtils.equals(value, Constant.note_from_core_type_UNDWRT)){
				return Constant.note_type_UNDWR_DESC;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_data_NOTE_TYPE_DESC + "值域内！"); 
			}
		}
	}
	/**
	 * 函件状态--代理人查看状态
	 * **/
	public static String transferNoteAgentStatus(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_data_NOTE_AGENT_STATUS_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.note_agent_status_UNPUSH)){
				return Constant.note_agent_status_UNPUSH_DESC;
			}else if(StringUtils.equals(value, Constant.note_agent_status_PUSHING)){
				return Constant.note_agent_status_PUSHING_DESC;
			}else if(StringUtils.equals(value, Constant.note_agent_status_PRODUCTING)){
				return Constant.note_agent_status_PRODUCTING_DESC;
			}else if(StringUtils.equals(value, Constant.note_agent_status_DEALING)){
				return Constant.note_agent_status_DEALING_DESC;
			}else if(StringUtils.equals(value, Constant.note_agent_status_IMAGE_FAILED)){
				return Constant.note_agent_status_IMAGE_FAILED_DESC;
			}else if(StringUtils.equals(value, Constant.note_agent_status_FINISH)){
				return Constant.note_agent_status_FINISH_DESC;
			}else if(StringUtils.equals(value, Constant.note_agent_status_LOADING)){
				return Constant.note_agent_status_LOADING_DESC;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_data_NOTE_AGENT_STATUS_DESC + "值域内！"); 
			}
		}
	}
	
	/**
	 * 函件状态--主表查看状态--函件列表页查看状态
	 * **/
	public static String transferNoteStatus(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_data_NOTE_STATUS_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.note_status_UNPUSH)){
				return Constant.note_status_UNPUSH_DESC;
			}else if(StringUtils.equals(value, Constant.note_status_PUSHING)){
				return Constant.note_status_PUSHING_DESC;
			}else if(StringUtils.equals(value, Constant.note_status_WAITINGPRINT)){
				return Constant.note_status_WAITINGPRINT_DESC;
			}else if(StringUtils.equals(value, Constant.note_status_DOWN)){
				return Constant.note_status_DOWN_DESC;
			}else if(StringUtils.equals(value, Constant.note_status_FINISHED)){
				return Constant.note_status_FINISHED_DESC;
			}else if(StringUtils.equals(value, Constant.note_status_NOTQUALIFIED)){
				return Constant.note_status_NOTQUALIFIED_DESC;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_data_NOTE_STATUS_DESC + "值域内！"); 
			}
		}
	}
	
	/**
	 * 健康问卷名称
	 * **/
	public static String transferHealthTypeName(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_data_HEALTH_TYPE_NAME_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.health_note_item_type_DIGESTIVE_SYSTEM_DISEASE)){
				return Constant.health_note_item_type_DIGESTIVE_SYSTEM_DISEASE_DESC;
			}else if(StringUtils.equals(value, Constant.health_note_item_type_FEMALE_DISEASE)){
				return Constant.health_note_item_type_FEMALE_DISEASE_DESC;
			}else if(StringUtils.equals(value, Constant.health_note_item_type_HYPERTENSION)){
				return Constant.health_note_item_type_HYPERTENSION_DESC;
			}else if(StringUtils.equals(value, Constant.health_note_item_type_LIVER)){
				return Constant.health_note_item_type_LIVER_DESC;
			}else if(StringUtils.equals(value, Constant.health_note_item_type_PAST_ILLNESSPAST)){
				return Constant.health_note_item_type_PAST_ILLNESSPAST_DESC;
			}else if(StringUtils.equals(value, Constant.health_note_item_type_THYROID_DISEASE)){
				return Constant.health_note_item_type_THYROID_DISEASE_DESC;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_data_HEALTH_TYPE_NAME_DESC + "值域内！"); 
			}
		}
	}
	
	/**
	 * 财务问卷名称
	 * **/
	public static String transferPeriodTypeName(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_core_PERIOD_TYPE_NAME_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.period_type_YEAR)){
				return Constant.period_type_YEAR_DESC;
			}else if(StringUtils.equals(value, Constant.period_type_MONTH)){
				return Constant.period_type_MONTH_DESC;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_core_PERIOD_TYPE_NAME_DESC + "值域内！"); 
			}
		}
	}
	
	/**
	 * 财务问卷名称
	 * **/
	public static String transferFinaTypeName(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_data_FINA_TYPE_NAME_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.finl_note_item_type_FINL)){
				return Constant.finl_note_item_type_FINL_DESC;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_data_FINA_TYPE_NAME_DESC + "值域内！"); 
			}
		}
	}
	
	/**
	 * 证件类型名称
	 * **/
	public static String transferCoreIdType(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_core_data_ID_TYPE_NAME_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.id_type_CERTIFICATE_OF_OFFICERS)){
				return Constant.id_type_CERTIFICATE_OF_OFFICERS_NAME;
			}else if(StringUtils.equals(value, Constant.id_type_HONG_KONG_AND_MACAO_PASS)){
				return Constant.id_type_HONG_KONG_AND_MACAO_PASS_NAME;
			}else if(StringUtils.equals(value, Constant.id_type_IDENTIFICATION_CARD)){
				return Constant.id_type_IDENTIFICATION_CARD_NAME;
			}else if(StringUtils.equals(value, Constant.id_type_LICENSE)){
				return Constant.id_type_LICENSE_NAME;
			}else if(StringUtils.equals(value, Constant.id_type_MTP)){
				return Constant.id_type_MTP_NAME;
			}else if(StringUtils.equals(value, Constant.id_type_PASSPORT)){
				return Constant.id_type_PASSPORT_NAME;
			}else if(StringUtils.equals(value, Constant.id_type_REGISTER)){
				return Constant.id_type_REGISTER_NAME;
			}else if(StringUtils.equals(value, Constant.id_type_SOLDIER_CARD)){
				return Constant.id_type_SOLDIER_CARD_NAME;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_core_data_ID_TYPE_NAME_DESC + "值域内！"); 
			}
		}
	}
	/**
	 * 核保结论
	 * **/
	public static String transferUndwrtResult(String value){
		if(StringUtils.isBlank(value)){
			throw new BusinessException(Constant.transfer_core_Undwrt_Result_DESC + "不能为空！");
		}else{
			if(StringUtils.equals(value, Constant.undwrt_result_A)){
				return Constant.undwrt_result_A_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_B)){
				return Constant.undwrt_result_B_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_C)){
				return Constant.undwrt_result_C_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_D)){
				return Constant.undwrt_result_D_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_E)){
				return Constant.undwrt_result_E_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_F)){
				return Constant.undwrt_result_F_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_G)){
				return Constant.undwrt_result_G_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_H)){
				return Constant.undwrt_result_H_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_I)){
				return Constant.undwrt_result_I_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_1)){
				return Constant.undwrt_result_1_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_2)){
				return Constant.undwrt_result_2_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_3)){
				return Constant.undwrt_result_3_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_4)){
				return Constant.undwrt_result_4_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_5)){
				return Constant.undwrt_result_5_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_6)){
				return Constant.undwrt_result_6_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_7)){
				return Constant.undwrt_result_7_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_8)){
				return Constant.undwrt_result_8_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_9)){
				return Constant.undwrt_result_9_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_10)){
				return Constant.undwrt_result_10_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_11)){
				return Constant.undwrt_result_11_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_12)){
				return Constant.undwrt_result_12_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_13)){
				return Constant.undwrt_result_13_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_14)){
				return Constant.undwrt_result_14_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_15)){
				return Constant.undwrt_result_15_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_16)){
				return Constant.undwrt_result_16_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_17)){
				return Constant.undwrt_result_17_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_18)){
				return Constant.undwrt_result_18_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_19)){
				return Constant.undwrt_result_19_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_20)){
				return Constant.undwrt_result_20_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_21)){
				return Constant.undwrt_result_21_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_99)){
				return Constant.undwrt_result_99_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_J)){
				return Constant.undwrt_result_J_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_K)){
				return Constant.undwrt_result_K_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_L)){
				return Constant.undwrt_result_L_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_M)){
				return Constant.undwrt_result_M_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_N)){
				return Constant.undwrt_result_N_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_O)){
				return Constant.undwrt_result_O_DESC;
			}else if(StringUtils.equals(value, Constant.undwrt_result_P)){
				return Constant.undwrt_result_P_DESC;
			}else{
				throw new BusinessException(value + "不在 " + Constant.transfer_core_Undwrt_Result_DESC + "值域内！"); 
			}
		}
	}
	
	/**
	 * 测试
	 * **/
	public static void main(String[] args){
		System.out.println(DataConVersion.dataConVersion(Constant.transfer_core_data_ID_TYPE_NAME, Constant.id_type_SOLDIER_CARD));
		
	}
	
}
