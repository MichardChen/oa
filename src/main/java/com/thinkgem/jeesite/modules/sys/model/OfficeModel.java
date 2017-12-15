/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

/**
 * 部门Model
 * @author chen_qiancheng
 * @version 2015-12-14
 */
public class OfficeModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String id;	// 部门id
	private String name;// 部门name
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

	
}