package com.hualife.wxhb.common;

import java.io.File;

/**
 * 常量类
 * @author zhanyl
 */
public class Constant {
	/**
	 * 函件类型-客户层函件
	 * **/
	public static  final String note_type_CLIENT = "01";
	/**
	 * 函件类型-问题件
	 * **/
	public static  final String note_type_PROBLEM = "02";
	/**
	 * 函件类型-问题件
	 * **/
	public static  final String note_type_PROBLEM_DESC = "问题件";
	/**
	 * 函件类型-核保函
	 * **/
	public static  final String note_type_UNDWRT = "03";
	/**
	 * 函件类型-核保函
	 * **/
	public static  final String note_type_UNDWR_DESC = "核保函";
	
	/**
	 * 函件下发方式-电子函件
	 * **/
	public static  final String note_push_type_LINED = "01";
	/**
	 * 函件下发方式-自己打印
	 * **/
	public static  final String note_push_type_SELF_PRINT = "02";
	/**
	 * 函件下发方式-机构打印
	 * **/
	public static  final String note_push_type_BRANCH_PRINT = "03";
	/**
	 * 函件下发方式-客户未选择下发方式
	 * **/
	public static  final String note_push_type_NOSELECT_PRINT = "04";
	
	/**
	 * 客户层函件类型-健康函
	 * **/
	public static  final String client_type_HEALTH = "03";
	/**
	 * 客户层函件类型-健康函
	 * **/
	public static  final String client_type_HEALTH_DESC = "健康函";
	/**
	 * 客户层函件类型-体检函
	 * **/
	public static  final String client_type_PHYSICAL  = "02";
	/**
	 * 客户层函件类型-体检函
	 * **/
	public static  final String client_type_PHYSICAL_DESC  = "体检函";
	/**
	 * 客户层函件类型-财务函 
	 * **/
	public static  final String client_type_FINA = "01";
	/**
	 * 客户层函件类型-财务函
	 * **/
	public static  final String client_type_FINA_DESC  = "财务函";
	/**
	 * 客户层函件类型-契调函 
	 * **/
	public static  final String client_type_SURVIVAL = "04";
	/**
	 * 客户层函件类型-契调函 
	 * **/
	public static  final String client_type_SURVIVAL_DESC = "契调函";
	
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 未转发
	 * **/
	public static  final String note_status_UNPUSH = "01"; 
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 未转发
	 * **/
	public static  final String note_status_UNPUSH_DESC = "未转发"; 
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 已转发处理中
	 * **/
	public static  final String note_status_PUSHING = "02";
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 已转发处理中
	 * **/
	public static  final String note_status_PUSHING_DESC = "已转发";
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 已处理
	 * **/
	public static  final String note_status_DOWN = "03";
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 已处理
	 * **/
	public static  final String note_status_DOWN_DESC = "已处理";
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 已结束
	 * **/
	public static  final String note_status_FINISHED = "04";
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 已结束
	 * **/
	public static  final String note_status_FINISHED_DESC = "已结束";
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 待打印
	 * **/
	public static  final String note_status_WAITINGPRINT = "05";
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 待打印
	 * **/
	public static  final String note_status_WAITINGPRINT_DESC = "待打印";
	
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 次品单
	 * **/
	public static  final String note_status_NOTQUALIFIED = "06";
	/**
	 * 函件任务处理状态 t_note_main.note_status
	 * 次品单
	 * **/
	public static  final String note_status_NOTQUALIFIED_DESC = "次品单";
	
	/**
	 * 渠道-营销个险
	 * **/
	public static  final String channel_type_PERSON = "02";
	/**
	 * 渠道-营销收展
	 * **/
	public static  final String channel_type_DEVELOPMENT = "05";
	/**
	 * 渠道-中介
	 * **/
	public static  final String channel_type_AGENCY = "07";
	
	/**
	 * 是
	 * **/
	public static  final String answer_Y = "Y";
	/**
	 * 否
	 * **/
	public static  final String answer_N = "N";
	
	/**
	 * 问题件业务处理状态 
	 * 未转发
	 * **/
	public static  final String problem_note_status_UNPUSH = "01";
	public static  final String problem_note_status_UNPUSH_DESC = "未转发";

	/**
	 * 问题件业务处理状态 
	 * 已转发处理中
	 * **/
	public static  final String problem_note_status_PUSHING = "02";
	public static  final String problem_note_status_PUSHING_DESC = "已转发处理中";

	/**
	 * 问题件业务处理状态 
	 * 已处理
	 * **/
	public static  final String problem_note_status_DOWN = "03";
	public static  final String problem_note_status_DOWN_DESC = "已处理";

	/**
	 * 问题件业务处理状态 
	 * 次品单处理
	 * **/
	public static  final String problem_note_status_IMAGE_FAILED = "05";
	public static  final String problem_note_status_IMAGE_FAILED_DESC = "次品单处理";

	/**
	 * 问题件业务处理状态 
	 * 已回销
	 * **/
	public static  final String problem_note_status_WRITEOFF = "06";
	public static  final String problem_note_status_WRITEOFF_DESC = "已回销";
	/**
	 * 问题件业务处理状态 
	 * 已作废
	 * **/
	public static  final String problem_note_status_CANCEL = "07";
	public static  final String problem_note_status_CANCEL_DESC = "已作废";

	/**
	 * 核保函业务处理状态 
	 * 未转发
	 * **/
	public static  final String undwrt_note_status_UNPUSH = "01";
	public static  final String undwrt_note_status_UNPUSH_DESC = "未转发";

	/**
	 * 核保函业务处理状态 
	 * 已转发处理中
	 * **/
	public static  final String undwrt_note_status_PUSHING = "02";
	public static  final String undwrt_note_status_PUSHING_DESC = "已转发处理中";

	/**
	 * 核保函业务处理状态 
	 * 已处理
	 * **/
	public static  final String undwrt_note_status_DOWN = "03";
	public static  final String undwrt_note_status_DOWN_DESC = "已处理";

	/**
	 * 核保函业务处理状态 
	 * 已回销
	 * **/
	public static  final String undwrt_note_status_WRITEOFF = "06";
	public static  final String undwrt_note_status_WRITEOFF_DESC = "已回销";

	
	/**
	 * 体检/健康问卷业务处理状态 
	 * 未转发
	 * **/
	public static  final String physical_health_note_status_UNPUSH = "01";
	public static  final String physical_health_note_status_UNPUSH_DESC = "未转发";


	/**
	 * 体检/健康问卷业务处理状态 
	 * 已转发处理中
	 * **/
	public static  final String physical_or_health_note_status_PUSHING = "02";
	public static  final String physical_or_health_note_status_PUSHING_DESC = "已转发处理中";

	/**
	 * 体检/健康问卷业务处理状态 
	 * 已处理
	 * **/
	public static  final String physical_or_health_note_status_DOWN = "03";
	public static  final String physical_or_health_note_status_DOWN_DESC = "已处理";

