/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.BaseModel;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.act.utils.Variable;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 流程人员分配Entity
 * @author 孟令岗
 * @version 2016-4-12
 */
public class SysWfAssignModel extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	private String id;              // 表的id
	private String idFmDB;          // idFmDB
	private String processDefId;    // 流程定义ID 如 incomeAudit:2:9646725afcf34df7b666574ea6864edd
	private String processDefName;  // 流程定义名称 如 工程计量审批流程
	private String processDefKey;   // 流程定义KEY 如  incomeAudit
	private String processDefVer;   // 流程定义版本  如 3 
	private String taskDefKey; 		// 流程节点KEY 如  audit
	private String roleIds;         // 候选角色  如  "2,3,4,"
	private String officeIds;       // 候选组织  如 "22,33,45,"
	private String userIds;         // 候选人员  如" 23,43,54,"
	
	// 这些内部变量是 set完成后自动转换过来的。
	private List<String> roleList     = new ArrayList<String>();
	private List<String> officeList = new ArrayList<String>();
	private List<String> userList = new ArrayList<String>();		
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdFmDB() {
		return idFmDB;
	}

	public void setIdFmDB(String idFmDB) {
		this.idFmDB = idFmDB;
	}

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
	
	//
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}

	public String getProcessDefName() {
		return processDefName;
	}

	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
	}

	public String getProcessDefKey() {
		return processDefKey;
	}

	public void setProcessDefKey(String processDefKey) {
		this.processDefKey = processDefKey;
	}

	public String getProcessDefVer() {
		return processDefVer;
	}

	public void setProcessDefVer(String processDefVer) {
		this.processDefVer = processDefVer;
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


