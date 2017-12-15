package com.thinkgem.jeesite.modules.hihunan.entity.listshow;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * @author yuyabiao
 * 
 */
public class HiListShow extends DataEntity<HiListShow> {

	private static final long serialVersionUID = 1L;

	private int autoIncId;// 自增长ID
	private String title;// 标题
	private String titlePhoto;// 标题图片
	private String address;// 地点
	private String type; // 类型   
	private String url;  // URL
	private String sourceFrom; //  1:添加文章  2:外部URL
	private int listMaxCount;

	private String message; // 用于保存后的消息显示

	
	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getListMaxCount() {
		return listMaxCount;
	}

	public void setListMaxCount(int listMaxCount) {
		this.listMaxCount = listMaxCount;
	}

}
