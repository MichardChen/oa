/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.model.news;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fastweixin.api.entity.BaseModel;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 新闻Entity
 * 
 * @author yuyabiao
 * @version 2016-11-29
 */
public class HiNewsModel extends BaseModel{

	private String id;
	private int autoIncId; // autoincid
	private String title; // 题头
	private String titlePhoto; // 题头图片
	private String publishDate; // 发表日期
	private String brief; // 简介
	private String keyword; // 关键字
	private String actor; // 作者
	private String articalType; // 文章类型，如美食，旅游等
	private String content; // content
	private String contentPhoto; // content_photo
	private String video; // video
	private String videoPhoto; // video_photo
	private String url; // url
	private String status; // 确认状态

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

	public String getContentPhoto() {
		return contentPhoto;
	}

	public void setContentPhoto(String contentPhoto) {
		this.contentPhoto = contentPhoto;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getVideoPhoto() {
		return videoPhoto;
	}

	public void setVideoPhoto(String videoPhoto) {
		this.videoPhoto = videoPhoto;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}