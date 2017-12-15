package com.thinkgem.jeesite.modules.sys.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

/**
 * 资金成本报表生成数据用，接收所有工程的信息
 * 
 * @author yuyabiao
 * 
 */
public class FundingCostDataModel extends BaseModel {

	private static final long serialVersionUID = -6506678621271129479L;
	private String companyId;// 会员ID
	private String prjName;// 工程名称
	private String cycleDescribe;// 期次描述
	private String projectId;// 工程ID
	private String rptCycleId;// 当前报告区间ID
	private String rptInfoId;//报表ID

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getCycleDescribe() {
		return cycleDescribe;
	}

	public void setCycleDescribe(String cycleDescribe) {
		this.cycleDescribe = cycleDescribe;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getRptCycleId() {
		return rptCycleId;
	}

	public void setRptCycleId(String rptCycleId) {
		this.rptCycleId = rptCycleId;
	}

	public String getRptInfoId() {
		return rptInfoId;
	}

	public void setRptInfoId(String rptInfoId) {
		this.rptInfoId = rptInfoId;
	}

}
