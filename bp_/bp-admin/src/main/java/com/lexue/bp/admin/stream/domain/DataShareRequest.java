package com.lexue.bp.admin.stream.domain;



public class DataShareRequest {
       
	   /** 业务编号 */
	   private String businessId;
	   
	   /** 用户编号 */
	   private long uid;
	   
	   /** 模块编号*/
	   private int moduleId;
	   
	   /** 分享时间 */
	   private long shareTime;

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public long getShareTime() {
		return shareTime;
	}

	public void setShareTime(long shareTime) {
		this.shareTime = shareTime;
	}
	   
	   
}
