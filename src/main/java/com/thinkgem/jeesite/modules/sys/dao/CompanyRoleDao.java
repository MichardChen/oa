/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.CompanyRole;
/**
 * 角色DAO接口
 * @author Meng
 * @version 2015-10-05
 */
@MyBatisDao
public interface CompanyRoleDao extends CrudDao<CompanyRole> {
	/**
	 * 根据用户来获取所在公司的所有权限id
	 * */
	public List<CompanyRole> getCompanyRoleList(String userId);
	
	/**
	 * 根据用户来获取所在公司的自己的权限id
	 * */
	public List<String> getCompanyRoleListByUser(String userId);
}
