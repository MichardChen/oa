package com.thinkgem.jeesite.modules.sys.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.BaseModel;

public class MemberModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2520896953285112422L;
	private String id;// 主键ID
	private int autoIncId;// 自增长ID
	private int concurrentPrjNum;// 同时使用最大工程数
	private String validFlag;// 有效标志
	private String areaId;// 区域(市)
	private String memberLevel;// 会员级别
	private String memberName;// 会员名
	private String addr;// 地址
	private Date memberStartDate;// 会员资格开始日
	private Date memberValidDate;// 会员资格截止日
	private String memberStatus;//会员状态
	private String tel;// 电话
	private String website;// 会员公司网址

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAutoIncId() {
		return autoIncId;
	}

	public void setAutoIncId(int autoIncId) {
		this.autoIncId = autoIncId;
	}

	public int getConcurrentPrjNum() {
		return concurrentPrjNum;
	}

	public void setConcurrentPrjNum(int concurrentPrjNum) {
		this.concurrentPrjNum = concurrentPrjNum;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@JsonFormat(pattern = "yyyy/MM/dd")
	public Date getMemberStartDate() {
		return memberStartDate;
	}

	public void setMemberStartDate(Date memberStartDate) {
		this.memberStartDate = memberStartDate;
	}

	@JsonFormat(pattern = "yyyy/MM/dd")
	public Date getMemberValidDate() {
		return memberValidDate;
	}

	public void setMemberValidDate(Date memberValidDate) {
		this.memberValidDate = memberValidDate;
	}
	
	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}
