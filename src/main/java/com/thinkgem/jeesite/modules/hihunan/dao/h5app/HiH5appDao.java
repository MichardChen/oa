package com.thinkgem.jeesite.modules.hihunan.dao.h5app;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.h5app.HiH5app;
import com.thinkgem.jeesite.modules.hihunan.model.h5app.HiH5appModel;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

@MyBatisDao
public interface HiH5appDao extends CrudDao<HiH5app>{
	
	public List<HiH5appModel> findTypeAll(HiH5app hiH5app);
	
	public List<HiH5appModel> getHeaderItem(HiH5app hiH5app);
	
	//取得首页列表题头及属性，本地生活除外
	public List<HiH5appModel> getListType(HiH5app hiH5app);
	
	//取得本地生活列表题头
	public List<HiH5appModel> getLocalListTitle(HiH5app hiH5app);
	
	//获取所有应用的下拉列表集合
	public List<Dict> getH5appList();

}