	/**
	 * 体检/健康问卷业务处理状态 
	 * 已授权代理人处理
	 * **/
	public static  final String physical_or_health_note_status_AUTHORIZATION = "04";
	public static  final String physical_or_health_note_status_AUTHORIZATION_DESC = "已授权代理人处理";

	/**
	 * 体检/健康问卷业务处理状态 
	 * 次品单处理
	 * **/
	public static  final String physical_or_health_note_status_IMAGE_FAILED= "05";
	public static  final String physical_or_health_note_status_IMAGE_FAILED_DESC= "次品单处理";

	/**
	 * 体检/健康问卷业务处理状态 
	 * 已回销
	 * **/
	public static  final String physical_or_health_note_status_WRITEOFF= "06";
	public static  final String physical_or_health_note_status_WRITEOFF_DESC= "已回销";

	/**
	 * 体检/健康问卷业务处理状态 
	 * 已作废
	 * **/
	public static  final String physical_or_health_note_status_CANCEL= "07";
	public static  final String physical_or_health_note_status_CANCEL_DESC= "已作废";

	/**
	 * 体检/健康问卷业务处理状态 
	 * 线下处理
	 * **/
	public static  final String physical_or_health_note_status_LINEDOWN= "08";
	public static  final String physical_or_health_note_status_LINEDOWN_DESC= "线下处理";

	
	/**
	 * 财务问卷业务处理状态 
	 * 未转发
	 * **/
	public static  final String fina_note_status_UNPUSH= "01";
	public static  final String fina_note_status_UNPUSH_DESC= "未转发";

	/**
	 * 财务问卷业务处理状态 
	 * 已转发处理中
	 * **/
	public static  final String fina_note_status_PUSHING= "02";
	public static  final String fina_note_status_PUSHING_DESC= "已转发处理中";

	/**
	 * 财务问卷业务处理状态 
	 * 已处理
	 * **/
	public static  final String fina_note_status_DOWN= "03";
	public static  final String fina_note_status_DOWN_DESC= "已处理";

	/**
	 * 财务问卷业务处理状态 
	 * 已授权代理人处理
	 * **/
	public static  final String fina_note_status_AUTHORIZATION= "04";
	public static  final String fina_note_status_AUTHORIZATION_DESC= "已授权代理人处理";

	/**
	 * 财务问卷业务处理状态 
	 * 已回销
	 * **/
	public static  final String fina_note_status_WRITEOFF= "06";
	public static  final String fina_note_status_WRITEOFF_DESC= "已回销";

	/**
	 * 财务问卷业务处理状态 
	 * 线下处理
	 * **/
	public static  final String fina_note_status_LINEDOWN= "08";
	public static  final String fina_note_status_LINEDOWN_DESC= "线下处理";

	/**
	 * 契调函业务处理状态 
	 * 未领取
	 * **/
	public static  final String survival_note_status_UNRECEIVE= "01";
	public static  final String survival_note_status_UNRECEIVE_DESC= "未领取";
	/**
	 * 契调函业务处理状态 
	 * 已领取处理中
	 * **/
	public static  final String survival_note_status_RECEIVEING= "02";
	public static  final String survival_note_status_RECEIVEING_DESC= "已领取处理中";

	/**
	 * 契调函业务处理状态 
	 * 已处理
	 * **/
	public static  final String survival_note_status_DOWN= "03";
	public static  final String survival_note_status_DOWN_DESC= "已处理";

	/**
	 * 契调函业务处理状态 
	 * 已回销
	 * **/
	public static  final String survival_note_status_WRITEOFF= "06";
	public static  final String survival_note_status_WRITEOFF_DESC= "已回销";

	/**
	 * 问题件对象-代理人
	 * **/
	public static  final String problem_object_AGENT= "03";
	/**
	 * 问题件对象-投保人
	 * **/
	public static  final String problem_object_APPLICANTION= "05";
	/**
	 * 问题件对象-被保人
	 * **/

	public static String problem_object_INSURED= "06";
	/**
	 * 问题件对象状态
	 * 未转发
	 */
	public static String problem_object_status_UNPUSH= "01";
	/**
	 * 问题件对象状态
	 * 已转发
	 */
	public static String problem_object_status_PUSH= "02";
	/**
	 * 问题件对象状态
	 * 未完成
	 */
	public static String problem_object_status_UNFINISHED= "03";
	/**
	 * 问题件对象状态
	 * 已完成
	 */
	public static String problem_object_status_FINISHED= "04";
	
	/**
	 * 客户层函件。代理人查看状态  t_note_client_note.note_agent_status
	 * 未转发
	 * **/
	public static  final String note_agent_status_UNPUSH = "01"; 
	public static  final String note_agent_status_UNPUSH_DESC = "未转发"; 

	/**
	 * 客户层函件。代理人查看状态  t_note_client_note.note_agent_status
	 * 已转发处理中
	 * **/
	public static  final String note_agent_status_PUSHING = "02";
	public static  final String note_agent_status_PUSHING_DESC = "已转发";

	/**
	 * 客户层函件。代理人查看状态  t_note_client_note.note_agent_status
	 * 已完成
	 * **/
	public static  final String note_agent_status_FINISH = "03";
	public static  final String note_agent_status_FINISH_DESC = "已完成";

	/**
	 * 客户层函件。代理人查看状态  t_note_client_note.note_agent_status
	 * 处理函件
	 * **/
	public static  final String note_agent_status_DEALING = "04";
	public static  final String note_agent_status_DEALING_DESC = "处理函件";

	/**
	 * 客户层函件。代理人查看状态  t_note_client_note.note_agent_status
	 * 次品单处理
	 * **/
	public static  final String note_agent_status_IMAGE_FAILED = "05";
	public static  final String note_agent_status_IMAGE_FAILED_DESC = "次品单处理";

	/**
	 * 客户层函件。代理人查看状态  t_note_client_note.note_agent_status
	 * 系统生成函件中
	 * **/
	public static  final String note_agent_status_PRODUCTING = "06";
	public static  final String note_agent_status_PRODUCTING_DESC = "系统生成函件中";

	/**
	 * 客户层函件。代理人查看状态  t_note_client_note.note_agent_status
	 * 下载函件
	 * **/
	public static  final String note_agent_status_LOADING = "07";
	public static  final String note_agent_status_LOADING_DESC = "下载函件";

	
	/**
	 * 客户层函件。客户查看状态  t_note_client_note.note_client_status
	 * 待完成
	 * **/
	public static  final String note_client_status_UNFINISHED = "01";
	public static  final String note_client_status_UNFINISHED_DESC = "待完成";

	/**
	 * 客户层函件。客户查看状态  t_note_client_note.note_client_status
	 * 已完成
	 * **/
	public static  final String note_client_status_FININSHED = "02";
	public static  final String note_client_status_FININSHED_DESC = "已完成";

	/**
	 * 客户层函件。客户查看状态  t_note_client_note.note_client_status
	 * 系统生成中
	 * **/
	public static  final String note_client_status_PRODUCTING = "03";
	public static  final String note_client_status_PRODUCTING_DESC = "系统生成中";

