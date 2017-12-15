package com.thinkgem.jeesite.modules.constructcost.member.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

/**
 * 用户角色关系Model
 * 
 * @author lin
 * 
 */
public class CcPrjUserMapModel extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8455971504257684504L;
	private String id;
	private String companyId;// 会员ID
	private String projectId;// 工程ID
	private String userId;// 用户ID
	private String userName;//用户名
	private String prjRoleId;// 此用户在工程中的角色id
	private String prjRoleName;//此用户在工程中的角色名
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPrjRoleId() {
		return prjRoleId;
	}
	public void setPrjRoleId(String prjRoleId) {
		this.prjRoleId = prjRoleId;
	}
	public String getPrjRoleName() {
		return prjRoleName;
	}
	public void setPrjRoleName(String prjRoleName) {
		this.prjRoleName = prjRoleName;
	}
	
	

}
