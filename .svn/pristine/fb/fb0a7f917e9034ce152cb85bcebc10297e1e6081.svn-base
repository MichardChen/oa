package com.thinkgem.jeesite.pj_common.persistence.ns.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.SysMessageHist;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 微信通告记录Entity
 * 
 * @author yuyabiao
 * @2016/7/21
 */
public class SysMessageHistRecord extends DataEntity<SysMessageHistRecord> {

	private static final long serialVersionUID = 1L;
	
	private SysMessageHist sysMessageHist;// 微信通告信息ID
	private User user; // 接受人
	private String readFlag; // 阅读标记（0：未读；1：已读）
	private Date readDate; // 阅读时间

	public SysMessageHistRecord() {
		super();
	}

	public SysMessageHistRecord(String id) {
		super(id);
	}

	public SysMessageHistRecord(SysMessageHist sysMessageHist) {
		this.sysMessageHist = sysMessageHist;
	}

	public SysMessageHist getSysMessageHist() {
		return sysMessageHist;
	}

	public void setSysMessageHist(SysMessageHist sysMessageHist) {
		this.sysMessageHist = sysMessageHist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Length(min = 0, max = 1, message = "阅读标记（0：未读；1：已读）长度必须介于 0 和 1 之间")
	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

}