	/**
	 * 客户层函件。客户查看状态  t_note_client_note.note_client_status
	 * 下载函件
	 * **/
	public static  final String note_client_status_LOADING = "04";
	public static  final String note_client_status_LOADING_DESC = "下载函件";

	/**
	 * 是否为次品单  is_not_qualified_note
	 * 是次品单--Y
	 * **/
	public static final String is_not_qualified_note_Y = "Y";
	
	/**
	 * 是否为次品单  is_not_qualified_note
	 * 不是次品单--N
	 * **/
	public static final String is_not_qualified_note_N = "N";
	
	
	/**
	 * 性别-男
	 * **/
	public static  final String sex_MALE = "0";
	/**
	 * 性别-男
	 * **/
	public static  final String sex_MALE_DESC = "男";
	/**
	 * 性别-女
	 * **/
	public static  final String sex_FEMALE = "1";
	/**
	 * 性别-女
	 * **/
	public static  final String sex_FEMALE_DESC = "女";
	
	/**
	 * 健康问卷类型-肝脏疾病调查问卷
	 * **/
	public static  final String health_note_item_type_LIVER = "4";
	/**
	 * 健康问卷类型-肝脏疾病调查问卷
	 * **/
	public static  final String health_note_item_type_LIVER_DESC = "肝脏疾病调查问卷";
	/**
	 * 健康问卷类型-高血压调查问卷
	 * **/
	public static  final String health_note_item_type_HYPERTENSION = "2";
	/**
	 * 健康问卷类型-高血压调查问卷
	 * **/
	public static  final String health_note_item_type_HYPERTENSION_DESC = "高血压调查问卷";
	/**
	 * 健康问卷类型-既往疾病调查问卷
	 * **/
	public static  final String health_note_item_type_PAST_ILLNESSPAST = "1";
	/**
	 * 健康问卷类型-既往疾病调查问卷
	 * **/
	public static  final String health_note_item_type_PAST_ILLNESSPAST_DESC = "既往疾病调查问卷";
	/**
	 * 健康问卷类型-甲状腺疾病调查问卷
	 * **/
	public static  final String health_note_item_type_THYROID_DISEASE = "11";
	/**
	 * 健康问卷类型-甲状腺疾病调查问卷
	 * **/
	public static  final String health_note_item_type_THYROID_DISEASE_DESC = "甲状腺疾病调查问卷";
	/**
	 * 健康问卷类型-女性疾病调查问卷
	 * **/
	public static  final String health_note_item_type_FEMALE_DISEASE = "5";
	/**
	 * 健康问卷类型-女性疾病调查问卷
	 * **/
	public static  final String health_note_item_type_FEMALE_DISEASE_DESC = "女性疾病调查问卷";
	/**
	 * 健康问卷类型-消化系统疾病调查问卷
	 * **/
	public static  final String health_note_item_type_DIGESTIVE_SYSTEM_DISEASE = "9";
	/**
	 * 健康问卷类型-消化系统疾病调查问卷
	 * **/
	public static  final String health_note_item_type_DIGESTIVE_SYSTEM_DISEASE_DESC = "消化系统疾病调查问卷";
	
	/**
	 * 高额业务员报告书
	 * **/
	public static final String note_agent_report_CLIENT="05";
	
	/**
	 * 财务问卷类型-财务问卷
	 * **/
	public static  final String finl_note_item_type_FINL = "6";
	/**
	 * 财务问卷类型-财务问卷
	 * **/
	public static  final String finl_note_item_type_FINL_DESC = "财务问卷";
	
	/**
	 * 体检函-客户选择处理体检方式
	 * 01-免陪检
	 * **/
	public static  final String physical_client_choose_type_WITHOUT_INSPECTION = "01";
	/**
	 * 体检函-客户选择处理体检方式
	 * 02-陪检
	 * **/
	public static  final String physical_client_choose_type_INSPECTION = "02";
	/**
	 * 体检函-客户选择处理体检方式
	 * 03-自己上传，
	 * **/
	public static  final String physical_client_choose_type_SELF_UPLOAD_IMAGE = "03";
	/**
	 * 体检函-客户选择处理体检方式
	 * 04-转交代理人上传
	 * **/
	public static  final String physical_client_choose_type_TRANSFER_TO_AGENT = "04";
		
	/**
	 * 健康函-财务函提交/客户处理--自己上传
	 * 01-自己上传
	 * **/
	public static  final String client_note_client_choose_type_SELF_UPLOAD_IMAGE = "01";
	/**
	 * 健康函-财务函提交/客户处理--转交代理人
	 * 02-转交代理人
	 * **/
	public static  final String client_note_client_choose_type_TRANSFER_TO_BRANCH = "02";
		
	/**
	 * 健康函-财务函/客户选择转交代理人处理--代理人选择处理体检方式
	 * 03-自己上传--代理人自己上传
	 * **/
	public static  final String client_note_agent_choose_type_SELF_UPLOAD_IMAGE = "03";
	/**
	 * 健康函-财务函/客户选择转交代理人处理--代理人选择处理体检方式
	 * 04-机构处理
	 * **/
	public static  final String client_note_agent_choose_type_TRANSFER_TO_BRANCH = "04";
	
	/**
	 * 体检函-健康函-财务函/函件提交人员类型--是客户/代理人在提交
	 * 01-客户在提交--调用接口
	 * **/
	public static  final String client_note_CLIENT_SUBMID = "01";
	/**
	 * 体检函-健康函-财务函/函件提交人员类型--是客户/代理人在提交
	 * 02-转交代理人--代理人提交--调用接口
	 * **/
	public static  final String client_note_AGENT_SUBMID = "02";
		
	/**
	 * 函件子问卷处理状态
	 * 待处理
	 * **/
	public static  final String child_note_status_UNFINISH = "01";
	/**
	 * 函件子问卷处理状态
	 * 已完成
	 * **/
	public static  final String child_note_status_FINISHED = "02";
	
	/**
	 * 核保函类型--次标准体（加费、除外）
	 * **/
	public static  final String undwrt_note_type_UNSTANDARD  = "1";
	/**
	 * 核保函类型--延期拒保
	 * **/
	public static  final String undwrt_note_type_POSTPONED_CANCEL  = "2";
	
	/**
	 * 核保函客户同意结果-同意
	 * **/
	public static  final String undwrt_note_type_AGREE  = "01";
	/**
	 * 核保函客户同意结果-拒绝
	 * **/
	public static  final String undwrt_note_type_REFUSE  = "02";
	
