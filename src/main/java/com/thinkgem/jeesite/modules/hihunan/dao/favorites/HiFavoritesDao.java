package com.thinkgem.jeesite.modules.hihunan.dao.favorites;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.favorites.HiFavorites;

@MyBatisDao
public interface HiFavoritesDao extends CrudDao<HiFavorites>{
	
	public String getInfo(HiFavorites hiFavorites);

}
