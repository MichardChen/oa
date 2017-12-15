package com.thinkgem.jeesite.modules.hihunan.entity.favorites;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class HiFavorites extends DataEntity<HiFavorites> {

	private static final long serialVersionUID = 1L;
	
	private String favoritesId;// 收藏编号
	private int autoIncId;//
	private int memberId;//
	private short favoritesType;// 收藏类型
	private String favoritesTitle;// 收藏标题
	private String favoritesComment;// 收藏内容
	private String favoritesImage;// 收藏图片
	private String favoritesUrl;// 收藏URl
	private Date favoritesDate;// 收藏时间

	public String getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(String favoritesId) {
		this.favoritesId = favoritesId;
	}

	public int getAutoIncId() {
		return autoIncId;
	}

	public void setAutoIncId(int autoIncId) {
		this.autoIncId = autoIncId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public short getFavoritesType() {
		return favoritesType;
	}

	public void setFavoritesType(short favoritesType) {
		this.favoritesType = favoritesType;
	}

	public String getFavoritesTitle() {
		return favoritesTitle;
	}

	public void setFavoritesTitle(String favoritesTitle) {
		this.favoritesTitle = favoritesTitle;
	}

	public String getFavoritesComment() {
		return favoritesComment;
	}

	public void setFavoritesComment(String favoritesComment) {
		this.favoritesComment = favoritesComment;
	}

	public String getFavoritesImage() {
		return favoritesImage;
	}

	public void setFavoritesImage(String favoritesImage) {
		this.favoritesImage = favoritesImage;
	}

	public String getFavoritesUrl() {
		return favoritesUrl;
	}

	public void setFavoritesUrl(String favoritesUrl) {
		this.favoritesUrl = favoritesUrl;
	}

	public Date getFavoritesDate() {
		return favoritesDate;
	}

	public void setFavoritesDate(Date favoritesDate) {
		this.favoritesDate = favoritesDate;
	}

}
