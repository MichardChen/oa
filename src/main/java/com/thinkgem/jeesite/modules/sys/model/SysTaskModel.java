/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.model;

import java.util.Date;
import com.thinkgem.jeesite.common.persistence.BaseModel;

/**
 * 菜单Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class SysTaskModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	/*private String id;
	private String taskType;
	private String taskOwner;
	private String taskData;
	private String taskStatus;
	private Date taskGenDate;
	private Date taskCompDate;*/
	private String Type;
	private String Action;
	private BaseModel data;
	
	
	/*public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	}*/
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public BaseModel getData() {
		return data;
	}
	public void setData(BaseModel data) {
		this.data = data;
	}
	
}