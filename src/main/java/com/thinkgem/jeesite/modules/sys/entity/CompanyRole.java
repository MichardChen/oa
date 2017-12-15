/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 角色Entity
 * @author 
 * @version 2013-12-05
 */
public class CompanyRole extends DataEntity<CompanyRole> {
	
	private static final long serialVersionUID = 1L;
	private Office office;	// 归属机构
	private Role   role;    // 权限
	private String companyId; // 公司ID
	private String roleId;    // 权限ID 
	
	public CompanyRole() {
		super();
	}
	
	public CompanyRole(String id){
		super(id);
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
