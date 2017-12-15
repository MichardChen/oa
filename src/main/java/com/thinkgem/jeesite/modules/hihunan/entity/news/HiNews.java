/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.news;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 新闻Entity
 * 
 * @author yuyabiao
 * @version 2016-11-29
 */
public class HiNews extends DataEntity<HiNews> {

	private static final long serialVersionUID = 1L;
	private int autoIncId; // autoincid
	private String title; // 题头
	private String titlePhoto; // 题头图片
	private Date publishDate; // 发表日期
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

	public HiNews() {
		super();
	}

	public HiNews(String id) {
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

	@Length(min = 0, max = 4000, message = "content长度必须介于 0 和 4000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Length(min = 0, max = 255, message = "content_photo长度必须介于 0 和 255 之间")
	public String getContentPhoto() {
		return contentPhoto;
	}

	public void setContentPhoto(String contentPhoto) {
		this.contentPhoto = contentPhoto;
	}

	@Length(min = 0, max = 255, message = "video长度必须介于 0 和 255 之间")
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Length(min = 0, max = 255, message = "video_photo长度必须介于 0 和 255 之间")
	public String getVideoPhoto() {
		return videoPhoto;
	}

	public void setVideoPhoto(String videoPhoto) {
		this.videoPhoto = videoPhoto;
	}

	@Length(min = 0, max = 255, message = "url长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Length(min = 0, max = 11, message = "确认状态长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}