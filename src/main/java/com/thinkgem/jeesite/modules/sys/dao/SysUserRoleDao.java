package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysUserRole;
import com.thinkgem.jeesite.modules.sys.model.SysUserModel;

@MyBatisDao
public interface SysUserRoleDao  extends CrudDao<SysUserRole>{
	
	//获取角色对应的用户需要的微信消息信息
	public List<SysUserModel> findWeChatData(@Param("roles")List<String> roles);

}
