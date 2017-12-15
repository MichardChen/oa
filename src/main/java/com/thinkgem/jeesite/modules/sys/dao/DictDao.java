/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.model.h5app.HiH5appModel;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.model.DictModel;
import com.thinkgem.jeesite.modules.sys.model.SysCodeListLevelThreeModel;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<Dict> findTypeList(Dict dict);
	
	public List<String> findLabelList(@Param("type") String type);
	
	//根据数据类型查询数据
	public List<SysCodeListLevelThreeModel> getDataByType(String codeType);
	
	// 根据type来获取 key/value
	public List<DictModel> getListByCodeType(String codeType);
	
	//根据type来获取 key/value
	public List<Dict> getListByType(String type);
	
	//根据type来获取 key/value
	public List<HiH5appModel> getListByh5appType(String type);
	
}
