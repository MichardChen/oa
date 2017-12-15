package com.thinkgem.jeesite.modules.sys.model;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class SysMessageHistModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3782434845969648929L;
	private int autoIncId;
	private String id;
	private String system; // 应用系统名.使用此消息的应用。如"atminspect","oa"
	private String procInsId; // 流程实例ID （仅当后续处理标志为3(消息反馈)时,记录发送消息时启动的工作流。）
	private String nextProcSign; // 后续处理标志（1:无需反馈(如公告,协助布设) 2:流程节点待办 3:消息反馈）
	private String replyContent; // 反馈信息
	private Date replyDate; // 反馈日期
	private String replyStatus;// 反馈标识 0,未反馈；1,已反馈
	private String senderWxUserid; // 发送者微信账号
	private String senderUserId; // 发送者的系统内部userID
	private String senderName; // 发送者名称
	private String receiverWxUserid; // 接收者微信账号
	private String receiverUserId; // 接收者的系统内部userID
	private String receiverName; // 接收者名称
	private String receiverOfficeName;// 接收单位名称
	private String title; //
	private String contentSend; // 文本类型的消息。可以xml,富文本,json等格式
	private String contentType; // 内容类型 字典表: msg_content_type
	private String contentDtl; // 文本类型的消息(查看详情用)。可以xml,富文本,json等格式
	private String bizId; // 关联业务id。此消息关联的业务id，可为空。如撤机时 关联的撤机表id。
	private String customizeField1; // 与消息内容关联的信息，当其他字段不够用时，写在这里。如流程节点id，
	private String customizeField2; //
	private String customizeField3; //
	private Date sendDate; // 发送日期
	private String sendStatus; // 发送状态 字典表 0:待发送 1:已发送 2:取消
	private Date readDate; // 阅读日期
	private String readTime;// 阅读时间String
	private String readFlag; // 阅读状态 字典表 0:未阅读 1:已阅读
	private String time;// 时间页面显示用字段

	private String type;// 单位id对应的type标识

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

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSenderWxUserid() {
		return senderWxUserid;
	}

	public void setSenderWxUserid(String senderWxUserid) {
		this.senderWxUserid = senderWxUserid;
	}

	public String getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(String senderUserId) {
		this.senderUserId = senderUserId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverWxUserid() {
		return receiverWxUserid;
	}

	public void setReceiverWxUserid(String receiverWxUserid) {
		this.receiverWxUserid = receiverWxUserid;
	}

	public String getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverOfficeName() {
		return receiverOfficeName;
	}

	public void setReceiverOfficeName(String receiverOfficeName) {
		this.receiverOfficeName = receiverOfficeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentSend() {
		return contentSend;
	}

	public void setContentSend(String contentSend) {
		this.contentSend = contentSend;
	}

	public String getContentDtl() {
		return contentDtl;
	}

	public void setContentDtl(String contentDtl) {
		this.contentDtl = contentDtl;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getCustomizeField1() {
		return customizeField1;
	}

	public void setCustomizeField1(String customizeField1) {
		this.customizeField1 = customizeField1;
	}

	public String getCustomizeField2() {
		return customizeField2;
	}

	public void setCustomizeField2(String customizeField2) {
		this.customizeField2 = customizeField2;
	}

	public String getCustomizeField3() {
		return customizeField3;
	}

	public void setCustomizeField3(String customizeField3) {
		this.customizeField3 = customizeField3;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getNextProcSign() {
		return nextProcSign;
	}

	public void setNextProcSign(String nextProcSign) {
		this.nextProcSign = nextProcSign;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
