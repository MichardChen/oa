/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWxqyDept;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface SysWxqyDeptDao extends TreeDao<SysWxqyDept> {
	
	/* 删除所有数据 
	 * */
	public int deleteAll();
	
	/**
	 * 增加全部数据。
	 * */
	public int insertAll(@Param("wxqyDeptList") List<SysWxqyDept> sysWxqyDept);
}
