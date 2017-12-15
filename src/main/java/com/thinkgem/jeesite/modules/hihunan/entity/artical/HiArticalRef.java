/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.artical;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * hi_artical_ref
 * 
 * @author lin
 * @version 2016-12-3
 */
public class HiArticalRef extends DataEntity<HiArticalRef> {

	private static final long serialVersionUID = 1L;
	private String id;//
	private String autoIncId; // autoincid
	private String referenceId; // 引用ID
	private String articalId; // 文章ID
	public String getAutoIncId() {
		return autoIncId;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public String getArticalId() {
		return articalId;
	}
	public void setAutoIncId(String autoIncId) {
		this.autoIncId = autoIncId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public void setArticalId(String articalId) {
		this.articalId = articalId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

	
	

	
}