package com.thinkgem.jeesite.modules.sys.model;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.BaseModel;


/**
 * 下拉框model
 * @author chen_qiancheng
 * @since 2015-12-8
 *
 */
public class SysCodeListLevelThreeModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7759622700969778275L;

	private String text;//这是显示的文字
	private String text1;//这是更多一个值
	private String value;//这是值
	private List<SysCodeListLevelThreeModel> children;//这里和Data本身结构一样，即为下层数据
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public List<SysCodeListLevelThreeModel> getChildren() {
		return children;
	}
	public void setChildren(List<SysCodeListLevelThreeModel> children) {
		this.children = children;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
