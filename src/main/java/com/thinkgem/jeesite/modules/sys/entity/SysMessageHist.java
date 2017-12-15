package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.pj_common.persistence.ns.entity.SysMessageHistRecord;

public class SysMessageHist extends DataEntity<SysMessageHist> {

	private static final long serialVersionUID = 1L;

	private int autoIncId;
	private String system; // 应用系统名.使用此消息的应用。如"atminspect","oa"
	private String procInsId; // 流程实例ID （仅当后续处理标志为3(消息反馈)时,记录发送消息时启动的工作流。）
	private String nextProcSign; // 后续处理标志（1:无需反馈(如公告,协助布设) 2:流程节点待办 3:消息反馈）
	private String replyContent; // 反馈信息
	private Date replyDate; // 反馈日期
	private String senderWxUserid; // 发送者微信账号
	private String senderUserId; // 发送者的系统内部userID
	private String senderName; // 发送者名称
	private String receiverWxUserid; // 接收者微信账号
	private String receiverUserId; // 接收者的系统内部userID
	private String receiverName; // 接收者名称
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
	private Date noticeSendDate;// 公告发布时间
	private String noticeSendDateString;// 公告发布时间String类型
	private Date readDate; // 阅读日期
	private String readFlag; // 阅读状态 字典表 0:未阅读 1:已阅读
	private String replyStatus;// 反馈的标志 0：未反馈 1：已反馈

	private boolean isSelf;// // 是否只查询自己的通知
	private String readNum; // 已读条数
	private String unReadNum; // 未读条数

	private Date startDate;// 查询消息的时间段截止开始时间
	private Date endDate;// 查询消息的时间段截止结束时间
	private List<SysMessageHistRecord> sysMessageRecordList = Lists
			.newArrayList();

	public SysMessageHist() {
		super();
	}

	public SysMessageHist(String id) {
		super(id);
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentDtl() {
		return contentDtl;
	}

	public void setContentDtl(String contentDtl) {
		this.contentDtl = contentDtl;
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

	public Date getNoticeSendDate() {
		return noticeSendDate;
	}

	public void setNoticeSendDate(Date noticeSendDate) {
		this.noticeSendDate = noticeSendDate;
	}

	public String getNoticeSendDateString() {
		return noticeSendDateString;
	}

	public void setNoticeSendDateString(String noticeSendDateString) {
		this.noticeSendDateString = noticeSendDateString;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public String getUnReadNum() {
		return unReadNum;
	}

	public void setUnReadNum(String unReadNum) {
		this.unReadNum = unReadNum;
	}

	public List<SysMessageHistRecord> getSysMessageRecordList() {
		return sysMessageRecordList;
	}

	public void setSysMessageRecordList(
			List<SysMessageHistRecord> sysMessageRecordList) {
		this.sysMessageRecordList = sysMessageRecordList;
	}

	/**
	 * 获取通知发送记录用户ID
	 */
	public String getSysMessageRecordIds() {
		return Collections3.extractToString(sysMessageRecordList, "user.id",
				",");
	}

	/**
	 * 设置通知发送记录用户ID
	 */
	public void setSysMessageRecordIds(String sysMessageHistRecord) {
		this.sysMessageRecordList = Lists.newArrayList();
		for (String id : StringUtils.split(sysMessageHistRecord, ",")) {
			SysMessageHistRecord entity = new SysMessageHistRecord();
			entity.setId(IdGen.uuid());
			entity.setSysMessageHist(this);
			entity.setUser(new User(id));
			entity.setReadFlag("0");
			this.sysMessageRecordList.add(entity);
		}
	}

	/**
	 * 获取通知发送记录用户Name
	 */
	public String getSysMessageHistRecordNames() {
		return Collections3.extractToString(sysMessageRecordList, "user.name",
				",");
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

}
