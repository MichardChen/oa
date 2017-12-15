/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.entity.artical;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 文章明细管理Entity
 * @author llin
 * @version 2016-12-01
 */
public class HiArticalDetail extends DataEntity<HiArticalDetail> {
	
	private static final long serialVersionUID = 1L;
	private String autoincid;		// autoincid
	private String fileType;		// 文件类型
	private String name;		// 名称
	private String content;		// 文本
	private String fileUrl;		// 文件路径
	private String seq;		// 顺序
	private String articalId;		// 所属文章id
	
	public HiArticalDetail() {
		super();
	}

	public HiArticalDetail(String id){
		super(id);
	}

	@Length(min=1, max=11, message="autoincid长度必须介于 1 和 11 之间")
	public String getAutoincid() {
		return autoincid;
	}

	public void setAutoincid(String autoincid) {
		this.autoincid = autoincid;
	}
	
	@Length(min=0, max=20, message="文件类型长度必须介于 0 和 20 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Length(min=0, max=20, message="名称长度必须介于 0 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=10000, message="文本长度必须介于 0 和 10000之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=255, message="文件路径长度必须介于 0 和 255 之间")
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	@Length(min=0, max=11, message="顺序长度必须介于 0 和 11 之间")
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	@Length(min=0, max=64, message="所属文章id长度必须介于 0 和 64 之间")
	public String getArticalId() {
		return articalId;
	}

	public void setArticalId(String articalId) {
		this.articalId = articalId;
	}
	
}