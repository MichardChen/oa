package com.thinkgem.jeesite.modules.hihunan.model.listshow;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class HiListShowModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String id;// 主键ID
	private String title;// 标题
	private String titlePhoto;// 标题图片
	private String address;// 地址
	private String type;// 类型
	private String tagType;// 标签类型，最热，最新等
    private String url; //外部链接URL 
    private String sourceFrom; //  1:添加文章  2:外部URL
    
    
	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

}
