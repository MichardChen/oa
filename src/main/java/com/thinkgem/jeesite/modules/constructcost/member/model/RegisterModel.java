package com.thinkgem.jeesite.modules.constructcost.member.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.BaseModel;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 用于传递注册页面的数据
 * 
 * @author yuyabiao
 * 
 */
public class RegisterModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7641501696070420934L;
	private String memberName;// 公司名
	private String loginName;// 登录名
	private String password;// 登录密码
	private String mobile;// 手机号码
	private String verifyCode;// 验证码
	private String areaId;// 区域ID
	private String name;// 联系人
	private String address;// 联系地址
	private String phone;// 固话
	private String qq;// QQ号码
	
	
	@JsonIgnore
	@NotNull(message = "归属公司不能为空")
	@ExcelField(title = "归属公司", align = 2, sort = 20)
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@JsonIgnore
	@NotNull(message = "登录名不能为空")
	@ExcelField(title = "登录名", align = 2, sort = 20)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@JsonIgnore
	@NotNull(message = "密码不能为空")
	@ExcelField(title = "密码", align = 2, sort = 20)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonIgnore
	@NotNull(message = "手机号码不能为空")
	@ExcelField(title = "手机", align = 2, sort = 20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

}
