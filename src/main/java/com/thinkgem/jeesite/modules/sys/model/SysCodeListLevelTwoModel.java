package com.thinkgem.jeesite.modules.sys.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.thinkgem.jeesite.common.persistence.BaseModel;


/**
 * 下拉框model
 * @author chen_qiancheng
 * @since 2015-12-8
 *
 */
public class SysCodeListLevelTwoModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7759622700969778275L;
	private String TypeCode;//当前数据所属的类型
	private String Layer;//层数，即数据共有几层的结构
	private List<SysCodeListLevelThreeModel> Data = new ArrayList<SysCodeListLevelThreeModel>();
	
	@JSONField(name="TypeCode")
	public String getTypeCode() {
		return TypeCode;
	}
	public void setTypeCode(String TypeCode) {
		this.TypeCode = TypeCode;
	}
	@JSONField(name="Layer")
	public String getLayer() {
		return Layer;
	}
	public void setLayer(String layer) {
		Layer = layer;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@JSONField(name="Data")
	public List<SysCodeListLevelThreeModel> getData() {
		return Data;
	}
	public void setData(List<SysCodeListLevelThreeModel> data) {
		Data = data;
	}
	
}
