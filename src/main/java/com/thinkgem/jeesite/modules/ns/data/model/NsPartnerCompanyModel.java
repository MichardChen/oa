package com.thinkgem.jeesite.modules.ns.data.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

/**
 * 相关单位Model,2016/5/10
 * 
 * @author yuyabiao
 * 
 */
public class NsPartnerCompanyModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String id;// 主键ID
	private String type;// 单位类型
	private String typeValue;// 单位类型value（英文表示名）
	private String name;// 单位名称
	private String officeId;// 对应单位ID
	private String officeName;// 对应部门名称
	private String primaryPersonId;// 对应单位的主负责人ID
	private int sort;// 排序
	private String remarks;// 备注
	private String head;// 部门对应的负责人名称
	private String headId; // 部门对应的负责人ID
	
	public NsPartnerCompanyModel(){
		super();
	}
	
	public NsPartnerCompanyModel(String type){
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getPrimaryPersonId() {
		return primaryPersonId;
	}

	public void setPrimaryPersonId(String primaryPersonId) {
		this.primaryPersonId = primaryPersonId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	

}
