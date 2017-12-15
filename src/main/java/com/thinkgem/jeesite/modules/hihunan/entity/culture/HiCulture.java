/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.culture;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 文化Entity
 * 
 * @author yuyabiao
 * @version 2016-11-29
 */
public class HiCulture extends DataEntity<HiCulture> {

	private static final long serialVersionUID = 1L;
	private int autoIncId; // autoincid
	private String title; // 题头
	private String titlePhoto; // 题头图片
	private String address; // 地点

	public HiCulture() {
		super();
	}

	public HiCulture(String id) {
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

	@Length(min = 0, max = 255, message = "地点长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}