	/**
	 * 核保函险种结论-加费
	 * **/
	public static  final String undwrt_product_result_ADD  = "01";
	/**
	 * 核保函险种结论-除外
	 * **/
	public static  final String undwrt_product_result_EXCLUSTION  = "02";
	/**
	 * 核保函险种结论-消减保额
	 * **/
	public static  final String undwrt_product_result_CUT_FACE  = "03";
	/**
	 * 核保函险种结论-加费+除外
	 * **/
	public static  final String undwrt_product_result_ADD_AND_EXCLUSTION  = "04";
	/**
	 * 核保函险种结论-拒保
	 * **/
	public static  final String undwrt_product_result_CANCEL  = "05";
	/**
	 * 核保函险种结论-延期
	 * **/
	public static  final String undwrt_product_result_POSTPONED  = "06";
	
	/**
	 * 是否有效-有效
	 * **/
	public static  final String valid_Y  = "Y";
	/**
	 * 是否有效-失效
	 * **/
	public static  final String valid_N  = "N";
	/**
	 * 问卷问答状态-已完成
	 * **/
	public static  final String  item_status_Y = "Y";
	/**
	 * 问卷问答状态-未完成
	 * **/
	public static  final String  item_status_N = "N";	
	/**
	 * 上传图片类型 image_type
	 * 健康函--病例资料--010001
	 * **/ 
	public static final String image_type_Health_UPLOAD_CASE_DATA_PICTURES = "010001";
	
	/**
	 * 上传图片类型 image_type
	 * 健康函--肝脏疾病调查问卷   -签名--010100  LIVER 
	 * **/ 
	public static final String image_type_Health_LIVER_TAKE_PICTURES = "010100";
	
	/**
	 * 上传图片类型 image_type
	 * 健康函--肝脏疾病调查问卷   -上传图片--010101
	 * **/ 
	public static final String image_type_Health_LIVER_TAKE_SIGNATURE = "010101";
	/**
	 * 上传图片类型 image_type
	 * 健康函--肝脏疾病调查问卷   -生成图片--010199
	 * **/ 
	public static final String image_type_Health_LIVER_CREATE_PICTURES = "010199";
	/**
	 * 上传图片类型 image_type
	 * 健康函--高血压调查问卷   -签名--010200  LIVER 
	 * **/ 
	public static final String image_type_Health_HYPERTENSION_TAKE_PICTURES = "010200";
	
	/**
	 * 上传图片类型 image_type
	 * 健康函--高血压调查问卷   -上传图片--010201
	 * **/ 
	public static final String image_type_Health_HYPERTENSION_TAKE_SIGNATURE = "010201";
	/**
	 * 上传图片类型 image_type
	 * 健康函--高血压调查问卷   -生成图片--010299
	 * **/ 
	public static final String image_type_Health_HYPERTENSION_CREATE_PICTURES = "010299";
	/**
	 * 上传图片类型 image_type
	 * 健康函--既往疾病调查问卷   -签名--010300  LIVER 
	 * **/ 
	public static final String image_type_Health_PAST_ILLNESSPAST_TAKE_PICTURES = "010300";
	
	/**
	 * 上传图片类型 image_type
	 * 健康函-既往疾病调查问卷   -上传图片--010301
	 * **/ 
	public static final String image_type_Health_PAST_ILLNESSPAST_TAKE_SIGNATURE = "010301";
	/**
	 * 上传图片类型 image_type
	 * 健康函-既往疾病调查问卷   -生成图片--010399
	 * **/ 
	public static final String image_type_Health_PAST_ILLNESSPAST_CREATE_PICTURES = "010399";
	/**
	 * 上传图片类型 image_type
	 * 健康函--甲状腺疾病调查问卷   -签名--010400  LIVER 
	 * **/ 
	public static final String image_type_Health_THYROID_DISEASE_TAKE_PICTURES = "010400";
	
	/**
	 * 上传图片类型 image_type
	 * 健康函--甲状腺疾病调查问卷    -上传图片--010401
	 * **/ 
	public static final String image_type_Health_THYROID_DISEASE_TAKE_SIGNATURE = "010401";
		/**
	 * 上传图片类型 image_type
	 * 健康函--甲状腺疾病调查问卷    -生成图片--010499
	 * **/ 
	public static final String image_type_Health_THYROID_DISEASE_CREATE_PICTURES = "010499";
	/**
	 * 上传图片类型 image_type
	 * 健康函--女性疾病调查问卷   -签名--010500  LIVER 
	 * **/ 
	public static final String image_type_Health_FEMALE_DISEASE_TAKE_PICTURES = "010500";
	
	/**
	 * 上传图片类型 image_type
	 * 健康函--女性疾病调查问卷   -上传图片--010501
	 * **/ 
	public static final String image_type_Health_FEMALE_DISEASE_TAKE_SIGNATURE = "010501";
	/**
	 * 上传图片类型 image_type
	 * 健康函--女性疾病调查问卷   -生成图片--010501
	 * **/ 
	public static final String image_type_Health_FEMALE_DISEASE_CREATE_PICTURES = "010599";
	/**
	 * 上传图片类型 image_type
	 * 健康函--消化系统调查问卷   -签名--010600  LIVER 
	 * **/ 
	public static final String image_type_Health_DIGESTIVE_SYSTEM_DISEASE_TAKE_PICTURES = "010600";
	
	/**
	 * 上传图片类型 image_type
	 * 健康函--消化系统调查问卷   -上传图片--010601
	 * **/ 
	public static final String image_type_Health_DIGESTIVE_SYSTEM_DISEASE_TAKE_SIGNATURE = "010601";
	/**
	 * 上传图片类型 image_type
	 * 健康函--消化系统调查问卷   -生成图片--010699
	 * **/ 
	public static final String image_type_Health_DIGESTIVE_SYSTEM_DISEASE_CREATE_PICTURES = "010699";
	/**
	 * 上传图片类型 image_type
	 * 健康函--次品单   -上传图片--010701
	 * **/ 
	public static final String image_type_Health_UPLOAD_PICTURES = "010701";

	/**
	 * 上传图片类型 image_type
	 * 体检函--客户自拍照--020000
	 * **/ 
	public static final String image_type_Physical_UPLOAD_SELF_PICTURES = "020000";
	
	/**
	 * 上传图片类型 image_type
	 * 体检函--上传图片--020101
	 * **/ 
	public static final String image_type_Physical_UPLOAD_PICTURES = "020101";
	/**
	 * 上传图片类型 image_type
	 * 体检函--次品单  上传图片--020101
	 * **/ 
	public static final String image_type_Physical_UPLOAD_FAILE_PICTURES = "020201";
	/**
	 * 上传图片类型 image_type
	 * 财务函--高额业务员报告书--签名--030000
	 * **/ 
	public static final String image_type_Fina_AGENT_REPORT_SIGNATURE = "030000";
	/**
	 * 上传图片类型 image_type
	 * 财务函--上传图片--030001
	 * **/ 
	public static final String image_type_Fina_CLIENT_TAKE_PICTURES = "030001";
	
