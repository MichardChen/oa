/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.supcan.annotation.treelist.cols.SupCol;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.utils.excel.fieldtype.RoleListType;

/**
 * 用户Entity
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;
	private int autoIncId;// 自增长ID
	private Member member; // 归属会员 2015年12月08日。
	private Office company; // 归属公司
	private Office office; // 归属部门
	private String loginName;// 登录名
	private String password;// 密码
	private String no; // 工号
	private String name; // 姓名
	private String email; // 邮箱
	private String phone; // 电话
	private String mobile; // 手机
	private String userType;// 用户类型
	private String loginIp; // 最后登陆IP
	private Date loginDate; // 最后登陆日期
	private String loginFlag; // 是否允许登陆
	private String photo; // 头像
	private String qq; // QQ号 2015年12月08日。
	private String wechatOpenId; // 微信公众号的OPENID.2015年12月08日。
	private String oldLoginName; // 原登录名
	private String oldMobile; // 原手机号
	private String newPassword; // 新密码
	private String token;// 单点登录Token，add by wyf 20160225
	private String wxUserId; // 微信企业号中对应的UserId(企业号中唯一).
	private String oldWxUserId;// 原微信企业号中对应的UserId(企业号中唯一)
	private String wxUserStatus; // 微信企业号UserId状态 1=已关注，2=已冻结，4=未关注
	private String wxUserStatusDesc; // 微信企业号的用户账号状态。 定义来自于微信企业号
										// 1=已关注，2=已冻结，4=未关注
	private String oldLoginIp; // 上次登陆IP
	private Date oldLoginDate; // 上次登陆日期
	private String wxAccessToken; // WEB的访问token。 2016-06-11
	private String appAccessToken;// APP的访问token. 2016-06-11

	private String primaryPersonSign; // 2016-6-28 该用户是否为某机构的负责人 。 1--是负责人
										// 0--不是负责人

	private String addToWxUserId; // 是否添加用户到微信企业号。'1'--添加，'0'-不添加。
	private Role role; // 根据角色查询用户条件

	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

	private String idFmDB;// 用户更新和删除时替代id
	private String currProjectId; // 当前工程id

	private String nsPosition;// 用户身份（针对农商行支行用户添加）

	public String getWxUserStatusDesc() {
		String desc = "";

		if ("1".equals(this.wxUserStatus)) {
			desc = "已关注";
		}
		if ("2".equals(this.wxUserStatus)) {
			desc = "已冻结";
		}
		if ("4".equals(this.wxUserStatus)) {
			desc = "未关注";
		}

		return desc;
	}

	public void setWxUserStatusDesc(String wxUserStatusDesc) {
		this.wxUserStatusDesc = wxUserStatusDesc;
	}

	public String getWxUserStatus() {
		return wxUserStatus;
	}

	public void setWxUserStatus(String wxUserStatus) {
		this.wxUserStatus = wxUserStatus;
	}

	public String getOldMobile() {
		return oldMobile;
	}

	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}

	public String getPrimaryPersonSign() {
		return primaryPersonSign;
	}

	public void setPrimaryPersonSign(String primaryPersonSign) {
		this.primaryPersonSign = primaryPersonSign;
	}

	public String getWxAccessToken() {
		return wxAccessToken;
	}

	public void setWxAccessToken(String wxAccessToken) {
		this.wxAccessToken = wxAccessToken;
	}

	public String getAppAccessToken() {
		return appAccessToken;
	}

	public void setAppAccessToken(String appAccessToken) {
		this.appAccessToken = appAccessToken;
	}

	public String getAddToWxUserId() {
		return addToWxUserId;
	}

	public void setAddToWxUserId(String addToWxUserId) {
		this.addToWxUserId = addToWxUserId;
	}

	public String getCurrProjectId() {
		return currProjectId;
	}

	public void setCurrProjectId(String currProjectId) {
		this.currProjectId = currProjectId;
	}

	public int getAutoIncId() {
		return autoIncId;
	}

	public void setAutoIncId(int autoIncId) {
		this.autoIncId = autoIncId;
	}

	public User() {
		super();
		this.loginFlag = Global.YES;
	}

	public User(String id) {
		super(id);
	}

	public User(String id, String loginName) {
		super(id);
		this.loginName = loginName;
	}

	public User(Role role) {
		super();
		this.role = role;
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getOldWxUserId() {
		return oldWxUserId;
	}

	public void setOldWxUserId(String oldWxUserId) {
		this.oldWxUserId = oldWxUserId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@SupCol(isUnique = "true", isHide = "true")
	@ExcelField(title = "ID", type = 1, align = 2, sort = 1)
	public String getId() {
		return id;
	}

	@JsonIgnore
	@NotNull(message = "归属公司不能为空")
	@ExcelField(title = "归属公司", align = 2, sort = 20)
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	@JsonIgnore
	@NotNull(message = "归属部门不能为空")
	@ExcelField(title = "归属部门", align = 2, sort = 25)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min = 1, max = 100, message = "登录名长度必须介于 1 和 100 之间")
	@ExcelField(title = "登录名", align = 2, sort = 30)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	@JsonIgnore
	@Length(min = 1, max = 100, message = "密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
	@ExcelField(title = "姓名", align = 2, sort = 40)
	public String getName() {
		return name;
	}

	// 2016-7-8 去除工号的限制。因为工号非必输。
	// @Length(min = 1, max = 100, message = "工号长度必须介于 1 和 100 之间")
	// 2016-7-8
	@ExcelField(title = "工号", align = 2, sort = 45)
	public String getNo() {
		return no;
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

	public void setNo(String no) {
		this.no = no;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Email(message = "邮箱格式不正确")
	@Length(min = 0, max = 200, message = "邮箱长度必须介于 1 和 200 之间")
	@ExcelField(title = "邮箱", align = 1, sort = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min = 0, max = 200, message = "电话长度必须介于 1 和 200 之间")
	@ExcelField(title = "电话", align = 2, sort = 60)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 200, message = "手机长度必须介于 1 和 200 之间")
	@ExcelField(title = "手机", align = 2, sort = 70)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "备注", align = 1, sort = 900)
	public String getRemarks() {
		return remarks;
	}

	@Length(min = 0, max = 100, message = "用户类型长度必须介于 1 和 100 之间")
	@ExcelField(title = "用户类型", align = 2, sort = 80, dictType = "sys_user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@ExcelField(title = "创建时间", type = 0, align = 1, sort = 90)
	public Date getCreateDate() {
		return createDate;
	}

	@ExcelField(title = "最后登录IP", type = 1, align = 1, sort = 100)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "最后登录日期", type = 1, align = 1, sort = 110)
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldLoginIp() {
		if (oldLoginIp == null) {
			return loginIp;
		}
		return oldLoginIp;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOldLoginDate() {
		if (oldLoginDate == null) {
			return loginDate;
		}
		return oldLoginDate;
	}

	public void setOldLoginDate(Date oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
	@ExcelField(title = "拥有角色", align = 1, sort = 800, fieldType = RoleListType.class)
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}

	// 2016-7-2 管理员的判断，移到了UserUtils.isAdmin()函数。
	// public boolean isAdmin() {
	// return isAdmin(this.id);
	// }

	// public static boolean isAdmin(String id) {
	// return id != null && "1".equals(id);
	// } 2016-7-2 end。

	@Override
	public String toString() {
		return id;
	}

	public String getIdFmDB() {
		return idFmDB;
	}

	public void setIdFmDB(String idFmDB) {
		this.idFmDB = idFmDB;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNsPosition() {
		return nsPosition;
	}

	public void setNsPosition(String nsPosition) {
		this.nsPosition = nsPosition;
	}

}