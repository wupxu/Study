package com.lexue.bp.admin.stream.domain;

public class DataWatchRequest {
	
	   /** 业务编号 */
	   private String businessId;
	   
	   /** 用户编号 */
	   private long uid;
	   
	   /** 模块编号 */
	   private int moduleId;
	   
	   /** 观看时长（秒） */
	   private long duration;
	   
	   /** 观看时间点 */
	   private long watchTime;

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

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getWatchTime() {
		return watchTime;
	}

	public void setWatchTime(long watchTime) {
		this.watchTime = watchTime;
	}
	   
	
}
