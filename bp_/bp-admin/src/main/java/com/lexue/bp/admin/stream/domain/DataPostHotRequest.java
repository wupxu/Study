package com.lexue.bp.admin.stream.domain;

public class DataPostHotRequest {
	   
	   /** 用户编号 */
	   public long uid;
	   
	   /** 模块编号*/
	   public int moduleId;
	   	   
	   /** 帖子编号 */
	   private String postId;
	   
	   /** 评为热帖时间 毫秒 */
	   public long postTime;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public long getPostTime() {
		return postTime;
	}

	public void setPostTime(long postTime) {
		this.postTime = postTime;
	}


	   
}
