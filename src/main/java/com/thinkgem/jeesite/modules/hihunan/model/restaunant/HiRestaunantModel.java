package com.thinkgem.jeesite.modules.hihunan.model.restaunant;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class HiRestaunantModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String id;// 主键ID
	private String title;// 标题
	private String titlePhoto;// 标题图片
	private String address;// 地址
	private String cookingStyle;// Cookie类型

	private String cookStyle;
	private String longitude;// 经度
	private String latitude;// 纬度
	private Integer star;// 评价
	private String trading_area;// 区域
	private String tel;// 电话
	private String description;// 描述
	private Integer seq;// 系列号
	private Integer isRecommend;// 推荐
	private String collect;

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

	public String getCookingStyle() {
		return cookingStyle;
	}

	public void setCookingStyle(String cookingStyle) {
		this.cookingStyle = cookingStyle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getTrading_area() {
		return trading_area;
	}

	public void setTrading_area(String trading_area) {
		this.trading_area = trading_area;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getCookStyle() {
		return cookStyle;
	}

	public void setCookStyle(String cookStyle) {
		this.cookStyle = cookStyle;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

}
