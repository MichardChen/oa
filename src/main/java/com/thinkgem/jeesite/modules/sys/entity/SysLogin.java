package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.act.entity.Act;

public class SysLogin extends DataEntity<SysLogin> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1723629971593993311L;
	private String userId;
	private String memberId;
	private String loginIp;
	private String code;
	private Date loginDate;
	private String token;
	private Date tokenDate;
	
	public String getUserId(){
		return this.userId;
	}
	public void setUserId(String userId){
		this.userId=userId;
	}
	
	public String getMemberId(){
		return this.memberId;
	}
	public void setMemberId(String memberId){
		this.memberId=memberId;
	}
	
	public String getLoginIp(){
		return this.loginIp;
	}
	public void setLoginIp(String loginIp){
		this.loginIp=loginIp;
	}
	
	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code=code;
	}
	
	public Date getLoginDate(){
		return this.loginDate;
	}
	public void setLoginDate(Date loginDate){
		this.loginDate=loginDate;
	}
	
	public String getToken(){
		return this.token;
	}
	public void setToken(String token){
		this.token=token;
	}
	
	public Date getTokenDate(){
		return this.tokenDate;
	}
	public void setTokenDate(Date tokenDate){
		this.tokenDate=tokenDate;
	}
	
}
