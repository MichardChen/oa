package com.thinkgem.jeesite.modules.constructcost.member.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

/*
 * 参数设置model
 * @author ruanxuefei
 * 
 */
public class CcPrjParameterModel extends BaseModel {

	private static final long serialVersionUID = 6112157865956741878L;

	private String id;
	private int autoIncID;//自增长ID
	private String companyId;//会员ID
	private String projectId;//工程ID
	private String keyword;//自定义参数关键字    
	private String value1;//参数值1
	private String value2;//参数值2
	private String value3;//参数值3
	private String value4;//参数值4
	private String value5;//参数值5
	private String value6;//参数值6
	private String value7;//参数值7
	private String value8;//参数值8
	private String value9;//参数值9
	private String value10;//参数值10
	private String value11;//参数值11
	private String value12;//参数值12
	private String value13;//小数位数值
	private String idRealcost;//临时存储 keyword 为 “assingRole4Realcost” 数据的ID
	private String idIncome;//临时存储 keyword 为 “assingRole4Income” 数据的ID
	private String idPayment;//临时存储 keyword 为 “assingRole4Payment” 数据的ID
	private String value;
	private String label;
	public int getAutoIncID() {
		return autoIncID;
	}
	public void setAutoIncID(int autoIncID) {
		this.autoIncID = autoIncID;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
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
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public String getValue6() {
		return value6;
	}
	public void setValue6(String value6) {
		this.value6 = value6;
	}
	public String getValue7() {
		return value7;
	}
	public void setValue7(String value7) {
		this.value7 = value7;
	}
	public String getValue8() {
		return value8;
	}
	public void setValue8(String value8) {
		this.value8 = value8;
	}
	public String getValue9() {
		return value9;
	}
	public void setValue9(String value9) {
		this.value9 = value9;
	}
	public String getValue10() {
		return value10;
	}
	public void setValue10(String value10) {
		this.value10 = value10;
	}
	public String getValue11() {
		return value11;
	}
	public void setValue11(String value11) {
		this.value11 = value11;
	}
	public String getValue12() {
		return value12;
	}
	public void setValue12(String value12) {
		this.value12 = value12;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getValue13() {
		return value13;
	}
	public void setValue13(String value13) {
		this.value13 = value13;
	}
	public String getIdRealcost() {
		return idRealcost;
	}
	public void setIdRealcost(String idRealcost) {
		this.idRealcost = idRealcost;
	}
	public String getIdIncome() {
		return idIncome;
	}
	public void setIdIncome(String idIncome) {
		this.idIncome = idIncome;
	}
	public String getIdPayment() {
		return idPayment;
	}
	public void setIdPayment(String idPayment) {
		this.idPayment = idPayment;
	}
	

	
}
