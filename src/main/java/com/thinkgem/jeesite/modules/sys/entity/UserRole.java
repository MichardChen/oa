package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class UserRole extends DataEntity<UserRole> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8246498879019812416L;

	private String userId;// 用户ID

	private String roleId;// 权限

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
