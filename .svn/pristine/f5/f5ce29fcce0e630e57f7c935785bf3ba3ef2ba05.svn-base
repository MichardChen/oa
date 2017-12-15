/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.tagref;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 标签关系Entity
 * @author yuyabiao
 * @version 2016-11-29
 */
public class SysTagRef extends DataEntity<SysTagRef> {
	
	private static final long serialVersionUID = 1L;
	private String tagType;		// 标签类型，最热，最新等
	private String referenceId;		// 被引用的栏目ID
	private int autoIncId;		// autoIncId
	private String referenceType;		// 被引用的栏目
	
	public SysTagRef() {
		super();
	}

	public SysTagRef(String id){
		super(id);
	}

	@Length(min=1, max=20, message="标签类型，最热，最新等长度必须介于 1 和 20 之间")
	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	
	@Length(min=1, max=64, message="被引用的栏目ID长度必须介于 1 和 64 之间")
	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	
	@NotNull(message="autoIncId不能为空")
	public int getAutoIncId() {
		return autoIncId;
	}

	public void setAutoIncId(int autoIncId) {
		this.autoIncId = autoIncId;
	}
	
	@Length(min=0, max=20, message="被引用的栏目长度必须介于 0 和 20 之间")
	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	
}