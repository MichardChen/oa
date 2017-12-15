/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.model.OfficeModel;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	//通过登录者的id查找部门id和部门name
	public OfficeModel getOfficeModelByLoginId(String loginId);
	
	//通过自增长ID查询数据
	public Office findByAutoIncId(@Param("autoIncId")int autoIncId);
	
	public Office getByName(@Param("name")String name);
}
