/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 微信企业号机构Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class SysWxqyDept extends TreeEntity<SysWxqyDept> {
	
	public SysWxqyDept(){
		super();
		this.sort = 30;
	}

	public SysWxqyDept(String id){
		super(id);
	}
	
//	@JsonBackReference
//	@NotNull
	public SysWxqyDept getParent() {
		return parent;
	}

	public void setParent(SysWxqyDept parent) {
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		return name;
	}
}