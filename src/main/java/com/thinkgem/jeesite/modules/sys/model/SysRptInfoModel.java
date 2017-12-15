package com.thinkgem.jeesite.modules.sys.model;


import com.thinkgem.jeesite.common.persistence.BaseModel;

public class SysRptInfoModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -141910869197978195L;
	private String id;//报表ID
	private String rptName;//资金成本名
	private String rptEngName;//报表英文名字 不可随意修改
	private String displayTarget;//显示场所
	private String majorCode;//专业代码
	private String reportFullPath;//报表文件的全路径
	private String reportFileName;//报表文件名(不含路径)
	private String remarks;//备注
	private String value;
	private String label;
	private String idFmDB;
	private String insertFlag;//插入标志
	private String modifyFlag;//修改标志
	
	public String getDisplayTarget() {
		return displayTarget;
	}
	public void setDisplayTarget(String displayTarget) {
		this.displayTarget = displayTarget;
	}
	public String getMajorCode() {
		return majorCode;
	}
	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}
	public String getReportFullPath() {
		return reportFullPath;
	}
	public void setReportFullPath(String reportFullPath) {
		this.reportFullPath = reportFullPath;
	}
	public String getReportFileName() {
		return reportFileName;
	}
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRptName() {
		return rptName;
	}
	public void setRptName(String rptName) {
		this.rptName = rptName;
	}
	public String getRptEngName() {
		return rptEngName;
	}
	public void setRptEngName(String rptEngName) {
		this.rptEngName = rptEngName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getInsertFlag() {
		return insertFlag;
	}
	public void setInsertFlag(String insertFlag) {
		this.insertFlag = insertFlag;
	}
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	public String getIdFmDB() {
		return idFmDB;
	}
	public void setIdFmDB(String idFmDB) {
		this.idFmDB = idFmDB;
	}
	
	
	

}
