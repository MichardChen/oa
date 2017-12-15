/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.dao.headershow;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.headershow.HiHeaderShow;
import com.thinkgem.jeesite.modules.hihunan.model.herdershow.HiHeaderShowModel;

/**
 * 头部轮播DAO接口
 * @author yuyabiao
 * @version 2016-11-30
 */
@MyBatisDao
public interface HiHeaderShowDao extends CrudDao<HiHeaderShow> {
	
	/**
	 * 查询全部轮播信息
	 * @param hiHeaderShow
	 * @return
	 */
	public List<HiHeaderShowModel> getAll(HiHeaderShow hiHeaderShow);
	
}