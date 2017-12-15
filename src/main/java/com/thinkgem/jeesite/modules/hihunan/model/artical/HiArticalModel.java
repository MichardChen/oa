package com.thinkgem.jeesite.modules.hihunan.model.artical;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class HiArticalModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String id;
	private String parentId; // 上级ID，用于关联如本地生活，美食，旅游等
	private String title; // 题头
	private String titlePhoto; // 题头图片
	private String publishDate; // 发表日期(String类型)
	private String brief; // 简介
	private String keyword; // 关键字
	private String actor; // 作者
	private String articalType; // 文章类型，如美食，旅游等
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitlePhoto() {
		return titlePhoto;
	}

	public void setTitlePhoto(String titlePhoto) {
		this.titlePhoto = titlePhoto;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

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
}
