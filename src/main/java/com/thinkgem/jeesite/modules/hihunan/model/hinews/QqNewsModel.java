package com.thinkgem.jeesite.modules.hihunan.model.hinews;

import com.thinkgem.jeesite.common.persistence.BaseModel;

public class QqNewsModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String id;// 主键ID
	private Integer exist;// 标题
	private Integer comments;// 标题图片
	private Integer video_hits;// 地址
	private Integer hidepic;// 经度
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getExist() {
		return exist;
	}
	public void setExist(Integer exist) {
		this.exist = exist;
	}
	public Integer getComments() {
		return comments;
	}
	public void setComments(Integer comments) {
		this.comments = comments;
	}
	public Integer getVideo_hits() {
		return video_hits;
	}
	public void setVideo_hits(Integer video_hits) {
		this.video_hits = video_hits;
	}
	public Integer getHidepic() {
		return hidepic;
	}
	public void setHidepic(Integer hidepic) {
		this.hidepic = hidepic;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
