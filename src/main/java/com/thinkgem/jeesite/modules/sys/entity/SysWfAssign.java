/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 流程人员分配Entity
 * @author 孟令岗
 * @version 2016-4-12
 */
public class SysWfAssign extends DataEntity<SysWfAssign> {
	
	private static final long serialVersionUID = 1L;
    
	private String procDefId;       // 流程定义id
	private String taskDefKey; 		// 流程节点KEY
	private String roleIds;         // 候选角色
	private String officeIds;       // 候选组织
	private String userIds;         // 候选人员
	
	// 这些内部变量是 set完成后自动转换过来的。
	private List<String> roleList     = new ArrayList<String>();
	private List<String> officeList = new ArrayList<String>();
	private List<String> userList = new ArrayList<String>();		
	
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	    // 设置到roleList
		if(StringUtils.isNotBlank(roleIds)){
			this.roleList.clear();
			
			String[] roleArr= roleIds.split(",");
			this.roleList = Arrays.asList(roleArr);
		}
	}
	
	public void setOfficeIds(String officeIds) {
		this.officeIds = officeIds;
		
		// 设置到officeList
		if(StringUtils.isNotBlank(officeIds)){
			this.officeList.clear();
			
			String[] officeArr= officeIds.split(",");
			this.officeList = Arrays.asList(officeArr);
		}
				
	}
	
	public void setUserIds(String userIds) {
		this.userIds = userIds;
		
		// 设置到userList
		if(StringUtils.isNotBlank(userIds)){
			this.userList.clear();
			
			String[] userArr= userIds.split(",");
			this.userList = Arrays.asList(userArr);
		}	
	}
	
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	//
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public List<String> getOfficeList() {
		return officeList;
	}

	public void setOfficeList(List<String> officeList) {
		this.officeList = officeList;
	}

	public List<String> getUserList() {
		return userList;
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getRoleIds() {
		return roleIds;
	}
	
	
	public String getOfficeIds() {
		return officeIds;
	}
	
	public String getUserIds() {
		return userIds;
	}
	
}


