package com.lexue.bp.admin.stream.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataPostRequest {
	   
	   /** 帖子编号 */
	   private String postId;
	   
	   /** 用户编号 */
	   private String uid;
	   
	   /** 模块编号 */
	   private String moduleId;

	   /**
	    * 0/1
	    * 未删除/已删除
	    * 是否删除
	    */
	   private int deleteFlag;
	   
	   /** 发帖时间 */
	   private String postTime;
	   

	   
	public String getPostId() {
		return postId;
	}



	public void setPostId(String postId) {
		this.postId = postId;
	}



	public String getUid() {
		return uid;
	}



	public void setUid(String uid) {
		this.uid = uid;
	}



	public String getModuleId() {
		return moduleId;
	}



	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}



	public int getDeleteFlag() {
		return deleteFlag;
	}



	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}



	public String getPostTime() {
		return postTime;
	}



	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}



	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("postId:").append(getPostId()).append(",")
		.append("moduleId:").append(getModuleId()).append(",")
		.append("uid:").append(getUid()).append(",")
		.append("deleteFlag:").append(getDeleteFlag()).append(",")
		.append("postTime:").append(getPostTime());
		return sb.toString();
	}
	   
	   
}
