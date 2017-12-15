package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户喜好
 * 
 * @author yuyabiao
 * 
 */
public class UserFavorite extends DataEntity<UserFavorite> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7191749598412759859L;
	private String userId;// 用户id(唯一)
	private String lastProjectId;// 最后一次使用的工程id
	private String lastProjectName;// 最后一次使用的工程名
	private String lastPrjStatus;// 最后一次使用的工程状态
	private String lastPrjIsDemo;// 最后一次使用的工程是否为例子工程

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastProjectId() {
		return lastProjectId;
	}

	public void setLastProjectId(String lastProjectId) {
		this.lastProjectId = lastProjectId;
	}

	public String getLastProjectName() {
		return lastProjectName;
	}

	public void setLastProjectName(String lastProjectName) {
		this.lastProjectName = lastProjectName;
	}

	public String getLastPrjStatus() {
		return lastPrjStatus;
	}

	public void setLastPrjStatus(String lastPrjStatus) {
		this.lastPrjStatus = lastPrjStatus;
	}

	public String getLastPrjIsDemo() {
		return lastPrjIsDemo;
	}

	public void setLastPrjIsDemo(String lastPrjIsDemo) {
		this.lastPrjIsDemo = lastPrjIsDemo;
	}

}
