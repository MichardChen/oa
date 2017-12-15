package com.thinkgem.jeesite.modules.act.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.BaseModel;

/**
 * 工作流返回数据Model
 * 
 * @author yuyabiao
 * @since 2016/6/12
 */
public class ActModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String taskId; // 任务编号
	private String taskName; // 任务名称
	private String taskDefKey; // 任务定义Key（任务环节标识）
	private String procInsId; // 流程实例ID
	private String procDefId; // 流程定义ID
	private String initiatorName;    // 任务发起者
	private String initiatorUserId;  //任务发起者的UserId. 如 u_234
	private String title; // 任务标题
	private String status; // 任务状态（todo/claim/finish)
	private String assignee; // 任务执行人编号
	private Date beginDate; // 开始日期
	private String userId;// 上个环节的参与者
	private String startFlag;// 上个环节的状态，Y：发起流程，N：其他状态

	private String activityName;// 环节名称
	private String assigneeName;// 执行人姓名
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private String durationTime;// 历时
    private String comment;//流程备注
    
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getInitiatorUserId() {
		return initiatorUserId;
	}

	public void setInitiatorUserId(String initiatorUserId) {
		this.initiatorUserId = initiatorUserId;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getInitiatorName() {
		return initiatorName;
	}

	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartFlag() {
		return startFlag;
	}

	public void setStartFlag(String startFlag) {
		this.startFlag = startFlag;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}
}
