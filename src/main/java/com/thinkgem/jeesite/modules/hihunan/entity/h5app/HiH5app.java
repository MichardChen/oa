package com.thinkgem.jeesite.modules.hihunan.entity.h5app;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 
 * @author lin
 * 
 */
public class HiH5app extends DataEntity<HiH5app> {

	private static final long serialVersionUID = 1L;

	private int autoIncId;// 自增长ID
	private String type1;// 一级分类
	private String type2;// 二级分类
	private String type3;// 三级分类
	private String name;// 名称
	private String icon;// 图标
	private String url;// url
	private String isEffect;// 是否生效
	private String isNeedLogin;// 是否需要登入
	private String seq;// 序号
	private String isTop;// 是否置顶
	private Date startDate;// 生效时间
	private Date endDate;// 生效时间
	private String versionNo;// 版本号
	private String listType;// 首页一览展示类型
	private String listIcon;
	private int listMaxCount;

	public int getAutoIncId() {
		return autoIncId;
	}

	public String getName() {
		return name;
	}

	public String getIcon() {
		return icon;
	}

	public String getUrl() {
		return url;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setAutoIncId(int autoIncId) {
		this.autoIncId = autoIncId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getType1() {
		return type1;
	}

	public String getType2() {
		return type2;
	}

	public String getType3() {
		return type3;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public void setType3(String type3) {
		this.type3 = type3;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getIsEffect() {
		return isEffect;
	}

	public String getIsNeedLogin() {
		return isNeedLogin;
	}

	public String getSeq() {
		return seq;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}

	public void setIsNeedLogin(String isNeedLogin) {
		this.isNeedLogin = isNeedLogin;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getListIcon() {
		return listIcon;
	}

	public void setListIcon(String listIcon) {
		this.listIcon = listIcon;
	}

	public int getListMaxCount() {
		return listMaxCount;
	}

	public void setListMaxCount(int listMaxCount) {
		this.listMaxCount = listMaxCount;
	}

}