	/**
	 * 上传图片类型 image_type
	 * 财务函--财务问卷--签名--030100
	 * **/ 
	public static final String image_type_Fina_FINL_TAKE_SIGNATURE = "030100";
	/**
	 * 上传图片类型 image_type
	 * 财务函--财务问卷--上传资料--030101
	 * **/ 
	public static final String image_type_Fina_FINL_UPLOAD_PICTURES = "030101";
	/**
	 * 上传图片类型 image_type
	 * 财务函--财务问卷--生成图片--030199
	 * **/ 
	public static final String image_type_Fina_FINL_CREATE_PICTURE = "030199";
	/**
	 * 上传图片类型 image_type
	 * 契调函--契约调查报告书--签名--040000
	 * **/ 
	public static final String image_type_Survival_Investigation_Report_SIGNATURE = "040000";
	/**
	 * 上传图片类型 image_type
	 * 契调函--契约调查报告书--上传图片--040001
	 * **/ 
	public static final String image_type_Survival_Investigation_Report_UPLOAD_PICTURES= "040001";
	/**
	 * 上传图片类型 image_type
	 * 契调函--契约调查报告书--生成图片--040099
	 * **/ 
	public static final String image_type_Survival_Investigation_Report_CREATE_PICTURES= "040099";
	/**
	 * 上传图片类型 image_type
	 * 契调函--契调报告书 --上传图片--040100
	 * **/ 
	public static final String image_type_Survival_CLIENT_TAKE_PICTURES = "040100"; 

	/**
	 * 上传图片类型 image_type
	 * 问题件--代理人--签名--050100
	 * **/ 
	public static final String image_type_Problem_AGENT_SIGNATURE = "050100";

	/**
	 * 上传图片类型 image_type
	 * 问题件--代理人--上传图片--050101
	 * **/ 
	public static final String image_type_Problem_AGENT_UPLOAD_PICTURES = "050101";
	
	/**
	 * 上传图片类型 image_type
	 * 问题件--投保人--签名--050200
	 * **/ 
	public static final String image_type_Problem_APPLICANT_SIGNATURE = "050200";
	/**
	 * 上传图片类型 image_type
	 *  问题件--投保人--上传图片--050201
	 * **/ 
	public static final String image_type_Problem_APPLICANT_UPLOAD_PICTURES = "050201";
	/**
	 * 上传图片类型 image_type
	 * 问题件--被保人--签名--050300
	 * **/ 
	public static final String image_type_Problem_INSURED_SIGNATURE = "050300";
	/**
	 * 上传图片类型 image_type
	 *  问题件--被保人--上传图片--050301
	 * **/ 
	public static final String image_type_Problem_INSURED_UPLOAD_PICTURES = "050301";
	/**
	 * 上传图片类型 image_type
	 *  问题件--被保人--上传图片--050401
	 * **/ 
	public static final String image_type_Problem_UPLOAD_FAILE_PICTURES = "050401";	
	/**
	 * 上传图片类型 image_type
	 * 核保函--投保人签名--060100
	 * **/ 
	public static final String image_type_Undwrt_APPLICANT_SIGNATURE = "060100";
	/**
	 * 上传图片类型 image_type
	 * 核保函--被保人签名--060200
	 * **/ 
	public static final String image_type_Undwrt_INSURED_SIGNATURE = "060200";

	/**
	 * 错误编码
	 * 1001-rest接口调用成功
	 * **/
	public static  final String RESULT_CODE_1006  = "1001";
	/**
	 * 下发客户层函件消息(体检函、财务函件、健康问卷，契调函)
	 **/
	public static  final String client_mode_type  = "1001";
	/**
	 *下发问题件消息
	 */
	public static  final String problem_mode_type  = "1002";
	/**
	 * 下发核保函消息  延期、拒保
	 */  
	public static  final String undwrt_refuse_mode_type  = "1003";
	/**
	 * 下发核保函消息  次标体
	 */
	public static  final String undwrt_addlist_mode_type = "1004";
	/**
	 * 重新下发体检、健康函件
	 */
	public static final String resend_client_mode_type="2001";
	/**
	 * 重新问题件
	 */
	public static final String resend_problem_mode_type="2002";
	/**
	 * 下发体检或健康次品单
	 */
	public static final String image_client_fail_mode_type="3001";
	/**
	 * 下发问题件次品单
	 */
	public static final String image_problem_fail_mode_type="3002";

	/**
	 * 客户授权代理人处理函件消息
	 */
	
	public static final String dealmess_mode_type="4001";
	/**
	 * 客户授权代理人处理体检通知书
	 */
	public static final String dealnotice_mode_type="5001";
	/**
	 * 批处理表推送状态
	 *  未发送
	 */
	public static  final String push_status_unsend="0";
	/**
	 * 批处理表推送状态
	 * 发送成功
	 */
	public static  final String push_status_success="1";
	/**
	 * 批处理表推送状态
	 * 发送失败
	 */
	public static  final String push_status_failed="2";
	
	/**
	 * 证件类型
	 * 身份证
	 */
	public static  final String id_type_IDENTIFICATION_CARD ="01";
	/**
	 * 证件类型
	 * 身份证
	 */
	public static  final String id_type_IDENTIFICATION_CARD_NAME ="身份证";
	/**
	 * 证件类型
	 * 驾驶证
	 */
	public static  final String id_type_LICENSE ="03";
	/**
	 * 证件类型
	 * 驾驶证
	 */
	public static  final String id_type_LICENSE_NAME ="驾驶证";
	/**
	 * 证件类型
	 * 士兵证
	 */
	public static  final String id_type_SOLDIER_CARD ="05";
	/**
	 * 证件类型
	 * 士兵证
	 */
	public static  final String id_type_SOLDIER_CARD_NAME ="士兵证";
	/**
	 * 证件类型
	 * 军官证
	 */
	public static  final String id_type_CERTIFICATE_OF_OFFICERS ="04";
	/**
	 * 证件类型
	 * 军官证
	 */
	public static  final String id_type_CERTIFICATE_OF_OFFICERS_NAME ="军官证";
	/**
	 * 证件类型
	 * 护照
	 */
	public static  final String id_type_PASSPORT ="07";
	/**
	 * 证件类型
	 * 护照
	 */
	public static  final String id_type_PASSPORT_NAME ="护照";
	/**
	 * 证件类型
	 * 港澳通行证
	 */
	public static  final String id_type_HONG_KONG_AND_MACAO_PASS ="09";
	/**
	 * 证件类型
	 * 港澳通行证
	 */
	public static  final String id_type_HONG_KONG_AND_MACAO_PASS_NAME ="港澳通行证";
	/**
	 * 证件类型
	 * 台胞证
	 */
	public static  final String id_type_MTP ="10";
	/**
	 * 证件类型
	 * 台胞证
	 */
	public static  final String id_type_MTP_NAME ="台胞证";
	
	/**
	 * 证件类型
	 * 户口簿
	 */
	public static  final String id_type_REGISTER ="02";
	/**
	 * 证件类型
	 * 户口簿
	 */
	public static  final String id_type_REGISTER_NAME ="户口簿";
	
