package com.thinkgem.jeesite.pj_common.persistence.ns.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 相关单位Entity,2016/5/10
 * 
 * @author yuyabiao
 * 
 */
public class NsPartnerCompany extends DataEntity<NsPartnerCompany> {

	private static final long serialVersionUID = 1L;
	private int autoIncId;// 自增长ID
	private String type;// 单位类型
	private String name;// 单位名称
	private String oldName;// 旧的单位名称
	private String officeId;// 对应部门ID
	private String oldOffice;// 修改数据时对应的部门ID
	private int sort;// 排序

	public int getAutoIncId() {
		return autoIncId;
	}

	public void setAutoIncId(int autoIncId) {
		this.autoIncId = autoIncId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOldOffice() {
		return oldOffice;
	}

	public void setOldOffice(String oldOffice) {
		this.oldOffice = oldOffice;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
