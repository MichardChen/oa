package com.thinkgem.jeesite.modules.hihunan.dao.listshow;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.listshow.HiListShow;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;

@MyBatisDao
public interface HiListShowDao extends CrudDao<HiListShow>{
	
	public List<HiListShowModel> findAll(HiListShow hiListShow);

}
