/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.dao.artical;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalDetail;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalDetailModel;

/**
 * 文章明细管理DAO接口
 * 
 * @author llin
 * @version 2016-12-01
 */
@MyBatisDao
public interface HiArticalDetailDao extends CrudDao<HiArticalDetail> {

	/**
	 * 根据文章ID获取文章的具体内容
	 * 
	 * @param hiArticalDetail
	 * @return
	 */
	public List<HiArticalDetailModel> getByArticalId(
			HiArticalDetail hiArticalDetail);
}