	/**
	 * 企业号appid
	 * **/
	public static final String appid = PropsConfig.getPropValue("enterprise.appid");
	/**
	 * 企业号key
	 * **/
	public static final String enterprise_QUERY_USER_INFOR_KEY = PropsConfig.getPropValue("enterprise.key");
	/**
	 * 企业号trade_source
	 * **/
	public static final String enterprise_QUERY_USER_INFOR_TRADE_SOURCE = PropsConfig.getPropValue("enterprise.trade_source");
	/**
	 * 调用企业号 获取企业号 接口地址
	 * **/
	public static final String enterprise_QUERY_BASE_URL = PropsConfig.getPropValue("enterprise.baseurl");
	/**
	 * 调用企业号 获取企业号 接口地址
	 * **/
	public static final String enterprise_QUERY_BASE_PRD_URL = PropsConfig.getPropValue("enterprise.basePrdurl");
	
	/**
	 * 调用企业号 获取企业号成本参数 接口地址
	 * **/
	public static final String enterprise_QUERY_USER_INFOR_URL = enterprise_QUERY_BASE_URL+"/ticket/get";
	/**
	 * 调用企业号 获取企业号成本参数 接口地址
	 * **/
	public static final String enterprise_QUERY_USER_PRD_INFOR_URL = enterprise_QUERY_BASE_PRD_URL+"/ticket/get";
	/**
	 * 调用企业号  发送文本信息
	 * **/
	public static final String enterprise_SEND_MESSAGE_URL = enterprise_QUERY_BASE_URL+"/message/text/send";
	/**
	 * 调用企业号 获取企业号accsetoken 接口地址
	 * **/
	public static final String enterprise_QUERY_TOKEN_URL = enterprise_QUERY_BASE_URL+"/token/get";
	/**
	 * 调用企业号 获取企业号accsetoken 接口地址生产地址
	 * **/
	public static final String enterprise_QUERY_PRD_TOKEN_URL = enterprise_QUERY_BASE_PRD_URL+"/token/get";

	/**
	 * 核心推送函件类型
	 * 财务函
	 */
	public static final String note_from_core_type_FINAOCCU = "01";
	/**
	 * 核心推送函件类型
	 * 体检函
	 */
	public static final String note_from_core_type_PHYSICAL = "02";
	/**
	 * 核心推送函件类型
	 * 健康函
	 */
	public static final String note_from_core_type_HEALTH = "03";
	/**
	 * 核心推送函件类型
	 * 契调函
	 */
	public static final String note_from_core_type_SURVIVAL = "04";
	/**
	 * 核心推送函件类型
	 * 问题件	
	 */
	public static final String note_from_core_type_PROBLEM = "05";
	/**
	 * 核心推送函件类型
	 * 核保函
	 */
	public static final String note_from_core_type_UNDWRT = "06";
	/**
	 * 阿里云OSS提供的ossEndPoint
	 */
	public static final String ossEndPoint =PropsConfig.getPropValue("oss.endpoint");
	/**
	 * 阿里云OSS提供的ossId
	 */
	public static final String ossId =PropsConfig.getPropValue("oss.accesskeyid");
	/**
	 * 阿里云OSS提供的ossKey
	 */
	public static final String ossKey = PropsConfig.getPropValue("oss.accesskeysecret");
	/**
	 * 阿里云OSS提供的ossBucket
	 */
	public static final String ossBucket = PropsConfig.getPropValue("oss.bucketname");
	
	/**
	 * 返回结果
	 * 正确
	 * **/
	public static final boolean result_TRUE = true;
	
	/**
	 * 返回结果
	 * 错误
	 * **/
	public static final boolean result_FALSE = false;
	
