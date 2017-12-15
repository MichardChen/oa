/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.localactivity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 本地生活Entity
 * 
 * @author yuyabiao
 * @version 2016-11-29
 */
public class HiLocalActivity extends DataEntity<HiLocalActivity> {

	private static final long serialVersionUID = 1L;
	private int autoIncId; // autoincid
	private String title; // 题头
	private String titlePhoto; // 题头图片
	private String contentDetail; // 内容
	private String contentPhoto; // 内容图片
	private Date startDate; // 开始日期
	private Date endDate; // 结束日期
	private String applyUserNumber; // 报名人数
	private String type; // 本地生活分类

	public HiLocalActivity() {
		super();
	}

	public HiLocalActivity(String id) {
		super(id);
	}

	@Length(min = 1, max = 11, message = "autoincid长度必须介于 1 和 11 之间")
	public int getAutoIncId() {
		return autoIncId;
	}

	public void setAutoIncId(int autoIncId) {
		this.autoIncId = autoIncId;
	}

	@Length(min = 0, max = 255, message = "题头长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min = 0, max = 255, message = "题头图片长度必须介于 0 和 255 之间")
	public String getTitlePhoto() {
		return titlePhoto;
	}

	public void setTitlePhoto(String titlePhoto) {
		this.titlePhoto = titlePhoto;
	}

	@Length(min = 0, max = 2000, message = "内容长度必须介于 0 和 2000 之间")
	public String getContentDetail() {
		return contentDetail;
	}

	public void setContentDetail(String contentDetail) {
		this.contentDetail = contentDetail;
	}

	@Length(min = 0, max = 255, message = "内容图片长度必须介于 0 和 255 之间")
	public String getContentPhoto() {
		return contentPhoto;
	}

	public void setContentPhoto(String contentPhoto) {
		this.contentPhoto = contentPhoto;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Length(min = 0, max = 11, message = "报名人数长度必须介于 0 和 11 之间")
	public String getApplyUserNumber() {
		return applyUserNumber;
	}

	public void setApplyUserNumber(String applyUserNumber) {
		this.applyUserNumber = applyUserNumber;
	}

	@Length(min = 0, max = 20, message = "本地生活分类长度必须介于 0 和 20 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}