package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class SysTask extends DataEntity<SysTask> {

	private static final long serialVersionUID = 1L;
	private String taskType;
	private String taskOwner;
	private String taskData;
	private String taskStatus;
	private Date taskGenDate;
	private Date taskCompDate;
	
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskOwner() {
		return taskOwner;
	}
	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}
	public String getTaskData() {
		return taskData;
	}
	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public Date getTaskCompDate() {
		return taskCompDate;
	}
	public void setTaskCompDate(Date taskCompDate) {
		this.taskCompDate = taskCompDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getTaskGenDate() {
		return taskGenDate;
	}
	public void setTaskGenDate(Date taskGenDate) {
		this.taskGenDate = taskGenDate;
	}
	
}