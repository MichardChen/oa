/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.dao.artical;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalRef;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;

/**
 * hiArticalRefDao接口
 * @author lin
 * @version 2016-12-3
 */
@MyBatisDao
public interface hiArticalRefDao extends CrudDao<HiArticalRef> {
	
	//批量添加文章引用关系
	public int addHiArticalMap(HiArticalRef hiArticalRef);
	
	//删除
	public int deleteHiArticalMap(HiArticalRef hiArticalRef);
	

	//获取商家关联的所有文章
	public List<HiArticalModel> getListByReferenceId(HiArticalRef hiArticalRef);
	
}