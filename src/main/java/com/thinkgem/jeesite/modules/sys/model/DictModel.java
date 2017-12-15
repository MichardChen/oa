package com.thinkgem.jeesite.modules.sys.model;

import com.thinkgem.jeesite.common.persistence.BaseModel;

/**
 * 关注类型model
 * 
 * @author chen_qiancheng
 * @since 2015-12-8
 * 
 */
public class DictModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7759622700969778275L;
	private String value;
	private String label;
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
