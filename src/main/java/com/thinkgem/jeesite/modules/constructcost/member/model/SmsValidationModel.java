package com.thinkgem.jeesite.modules.constructcost.member.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class SmsValidationModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String id;
	private String mobileNo;
	private String sendTime;
	private String nowTime;
	private String validateCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getNowTime() {
		return nowTime;
	}

	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

}
