/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.headershow;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 头部轮播Entity
 * 
 * @author yuyabiao
 * @version 2016-11-30
 */
public class HiHeaderShow extends DataEntity<HiHeaderShow> {

	private static final long serialVersionUID = 1L;
	private int autoIncId; // autoincid
	private String title; // 题头
	private String titlePhoto; // 题头图片
	private String listType; // 类型
	private String seq; // 顺序
	private String url; // 链接路径
	private String isEffect; // 是否生效
	private String h5appId; // 所属栏位类别

	public HiHeaderShow() {
		super();
	}

	public HiHeaderShow(String id) {
		super(id);
	}

	@NotNull(message = "autoincid不能为空")
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

	@Length(min = 0, max = 11, message = "顺序长度必须介于 0 和 11 之间")
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	@Length(min = 0, max = 255, message = "链接路径长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Length(min = 0, max = 11, message = "是否生效长度必须介于 0 和 11 之间")
	public String getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}

	@Length(min = 0, max = 20, message = "所属栏位类别长度必须介于 0 和 20 之间")
	public String getH5appId() {
		return h5appId;
	}

	public void setH5appId(String h5appId) {
		this.h5appId = h5appId;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

}