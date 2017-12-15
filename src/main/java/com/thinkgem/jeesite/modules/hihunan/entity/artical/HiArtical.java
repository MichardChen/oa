/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.artical;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * hi_articalEntity
 * 
 * @author yu
 * @version 2016-11-28
 */
public class HiArtical extends DataEntity<HiArtical> {

	private static final long serialVersionUID = 1L;
	private String autoIncId; // autoincid
	private String parentId; // 上级ID，用于关联如本地生活，美食，旅游等
	private String title; // 题头
	private String titlePhoto; // 题头图片
	private String articalPhoto;//
	private Date publishDate; // 发表日期
	private String brief; // 简介
	private String keyword; // 关键字
	private String actor; // 作者
	private String articalType; // 文章类型，如美食，旅游等
	private String content;//
	private String auditState;// 审核状态
	private int listMaxCount;
	private int validDay;// 有效时间
	
	private Date startTime; // 查询开始时间
	private Date endTime; // 查询结束时间

	public HiArtical() {
		super();
	}

	public HiArtical(String id) {
		super(id);
	}

	@Length(min = 1, max = 11, message = "autoincid长度必须介于 1 和 11 之间")
	public String getAutoIncId() {
		return autoIncId;
	}

	public void setAutoIncId(String autoIncId) {
		this.autoIncId = autoIncId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@Length(min = 0, max = 255, message = "简介长度必须介于 0 和 255 之间")
	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	@Length(min = 0, max = 255, message = "关键字长度必须介于 0 和 255 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Length(min = 0, max = 50, message = "作者长度必须介于 0 和 50 之间")
	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	@Length(min = 0, max = 20, message = "文章类型，如美食，旅游等长度必须介于 0 和 20 之间")
	public String getArticalType() {
		return articalType;
	}

	public void setArticalType(String articalType) {
		this.articalType = articalType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getArticalPhoto() {
		return articalPhoto;
	}

	public void setArticalPhoto(String articalPhoto) {
		this.articalPhoto = articalPhoto;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public int getListMaxCount() {
		return listMaxCount;
	}

	public void setListMaxCount(int listMaxCount) {
		this.listMaxCount = listMaxCount;
	}

	public int getValidDay() {
		return validDay;
	}

	public void setValidDay(int validDay) {
		this.validDay = validDay;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	

}