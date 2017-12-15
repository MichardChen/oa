package com.thinkgem.jeesite.modules.hihunan.model.artical;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class HiArticalDetailModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String filetype; // 文件类型
	private String name; // 名称
	private String content; // 文本
	private String fileUrl; // 文件路径
	private String seq; // 顺序
	private String articalId; // 所属文章id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getArticalId() {
		return articalId;
	}

	public void setArticalId(String articalId) {
		this.articalId = articalId;
	}

}
