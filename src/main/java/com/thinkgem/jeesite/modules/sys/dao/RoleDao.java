/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.model.RoleModel;

/**
 * 角色DAO接口
 * @author ThinkGem
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	//根据自增长ID查询数据
    public RoleModel findByAutoIncId(@Param("autoIncId")int autoIncId);
    
	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);
	
	//通过登陆者的id获得登录这的英文名称（enname）
	public String getEnnameByLoginId(String loginId);
	
	//获得所有的部门
	public List<RoleModel> getRoleList();
	
	//注册用户时获取角色ID
	public String findRoleIdByEnname(String enname);
	
	//获得除系统管理员外的所有角色
	public List<RoleModel> getRoleListForMember();
	
}