	/**
	 * 错误提示
	 * 请求报文不能为空
	 * **/
	public static final String error_DESC_BODY_NULL = "报文不能为空！";
	/**
	 * 数据类型-性别
	 * **/
    public static final String transfer_data_SEX = "1";
    /**
	 * 数据类型-性别描述
	 * **/
    public static final String transfer_data_SEX_DESC = "性别";
    /**
	 * 客户层函件类型
	 * **/
	public static final String transfer_data_NOTE_TYPE= "2";
	/**
	 * 客户层函件类型描述
	 * **/
	public static final String transfer_data_NOTE_TYPE_DESC= "客户层函件状态";
	/**
	 * 函件状态--代理人查看状态
	 * **/
	public static final String transfer_data_NOTE_AGENT_STATUS = "3";
	/**
	 * 函件状态--代理人查看状态描述
	 * **/
	public static final String transfer_data_NOTE_AGENT_STATUS_DESC = "代理人查看函件状态";
	/**
	 * 函件任务处理状态 
	 * **/
	public static final String transfer_data_NOTE_STATUS = "4";
	/**
	 * 函件任务处理状态 
	 * **/
	public static final String transfer_data_NOTE_STATUS_DESC = "函件任务处理状态 ";
	/**
	 * 健康问卷名称
	 * **/
	public static final String transfer_data_HEALTH_TYPE_NAME = "5";
	/**
	 * 健康问卷名称
	 * **/
	public static final String transfer_data_HEALTH_TYPE_NAME_DESC = "健康问卷名称 ";
	/**
	 * 财务问卷名称
	 * **/
	public static final String transfer_data_FINA_TYPE_NAME = "6";
	/**
	 * 财务问卷名称
	 * **/
	public static final String transfer_data_FINA_TYPE_NAME_DESC = "财务问卷名称 ";
	/**
	 * 核心函件类型名称
	 */
	public static final String transfer_core_data_Note_TYPE_NAME="7";
	/**
	 * 核心函件类型名称
	 */
	public static final String transfer_core_data_Note_TYPE_NAME_DESC="核心函件类型名称";
	/**
	 * 证件类型名称
	 */
	public static final String transfer_core_data_ID_TYPE_NAME="8";
	/**
	 * 证件类型名称
	 */
	public static final String transfer_core_data_ID_TYPE_NAME_DESC="证件类型名称";
	/**
	 * 期限单位
	 */
	public static final String transfer_core_PERIOD_TYPE_NAME="9";
	/**
	 * 期限单位名称
	 */
	public static final String transfer_core_PERIOD_TYPE_NAME_DESC="期限单位名称";
	/**
	 * 核保结论
	 */
	public static  final String transfer_core_Undwrt_Result = "10";
	public static  final String transfer_core_Undwrt_Result_DESC = "核保结论";
	
	
	/**
	 * webservice接口处理返回代码
	 * 成功
	 */
	public final static String webservice_returnCode_SUCC = "200";
	/**
	 * webservice接口处理返回代码
	 * 失败
	 */
	public final static String webservice_returnCode_FAILED = "400";
	/**
	 * 核保涵客户回答结果
	 * 同意
	 */
	public final static String client_answer_result_Yes = "01";
	/**
	 * 核保涵客户回答结果
	 * 拒绝
	 */
	public final static String client_answer_result_No = "02";	
	/**
	 * 核保结论
	 * 标体通过
	 */
	public static  final String undwrt_result_A = "A";
	public static  final String undwrt_result_A_DESC = "标体通过";
	/**
	 * 核保结论
	 * 加费
	 */
	public static  final String undwrt_result_B = "B";
	public static  final String undwrt_result_B_DESC = "加费";
	/**
	 * 核保结论
	 * 除外
	 */
	public static  final String undwrt_result_C = "C";
	public static  final String undwrt_result_C_DESC = "除外";
	/**
	 * 核保结论
	 * 削减保额
	 */
	public static  final String undwrt_result_D = "D";
	public static  final String undwrt_result_D_DESC = "削减保额";
	/**
	 * 核保结论
	 * 限制保额
	 */
	public static  final String undwrt_result_E = "E";
	public static  final String undwrt_result_E_DESC = "限制保额";
	/**
	 * 核保结论
	 * 限制缴费期
	 */
	public static  final String undwrt_result_F = "F";
	public static  final String undwrt_result_F_DESC = "限制缴费期";
	/**
	 * 核保结论
	 * 延期
	 */
	public static  final String undwrt_result_G = "G";
	public static  final String undwrt_result_G_DESC = "延期";
	/**
	 * 核保结论
	 * 拒保
	 */
	public static  final String undwrt_result_H = "H";
	public static  final String undwrt_result_H_DESC = "拒保";
	/**
	 * 核保结论
	 * 风险保险费加费
	 */
	public static  final String undwrt_result_I = "I";
	public static  final String undwrt_result_I_DESC = "风险保险费加费";
	/**
	 * 核保结论
	 * 加费+除外
	 */
	public static  final String undwrt_result_1 = "1";
	public static  final String undwrt_result_1_DESC = "加费+除外";
	/**
	 * 核保结论
	 * 加费+限制保额
	 */
	public static  final String undwrt_result_2 = "2";
	public static  final String undwrt_result_2_DESC = "加费+限制保额";
	/**
	 * 核保结论
	 * 加费+限制缴费期
	 */
	public static  final String undwrt_result_3 = "3";
	public static  final String undwrt_result_3_DESC = "加费+限制缴费期";
	/**
	 * 核保结论
	 * 除外+削减保额
	 */
	public static  final String undwrt_result_4 = "4";
	public static  final String undwrt_result_4_DESC = "除外+削减保额";
	/**
	 * 核保结论
	 * 除外+限制保额
	 */
	public static  final String undwrt_result_5 = "5";
	public static  final String undwrt_result_5_DESC = "除外+限制保额";
	/**
	 * 核保结论
	 * 除外+限制缴费期
	 */
	public static  final String undwrt_result_6 = "6";
	public static  final String undwrt_result_6_DESC = "除外+限制缴费期";
	/**
	 * 核保结论
	 * 削减保额+限制缴费期
	 */
	public static  final String undwrt_result_7 = "7";
	public static  final String undwrt_result_7_DESC = "削减保额+限制缴费期";
	/**
	 * 核保结论
	 * 限制保额+限制缴费期
	 */
	public static  final String undwrt_result_8 = "8";
	public static  final String undwrt_result_8_DESC = "限制保额+限制缴费期";
	/**
	 * 核保结论
	 * 加费+除外+限制保额
	 */
	public static  final String undwrt_result_9 = "9";
	public static  final String undwrt_result_9_DESC = "加费+除外+限制保额";
	/**
	 * 核保结论
	 * 加费+除外+限制缴费期
	 */
	public static  final String undwrt_result_10 = "10";
	public static  final String undwrt_result_10_DESC = "加费+除外+限制缴费期";
	/**
	 * 核保结论
	 * 加费+限制保额+限制缴费期
	 */
	public static  final String undwrt_result_11 = "11";
	public static  final String undwrt_result_11_DESC = "加费+限制保额+限制缴费期";
	/**
	 * 核保结论
	 * 加费
	 */
	public static  final String undwrt_result_12 = "12";
	public static  final String undwrt_result_12_DESC = "除外+削减保额+限制缴费期";
	/**
	 * 核保结论
	 * 加费
	 */
	public static  final String undwrt_result_13 = "13";
	public static  final String undwrt_result_13_DESC = "除外+限制保额+限制缴费期";
	/**
	 * 核保结论
	 * 削减保额+限制保额+限制缴费期
	 */
	public static  final String undwrt_result_14 = "14";
	public static  final String undwrt_result_14_DESC = "削减保额+限制保额+限制缴费期";
	/**
	 * 核保结论
	 * 加费+除外+限制保额+削减保额
	 */
	public static  final String undwrt_result_15 = "15";
	public static  final String undwrt_result_15_DESC = "加费+除外+限制保额+削减保额";
	/**
	 * 核保结论
	 * 加费+除外+限制保额+限制缴费期
	 */
	public static  final String undwrt_result_16 = "16";
	public static  final String undwrt_result_16_DESC = "加费+除外+限制保额+限制缴费期";
	/**
	 * 核保结论
	 * 加费+削减保额+限制保额+限制缴费期
	 */
	public static  final String undwrt_result_17 = "17";
	public static  final String undwrt_result_17_DESC = "加费+削减保额+限制保额+限制缴费期";
	/**
	 * 核保结论
	 * 除外+削减保额+限制保额+限制缴费期
	 */
	public static  final String undwrt_result_18 = "18";
	public static  final String undwrt_result_18_DESC = "除外+削减保额+限制保额+限制缴费期";
	/**
	 * 核保结论
	 * 风险保险费加费+除外
	 */
	public static  final String undwrt_result_19 = "19";
	public static  final String undwrt_result_19_DESC = "风险保险费加费+除外";
	/**
	 * 核保结论
	 * 风险保险费加费+限制保额
	 */
	public static  final String undwrt_result_20 = "20";
	public static  final String undwrt_result_20_DESC = "风险保险费加费+限制保额";
	/**
	 * 核保结论
	 * 风险保险费加费+除外+限制保额
	 */
	public static  final String undwrt_result_21 = "21";
	public static  final String undwrt_result_21_DESC = "风险保险费加费+除外+限制保额";
	/**
	 * 核保结论
	 * 待核
	 */
	public static  final String undwrt_result_99 = "99";
	public static  final String undwrt_result_99_DESC = "待核";
	/**
	 * 核保结论
	 * 接受申请并维持原决定
	 */
	public static  final String undwrt_result_J = "J";
	public static  final String undwrt_result_J_DESC = "接受申请并维持原决定";
	/**
	 * 核保结论
	 * 拒绝保全申请
	 */
	public static  final String undwrt_result_K = "K";
	public static  final String undwrt_result_K_DESC = "拒绝保全申请";
	/**
	 * 核保结论
	 * 退回保全
	 */
	public static  final String undwrt_result_L = "L";
	public static  final String undwrt_result_L_DESC = "退回保全";
	/**
	 * 核保结论
	 * 超优体
	 */
	public static  final String undwrt_result_M = "M";
	public static  final String undwrt_result_M_DESC = "超优体";
	/**
	 * 核保结论
	 * 优选体
	 */
	public static  final String undwrt_result_N = "N";
	public static  final String undwrt_result_N_DESC = "优选体";
	/**
	 * 核保结论
	 * 优标体
	 */
	public static  final String undwrt_result_O = "O";
	public static  final String undwrt_result_O_DESC = "优标体";
	/**
	 * 核保结论
	 * 标准体
	 */
	public static  final String undwrt_result_P = "P";
	public static  final String undwrt_result_P_DESC = "标准体";
	/**
	 * 时间日期
	 * 
	 */
	public final static String time_DATE = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 空字符
	 * 
	 */
	
