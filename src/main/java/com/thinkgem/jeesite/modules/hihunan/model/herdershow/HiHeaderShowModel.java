package com.thinkgem.jeesite.modules.hihunan.model.herdershow;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class HiHeaderShowModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String id;
	private String title; // 题头
	private String titlePhoto; // 题头图片
	private String seq; // 顺序
	private String url; // 链接路径
	private String isEffect; // 是否生效
	private String h5appId; // 所属栏位类别
	private String listType;// 类型

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}

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
