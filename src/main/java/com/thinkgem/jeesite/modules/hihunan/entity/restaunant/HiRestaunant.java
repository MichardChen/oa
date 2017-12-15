/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.restaunant;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 美食Entity
 * 
 * @author lin
 * @version 2016-12-02
 */
public class HiRestaunant extends DataEntity<HiRestaunant> {

	private static final long serialVersionUID = 1L;
	private int autoIncId; // autoincid
	private String title; // 题头
	private String titlePhoto; // 题头图片
	private String address; // 地点
	private String cookingStyle; // 菜系
	private BigDecimal longitude; // 经度
	private BigDecimal latitude; // 纬度
	private String star; // 评级
	private String tradingArea; // 商圈
	private String tel; // 电话
	private String description; // 说明
	private int seq; // 顺序
	private String isRecommend; // 是否推荐
	private int minDistance;// 最小距离
	private int maxDistance;// 最大距离
	private int listMaxCount;
	private int memberId;
	private String url;

	public HiRestaunant() {
		super();
	}

	public HiRestaunant(String id) {
		super(id);
	}

	/* @Length(min=1, max=11, message="autoincid长度必须介于 1 和 11 之间") */
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

	@Length(min = 0, max = 20, message = "菜系长度必须介于 0 和 20 之间")
	public String getCookingStyle() {
		return cookingStyle;
	}

	public void setCookingStyle(String cookingStyle) {
		this.cookingStyle = cookingStyle;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Length(min = 0, max = 11, message = "评级长度必须介于 0 和 11 之间")
	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	@Length(min = 0, max = 20, message = "商圈长度必须介于 0 和 20 之间")
	public String getTradingArea() {
		return tradingArea;
	}

	public void setTradingArea(String tradingArea) {
		this.tradingArea = tradingArea;
	}

	@Length(min = 0, max = 20, message = "电话长度必须介于 0 和 20 之间")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Length(min = 0, max = 512, message = "说明长度必须介于 0 和 512 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/* @Length(min=0, max=11, message="顺序长度必须介于 0 和 11 之间") */
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Length(min = 0, max = 11, message = "是否推荐长度必须介于 0 和 11 之间")
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public int getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(int minDistance) {
		this.minDistance = minDistance;
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public int getListMaxCount() {
		return listMaxCount;
	}

	public void setListMaxCount(int listMaxCount) {
		this.listMaxCount = listMaxCount;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}