	public final static String empty_STRING = "";
	
	/**
	 * 是否重新下发  is_not_qualified_note
	 * 是--Y
	 * **/
	public static final String is_not_second_note_Y = "Y";
	
	/**
	 * 是否重新下发  is_not_qualified_note
	 * 是--N
	 * **/
	public static final String is_not_second_note_N  = "N";
	
	/**
	 * 期限单位
	 * 年
	 * **/
	public static final String period_type_YEAR = "2";
	/**
	 * 期限单位
	 * 年
	 * **/
	public static final String period_type_YEAR_DESC = "年";
	/**
	 * 期限单位
	 * 月
	 * **/
	public static final String period_type_MONTH = "5";
	/**
	 * 期限单位
	 * 月
	 * **/
	public static final String period_type_MONTH_DESC = "月";
	
	/**
	 * 短信是否使用过
	 * 是--1
	 * **/
	public static final String sms_Y  = "1";
	
	/**
	 * 短信是否使用过
	 * -否-0
	 * **/
	public static final String sms_N  = "0";
	
	/**
	 * 影像的绝对路径
	 */
	public static final String ossLocalPath = PropsConfig.getPropValue("ossLocalPath");
	
	/**
	 * 微信提供的错误 代码
	 */
	public static final String errCode = "errcode";
	/**
	 * 契调函任务状态
	 */
	public static final String survival_note_status_desc = "契调中";
	/**
	 * 
	 */
	public static final String oss_sysname ="wxhb";
	/**
	 * 
	 */
	public static final String CHANNEL_NO =oss_sysname+"/UploadImage/";
	/**
	 * 
	 */
	public static final String OSS_PDF_PATH =oss_sysname+"/UploadPdf/";
	/**
	 * 是否打印成功
	 * 成功
	 */
	public static final String printResult_success = "200";
	/**
	 * 是否打印成功
	 * 失败
	 */
	public static final String printResult_fail = "400";
	/**
	 * 回销类型-普通
	 */
	public static final String buyback_type_ordinary = "0";
	/**
	 * 回销类型-次品单
	 */
	public static final String buyback_type_defective_list = "1";
	/**
	 * 回销类型-重新下发
	 */
	public static final String buyback_type_again = "2";
	/**
	 * 推送结果
	 */
	public static final String push_status = "suc";/**
	 * 契调任务申请
	 */
	public static final String survival_note_APPLY = "01";
	
	/**
	 * 契调任务放弃
	 */
	public static final String survival_note_GIVEUP = "02";
	
	/**
	 * 契调任务放弃将原契调员编号置空
	 */
	public static final String survival_note_NULL = " ";
	
	/**
	 * 调用核心接口对应serviceid
	 * 函件打印
	 */
	public static final String serviceid_PRINT = "11100233";
	
	/**
	 * 调用核心接口对应serviceid
	 * 函件下发
	 */
	public static final String serviceid_CHOOSETYPEPUSH = "11100238";
	
	/**
	 * 调用核心接口对应serviceid
	 * 函件消息推送
	 */
	public static final String serviceid_NOTEMESSAGEPUSH = "11100237";
	
	/**
	 * 调用核心接口对应serviceid
	 * 函件打印
	 */
	public static final String serviceid_IMAGE = "4c300191";
	/**
	 * 直调
	 */
	public static final String survival_MODEDERICT = "1";
	/**
	 * 直调描述
	 */
	public static final String survival_MODEDERICTDESC = "直调";
	/**
	 * 侧调
	 */
	public static final String survival_MODESIDE = "2";
	/**
	 * 侧调描述
	 */
	public static final String survival_MODESIDEDESC = "侧调";
	/**
	 * ftp Server
	 */
	public static final String FTP_SERVER = PropsConfig.getPropValue("ftp.Server");
	/**
	 * ftp username
	 */
	public static final String FTP_USERNAME= PropsConfig.getPropValue("ftp.Username");
	/**
	 * ftp Password
	 */
	public static final String FTP_PASSWORD= PropsConfig.getPropValue("ftp.Password");
	/**
	 * ftp Port
	 */
	public static final int FTP_RPORT = Integer.valueOf(PropsConfig.getPropValue("ftp.Port"));
	
	/**
	 * ftp 元镁文件存放路径
	 */
	public static final String FTP_YUANMEI_PATH = PropsConfig.getPropValue("ftp.YuanMeiPath");
	
	/**
	 * ftp 核心路径
	 */
	public static final String FTP_HEXIN_PATH = PropsConfig.getPropValue("ftp.HeXinPath");
	
	/**
	 * ftp 影像路径
	 */
	public static final String FTP_IMAGESYSTEM_PATH = PropsConfig.getPropValue("ftp.ImageSystemPath");
	
	/**
	 * ftp 本地存放路径
	 */
	public static final String FTP_LOCAL_PATH = new File(new File(System.getProperty("user.dir")).getParent()).getParent()+"/image";
	/**
	 * ftp 本地存放路径
	 */
	public static final String PDF_LOCAL_PATH = new File(new File(System.getProperty("user.dir")).getParent()).getParent()+"/pdf";

	/**
	 * ftp 本地存放路径
	 */
	public static final String ImageSystem_LOCAL_PATH = new File(new File(System.getProperty("user.dir")).getParent()).getParent()+"/ImageSystem";
	/**
	 * 短信验证有效时间
	 */
	public static final int SMS_VALID_TIME = 5;
	/**
	 * 影像推送状态
	 * 等待上载
	 */
	public static final String	waitUpLoad = "0";
	/**
	 * 影像推送状态
	 * 上载失败
	 */
	public static final String	failUpLoad = "2";
	/**
	 * 影像推送状态
	 * 等待删除
	 */
	public static final String	waitDelete = "待删除";
	/**
	 * 影像推送状态
	 * 删除失败
	 */
	public static final String	failDelete = "删除失败";
	/**
	 * 影像推送状态
	 * 推送成功
	 */
	public static final String	sendSuss = "1";
	/**
	 * 影像推送状态
	 * 推送结束
	 */
	public static final String	sendEnd = "3";
	/**
	 * 影像推送成功编码
	 */
	public static final String	sendImageSuss = "000000";
	/**
	 * 微信端前端地址
	 */
	public static final String WXWEB_URL = PropsConfig.getPropValue("wxsystem.url");
	/**
	 * 
	 */
	public static final String IMAGECALLBACK_URL = PropsConfig.getPropValue("imagecallback.url");

}
