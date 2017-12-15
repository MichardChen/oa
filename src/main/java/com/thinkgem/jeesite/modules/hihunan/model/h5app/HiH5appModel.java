package com.thinkgem.jeesite.modules.hihunan.model.h5app;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.BaseModel;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;

public class HiH5appModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String id;// 主键ID
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
	private String startDate;// 生效时间
	private String endDate;// 生效时间
	private String vertionNo;// 版本号
	private String listType;// 首页一览展示类型
	private int listMaxCount;
	private String listIcon;//

	private String value;
	private String label;

	private List<HiH5appModel> hiH5appModel;// 类别大类下 所有的数据集合

	private List<HiListShowModel> listModel;

	public String getId() {
		return id;
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

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getVertionNo() {
		return vertionNo;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setVertionNo(String vertionNo) {
		this.vertionNo = vertionNo;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<HiH5appModel> getHiH5appModel() {
		return hiH5appModel;
	}

	public void setHiH5appModel(List<HiH5appModel> hiH5appModel) {
		this.hiH5appModel = hiH5appModel;
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

	public String getListType() {
		return listType;
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

	public void setListType(String listType) {
		this.listType = listType;
	}

	public List<HiListShowModel> getListModel() {
		return listModel;
	}

	public void setListModel(List<HiListShowModel> listModel) {
		this.listModel = listModel;
	}

	public int getListMaxCount() {
		return listMaxCount;
	}

	public void setListMaxCount(int listMaxCount) {
		this.listMaxCount = listMaxCount;
	}

	public String getListIcon() {
		return listIcon;
	}

	public void setListIcon(String listIcon) {
		this.listIcon = listIcon;
	}
}
