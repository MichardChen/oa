package com.thinkgem.jeesite.modules.sys.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class UserRoleModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3782434845969648929L;
	private String userId;//用户ID
	private String roleId;//角色ID
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	
	
}
