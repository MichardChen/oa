/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

/**
 * 用户Model
 * 
 * @author chen_qiancheng
 * @version 2015-12-13
 */
public class UserModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String id;// 主键ID
	private String idFmDB;// 用户更新和删除时替代id
	private String name; // 姓名
	private String enname;// 英文名
	private String loginName;// 登录名
	private String password;// 密码
	private String loginFlagRepeat;// 隐藏的生效标志
	private String oldPassword;// 旧密码
	private String officeId;// 归属部门
	private String role;// 角色
	private String mobile; // 手机
	private String loginFlag; // 是否允许登陆
	private String qq; // QQ号 2015年12月08日。
	private String wechatOpenId; // 微信OPENID.2015年12月08日。
	private String modifyFlag = "0"; // 判断画面当前行 是否修改。’0’--没修改。 2016-1-4 mlg.
	private String wxUserId; //微信企业号UserId
	private String userName;// 用户名
	private String prjRoleId;// 此用户在工程中的角色id
	private String prjRoleName;// 此用户在工程中的角色名
	private String nsPosition;//用户身份(农商行添加)

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

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getLoginFlagRepeat() {
		return loginFlagRepeat;
	}

	public void setLoginFlagRepeat(String loginFlagRepeat) {
		this.loginFlagRepeat = loginFlagRepeat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechatOpenId() {
		return wechatOpenId;
	}

	public void setWechatOpenId(String wechatOpenId) {
		this.wechatOpenId = wechatOpenId;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIdFmDB() {
		return idFmDB;
	}

	public void setIdFmDB(String idFmDB) {
		this.idFmDB = idFmDB;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrjRoleName() {
		return prjRoleName;
	}

	public void setPrjRoleName(String prjRoleName) {
		this.prjRoleName = prjRoleName;
	}

	public String getPrjRoleId() {
		return prjRoleId;
	}

	public void setPrjRoleId(String prjRoleId) {
		this.prjRoleId = prjRoleId;
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getNsPosition() {
		return nsPosition;
	}

	public void setNsPosition(String nsPosition) {
		this.nsPosition = nsPosition;
	}
}