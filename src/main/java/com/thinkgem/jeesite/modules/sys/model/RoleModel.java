/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

/**
 * 角色Model
 * 
 * @author chen_qiancheng
 * @version 2015-12-14
 */
public class RoleModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String id; // 角色id
	private String name;// 角色name
	private String enname;// 英文名称
	private String modelFlag;//模型标志（CC 成本模块用，XX 其他）
	private String roleType;// 权限类型
	private String dataScope;// 数据范围
	private String officeName;//专门临时储存（机构名称）字段  2016-6-30 已废除
	private String officeId;  //专门临时储存（机构id）字段     2016-6-30 已废除
	private String sysData; 		//是否是系统数据
	private String useable; 		//是否是可用
	private String remarks;	// 备注
	
	private String menuIds; // 该角色下的菜单id列表。如 
	
	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getSysData() {
		return sysData;
	}

	public void setSysData(String sysData) {
		this.sysData = sysData;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModelFlag() {
		return modelFlag;
	}

	public void setModelFlag(String modelFlag) {
		this.modelFlag = modelFlag;
	}

}