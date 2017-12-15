package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class SysRptInfo extends DataEntity<SysRptInfo>{

	private static final long serialVersionUID = 8479664019906659611L;
	
	private String rptName;
	private String rptEngName;//报表英文名字 不可随意修改
	private String displayTarget;//显示场所
	private String majorCode;//专业代码
	private String reportFullPath;//报表文件的全路径
	private String reportFileName;//报表文件名(不含路径)
	private String remarks;//备注
	private String insertFlag;//插入标志
	private String modifyFlag;//修改标志
	private String idFmDB;
	private int autoIncId;//自增长ID
	
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRptEngName() {
		return rptEngName;
	}
	public void setRptEngName(String rptEngName) {
		this.rptEngName = rptEngName;
	}
	public String getDisplayTarget() {
		return displayTarget;
	}
	public void setDisplayTarget(String displayTarget) {
		this.displayTarget = displayTarget;
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
	public int getAutoIncId() {
		return autoIncId;
	}
	public void setAutoIncId(int autoIncId) {
		this.autoIncId = autoIncId;
	}
	public String getIdFmDB() {
		return idFmDB;
	}
	public void setIdFmDB(String idFmDB) {
		this.idFmDB = idFmDB;
	}
	
	
	
}
