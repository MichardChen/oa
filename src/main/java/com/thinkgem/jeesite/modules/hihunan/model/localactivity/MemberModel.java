package com.thinkgem.jeesite.modules.hihunan.model.localactivity;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class MemberModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String mobile;
	private String token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
