/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.functiontype;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 栏位类别Entity
 * 
 * @author yuyabiao
 * @version 2016-11-29
 */
public class HiFunctionType extends DataEntity<HiFunctionType> {

	private static final long serialVersionUID = 1L;
	private int autoIncId; // autoincid
	private String name; // 名称
	private String icon; // 图标
	private String url; // 链接路径
	private String seq; // 顺序

	public HiFunctionType() {
		super();
	}

	public HiFunctionType(String id) {
		super(id);
	}

	@Length(min = 1, max = 11, message = "autoincid长度必须介于 1 和 11 之间")
	public int getAutoIncId() {
		return autoIncId;
	}

	public void setAutoIncId(int autoIncId) {
		this.autoIncId = autoIncId;
	}

	@Length(min = 0, max = 255, message = "名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 255, message = "图标长度必须介于 0 和 255 之间")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Length(min = 0, max = 255, message = "链接路径长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Length(min = 0, max = 11, message = "顺序长度必须介于 0 和 11 之间")
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}