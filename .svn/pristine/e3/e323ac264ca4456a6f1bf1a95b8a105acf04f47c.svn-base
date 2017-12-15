/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.dao.restaunant;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.restaunant.HiRestaunant;
import com.thinkgem.jeesite.modules.hihunan.model.restaunant.HiRestaunantModel;

/**
 * 美食DAO接口
 * @author lin
 * @version 2016-12-02
 */
@MyBatisDao
public interface HiRestaunantDao extends CrudDao<HiRestaunant> {
	//取得餐厅列表
	public List<HiRestaunantModel> getAll(HiRestaunant hiRestaunant);
	
	//取得餐厅详情
	public HiRestaunantModel getModel(HiRestaunant hiRestaunant);
	
	//取得推荐餐厅
	public List<HiRestaunantModel> getRecommondRestaunant(HiRestaunant hiRestaunant);
	
	//取得餐厅详情
	public HiRestaunantModel findRestaunant(HiRestaunant hiRestaunant);
	
	public HiRestaunantModel findRestaunantAndIsCollect(HiRestaunant hiRestaunant);
	
}